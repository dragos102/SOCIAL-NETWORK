package socialnetwork.domain;

import java.sql.Date;
import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    public Prietenie(LocalDateTime date)
    {
        this.date= date;
    }

    public Prietenie() {
        date = LocalDateTime.now();
    }



    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "Id= '"+this.getId() +"\'"+
                ", Data" + this.getDate() +
                "}";
    }
}
