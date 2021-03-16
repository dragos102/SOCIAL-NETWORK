package socialnetwork;

import com.itextpdf.text.List;
import jdk.vm.ci.meta.Local;
import socialnetwork.config.ApplicationContext;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.*;
import socialnetwork.repository.Repository;
import socialnetwork.repository.database.*;
import socialnetwork.repository.file.CereriFile;
import socialnetwork.repository.file.MessageFile;
import socialnetwork.repository.file.PrietenieFile;
import socialnetwork.repository.file.UtilizatorFile;
import socialnetwork.service.UtilizatorService;

import socialnetwork.ui.Ui;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {



        String fileName    =ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.users");
        String prieteniName=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.prietenii");
        String mesajeFile=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.mesaje");
        String cereriFile=ApplicationContext.getPROPERTIES().getProperty("data.socialnetwork.cereri");
        //String fileName="data/users.csv";
       // String prieteniName = "data/prietenii.csv";

        String username   =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password   =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.password");
        String url  =ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");
        Repository<Long,Utilizator> repoDatabase=new UtilizatorDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Tuple<Long,Long>,Prietenie> repoDatabase2=new FriendshipDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Long,Message> repoDatabase3=new MessageDBRepository(url,username,password,new UtilizatorValidator());
        Repository<Long,Message> repoDatabase4=new CereriDBRepository(url,username,password,new UtilizatorValidator());
       EvenimenteDBRepository repoDatabase5=new EvenimenteDBRepository(url,username,password,new UtilizatorValidator());



        Repository<Long,Utilizator> userFileRepository = new UtilizatorFile(fileName, new UtilizatorValidator());
       Repository<Tuple<Long,Long>,Prietenie> prietenieFileRepository = new PrietenieFile(prieteniName,new PrietenieValidator());
       Repository<Long, Message> messageFileRepository = new MessageFile(mesajeFile,new MesajeValidator());
       Repository<Long, Message> cereriFileRepository = new CereriFile(cereriFile,new MesajeValidator());


        UtilizatorService srv = new UtilizatorService(repoDatabase,repoDatabase2,repoDatabase3,repoDatabase4,repoDatabase5);
//
//       Ui ui = new Ui(srv);
//
//       ui.run();



        MainFX.main(args);
    }
}


