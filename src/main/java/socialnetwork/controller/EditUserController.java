package socialnetwork.controller;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

public class EditUserController {

    @FXML
    private TextField textFieldFirstName ;
    @FXML
    private TextField textFieldLastName;
    ObservableList<Utilizator> modelGrade= FXCollections.observableArrayList();
    @FXML
    private TableColumn firstName;
    @FXML
    private TableColumn secondName;
    @FXML
    private TableView tabel_secund;
    @FXML
    Pagination pagina_tabel_secund;
    private int value=5;

    @FXML
    public void initialize()
    {
        firstName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("firstName"));
        secondName.setCellValueFactory(new PropertyValueFactory<Utilizator,String>("lastName"));
        tabel_secund.setItems(modelGrade);

    }
    public List<Utilizator> getListaUsers()
    {
        List<Utilizator> lista=new ArrayList<Utilizator>();

        service.getAll().forEach(x->
        {
            List<Integer> lis=new ArrayList<Integer>();
            if(x.getId()!=user.getId()) {

                user.getFriends().forEach(y ->
                {
                    if (y.getId() == x.getId()) {
                        lis.add(1);
                    }
                });

                if (lis.size() == 0) {

                    service.get_cereri().forEach(y ->
                    {
                        if (y.getFrom() == user.getId()) {
                            y.getTo().forEach(z ->
                            {
                                if (z.getId() == x.getId()) {
                                    lis.add(1);
                                }
                            });
                        }
                    });
                    if (lis.size() == 0) {
                        lista.add(x);
                    }
                }
            }
        });
        return lista;
    }

    private ComplexController refresh;
    private UtilizatorService service;
    private Utilizator user;
    Stage dialogStage;

    public void handleSave(ActionEvent actionEvent) {
        String first,second;
        Utilizator selected=(Utilizator) tabel_secund.getSelectionModel().getSelectedItem();

        if (selected != null) {
            first=selected.getFirstName();
            second=selected.getLastName();
        }
        else {
            first = textFieldFirstName.getText();
             second = textFieldLastName.getText();
        }
        if(first.equals(user.getFirstName())==true && second.equals(user.getLastName())==true )
        {
            MessageAlert.showMessage(null,Alert.AlertType.INFORMATION,"error","NU ITI POTI TRIMITE TIE CERERE DE PRIETENIE");
        }
        else {
            Utilizator a = new Utilizator(first, second);
            service.getAll().forEach(x ->
            {
                if (x.getFirstName().equals(first) && x.getLastName().equals(second)) {
                    a.setId(x.getId());
                }
            });
            int ok = 0;
            try {
                ArrayList<Long> lista = new ArrayList<Long>();
                lista.add(a.getId());
                service.add_friend(user.getId(), lista, "pending");
                refresh.improspatare();
            } catch (Exception e) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "error", e.getMessage());
                ok = 1;
            }
            if (ok == 0) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "send", "request sended");
                handleCancel();
            }
        }
    }





    public void setService(UtilizatorService service, Stage stage,Utilizator user,ComplexController refresh) {
        this.service = service;
        this.dialogStage=stage;
        this.user=user;
        this.refresh=refresh;
        //modelGrade.setAll(getListaUsers());
        pagina_tabel_secund.setPageCount((getListaUsers().size()+4)/5);
        pagina_tabel_secund.setPageFactory((index)->{
            System.out.println("Size "+getListaUsers().size());

            creare_pagina(index);
            return new AnchorPane();
        });
    }
    private void creare_pagina(int index) {

        int st=(index)*value+1;
        int dr=st+value-1;
        System.out.println(st+" "+dr);
        dr=Math.min(dr,getListaUsers().size());
        List<Utilizator> lista=getListaUsers();
        List<Utilizator> obj = new ArrayList<Utilizator>();
        for(int i=st-1;i<dr;i++)
            obj.add(lista.get(i));
        modelGrade.setAll(obj);
    }


    @FXML
    public void handleCancel(){
        dialogStage.close();
    }


}
