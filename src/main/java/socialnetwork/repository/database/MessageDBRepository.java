package socialnetwork.repository.database;

import jdk.vm.ci.meta.Local;
import socialnetwork.domain.Message;
import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessageDBRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public MessageDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Message findOne(Long aLong) {
        try
        {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM mesaje WHERE id=? ");
            statement.setLong(1,aLong);
            ResultSet rezultat1=statement.executeQuery();
            rezultat1.next();
            Long id=rezultat1.getLong("fr");
            Long id2=rezultat1.getLong("id");
            String date=rezultat1.getString("data");
            String message=rezultat1.getString("mesaj");

            Message a=new Message(id,message,LocalDateTime.parse(date));
            a.setId(id2);
            return a;

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        Map<Long,Message> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from mesaje");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long from = resultSet.getLong("fr");
                String mesaj=resultSet.getString("mesaj");
                String data=resultSet.getString("data");
                Long id=resultSet.getLong("id");


                Message mesage = new Message(from,mesaj, LocalDateTime.parse(data));
                mesage.setId(id);
                users.put(id,mesage);
            }
            Connection connection2 = DriverManager.getConnection(url, username, password);
            PreparedStatement statement2 = connection.prepareStatement("SELECT * from destinatari_mesaje");
            ResultSet resultSet2 = statement2.executeQuery();
            while(resultSet2.next())
            {
                Long id2 = resultSet2.getLong("id_message");
                Long id3=resultSet2.getLong("id_to");
                Message unu=users.get(id2);
                Utilizator a=new Utilizator("","");
                a.setId(id3);
                unu.add_To(a);
            }
            return users.values();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users.values();
    }

    @Override
    public Message save(Message entity) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO mesaje(fr,mesaj,data,id) VALUES (?,?,?,?) ");
            statement.setLong(1, entity.getFrom());
            statement.setString(2, entity.getMessage());
            statement.setString(3, entity.getDate().toString());
            statement.setLong(4, entity.getId());
            statement.execute();
            entity.getTo().stream().forEach(x->
            {
                try {
                    Connection connection2 = DriverManager.getConnection(url, username, password);
                    PreparedStatement statement2 = connection2.prepareStatement("INSERT INTO destinatari_mesaje(id_message,id_to) VALUES (?,?)");
                    statement2.setLong(1, entity.getId());
                    statement2.setLong(2,x.getId());
                    statement2.execute();
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
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }
}
