package socialnetwork.repository.database;

import jdk.vm.ci.meta.Local;
import socialnetwork.domain.*;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongFunction;

public class CereriDBRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public CereriDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Message findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        Map<Long,Message> users=new HashMap<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM CERERI");
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next())
            {
                Long id=resultSet.getLong("id");
                Long de_la=resultSet.getLong("de_la");
                String date=resultSet.getString("data");
                String status=resultSet.getString("status");
                Message a=new Message(de_la,status, LocalDateTime.parse(date));
                a.setId(id);
                users.put(id,a);
            }
            PreparedStatement statement1=connection.prepareStatement("SELECT * FROM destinatari_cereri");
            ResultSet resultSet1=statement1.executeQuery();
            while(resultSet1.next())
            {
                Long id=resultSet1.getLong("id_cerere");
                Long destinatar=resultSet1.getLong("destinatar");
                Message a=users.get(id);
                Utilizator b=new Utilizator("","");
                b.setId(destinatar);
                a.add_To(b);
            }
            return users.values();

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }


    @Override
    public Message save(Message entity) {
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO CERERI(id,de_la,data,status) VALUES (?,?,?,?)");
            statement.setLong(1,entity.getId());
            statement.setLong(2,entity.getFrom());
            statement.setString(3,entity.getDate().toString());
            statement.setString(4,entity.getMessage());
            statement.execute();

            entity.getTo().stream().forEach(x->
            {
                try {
                    Connection connection1 = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement1 = connection1.prepareStatement("INSERT INTO destinatari_cereri(id_cerere,destinatar) VALUES(?,?)");
                    statement1.setLong(1,entity.getId());
                    statement1.setLong(2,x.getId());
                    statement1.execute();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
            });
            return entity;
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Message delete(Long aLong) {
        try {
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("DELETE FROM cereri WHERE id=?");
            statement.setLong(1,aLong);
            statement.execute();

            Connection connection2= DriverManager.getConnection(url,username,password);
            PreparedStatement statement2=connection2.prepareStatement("DELETE FROM destinatari_cereri WHERE id_cerere=?");
            statement2.setLong(1,aLong);
            statement2.execute();
            return null;
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Message update(Message entity) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement=connection.prepareStatement("UPDATE CERERI SET status=? WHERE id=?");

            statement.setLong(2,entity.getId());
            statement.setString(1,entity.getMessage());
            statement.execute();

            PreparedStatement statement1=connection.prepareStatement("DELETE FROM destinatari_cereri WHERE id_cerere=?");
            statement1.setLong(1,entity.getId());
            statement1.execute();

            entity.getTo().stream().forEach(x->
            {
                try {
                    Connection connection1 = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement11 = connection1.prepareStatement("INSERT INTO destinatari_cereri(id_cerere,destinatar) VALUES(?,?)");
                    statement11.setLong(1,entity.getId());
                    statement11.setLong(2,x.getId());
                    statement11.execute();
                }
                catch (SQLException e)
                {
                    System.out.println(e);
                }
            });

            return entity;
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }
}
