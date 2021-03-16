package socialnetwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.DataAmount;
import jdk.vm.ci.meta.Local;
import socialnetwork.domain.Entity;
import socialnetwork.domain.Eveniment;
import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;
import sun.nio.ch.Util;


import javax.xml.crypto.Data;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class ComplexController {
    ObservableList<Utilizator> modelGrade = FXCollections.observableArrayList();
    ObservableList<Utilizator> modelGrade4 = FXCollections.observableArrayList();
    ObservableList<Message> modelGrade2 = FXCollections.observableArrayList();
    ObservableList<Message> modelGrade3 = FXCollections.observableArrayList();
    ObservableList<Message> modelGrade5 = FXCollections.observableArrayList();
    ObservableList<Utilizator> modelGrade6 = FXCollections.observableArrayList();
    ObservableList<Eveniment> modelGrade7=FXCollections.observableArrayList();
    private UtilizatorService service;
    private String password;
    private String email;

    @FXML
    TableColumn id;
    @FXML
    TableColumn first_name;
    @FXML
    TableColumn last_name;
    @FXML
    TableView tabel_prietenii;

    @FXML
    TableView tabel_cereri;
    @FXML
    TableColumn id_cerere;
    @FXML
    TableColumn from;
    @FXML
    TableColumn to;
    @FXML
    TableColumn status;
    @FXML
    TableColumn date;

    @FXML
    TableView tabel_cereri_2;
    @FXML
    TableColumn id_cerere_2;
    @FXML
    TableColumn from_2;
    @FXML
    TableColumn to_2;
    @FXML
    TableColumn status_2;
    @FXML
    TableColumn date_2;

    @FXML
    TableView prieteni_mess;
    @FXML
    TableColumn Nume_mess;
    @FXML
    TableColumn Prenume_mess;

    @FXML
    VBox vbox;
    @FXML
    TextField mesaj;

    @FXML
    TableColumn Nume_grup;
    @FXML
    TableView groups_mess;



    @FXML
    Button raport1;
    @FXML
    Button raport2;
    @FXML
    DatePicker data1;
    @FXML
    DatePicker data2;
    @FXML
    TableView tabel_selectare;
    @FXML
    TableColumn nume_selectare;
    @FXML
    TableColumn prenume_selectare;
    @FXML
    Button buton_pdf;
    @FXML
    Label label_pdf;

    @FXML
    VBox boxhome;
    @FXML
    AnchorPane anchor1;
    @FXML
    AnchorPane anchor2;
    @FXML
    AnchorPane anchor3;
    @FXML
    AnchorPane anchor4;
    @FXML
    AnchorPane anchor5;

    @FXML
    Button add_friend;
    @FXML
    Button delete_friend;

    ComplexController controller;
    private int raporttip=0;


    public AnchorPane getAnchor1()
    {
        return  anchor1;
    }
    public AnchorPane getAnchor2()
    {
        return  anchor2;
    }
    public AnchorPane getAnchor3()
    {
        return  anchor3;
    }
    public AnchorPane getAnchor4()
    {
        return  anchor4;
    }
    public AnchorPane getAnchor5()
    {
        return  anchor5;
    }

    @FXML
    Pagination pagina_prieteni;
    @FXML
    Pagination pagina_cereri;
    @FXML
    Pagination pagina_cereri_2;
    @FXML
    Pagination pagina_prieteni_mess;
    @FXML
    Pagination pagina_groups_mess;
    @FXML
    Pagination pagina_tabel_selectare;


    private int value=5;


    @FXML
    TableView tabel_evenimente;
    @FXML
    TableColumn c1_eveniment;
    @FXML
    TableColumn c2_eveniment;
    @FXML
    TextField descriere_eveniment;
    @FXML
    DatePicker data_eveniment;
    @FXML
    Pagination pagina_evenimente;

    @FXML
    VBox vbox_notificari1;

    @FXML
    public void initialize() {
        //id.setCellValueFactory(new PropertyValueFactory<Entity,Long>("id"));
        first_name.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        last_name.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));

        id_cerere.setCellValueFactory(new PropertyValueFactory<Message,Long>("id"));
        from.setCellValueFactory(new PropertyValueFactory<Message,Long>("from"));
        to.setCellValueFactory(new PropertyValueFactory<Message,Long>("ListaId"));
        status.setCellValueFactory(new PropertyValueFactory<Message,String >("message"));
        date.setCellValueFactory(new PropertyValueFactory<Message,String>("date"));

        id_cerere_2.setCellValueFactory(new PropertyValueFactory<Message,Long>("id"));
        from_2.setCellValueFactory(new PropertyValueFactory<Message,Long>("from"));
        to_2.setCellValueFactory(new PropertyValueFactory<Message,Long>("ListaId"));
        status_2.setCellValueFactory(new PropertyValueFactory<Message,String >("message"));
        date_2.setCellValueFactory(new PropertyValueFactory<Message,String>("date"));

        Nume_mess.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        Prenume_mess.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        prieteni_mess.setItems(modelGrade4);
        tabel_prietenii.setItems(modelGrade);
        tabel_cereri.setItems(modelGrade2);
        tabel_cereri_2.setItems(modelGrade3);

        Nume_grup.setCellValueFactory(new PropertyValueFactory<Message,Long>("ListaId2"));
        groups_mess.setItems(modelGrade5);

        prieteni_mess.getSelectionModel().selectedItemProperty().addListener(c->print_message());
        groups_mess.getSelectionModel().selectedItemProperty().addListener(c->print_group_message());

        data1.setVisible(false);
        data2.setVisible(false);
        tabel_selectare.setVisible(false);
        buton_pdf.setVisible(false);
        label_pdf.setVisible(false);
        pagina_tabel_selectare.setVisible(false);

        nume_selectare.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        prenume_selectare.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tabel_selectare.setItems(modelGrade6);

        c1_eveniment.setCellValueFactory(new PropertyValueFactory<String,Eveniment>("descriere"));
        c2_eveniment.setCellValueFactory(new PropertyValueFactory<LocalDateTime,Eveniment>("data"));
        tabel_evenimente.setItems(modelGrade7);


    }


    private void print_group_message() {
        vbox.getChildren().removeAll(vbox.getChildren());
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        Message lista= (Message) groups_mess.getSelectionModel().getSelectedItem();
        service.get_mesaje().forEach(x->
        {
            List<Long> unu=x.getListaId2();
            List<Long> doi=lista.getListaId2();
            Collections.sort(unu);
            Collections.sort(doi);
            if(unu.equals(doi)==true)
            {
                HBox aici=new HBox();
                Button btn1=new Button("reply");
                btn1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        reply_message2(LISTA.get(0).getId(),x.getId());
                    }
                });
                Label lab1=new Label(x.getFrom()+":" +x.getMessage());
                if(x.getFrom().equals(LISTA.get(0).getId()))
                {
                    lab1.setStyle("-fx-background-color: yellow");
                }
                aici.getChildren().addAll(btn1,lab1);
                vbox.getChildren().add(aici);
            }
        });

    }

    public void print_message()
    {
        vbox.getChildren().removeAll(vbox.getChildren());
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        Utilizator selected=(Utilizator)prieteni_mess.getSelectionModel().getSelectedItem();
        Utilizator a=LISTA.get(0);
        List<Integer > check=new ArrayList<Integer>();
        service.get_mesaje().forEach(x->
        {
            if(x.getFrom().equals(a.getId()))
            {
                if(x.getTo().size()==1)
                {
                    if(selected!=null) {
                        if (x.getTo().get(0).getId() == selected.getId()) {
                            Label text = new Label();
                            Button reply = new Button("reply");
                            reply.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    reply_message(a.getId(), x.getId());
                                }
                            });
                            HBox secunda = new HBox();
                            text.setText(" " + x.getMessage());
                            text.setStyle("-fx-background-color: yellow");
                            secunda.getChildren().addAll(text, reply);
                            vbox.getChildren().addAll(secunda);
                            check.add(1);
                        }
                    }
                }
            }
            if(x.getFrom().equals(selected.getId()))
            {
                if(x.getTo().size()==1)
                {
                    if(x.getTo().get(0).getId()==a.getId())
                    {
                        Label text=new Label();
                        Button reply=new Button("reply");
                        reply.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                reply_message(a.getId(),x.getId())    ;
                            }
                        });

                        HBox secunda=new HBox();
                        text.setText("  "+x.getMessage());
                        text.setStyle("-fx-background-color: white");
                        secunda.getChildren().addAll(text,reply);
                        vbox.getChildren().addAll(secunda);
                        check.add(1);
                    }
                }
            }
        });
        if(check.size()==0)
        {
            Label gg=new Label("                               start a conversation                                          ");
            gg.setStyle("-fx-background-color: grey");
            vbox.getChildren().add(gg);
        }


    }
    public void reply_message(Long id_user,Long id_mesaj)
    {
        if(mesaj.getText().isEmpty())
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "enter reply", "ENTER THE REPLY");
        }
        else{
        String text=mesaj.getText();
        mesaj.clear();
        service.reply_conversation(id_user,id_mesaj,text);
        print_message();
        }
    }
    public void reply_message2(Long id_user,Long id_mesaj)
    {
        if(mesaj.getText().isEmpty())
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "enter reply", "ENTER THE REPLY");
        }
        else{
            String text=mesaj.getText();
            mesaj.clear();
            service.reply_conversation(id_user,id_mesaj,text);
            print_group_message();
        }
    }
    public List<Eveniment> getallevents()
    {
        List<Eveniment> lista=new ArrayList<Eveniment>();
        service.getallevents().forEach(x->
        {
            lista.add(x);
        });
        return lista;
    }
    public void setare_pagini()
    {
        pagina_tabel_selectare.setPageCount((getallwithoume().size()+4)/5);
        pagina_tabel_selectare.setPageFactory((index)->
        {
            creare_pagina_6(index);
            return new AnchorPane();
        });

        pagina_prieteni.setPageCount((getUtilizatorDTOList().size()+4)/5);
        pagina_prieteni.setPageFactory((index)->{
            System.out.println("Size "+getUtilizatorDTOList().size());

            creare_pagina(index);
            return new AnchorPane();
        });
        pagina_cereri.setPageCount((getMessagesDTOList().size()+4)/5);
        pagina_cereri.setPageFactory((index)->{
            System.out.println("Size "+getMessagesDTOList().size());

            creare_pagina_2(index);
            return new AnchorPane();
        });

        pagina_cereri_2.setPageCount((getMessagesDTOList2().size()+4)/5);
        pagina_cereri_2.setPageFactory((index)->{
            System.out.println("Size "+getMessagesDTOList2().size());

            creare_pagina_3(index);
            return new AnchorPane();
        });
        pagina_prieteni_mess.setPageCount((getallusers().size()+4)/5);
        try {
            pagina_prieteni_mess.setPageFactory((index) -> {
                System.out.println("Size " + getallusers().size());

                creare_pagina_4(index);
                return new AnchorPane();
            });
        }
        catch(Exception e)
        {

        }
        pagina_groups_mess.setPageCount((getgroupsname().size()+4)/5);
        pagina_groups_mess.setPageFactory((index)->
        {
            creare_pagina_5(index);
            return new AnchorPane();
        });
        pagina_evenimente.setPageCount((getallevents().size()+4)/5);
        pagina_evenimente.setPageFactory((index)->
        {
            creare_pagina_7(index);
            return new AnchorPane();
        });

    }
    public void creare_pagina_7(int index)
    {
        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getallevents().size());
        List<Eveniment> lista=getallevents();
        List<Eveniment> obj = new ArrayList<Eveniment>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade7.setAll(obj);
    }
    @FXML
    Label label_welcome;
    public void adauga()
    {
        vbox_notificari1.getChildren().clear();
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        Utilizator a=LISTA.get(0);

        service.find_all_abonari(LISTA.get(0)).stream().forEach(x->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/news.fxml"));
                AnchorPane root  = (AnchorPane) loader.load();

                // stage
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Message");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                final_controller emtc  = loader.getController();
                Eveniment ev=service.find_eveniment(x);
                if(ev.getData().getDayOfMonth()==LocalDateTime.now().getDayOfMonth() &&
                ev.getData().getMonth()==LocalDateTime.now().getMonth() &&
                ev.getData().getYear()==LocalDateTime.now().getYear()) {
                    emtc.set_label(ev.getDescriere() +"VA INCEPE ASTAZI LA  ORA 20:00");
                    vbox_notificari1.getChildren().add(root);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });



    }
    public void setService(UtilizatorService service,String email,String password,boolean var )
    {
        this.email=email;
        this.password=password;
        this.service=service;
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        Utilizator a=LISTA.get(0);
        label_welcome.setText("WELCOME   "+a.getFirstName()+"  "+a.getLastName());
        adauga();
       setare_pagini();

    }
    private void creare_pagina_6(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getallwithoume().size());
        List<Utilizator> lista=getallwithoume();
        List<Utilizator> obj = new ArrayList<Utilizator>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade6.setAll(obj);
    }
    private void creare_pagina_5(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getgroupsname().size());
        List<Message> lista=getgroupsname();
        List<Message> obj = new ArrayList<Message>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade5.setAll(obj);
    }
    private void creare_pagina_4(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getallusers().size());
        List<Utilizator> lista=getallusers();
        List<Utilizator> obj = new ArrayList<Utilizator>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade4.setAll(obj);
    }
    private void creare_pagina(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getUtilizatorDTOList().size());
        List<Utilizator> lista=getUtilizatorDTOList();
        List<Utilizator> obj = new ArrayList<Utilizator>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade.setAll(obj);
    }
    private void creare_pagina_2(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getMessagesDTOList().size());
        List<Message> lista=getMessagesDTOList();
        List<Message> obj = new ArrayList<Message>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade2.setAll(obj);
    }
    private void creare_pagina_3(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getMessagesDTOList2().size());
        List<Message> lista=getMessagesDTOList2();
        List<Message> obj = new ArrayList<Message>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade3.setAll(obj);
    }

    private List<Utilizator> getallwithoume() {
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        List<Utilizator> lista=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getId()!=LISTA.get(0).getId()) {
                lista.add(x);
            }
        });
        return lista;
    }

    private List<Message> getgroupsname()
    {
        List<Message> lista=new ArrayList<Message>();
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        Utilizator a=LISTA.get(0);
        service.get_mesaje().forEach(x->
        {
            if(x.getFrom().equals(a.getId())==true && x.getTo().size()>1)
            {
                lista.add(x);

            }
            if(x.getTo().size()>1) {
                List<Integer> pp=new ArrayList<Integer>();
                x.getTo().forEach(z ->
                {
                    if (z.getId() ==a.getId())
                    {
                        pp.add(1);
                    }
                });
                if(pp.size()>0)
                {
                    lista.add(x);
                }
            }
        });
        List<Message> listafinala=new ArrayList<Message>();
        lista.stream().forEach(x->
        {
            List<Integer> li=new ArrayList<Integer>();
            listafinala.stream().forEach(y->
            {
                List<Long> unu=x.getListaId2();
                List<Long> doi=y.getListaId2();

                Collections.sort(unu);
                Collections.sort(doi);
                System.out.println(unu.equals(doi));
                if(unu.equals(doi)==true)
                {
                    System.out.println(11111);
                    li.add(1);
                }
            });
            if(li.size()==0)
            {
                listafinala.add(x);
            }
        });
        return listafinala;
    }
    private List<Utilizator> getallusers() {
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        List<Utilizator> lista=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getId()!=LISTA.get(0).getId()) {
                lista.add(x);
            }
        });
        return lista;
    }

    private List<Message> getMessagesDTOList2()
    {
        List<Message> lista=new ArrayList<Message>();
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        service.get_cereri().forEach(x->
        {
            x.getTo().forEach(y->
            {
                if(y.getId().equals(LISTA.get(0).getId()))
                {
                    lista.add(x);
                }
            });

        });
        return lista;
    }
    private List<Utilizator> getUtilizatorDTOList() {
        // TODO
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                x.getFriends().stream().forEach(y->
                {
                    LISTA.add(y);
                });
            }
        });
        int valoare=pagina_prieteni.getCurrentPageIndex();
        return LISTA;
    }
    private List<Message> getMessagesDTOList()
    {
        List<Message> lista=new ArrayList<Message>();
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                    LISTA.add(x);
            }
        });
        service.get_cereri().forEach(x->
                {

                    if(x.getFrom().equals(LISTA.get(0).getId()))
                    {
                        lista.add(x);
                    }
                });
        lista.forEach(x->
        {
            System.out.println(x.getFrom());
        });
        return lista;
    }

    public void add_friend(ActionEvent actionEvent) {
        List<Utilizator> list=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getEmail().equals(email)== true && x.getPassword().equals(password)==true)
            {
                list.add(x);
            }
        });

        showMessageTaskEditDialog(list.get(0));


    }
    public void showMessageTaskEditDialog(Utilizator user) {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/editutilizatorviwe.fxml"));
            AnchorPane root  = (AnchorPane) loader.load();

            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Message");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            EditUserController emtc  = loader.getController();
            emtc.setService(service,dialogStage,user,this);
            dialogStage.show();

        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
    public void improspatare()
    {
        if(service==null)
            return;
        try {
            modelGrade2.setAll(getMessagesDTOList());

            modelGrade.setAll(getUtilizatorDTOList());
            //modelGrade2.setAll(getMessagesDTOList());
            modelGrade3.setAll(getMessagesDTOList2());
            modelGrade4.setAll(getallusers());
            modelGrade5.setAll(getgroupsname());
            modelGrade6.setAll(getallwithoume());
            setare_pagini();
        }
        catch (Exception e)
        {

        }
    }
    public void improspatare2()
    {

        modelGrade.setAll(getUtilizatorDTOList());
        modelGrade2.setAll(getMessagesDTOList());
        modelGrade3.setAll(getMessagesDTOList2());

        //modelGrade.setAll(getUtilizatorDTOList());
        //modelGrade2.setAll(getMessagesDTOList());
        //modelGrade3.setAll(getMessagesDTOList2());
        modelGrade4.setAll(getallusers());
        modelGrade5.setAll(getgroupsname());
        modelGrade6.setAll(getallwithoume());
        setare_pagini();
    }
    public void delete_friend(ActionEvent actionEvent) {
        Utilizator selected=(Utilizator) tabel_prietenii.getSelectionModel().getSelectedItem();


        if(selected!=null)
        {
            int ok=0;
            List<Utilizator> list=new ArrayList<Utilizator>();
            service.getAll().forEach(x->
            {
                if(x.getEmail().equals(email)== true && x.getPassword().equals(password)==true)
                {
                    list.add(x);
                }
            });

            if(list.size()>1)
            {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "enter complete name", "there are many clients with this name,enter full name please");

            }
            else {
                Utilizator a=list.get(0);
                try {
                    service.delete_friendship(a.getId(), selected.getId());
                } catch (Exception e) {
                    ok += 1;


                }
                try {
                    service.delete_friendship(selected.getId(), a.getId());
                } catch (Exception e) {
                    ok += 1;


                }
                if (ok <= 1) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "done", "friendship deleted");
                    modelGrade.setAll(getUtilizatorDTOList());

                    //modelGrade.setAll(getUtilizatorDTOList());\
                    try {
                        modelGrade2.setAll(getMessagesDTOList());
                        modelGrade3.setAll(getMessagesDTOList2());
                        modelGrade4.setAll(getallusers());
                        modelGrade5.setAll(getgroupsname());
                        modelGrade6.setAll(getallwithoume());
                    }
                    catch (Exception e)
                    {

                    }
                } else {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "can't delete");
                }
            }
        }
        else
        {
            MessageAlert.showMessage(null,Alert.AlertType.INFORMATION,"error","nothing selected");
        }
    }

    public void delete_cerere(ActionEvent actionEvent) {
       Message selected=(Message) tabel_cereri.getSelectionModel().getSelectedItem();
        if(selected==null)
        {
            MessageAlert.showMessage(null,Alert.AlertType.INFORMATION,"error","nothing selected");

        }
        else
        {
            int ok=0;
            try {
                service.delete_cerere(selected.getId());
            }
            catch (Exception e)
            {
                ok=1;
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "can't delete");
            }
            if(ok==0){
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "done", "request deleted");
                try {
                    modelGrade2.setAll(getMessagesDTOList());

                    modelGrade.setAll(getUtilizatorDTOList());
                    //modelGrade2.setAll(getMessagesDTOList());
                    modelGrade3.setAll(getMessagesDTOList2());
                    modelGrade4.setAll(getallusers());
                    modelGrade5.setAll(getgroupsname());
                    modelGrade6.setAll(getallwithoume());
                    improspatare();
                }
                catch (Exception e)
                {

                }
            }
        }
    }

    public void raspunde_buton(ActionEvent actionEvent) {
        Message selected=(Message) tabel_cereri_2.getSelectionModel().getSelectedItem();
        if(selected==null)
        {
            MessageAlert.showMessage(null,Alert.AlertType.INFORMATION,"error","nothing selected");

        }
        else {
            List<Utilizator> list=new ArrayList<Utilizator>();
            service.getAll().forEach(x->
            {
                if(x.getEmail().equals(email)== true && x.getPassword().equals(password)==true)
                {
                    list.add(x);
                }
            });
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/raspuns.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

                // stage
                Stage dialogStage = new Stage();
                dialogStage.setTitle("RASPUNDE CERERILOR");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                Raspuns emtc = loader.getController();
                emtc.setService(service, dialogStage,selected,list.get(0),this);
                dialogStage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void create_message(ActionEvent actionEvent) {
        if(mesaj.getText().isEmpty())
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "enter reply", "ENTER THE REPLY");
        }

        else{
            List<Utilizator> LISTA=new ArrayList<Utilizator>();
            service.getAll().forEach(x->
            {
                if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
                {
                    LISTA.add(x);
                }
            });
            Utilizator selected=(Utilizator)prieteni_mess.getSelectionModel().getSelectedItem();
            Message selected2 = (Message) groups_mess.getSelectionModel().getSelectedItem();
            if(selected!=null && selected2!=null)
            {
                groups_mess.getSelectionModel().clearSelection();
                prieteni_mess.getSelectionModel().clearSelection();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "NOTHING SELECTED");

            }
            else {
                if (selected != null) {
                    Utilizator a = LISTA.get(0);
                    List<Long> lista = new ArrayList<Long>();
                    lista.add(selected.getId());
                    String text = mesaj.getText();
                    mesaj.clear();
                    service.create_conversation(a.getId(), lista, text);
                    print_message();
                } else {
                    selected2 = (Message) groups_mess.getSelectionModel().getSelectedItem();
                    if (selected2 != null) {
                        Utilizator a = LISTA.get(0);
                        List<Long> lista = new ArrayList<Long>();
                        selected2.getListaId2().stream().forEach(x ->
                        {
                            if (x != a.getId()) {
                                lista.add(x);
                            }
                        });
                        String text = mesaj.getText();
                        mesaj.clear();
                        service.create_conversation(a.getId(), lista, text);
                        print_group_message();
                    } else {
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "NOTHING SELECTED");
                    }
                }
            }

        }
    }

    public void raport_unu(ActionEvent actionEvent) {
        data1.setVisible(true);
        data2.setVisible(true);
        buton_pdf.setVisible(true);
        label_pdf.setVisible(false);
        tabel_selectare.setVisible(false);
        pagina_tabel_selectare.setVisible(false);
        raporttip=1;

    }

    public void raport_doi(ActionEvent actionEvent) {
        data1.setVisible(true);
        data2.setVisible(true);
        buton_pdf.setVisible(true);
        label_pdf.setVisible(true);
        tabel_selectare.setVisible(true);
        pagina_tabel_selectare.setVisible(true);
        raporttip=2;
    }

    public void salveaza_pdf(ActionEvent actionEvent) {
        LocalDate data3 = data1.getValue();
        LocalDate data4 = data2.getValue();
        if(data3==null  || data4==null)
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "please enter data");

        }
        else {
            LocalDateTime startDate = LocalDateTime.of(data3.getYear(), data3.getMonthValue(), data3.getDayOfMonth(), 0, 1);

            LocalDateTime finishDate = LocalDateTime.of(data4.getYear(), data4.getMonthValue(), data4.getDayOfMonth(), 0, 1);

            List<Utilizator> LISTA = new ArrayList<Utilizator>();
            service.getAll().forEach(x ->
            {
                if (x.getPassword().equals(password) == true && x.getEmail().equals(email) == true) {
                    LISTA.add(x);
                }
            });
            Utilizator eu = LISTA.get(0);
            if (raporttip == 1) {
                int ok = 0;
                try {

                    service.friendships_messages_period(eu.getId(), startDate.getDayOfMonth(), startDate.getMonthValue(), startDate.getYear(), finishDate.getDayOfMonth(), finishDate.getMonthValue(), finishDate.getYear());
                } catch (Exception e) {
                    ok++;
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", e.getMessage());
                }
                if (ok == 0) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "saved", "RAPORT1 CREAT");
                }
            }
            if (raporttip == 2) {
                Utilizator asta = (Utilizator) tabel_selectare.getSelectionModel().getSelectedItem();
                if (asta == null) {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "nothing selected");
                } else {
                    int ok = 0;
                    try {
                        label_pdf.setText(" utilizatorul selectat este : " + asta.getFirstName() + " " + asta.getLastName());
                        service.get_messages_from_a_friend(eu.getId(), asta.getId(), startDate.getDayOfMonth(), startDate.getMonthValue(), startDate.getYear(), finishDate.getDayOfMonth(), finishDate.getMonthValue(), finishDate.getYear());
                    } catch (Exception e) {
                        ok++;
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", e.getMessage());
                    }
                    if (ok == 0) {
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "saved", "RAPORT 2 CREAT");
                    }
                }
            }
        }

    }

    public void reimprospatare(Event event) {
        if (controller!=null) {
        controller.improspatare();
        }
        improspatare();
    }

    public void adauga_eveniment(ActionEvent actionEvent) {
        if(descriere_eveniment.getText().isEmpty())
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "insereaza o descriere");

        }
        else {
            if(data_eveniment.getValue()==null)
            {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "insereaza o data ");

            }
            else {
                if(data_eveniment.getValue().compareTo(LocalDate.now())<=0)
                {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "evenimentul poate avea loc cel tarziu maine ");

                }
                else {
                    String descriere = descriere_eveniment.getText();
                    LocalDate data2 = data_eveniment.getValue();
                    LocalDateTime data = LocalDateTime.of(data2.getYear(), data2.getMonth(), data2.getDayOfMonth(), 20, 00, 00);
                    Eveniment nou = new Eveniment(descriere, data);
                    service.add_eveniment(nou);
                    modelGrade7.setAll(getallevents());
                    improspatare();
                    descriere_eveniment.clear();
                    data_eveniment.getEditor().clear();
                }
            }
        }
    }

    public void abonare(ActionEvent actionEvent) {
        Eveniment selected=(Eveniment)tabel_evenimente.getSelectionModel().getSelectedItem();
        if(selected!=null)
        {
            List<Utilizator> LISTA=new ArrayList<Utilizator>();
            service.getAll().forEach(x->
            {
                if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
                {
                    LISTA.add(x);
                }
            });
            if(service.check_abonare(LISTA.get(0),selected)==1){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "aceasta abonare exista deja");

            }
            else {
                if(selected.getData().compareTo(LocalDateTime.now())<0)
                {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "acest eveniment a expirat");

                }
                else {
                    service.add_abonare(LISTA.get(0), selected);
                    adauga();
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "done", "abonare realizata cu succes");
                }
            }
        }
        else
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "nothing selected");

        }
    }

    public void dezabonare(ActionEvent actionEvent) {
        Eveniment selected=(Eveniment)tabel_evenimente.getSelectionModel().getSelectedItem();
        if(selected==null)
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "nothing selected");

        }
        else
        {
            List<Utilizator> LISTA=new ArrayList<Utilizator>();
            service.getAll().forEach(x->
            {
                if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
                {
                    LISTA.add(x);
                }
            });
            if(service.check_abonare(LISTA.get(0),selected)==0){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", "nu esti abonat la acest eveniment");
            }
            else
            {
                service.delete_abonare(LISTA.get(0),selected);
                adauga();
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "done", "dezabonare realizata cu succes");

            }
        }
    }
    @FXML
    VBox box_news;
    public void afisare_news(Event event) {
        box_news.getChildren().clear();
        List<Utilizator> LISTA=new ArrayList<Utilizator>();
        service.getAll().forEach(x->
        {
            if(x.getPassword().equals(password)==true && x.getEmail().equals(email)==true)
            {
                LISTA.add(x);
            }
        });
        service.find_all_abonari(LISTA.get(0)).stream().forEach(x->
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/news.fxml"));
                AnchorPane root  = (AnchorPane) loader.load();

                // stagevbox_notificari1
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Edit Message");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                final_controller emtc  = loader.getController();
                Eveniment ev=service.find_eveniment(x);
                emtc.set_label("Veti participa la evenimentul  : " +ev.getDescriere() +" care incepe in data si ora  :"+ ev.getData());
                box_news.getChildren().add(root);

            }
            catch(Exception e){
                e.printStackTrace();
            }


            /*Label unu=new Label();
            Eveniment nou=service.find_eveniment(x);
            unu.setText("Veti participa la evenimentul  : " +nou.getDescriere() +" care incepe in data si ora  :"+ nou.getData());
            unu.setFont(Font.font(15));
            unu.setStyle("-fx-background-color: red");
            box_news.getChildren().add(unu);*/
        });
    }
}
