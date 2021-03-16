package socialnetwork.domain;

import socialnetwork.domain.validators.ValidationException;
import sun.nio.ch.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utilizator extends Entity<Long>{
    private String firstName;
    private String lastName;
    private List<Utilizator> friends;
    private String email;
    private String password;


    public Utilizator(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email="";
        this.password="";
        friends = new ArrayList<Utilizator>();
    }
    public Utilizator(String firstName, String lastName,String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.password=password;
        friends = new ArrayList<Utilizator>();
    }
    public Utilizator(Long id)
    {
        this.setId(id);
    }


//    public Utilizator(String firstName,String lastName,int id)
//    {
//
//    }

    public String getEmail()
    {
        return this.email;
    }
    public String getPassword()
    {
        return this.password;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Utilizator> getFriends() {
        return friends;
    }

    public void addFriend(Utilizator fr)
    { int ok=0;

            for(Utilizator user: friends)
                if (user.equals(fr)) {
                    ok = 1;
                }

        if(ok==0)
        {
            friends.add(fr);
        }
    }
    public void delete_friends()
    {
        this.friends=new ArrayList<Utilizator>();
    }
    public void remove_Friend(Utilizator fr)
    {
        int ok=0;
        System.out.println(friends);
        for(Utilizator user:friends)
            if(user.equals(fr))
            {
                ok=1;
            }
        if(ok==0)
            throw new ValidationException("utilizatorul nu este in lista de prieteni");
        else
            friends.remove(fr);
    }

    @Override
    public String toString() {
        String str="[ ";
        for(Utilizator i:friends)
            str=str+i.getId() + " ";
        str=str+"]";
        return "Utilizator{" +
                "Id=" + this.getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + str +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Utilizator)) return false;
        Utilizator that = (Utilizator) o;
        return getFirstName().equals(that.getFirstName()) &&
                getLastName().equals(that.getLastName()) ;//&&
               // getFriends().equals(that.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),getFirstName(), getLastName(), getFriends(),getEmail(),getPassword());
    }
}