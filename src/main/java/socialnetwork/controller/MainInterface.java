package socialnetwork.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import socialnetwork.service.UtilizatorService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;


public class MainInterface {
    private UtilizatorService service;
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public void create_new_account(ActionEvent actionEvent) {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/create_new_account.fxml"));
            AnchorPane root  = (AnchorPane) loader.load();

            // stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create new account");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            CreateNewAccount emtc  = loader.getController();
            emtc.setService(service);
            dialogStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public String hash(String passw )
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passw.getBytes(),0,passw.length());
            String zz = new BigInteger(1,md.digest()).toString(16);

            return zz;
        }
        catch(Exception e)
        {
            System.out.println(e);
            return null;
        }
    }
    public void connecting(ActionEvent actionEvent) {
        String email=username.getText();
        String passw=hash(password.getText());
        ArrayList<Integer> a=new ArrayList<Integer>();
        service.getAll().forEach(x->{
                if (x.getEmail().equals(email) == true && x.getPassword().equals(passw) == true) {
                    a.add(1);
                    System.out.println(1);

                }

        });
        if(a.size()>0)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/complex.fxml"));
                AnchorPane root  = (AnchorPane) loader.load();

                // stage
                Stage dialogStage = new Stage();
                dialogStage.setTitle("MAIN MENU");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                Scene scene = new Scene(root);
                dialogStage.setScene(scene);

                ComplexController emtc  = loader.getController();
                emtc.setService(service,email,passw,true);
                dialogStage.show();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else
        {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"error","date incorecte");

        }
    }
    public void setService(UtilizatorService service)
    {
        this.service=service;
    }
}
