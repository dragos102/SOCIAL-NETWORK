package socialnetwork.controller;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
public class final_controller {

    @FXML
    Label labelnews1;

    public void set_label(String nume)
    {
        labelnews1.setText(nume);
    }

}
