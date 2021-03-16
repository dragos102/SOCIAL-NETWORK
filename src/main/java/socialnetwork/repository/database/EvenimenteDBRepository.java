package socialnetwork.repository.database;

import socialnetwork.domain.Eveniment;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;
import socialnetwork.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EvenimenteDBRepository implements Repository<Long, Eveniment> {
    private String url;
    private String username;
    private String password;
    private Validator<Utilizator> validator;

    public EvenimenteDBRepository(String url, String username, String password, Validator<Utilizator> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Eveniment findOne(Long aLong) {
        try
        {
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM evenimente WHERE id=? ");
            statement.setLong(1,aLong);
            ResultSet rezultat=statement.executeQuery();
            rezultat.next();
            Long id=rezultat.getLong("id");
            String descriere=rezultat.getString("descriere");
            String data=rezultat.getString("data");
            Eveniment nou=new Eveniment(descriere, LocalDateTime.parse(data));
            nou.setId(id);

            return nou;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Iterable<Eveniment> findAll() {
        Map<Long,Eveniment> users = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from evenimente");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("descriere");
                String lastName = resultSet.getString("data");


                Eveniment utilizator = new Eveniment(firstName, LocalDateTime.parse(lastName));
                utilizator.setId(id);
                users.put(id,utilizator);
            }
            return users.values();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return users.values();
    }

    @Override
    public Eveniment save(Eveniment entity) {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO evenimente(id,descriere,data) VALUES (?,?,?) ");
            statement.setLong(1,entity.getId());
            statement.setString(2,entity.getDescriere());
            statement.setString(3,entity.getData().toString());
            statement.execute();
            return entity;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Eveniment delete(Long aLong) {
        return null;
    }


    @Override
    public Eveniment update(Eveniment entity) {
        return null;
    }
    public void add_abonari(Utilizator unu,Eveniment doi)
    {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("INSERT INTO abonari(id_user,id_eveniment) VALUES (?,?) ");
            statement.setLong(1,unu.getId());
            statement.setLong(2,doi.getId());
            statement.execute();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }
    public int find_abonare(Utilizator unu,Eveniment doi)
    {
        try
        {
            Connection connection= DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("SELECT * FROM abonari WHERE id_user=? and id_eveniment=? ");
            statement.setLong(1,unu.getId());
            statement.setLong(2,doi.getId());
            ResultSet rezultat=statement.executeQuery();
            rezultat.next();
            Long id=rezultat.getLong("id_user");
            Long id2=rezultat.getLong("id_eveniment");

            if(id!=null && id2!=null) {

                return 1;
            }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
    public List<Long> find_all_abonari(Utilizator unu)
    {
        List<Long> lista=new ArrayList<Long>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from abonari where id_user=?"))
             {
                  statement.setLong(1,unu.getId());
                  ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_eveniment");
                lista.add(id);
            }
            return lista;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return lista;
    }
    public void delete_abonare(Utilizator unu,Eveniment doi)
    {
        try
        {
            Connection connection=DriverManager.getConnection(url,username,password);
            PreparedStatement statement=connection.prepareStatement("DELETE FROM abonari WHERE id_user=? and id_eveniment=?  ");
            statement.setLong(1,unu.getId());
            statement.setLong(2,doi.getId());
            statement.execute();

        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }

}
