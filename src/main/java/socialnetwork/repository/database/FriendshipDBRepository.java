package socialnetwork.repository.database;

import socialnetwork.domain.Prietenie;
import socialnetwork.domain.Tuple;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDBRepository implements Repository<Tuple<Long,Long>, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public FriendshipDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }


    @Override
    public Prietenie findOne(Tuple<Long, Long> longLongTuple) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM friendships WHERE (first_id=? AND second_id=?) OR  (first_id=? AND second_id=?)");
            statement.setLong(1,longLongTuple.getLeft());
            statement.setLong(2,longLongTuple.getRight());
            statement.setLong(4,longLongTuple.getLeft());
            statement.setLong(3,longLongTuple.getRight());
            ResultSet rezultat1=statement.executeQuery();
            rezultat1.next();
            Long id=rezultat1.getLong("first_id");
            Long id2=rezultat1.getLong("second_id");
            String date=rezultat1.getString("data");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date);

            Tuple a=new Tuple(id,id2);
            Prietenie nou=new Prietenie(dateTime);
            nou.setId(a);
            return nou;
        }
        catch(SQLException e) {

        }
        return null;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("first_id");
                Long id_2 = resultSet.getLong("second_id");
                String date=resultSet.getString("data");

                //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(date);
                Tuple a=new Tuple(id,id_2);
                Prietenie tip=new Prietenie(dateTime);
                tip.setId(a);
                users.add(tip);
            }
            return users;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users;
    }

    @Override
    public Prietenie save(Prietenie entity) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO friendships(first_id,second_id,data) VALUES (?,?,?) ");
            statement.setLong(1,entity.getId().getLeft());
            statement.setLong(2,entity.getId().getRight());
            statement.setString(3,entity.getDate().toString());

            Connection connection2=DriverManager.getConnection(url,username,password);
            PreparedStatement statement2=connection.prepareStatement("INSERT INTO lista_prieteni(id_user,id_friend) VALUES (?,?) ");
            statement2.setLong(1,entity.getId().getLeft());
            statement2.setLong(2,entity.getId().getRight());
            statement.execute();
            statement2.execute();
            return entity;
        }
        catch(SQLException e) {

        }
        return null;
    }

    @Override
    public Prietenie delete(Tuple<Long, Long> longLongTuple) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("DELETE FROM friendships WHERE first_id=? AND second_id=?  ");
            statement.setLong(1,longLongTuple.getLeft());
            statement.setLong(2,longLongTuple.getRight());

            PreparedStatement statement2=connection.prepareStatement("DELETE FROM lista_prieteni WHERE (id_user=? AND id_friend=?) OR  (id_user=? AND id_friend=?) ");
            statement2.setLong(1,longLongTuple.getLeft());
            statement2.setLong(2,longLongTuple.getRight());
            statement2.setLong(4,longLongTuple.getLeft());
            statement2.setLong(3,longLongTuple.getRight());
            statement2.execute();
            statement.execute();
            return null;
        }
        catch(SQLException e) {

        }
        return null;
    }

    @Override
    public Prietenie update(Prietenie entity) {
        return null;
    }


}
