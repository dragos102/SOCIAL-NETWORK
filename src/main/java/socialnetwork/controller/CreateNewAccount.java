package socialnetwork.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.ValidationException;
import socialnetwork.domain.validators.ValidatorUi;
import socialnetwork.service.UtilizatorService;
import sun.nio.ch.Util;

import java.math.BigInteger;
import java.security.MessageDigest;


public class CreateNewAccount {

    private ValidatorUi valUi = new ValidatorUi();
    private UtilizatorService service;
    @FXML
    TextField first_name;
    @FXML
    TextField second_name;
    @FXML
    TextField id;
    @FXML
    TextField email;
    @FXML
    PasswordField password1;
    @FXML
    PasswordField password2;
    @FXML
    Label label1;
    @FXML
    Label label2;
    @FXML
    Label label3;
    @FXML
    Label label4;
    @FXML
    Label label5;
    @FXML
    Label label6;
    public void create_account(ActionEvent actionEvent) {
        label1.setText("");
        label2.setText("");
        label3.setText("");
        label4.setText("");
        label5.setText("");
        label6.setText("");

        try {
            valUi.validate_name(first_name.getText());
        }
         catch(Exception e)
         {
             label5.setText(e.getMessage());
         }
        try
        {
            valUi.validate_name(second_name.getText());
        }
        catch (Exception e)
        {
            label6.setText(e.getMessage());
        }
        try
        {
            valUi.validate_id(id.getText());

        }
        catch (Exception e)
        {
            label1.setText(e.getMessage());
        }

        if(email.getText().contains("@gmail.com")==false && email.getText().contains("@yahoo.com")==false)
        {
            label2.setText("email adrees must contains @gmail.com or @yahoo.com");
        }
        if(password1.getText().isEmpty())
        {
            label3.setText("enter a password");
        }
        if(password2.getText().isEmpty())
        {
            label4.setText("re-enter the password");
        }
        if(password2.getText().compareTo(password1.getText())!=0)
        {
            label4.setText("the passwords are not the same,re-enter correct");
        }
        try {
            service.getAll().forEach(x ->
            {
                if (x.getId() == Long.parseLong(id.getText())) {
                    label1.setText("this id already exists,enter another one");
                }
                if (x.getEmail().equals(email.getText())) {
                    label2.setText("for this email already exists an account");
                }
            });
        }
        catch (Exception e)
        {

        }
        if(label1.getText().isEmpty() && label2.getText().isEmpty() && label3.getText().isEmpty() && label4.getText().isEmpty())
        {
            label1.setText("correct");
            label2.setText("correct");
            label3.setText("correct");
            label4.setText("correct");
            label5.setText("correct");
            label6.setText("correct");
            int ok=0;
                Utilizator a=new Utilizator(first_name.getText(),second_name.getText(),email.getText(),hash(password1.getText()));

                a.setId(Long.parseLong(id.getText()));
                if(service.addUtilizator(a)==null)
                {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "ERROR", "the account couldn't been created");

                }

                else {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "DONE", "you're account have been created succesfully");
                }
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
    public void setService(UtilizatorService utilizatorService) {
        this.service=utilizatorService;
    }
}
