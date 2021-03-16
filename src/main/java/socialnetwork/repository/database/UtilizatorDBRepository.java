package socialnetwork.repository.database;


import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.util.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UtilizatorDBRepository implements Repository<Long, Utilizator> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public UtilizatorDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Utilizator findOne(Long aLong) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM users WHERE id=? ");
            statement.setLong(1,aLong);
            ResultSet rezultat=statement.executeQuery();
            rezultat.next();
            Long id=rezultat.getLong("id");
            String firstname=rezultat.getString("first_name");
            String lastname=rezultat.getString("last_name");
            Utilizator nou=new Utilizator(firstname,lastname);
            nou.setId(id);

            Connection connection2 = DriverManager.getConnection(url, username, password);
            PreparedStatement statement2 = connection2.prepareStatement("SELECT * from lista_prieteni where id_user=? OR id_friend=?");
            statement2.setLong(1,nou.getId());
            statement2.setLong(2,nou.getId());
            ResultSet resultSet2 = statement2.executeQuery();
            while(resultSet2.next())
            {
                Long id2 = resultSet2.getLong("id_user");
                Long id3=resultSet2.getLong("id_friend");
                Utilizator nou3;
                if(id2==nou.getId())
                {
                    Connection connection3=DriverManager.getConnection(url,username,password);
                    PreparedStatement statement3=connection3.prepareStatement("SELECT * FROM users WHERE id=? ");
                    statement3.setLong(1,id3);
                    ResultSet rezultat3=statement3.executeQuery();
                    rezultat3.next();
                    Long id33=rezultat3.getLong("id");
                    String firstname3=rezultat3.getString("first_name");
                    String lastname3=rezultat3.getString("last_name");
                     nou3=new Utilizator(firstname3,lastname3);
                    nou3.setId(id33);

                }
                else
                {
                    Connection connection3=DriverManager.getConnection(url,username,password);
                    PreparedStatement statement3=connection3.prepareStatement("SELECT * FROM users WHERE id=? ");
                    statement3.setLong(1,id2);
                    ResultSet rezultat3=statement3.executeQuery();
                    rezultat3.next();
                    Long id33=rezultat3.getLong("id");
                    String firstname3=rezultat3.getString("first_name");
                    String lastname3=rezultat3.getString("last_name");
                    nou3=new Utilizator(firstname3,lastname3);
                    nou3.setId(id33);

                }
                nou.addFriend(nou3);
            }
            return nou;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Map<Long,Utilizator> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String password=resultSet.getString("password");
                String email=resultSet.getString("email");

                Utilizator utilizator = new Utilizator(firstName, lastName,email,password);
                utilizator.setId(id);
                users.put(id,utilizator);
            }
            Connection connection2 = DriverManager.getConnection(url, username, password);
            PreparedStatement statement2 = connection.prepareStatement("SELECT * from lista_prieteni");
            ResultSet resultSet2 = statement2.executeQuery();
            while(resultSet2.next())
            {
                Long id2 = resultSet2.getLong("id_user");
                Long id3=resultSet2.getLong("id_friend");
                Utilizator unu=users.get(id2);
                Utilizator doi=users.get(id3);
                unu.addFriend(doi);
                doi.addFriend(unu);
            }
            return users.values();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users.values();
    }

    @Override
    public Utilizator save(Utilizator entity) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO users(id,first_name,last_name,email,password) VALUES (?,?,?,?,?) ");
            statement.setLong(1,entity.getId());
            statement.setString(2,entity.getFirstName());
            statement.setString(3,entity.getLastName());
            statement.setString(4,entity.getEmail());
            statement.setString(5,entity.getPassword());
            statement.execute();
            return entity;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Utilizator delete(Long aLong) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("DELETE FROM users WHERE ID=?  ");
            statement.setLong(1,aLong);
            statement.execute();

            PreparedStatement statement2=connection.prepareStatement("DELETE FROM lista_prieteni WHERE id_user=?  OR id_friend=? ");
            statement2.setLong(1,aLong);
            statement2.setLong(2,aLong);
            statement2.execute();

            return null;
        }
        catch(SQLException e) {

        }
        return null;
    }

    @Override
    public Utilizator update(Utilizator entity) {
        return null;
    }
}
