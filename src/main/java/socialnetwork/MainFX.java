package socialnetwork;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import socialnetwork.config.ApplicationContext;
import socialnetwork.controller.MainInterface;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.UtilizatorValidator;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.*;
import socialnetwork.service.UtilizatorService;


public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        String username   =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password   =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        String url  =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        Repository<Long,Utilizator> repoDatabase=new UtilizatorDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Tuple<Long,Long>,Prietenie> repoDatabase2=new FriendshipDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Long,Message> repoDatabase3=new MessageDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Long,Message> repoDatabase4=new CereriDBRepository(url,username,password,new UtilizatorValidator());
        EvenimenteDBRepository repoDatabase5=new EvenimenteDBRepository(url,username,password,new UtilizatorValidator());
//        FXMLLoader loader=new FXMLLoader();
//        loader.setLocation(getClass().getResource("/view/utilizatorview.fxml"));
//        AnchorPane root=loader.load();
//        UtilizatorController ctrl=loader.getController();
//        ctrl.setService(new UtilizatorService(repoDatabase,repoDatabase2,repoDatabase3,repoDatabase4));

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/mainInterface.fxml"));
        AnchorPane root=loader.load();
        MainInterface ctrl=loader.getController();
        ctrl.setService(new UtilizatorService(repoDatabase,repoDatabase2,repoDatabase3,repoDatabase4,repoDatabase5));

        primaryStage.setScene(new Scene(root, 600, 180));
        primaryStage.setTitle("WELCOME TO MY APPLICATION");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}