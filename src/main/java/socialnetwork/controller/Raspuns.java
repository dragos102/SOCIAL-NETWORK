package socialnetwork.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.UtilizatorService;

public class Raspuns {
    private UtilizatorService service;
    private Message mesaj;
    private Utilizator user;
    private ComplexController ctrl;
    Stage stage;
    public void setService(UtilizatorService service,Stage stage,Message mesaj,Utilizator user,ComplexController ctrl)
    {
        this.stage=stage;
        this.service=service;
        this.mesaj=mesaj;
        this.user=user;
        this.ctrl=ctrl;
    }

    public void accept(ActionEvent actionEvent) {
        service.accept_cerere(mesaj,user.getId());
        ctrl.improspatare2();
        stage.close();
    }

    public void refuze(ActionEvent actionEvent) {
        service.delete_cerere(mesaj,user.getId());
        ctrl.improspatare2();
        stage.close();
    }
}
