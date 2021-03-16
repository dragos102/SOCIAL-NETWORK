package socialnetwork.domain;

import jdk.vm.ci.meta.Local;

import java.awt.*;
import java.time.LocalDateTime;

public class Eveniment extends Entity<Long> {
    private String descriere;
    private LocalDateTime data;
    public Eveniment(String descriere, LocalDateTime data)
    {
        this.descriere=descriere;
        this.data=data;
    }
    public String getDescriere()
    {
        return this.descriere;

    }
    public LocalDateTime getData()
    {
        return this.data;
    }
    public void setDescriere(String descriere)
    {
        this.descriere=descriere;
    }
    public void setData(LocalDateTime data)
    {
        this.data=data;
    }

}
