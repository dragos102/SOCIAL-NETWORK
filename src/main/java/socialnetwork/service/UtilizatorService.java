package socialnetwork.service;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.html.table.Table;
import javafx.scene.shape.VLineTo;
import jdk.nashorn.internal.runtime.ListAdapter;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.MesajeValidator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.EvenimenteDBRepository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class UtilizatorService {
    private Repository<Long, Utilizator> repoUtilizatori;
    private Repository<Long, Message> repoMesaje;
    private Repository<Long, Message> repoCereri;
    private EvenimenteDBRepository repoEvenimente;
    private Repository<Tuple<Long, Long>, Prietenie> repoPrietenie;
    private Long cont = 0L;
    private Long cont2 = 0L;
    private Long cont3=0L;

    public UtilizatorService(Repository<Long, Utilizator> repo, Repository<Tuple<Long, Long>, Prietenie> repo2, Repository<Long, Message> repoMesaje, Repository<Long, Message> repoCereri,EvenimenteDBRepository repoEvenimente) {
        this.repoEvenimente=repoEvenimente;
        this.repoUtilizatori = repo;
        this.repoPrietenie = repo2;
        this.repoMesaje = repoMesaje;
        this.repoCereri = repoCereri;
        add_allfriends();
        numerate();
    }


    /**
     * this function counts the number of objects that exists in repoMesaje and RepoCereri,from their files
     */
    public void numerate() {
        repoMesaje.findAll().forEach(x ->
        {
            cont++;
        });
        repoCereri.findAll().forEach(x ->
        {
            cont2++;
            if(x.getId()>cont2)
            {
                cont2=x.getId();
            }
        });
        getallevents().forEach(x->
        {

            cont3++;
            if(x.getId()>cont3)
            {
                cont3=x.getId();
            }
        });
    }



    /**
     * this function add to the each user friends list the friend using the file that shows the friendshipp from file
     */
    public void add_allfriends() {
       /* repoPrietenie.findAll().forEach(x ->
        {
            Utilizator first = repoUtilizatori.findOne(x.getId().getLeft());
            Utilizator second = repoUtilizatori.findOne(x.getId().getRight());
            first.addFriend(second);
            second.addFriend(first);
        });*/
    }


    /**
     * this function add a user to the network
     *
     * @param messageTask
     * @return
     */
    public Utilizator addUtilizator(Utilizator messageTask) {
        Utilizator task = repoUtilizatori.save(messageTask);
        return task;
    }


    /**
     * this function returns all the users from the network
     *
     * @return
     */
    public Iterable<Utilizator> getAll() {
        return repoUtilizatori.findAll();
    }


    /**
     * this function delete a user from network
     *
     * @param id-the user's id which will be deleted
     */
    public void delete_user(Long id) {

            Predicate<Prietenie> verify = x -> x.getId().getRight() == id;
            Predicate<Prietenie> verify2 = x -> x.getId().getLeft() == id;
            List<Tuple<Long,Long>> lista1=new ArrayList<>();

            repoPrietenie.findAll().forEach(x ->
            {
                if (verify.test(x) == true) {
                    Long abc = x.getId().getLeft();
                    Long abd = x.getId().getRight();
                    Tuple<Long,Long> a=new Tuple<>(abc,abd);
                    lista1.add(a);

                }
                if (verify2.test(x) == true) {
                    Long abc = x.getId().getLeft();
                    Long abd = x.getId().getRight();
                    Tuple<Long,Long> a=new Tuple<>(abc,abd);
                    lista1.add(a);
                }
            });
        lista1.stream()
                .forEach(x->
                {
                    try {
                        delete_friendship(x.getLeft(), x.getRight());
                    }catch (Exception e)
                    {

                    }
                });
        repoUtilizatori.delete(id);
    }


    /**
     * this function add a friendship between two users from network
     *
     * @param id1-the first user's id
     * @param id2-the second user's id
     */
    public void add_friendship(Long id1, Long id2) {
        Tuple tup = new Tuple(id1, id2);

        Utilizator u1 = repoUtilizatori.findOne(id1);
        Utilizator u2 = repoUtilizatori.findOne(id2);

        if (u1 == null)
            throw new ValidationException("no user with id1...");
        if (u2 == null)
            throw new ValidationException("no user with id2...");

        if (u1 == u2)
            throw new ValidationException("can not make friends with himself");
        if (repoPrietenie.findOne(new Tuple(id1, id2)) != null)
            throw new ValidationException("this friendship already exists: \n" + repoPrietenie.findOne(new Tuple(id1, id2)));
        if (repoPrietenie.findOne(new Tuple(id2, id1)) != null)
            throw new ValidationException("this friendship already exists: \n" + repoPrietenie.findOne(new Tuple(id2, id1)));
        u1.addFriend(u2);
        u2.addFriend(u1);
        Prietenie p = new Prietenie();
        p.setId(tup);
        repoPrietenie.save(p);
    }


    /**
     * this function returns all the friendships that exist in our project
     *
     * @return all the friendships
     */
    public Iterable<Prietenie> get_prietenii() {
        return repoPrietenie.findAll();
    }
    public Iterable<Message> get_cereri() {
        return repoCereri.findAll();
    }
    public Iterable<Message> get_mesaje(){
        return repoMesaje.findAll();
    }


    /**
     * this function deletes a friendship between two users
     *
     * @param id1-the id of the first user
     * @param id2-the id of the second user
     */
    public void delete_friendship(Long id1, Long id2) {
        Utilizator uwantdell = repoUtilizatori.findOne(id1);
        Utilizator udeleted = repoUtilizatori.findOne(id2);

        if (uwantdell == null)
            throw new ValidationException("no user with id1");
        if (udeleted == null)
            throw new ValidationException("no user with id2");

        repoPrietenie.delete(new Tuple(id1, id2));
        uwantdell.remove_Friend(udeleted);
        udeleted.remove_Friend(uwantdell);


    }


    /**
     * this function prints the friends of a user
     *
     * @param id-the user's id
     */
    public void print_friendsforuser(Long id) {
        Utilizator unu = repoUtilizatori.findOne(id);
        if (unu == null) {
            throw new ValidationException("THIS ID DOESN'T EXIST");
        }
        unu.getFriends()
                .stream()
                .forEach(x ->
                {
                    System.out.println("The name is: " + x.getFirstName() + "  The second name is: " + x.getLastName() + "  The date is: ");
                    Tuple a = new Tuple(unu.getId(), x.getId());
                    Prietenie doi = repoPrietenie.findOne(a);
                    if (doi != null) {
                        System.out.println(doi.getDate() + "\n");
                    }
                    else{
                    Tuple b = new Tuple(x.getId(), unu.getId());
                    Prietenie trei = repoPrietenie.findOne(b);
                    if (trei != null) {
                        System.out.println(trei.getDate() + "\n");
                    }}
                });

        /*
        !Implementation using lambda ,but not .stream() !
        Predicate<Prietenie> pred=x->x.getId().getLeft()==unu.getId();
        Predicate<Prietenie> pred2=x->x.getId().getRight()==unu.getId();

        repoPrietenie.findAll().forEach(x->
        {
            if(pred.test(x)==true)
            {
                Utilizator copie=repoUtilizatori.findOne(x.getId().getRight());
                System.out.println("Numele este :"+copie.getFirstName()+" Data prieteniei este: "+x.getDate()+'\n');
            }
            if(pred2.test(x)==true)
            {
                Utilizator copie=repoUtilizatori.findOne(x.getId().getLeft());
                System.out.println("Numele este :"+copie.getFirstName()+" Data prieteniei este: "+x.getDate()+'\n');
            }
        });*/

    }


    /**
     * this function prints the friends of a user,and verify that their friendship was made in a month
     *
     * @param id-the    user's id
     * @param month-the month when the friendship was done
     */
    public void print_friendsforuser_month(Long id, String month) {

        Utilizator nou = repoUtilizatori.findOne(id);
        if (nou == null) {
            throw new ValidationException("THIS ID DOESN'T EXIST");
        }
        final String month1 = month;
        nou.getFriends()
                .stream()
                .filter(x -> {
                    Tuple fi = new Tuple(id, x.getId());
                    Prietenie a = repoPrietenie.findOne(fi);
                    if(a!=null) {
                        return a.getDate().getMonth().toString().equals(month);
                    }
                    return false;
                })
                .forEach(x ->
                {
                    Utilizator unu = repoUtilizatori.findOne(id);
                    System.out.println("The name is: " + x.getFirstName() + "  The second name is: " + x.getLastName() + "  The date is: ");
                    Tuple a = new Tuple(unu.getId(), x.getId());
                    Prietenie doi = repoPrietenie.findOne(a);
                    if(doi!=null) {
                        System.out.println(doi.getDate() + "\n");
                    }
                });
        /*
        here it is not necessary becouse databese search for both pairs (unu.getId(),x.getId()) and (x.getId(),unu.getId())
        nou.getFriends()
                .stream()
                .filter(x -> {
                    Tuple fi = new Tuple( x.getId(),id);
                    Prietenie a = repoPrietenie.findOne(fi);
                    if(a!=null) {
                        return a.getDate().getMonth().toString().equals(month);
                    }
                    return false;
                })
                .forEach(x ->
                {
                    Utilizator unu = repoUtilizatori.findOne(id);
                    System.out.println("The name is: " + x.getFirstName() + "  The second name is: " + x.getLastName() + "  The date is: ");
                    Tuple a = new Tuple( x.getId(),unu.getId());
                    Prietenie doi = repoPrietenie.findOne(a);
                    if(doi!=null) {
                        System.out.println(doi.getDate() + "\n");
                    }
                });
                */

    }



    /**
     * this function creates a conversation that will be send to 1 or many users
     * @param id1-the user's id that will send the conversation
     * @param lista-the user's ids that will receive the conversation
     * @param message-the message of the conversation between users
     */
    public void create_conversation(Long id1, List<Long> lista, String message) {
       /* Utilizator unu=repoUtilizatori.findOne(id1);
        Utilizator doi=repoUtilizatori.findOne(id2);
        if(unu==null)
        {
            throw new ValidationException("The first id is not in our list");
        }
        if(doi==null)
        {
            throw new ValidationException("The second id is not in our list");
        }
        LocalDateTime now=LocalDateTime.now();
        cont++;
        System.out.println(cont);
        Message mesaj=new Message(id1,id2,message,now);
        mesaj.setId(cont);
        repoMesaje.save(mesaj);*/


        if (repoUtilizatori.findOne(id1) == null) {
            throw new ValidationException("First id doesn't exist");
        }
        List<Utilizator> listaUt = new ArrayList<Utilizator>();
        lista.stream().forEach(x ->
        {
            Utilizator a = repoUtilizatori.findOne(x);
            if (a != null) {
                a.setId(x);
                listaUt.add(a);
            } else {
                throw new ValidationException("the id " + x + " doesn't exist");
            }
        });

        cont++;
        Message mesaj = new Message(id1, listaUt, message, LocalDateTime.now());
        mesaj.setId(cont);
        repoMesaje.save(mesaj);
    }





    public void reply_conversation(Long id_user, Long id_conversation,String reply) {
        if(repoMesaje.findOne(id_conversation)==null)
        {
            throw new ValidationException("the id of the conversation doesn't exist");
        }
        List<ReplyMessage> lista=new ArrayList<>();
        repoMesaje.findAll().forEach(x->
        {
            if(x.getId()==id_conversation)
            {
                Utilizator dd=new Utilizator("","");
                dd.setId(id_user);
                List<Long> numarare=new ArrayList<Long>();
                x.getTo().stream().forEach(y->
                {
                    if(y.getId()==id_user)
                    {
                        numarare.add(1L);
                    }
                });
                if(x.getFrom()==id_user)
                {
                    cont++;
                    Message nou = new Message(id_user, x.getTo(), x.getMessage(), x.getDate());
                    ReplyMessage nou2 = new ReplyMessage(nou, reply);
                    nou2.setId(cont);
                    lista.add(nou2);
                }
                else {
                    if (numarare.size() == 0) {
                        System.out.println(x.getTo() + " ; " + dd);
                        throw new ValidationException("the conversation wasn't for that user");
                    }
                    cont++;
                    x.getTo().removeIf(y -> (y.getId() == id_user));
                    System.out.println(x.getTo() + ";" + dd);
                    Utilizator a = repoUtilizatori.findOne(x.getFrom());
                    x.getTo().add(a);
                    Message nou = new Message(id_user, x.getTo(), x.getMessage(), x.getDate());
                    ReplyMessage nou2 = new ReplyMessage(nou, reply);
                    nou2.setId(cont);
                    lista.add(nou2);
                }
            }
        });
        lista.stream().forEach(x->
                repoMesaje.save(x));

    }




    /**
     * this function show the conversation between two users
     * @param id1-the first user's id
     * @param id2-the second user's id
     */
    public void print_conversation(Long id1, Long id2) {
        Utilizator unu1 = repoUtilizatori.findOne(id1);
        Utilizator unu=new Utilizator("","");
        unu.setId(id1);
        Utilizator doi2 = repoUtilizatori.findOne(id2);
        Utilizator doi=new Utilizator("","");
        doi.setId(id2);
        if (unu == null) {
            throw new ValidationException("the first id doesn't exist");
        }
        if (doi == null) {
            throw new ValidationException("the second id doesn't exist");
        }
        repoMesaje.findAll().forEach(x ->
        {
            List<Integer> listaex =new ArrayList<>();
            x.getTo().stream().forEach(z->
            {
                if(z.getId()==id2)
                {
                    listaex.add(1);
                }
            });
            List<Integer> listaex2 =new ArrayList<>();
            x.getTo().stream().forEach(z->
            {
                if(z.getId()==id1)
                {
                    listaex2.add(1);
                }
            });
            List<Long> copie=new ArrayList<>();
            x.getTo().forEach(y->
            {
                copie.add(y.getId());
            });
            if ((x.getFrom() == id1 && listaex.size()>0) || (x.getFrom() == id2 && listaex2.size()>0)) {
                System.out.println(x.getFrom() + " ; " +"Utilizator ul/ii"+copie + " ; " + x.getMessage() + " ; " + x.getDate() + "\n");
            }
        });
    }



    /**
     * this function send a friend request to a list of users
     * @param id1 the user's id that will send the friend request
     * @param lista-the user's ids that will receive the friend reques
     * @param message-the message that shows the status of the reques accepted/rejected/pending
     */

    public void add_friend(Long id1, List<Long> lista, String message) {
        Utilizator a = repoUtilizatori.findOne(id1);
        if (a == null) {
            throw new ValidationException("your id doesn't exist");
        }
        lista.stream().forEach(x ->
        {
            if (repoUtilizatori.findOne(x) == null) {
                throw new ValidationException("one id from the list doesn't exist");
            }
            repoPrietenie.findAll().forEach(y->
            {
                if((y.getId().getRight()==x && y.getId().getLeft()==id1) ||( y.getId().getLeft()==x && y.getId().getRight()==id1))
                {
                    throw new ValidationException("a friend between two users already exists");
                }
            });
            repoCereri.findAll().forEach(y->
            {

                if(y.getFrom()==id1)
                {
                    y.getTo().stream().forEach(z->
                    {
                        if(z.getId()==x)
                        {
                            throw new ValidationException("a request which include two of the users already exists");
                        }
                    });


                }
            });
        });
        List<Utilizator> listafinala = new ArrayList<Utilizator>();
        lista.stream().forEach(x ->
        {
            Utilizator doi = repoUtilizatori.findOne(x);
            listafinala.add(doi);
        });
        Message mess = new Message(id1, listafinala, message, LocalDateTime.now());
        cont2++;
        mess.setId(cont2);
        repoCereri.save(mess);
    }


    /**
     *
     * @param mesaj
     * @param id_user
     */
    public void accept_cerere(Message mesaj,Long id_user)
    {
        mesaj.add_mesage(" -> + "+id_user+" ->added");
        add_friendship(mesaj.getFrom(),id_user);
        List<Utilizator> listaa = new ArrayList<Utilizator>();
        mesaj.getTo().stream().forEach(y ->
        {
            if (y.getId() == id_user) {
                listaa.add(y);
            }
        });
        mesaj.delete_from_to(listaa.get(0));
        if(mesaj.getTo().isEmpty())
        {
            mesaj.add_mesage("->final");
        }
        repoCereri.update(mesaj);
    }
    public void delete_cerere(Message mesaj,Long id_user)
    {
        mesaj.add_mesage(" ->"+id_user+" ->refuzed");
        List<Utilizator> listaa = new ArrayList<Utilizator>();
        mesaj.getTo().stream().forEach(y ->
        {
            if (y.getId() == id_user) {
                listaa.add(y);
            }
        });
        mesaj.delete_from_to(listaa.get(0));
        if(mesaj.getTo().isEmpty())
        {
            mesaj.add_mesage("->final");
        }
        repoCereri.update(mesaj);
    }
    public void accept_refuze(Long id) {
        if (repoUtilizatori.findOne(id) == null) {
            throw new ValidationException("this id doesn't exist");
        }
        /*repoCereri.findAll().forEach(x ->
        {
            if (x.getcatre() == id && x.getMessage() == "pending") {

                System.out.println("you have received a nev friend request from: " + x.getFrom() + " :choose what to do with it \n");
                String todo;
                Scanner scan = new Scanner(System.in);
                todo = scan.nextLine();
                System.out.println(todo);
                if (todo.equals("accept")) {

                    x.add_mesage(":->accepted");
                    add_friendship(x.getFrom(), x.getcatre());
                    repoCereri.update(x);

                } else {
                    if (todo.equals("reject")) {
                        x.add_mesage("->rejected");
                        repoCereri.update(x);
                    }
                }


            }
        });*/

        Utilizator unu=new Utilizator("","");
        unu.setId(id);
        //x.getTo().contains(unu)
        repoCereri.findAll().forEach(x->
        {
            List<Integer> listaex =new ArrayList<>();
            x.getTo().stream().forEach(z->
            {
                if(z.getId()==id)
                {
                    listaex.add(1);
                }
            });
            if(listaex.size()>0)
            {
                System.out.println("you have a friend request from user: "+x.getFrom()+" you want to accept/refuze or let him/her wait?");
                Scanner scan=new Scanner(System.in);
                String th=scan.next();
                if(th.equals("accept")) {
                    System.out.println("you have a new friendship");

                    x.add_mesage(" ->"+ unu.getId()+"->added");
                    add_friendship(x.getFrom(), unu.getId());
                    List<Utilizator > listaa=new ArrayList<Utilizator>();
                    x.getTo().stream().forEach(y->
                    {
                        if(y.getId()==id)
                        {
                            listaa.add(y);
                        }
                    });
                    x.delete_from_to(listaa.get(0));
                    repoCereri.update(x);
                }
                if(th.equals("refuze"))
                {
                    System.out.println("the friend request was rejected");

                    x.add_mesage(" ->"+ unu.getId()+"->refuzed");
                    List<Utilizator > listaa=new ArrayList<Utilizator>();
                    x.getTo().stream().forEach(y->
                    {
                        if(y.getId()==id)
                        {
                            listaa.add(y);
                        }
                    });
                    x.delete_from_to(listaa.get(0));
                    repoCereri.update(x);
                }
            }

        });
        repoCereri.findAll().forEach(x->
        {
            if(x.getTo().size()==0 && x.getMessage().contains("final")==false)
            {
                x.add_mesage("->final");
                repoCereri.update(x);
            }
        });

    }



    /**
     * this function show the requests in pending for a user
     * @param id-the user's id
     */

    public void print_requests(Long id)
    {
        Utilizator unu = new Utilizator("","");
        unu.setId(id);
        repoCereri.findAll().forEach(x->
        {
            if(x.getTo().contains(unu))
            {
                System.out.println(x.getFrom());
            }
        });
    }


    public void delete_cerere(Long id)
    {
        List<Integer> a=new ArrayList<Integer>();
        repoCereri.findAll().forEach(x->
        {
            if(x.getId()==id && x.getMessage().equals("pending")==true)
            {
                a.add(1);
            }
        });
        if(a.size()==0)
        {
            throw new ValidationException("error check the status to be pending nothing more");
        }
        repoCereri.delete(id);

    }



    /**
     *
     * @param d1
     * @param m1
     * @param y1
     * @param d2
     * @param m2
     * @param y2
     */
    public void friendships_messages_period(Long id,int d1,int m1,int y1,int d2,int m2,int y2) {
        if(y1>y2 || (y1==y2 && m1>m2) || (y1==y2  && m1==m2 && d1>d2) )
        {
            throw new ValidationException("please be careful second date must be older than first date");
        }
        if(m1>12 || d1 >31 || m2 >12 || d2>31)
        {
            throw new ValidationException("incorect data");
        }
        List<Prietenie> lista = new ArrayList<Prietenie>();
        repoPrietenie.findAll().forEach(x ->
        {
            if (x.getId().getRight() == id || x.getId().getLeft() == id) {
                if ((x.getDate().getDayOfMonth() >= d1 && x.getDate().getDayOfMonth() <= d2) || x.getDate().getMonthValue() < m2 || x.getDate().getYear()<y2) {

                    if (x.getDate().getMonthValue() >= m1 && x.getDate().getMonthValue() <= m2 || (x.getDate().getYear()<y2)) {

                        if (x.getDate().getYear() >= y1 && x.getDate().getYear() <= y2) {
                            System.out.println(3);
                            lista.add(x);
                        }
                    }
                }
            }
        });
        List<Message> listames = new ArrayList<Message>();
        repoMesaje.findAll().forEach(x ->
        {
            x.getTo().stream().forEach(y ->
            {
                if (y.getId() == id) {
                    if ((x.getDate().getDayOfMonth() >= d1 && x.getDate().getDayOfMonth() <= d2) || x.getDate().getMonthValue() < m2 || x.getDate().getYear()<y2) {
                        if (x.getDate().getMonthValue() >= m1 && x.getDate().getMonthValue() <= m2 || (x.getDate().getYear()<y2)) {

                            if (x.getDate().getYear() >= y1 && x.getDate().getYear() <= y2) {
                                System.out.println(3);
                                listames.add(x);
                            }
                        }
                    }
                }
            });
        });


        try {
            com.itextpdf.text.Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream("MyPDF1.pdf"));

            document.open();
            Paragraph paragraph = new Paragraph("-------NEW FRIENDS FOR USER --"+id +" in period "+d1+"/"+m1+"/"+y1+"--"+d2+"/"+m2+"/"+y2+"---");
            document.add(paragraph);
            PdfPTable table = new PdfPTable(2);
            table.addCell("id");
            table.addCell("data");
            lista.forEach(y->
            {
                table.addCell(y.getId().toString());
                table.addCell(y.getDate().toString());
            });
            document.add(table);
            document.add(new Paragraph("  "));
            document.add(new Paragraph("-------NEW MESSAGES FOR USER --"+id +" in period "+d1+"/"+m1+"/"+y1+"--"+d2+"/"+m2+"/"+y2+"---"));
            PdfPTable table2 = new PdfPTable(4);
            table2.addCell("from");
            table2.addCell("to");
            table2.addCell("message");
            table2.addCell("data");
            listames.forEach(y->
            {
               table2.addCell(y.getFrom().toString());
               table2.addCell(y.getListaId().toString());
               table2.addCell(y.getMessage());
               table2.addCell(y.getDate().toString());
            });
            document.add(table2);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }



    }
    public void get_messages_from_a_friend(Long id,Long id2,int d1,int m1,int y1,int d2,int m2,int y2)
    {
        List<Message> listanoua=new ArrayList<Message>();
        Tuple a=new Tuple(id,id2);
        Tuple b=new Tuple(id2,id);
        /*if(repoPrietenie.findOne(a)==null && repoPrietenie.findOne(b)==null)
        {
            throw new ValidationException("the friendship doesn't exist");
        }*/
        if(y1>y2 || (y1==y2 && m1>m2) || (y1==y2  && m1==m2 && d1>d2) )
        {
            throw new ValidationException("please be careful second date must be older than first date");
        }
        if(m1>12 || d1 >31 || m2 >12 || d2>31)
        {
            throw new ValidationException("incorect data");
        }
        repoMesaje.findAll().forEach(x->
        {
            if(x.getFrom()==id2)
            {
                x.getTo().stream().forEach(y->{
                    if(y.getId()==id)
                    {
                        if ((x.getDate().getDayOfMonth() >= d1 && x.getDate().getDayOfMonth() <= d2) || x.getDate().getMonthValue() < m2 || x.getDate().getYear()<y2) {
                            if (x.getDate().getMonthValue() >= m1 && x.getDate().getMonthValue() <= m2 || (x.getDate().getYear()<y2)) {

                                if (x.getDate().getYear() >= y1 && x.getDate().getYear() <= y2) {
                                    System.out.println(3);
                                    listanoua.add(x);
                                }
                            }
                        }
                    }
                });
            }
        });

        try {
            com.itextpdf.text.Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream("MyPDF2.pdf"));

            document.open();
            Paragraph paragraph = new Paragraph("-------NEW MESSAGES FOR USER --"+id +" from user "+id2 +" in period  "+d1+"/"+m1+"/"+y1+"--"+d2+"/"+m2+"/"+y2+"---");
            document.add(paragraph);
            PdfPTable table = new PdfPTable(4);
            table.addCell("from");
            table.addCell("to");
            table.addCell("message");
            table.addCell("data");
            listanoua.forEach(y->
            {
                table.addCell(y.getFrom().toString());
                table.addCell(y.getListaId().toString());
                table.addCell(y.getMessage());
                table.addCell(y.getDate().toString());
            });
            document.add(table);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void add_eveniment(Eveniment nou)
    {

        cont3++;
        nou.setId(cont3);
        repoEvenimente.save(nou);
    }
    public Iterable<Eveniment> getallevents()
    {
        return repoEvenimente.findAll();
    }

    public void add_abonare(Utilizator unu,Eveniment doi)
    {

        repoEvenimente.add_abonari(unu,doi);
    }
    public int check_abonare(Utilizator unu,Eveniment doi)
    {
        return repoEvenimente.find_abonare(unu,doi);
    }
    public List<Long> find_all_abonari(Utilizator unu)
    {
        return repoEvenimente.find_all_abonari(unu);
    }
    public Eveniment find_eveniment(Long unu)
    {
        return repoEvenimente.findOne(unu);
    }
    public void delete_abonare(Utilizator unu,Eveniment doi)
    {
        repoEvenimente.delete_abonare(unu,doi);
    }
}
