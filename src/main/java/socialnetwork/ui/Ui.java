package socialnetwork.ui;

import com.sun.media.sound.SoftTuning;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.service.UtilizatorService;
import socialnetwork.domain.validators.ValidatorUi;

import java.nio.file.LinkOption;
import java.util.*;
import java.util.Scanner;
public class Ui {
    private UtilizatorService srv;
    private Scanner scanner = new Scanner(System.in);
    private ValidatorUi valUi = new ValidatorUi();

    public Ui(UtilizatorService srv)
    {
        this.srv = srv;
    }


    /**
     * this function print the initial menu
     */
    public void printMenu(){
        System.out.println("cmd for app (press)");
        System.out.println("add user: 1");
        System.out.println("print list : 2 ");
        System.out.println("delete user: 3 ");
        System.out.println("add friends for a user: 4");
        System.out.println("unfriend someone 5:");
        System.out.println("print friend lists 6: ");
        System.out.println("print the friends for a user 7:");
        System.out.println("print the friends for a user checking the month 8:");
        System.out.println("to write a conversation to a user choose 9:");
        System.out.println("to reply to a conversation choose 10:");
        System.out.println("to print all the conversation of two users press 11:");
        System.out.println("to make a friend request 12:");
        System.out.println("to accept or refuze a friend request press 13:");
        System.out.println("to print all the requests for a user press 14:");
        System.out.println("to print all the new friends and messages received in a period press 15:");
        System.out.println("to print all the messages of a user received by a friend in a period press 16:");
        System.out.println("exit:0");

    }


    /**
     * this function read the dates of a user and add it to the list of users
     */
    public void addUser()
    {
        String nume,prenume;
        String id;



        System.out.println("Insert first name:");
        prenume = scanner.next();
        valUi.validate_name(prenume);

        System.out.println("Insert last name:");
        nume = scanner.next();
        valUi.validate_name(nume);

        System.out.println("insert ID:");
        id = scanner.next();
        valUi.validate_id(id);

        Utilizator ut1 = new Utilizator(prenume,nume);

        // pot sa ii dau deja asa pentru ca deja e validat ca si long
        ut1.setId(Long.parseLong(id));

        srv.addUtilizator(ut1);
        System.out.println("user added");

    }


    /**
     * this function reads the dates about a user and validate them and call the service to delete it
     */
    public void delete_user()
    {
        System.out.println("insert ID for del:");
        String id;
        id = scanner.next();
        valUi.validate_id(id);

        srv.delete_user(Long.parseLong(id));
    }


    /**
     * this function print the dates from the list of users
     */
    public void print_list(){

        srv.getAll().forEach(System.out::println);
    }


    /**
     * this function print the friendlist
     */
    public void print_friendlist()
    {
        srv.get_prietenii().forEach(System.out::println);

    }


    /**
     * this function read the dates about users ,validate them and after call the service to add the friendship
     */
    public void add_friendship()
    {

        String id1,id2;
        System.out.println("insert first friend id: ");
        id1 = scanner.next();
        valUi.validate_id(id1);
        System.out.println("insert second friend id: ");
        id2 = scanner.next();
        valUi.validate_id(id2);


        srv.add_friendship(Long.parseLong(id1),Long.parseLong(id2));
        System.out.println("friend added");
    }


    /**
     * this function delete a friendship calling the service
     * it read the data about two users ,validate them and after call the service to delete the friendship
     */
    public void delete_friendship()
    {
        String id1,id2;
        System.out.println("id for person who want to delete a friend: ");
        id1 = scanner.next();
        valUi.validate_id(id1);
        System.out.println("id of the person ID1 want to unfriend: ");
        id2 = scanner.next();
        valUi.validate_id(id2);

        srv.delete_friendship(Long.parseLong(id1),Long.parseLong(id2));
        System.out.println("unfriended");

    }


    /**
     * this function read the data about 1 user and after call the service to print it's friends
     */
    public void print_friendsforuser()
    {
        String id;
        System.out.println("Enter the id for that user: ");
        id=scanner.next();
        valUi.validate_id(id);

        srv.print_friendsforuser(Long.parseLong(id));
    }

    /**
     * this function print the friend of a user calling the service
     * it read the data about the users,validate them and after call service to print the users
     */
    public void print_friendsforuser_month()
    {
        String id,month;
        System.out.println("Enter the id for that user: ");
        id=scanner.next();
        System.out.println("Enter the month");
        month=scanner.next();
        valUi.validate_id(id);
        valUi.validate_month(month);
        srv.print_friendsforuser_month(Long.parseLong(id),month.toUpperCase());
    }

    /**
     * this function read the data about the users,the message and after call the create a conversation
     */
    public void create_conversation()
    {
        String id1,id2;
        System.out.println("Enter the ID of the first person: ");
        id1=scanner.next();
        System.out.println("Enter the numbers of persons which will receive the message: ");
        String number;
        number=scanner.next();
        valUi.validate_id(number);
        List<Long> lista=new ArrayList<Long>();
        int val= Integer.parseInt(number);
        String fin="";
        while(val>0)
        {
            System.out.println("ENTER THE ID OF THE PERSON");
            String poz=scanner.next();
            valUi.validate_id(poz);
            lista.add(Long.parseLong(poz));
            val--;
        }
        System.out.println("enter the conversation: ");
        String text;
        Scanner scanner11 = new Scanner(System.in);
        text= scanner11.nextLine();
        srv.create_conversation(Long.parseLong(id1), lista,text);
    }

    /**
     * this function read the data about users,validate them and after call the service to reply to the conversation
     */
    public void reply_conversation()
    {
        String id1,id2;
        System.out.println("Enter your id:");
        id1=scanner.next();

        System.out.println("enter the id  conversation: ");
        id2=scanner.next();

        Scanner scan=new Scanner(System.in);
        System.out.println("enter the reply:");
        String text=scan.nextLine();
        srv.reply_conversation(Long.parseLong(id1),Long.parseLong(id2),text);
    }

    /**
     * this function print the conversation between 2 users
     * it read their dates,validate them and afte call the service to print the conversation
     */
    public void print_conversation()
    {
        String id1,id2;
        System.out.println("Enter the ID of the first person : ");
        id1=scanner.next();
        System.out.println("Enter the ID of the second person: ");
        id2=scanner.next();
        valUi.validate_id(id1);
        valUi.validate_id(id2);
        srv.print_conversation(Long.parseLong(id1),Long.parseLong(id2));
    }

    /**
     * this function create a friendrequest
     * it reads the data about users,validate them and after call the service to create a friendrequest
     */
    public void add_friend()
    {
        String id1,id2;
        System.out.println("Enter your personal ID : ");
        id1=scanner.next();
        System.out.println("Enter the number of the person that you want to send requests and after their id's: ");
        String number;
        number=scanner.next();
        valUi.validate_id(number);
        List<Long> lista=new ArrayList<Long>();
        int val= Integer.parseInt(number);
        while(val>0)
        {
            System.out.println("ENTER THE ID OF THE PERSON");
            String poz=scanner.next();
            valUi.validate_id(poz);
            lista.add(Long.parseLong(poz));
            val--;
        }
        String text="pending";
        srv.add_friend(Long.parseLong(id1),lista,text);
    }

    /**
     * this fucntion accept a friend request or delete it or let it pending
     * it reads the user's id and after call the service
     */
    public void accept_refuze()
    {
        String id1;
        System.out.println("Enter your personal ID : ");
        id1=scanner.next();
        srv.accept_refuze(Long.parseLong(id1));
    }

    /**
     * this function print the request for a user
     * it reads the user's id and after call the service to print it's requests
     */
    public void print_requests()
    {

        String id1,id2;
        System.out.println("Enter your personal ID : ");
        id1=scanner.next();
        valUi.validate_id(id1);
        srv.print_requests(Long.parseLong(id1));

    }
    public void friendships_messages_period()
    {
        System.out.println("Enter the id:");
        String id=scanner.next();

        String d1,m1,y1,d2,m2,y2;
        System.out.println("Enter the day:");
        d1=scanner.next();
        System.out.println("Enter the month:");
        m1=scanner.next();
        System.out.println("Enter the year:");
        y1=scanner.next();

        System.out.println("Enter the end day:");
        d2=scanner.next();
        System.out.println("Enter the end month:");
        m2=scanner.next();
        System.out.println("Enter the end year:");
        y2=scanner.next();
        valUi.validate_day_year(d1,d2);
        valUi.validate_day_year(d1,d2);
        valUi.validate_id(id);
        srv.friendships_messages_period(Long.parseLong(id),Integer.parseInt(d1),Integer.parseInt(m1),Integer.parseInt(y1),Integer.parseInt(d2),Integer.parseInt(m2),Integer.parseInt(y2));

    }
    public void get_messages_from_a_friend()
    {
        System.out.println("Enter the id:");
        String id=scanner.next();
        System.out.println("Enter the friend's id:");
        String id2=scanner.next();

        String d1,m1,y1,d2,m2,y2;
        System.out.println("Enter the day:");
        d1=scanner.next();
        System.out.println("Enter the month:");
        m1=scanner.next();
        System.out.println("Enter the year:");
        y1=scanner.next();

        System.out.println("Enter the end day:");
        d2=scanner.next();
        System.out.println("Enter the end month:");
        m2=scanner.next();
        System.out.println("Enter the end year:");
        y2=scanner.next();
        valUi.validate_day_year(d1,d2);
        valUi.validate_day_year(d1,d2);
        valUi.validate_id(id);
        srv.get_messages_from_a_friend(Long.parseLong(id),Long.parseLong(id2),Integer.parseInt(d1),Integer.parseInt(m1),Integer.parseInt(y1),Integer.parseInt(d2),Integer.parseInt(m2),Integer.parseInt(y2));

    }
    public void run()
    {
        boolean running = true;

        while(running)
        {
            printMenu();
            int cmd;
            System.out.println("choose comand: ");
            cmd = scanner.nextInt();
            try{
                switch(cmd) {
                    case(0): running=false;
                        break;

                    case (1): addUser();
                        break;
                    case(2): print_list();
                        break;
                    case(3): delete_user();
                        break;
                    case(4): add_friendship();
                        break;
                    case(5):delete_friendship();
                        break;
                    case(6):print_friendlist();
                        break;
                    case(7):print_friendsforuser();
                        break;
                    case(8):print_friendsforuser_month();
                        break;
                    case(9):create_conversation();
                        break;
                    case(10):reply_conversation();
                        break;
                    case(11):print_conversation();
                        break;
                    case(12):add_friend();
                        break;
                    case(13):accept_refuze();
                        break;
                    case(14):print_requests();
                        break;
                    case(15):friendships_messages_period();
                        break;
                    case(16):get_messages_from_a_friend();
                        break;
                    default:
                        System.out.println("Invalid cmd");
                        break;
                }
            }catch(ValidationException ex )
            {
                System.out.println(ex.getMessage());
            }
            catch(IllegalArgumentException ex)
            {
                System.out.println(ex.getMessage());
            }



        }
    }

}
