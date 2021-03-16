package socialnetwork.domain;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;




public class Message extends Entity<Long>{
        private Long from;
        private List<Utilizator> to;
        private Long catre;
        private String message;
        private LocalDateTime date;
        /*public Message(Long from,Long catre ,String message,LocalDateTime date)
        {

            this.from=from;
            this.message=message;
            this.date=date;
            this.catre=catre;

        }*/
        public List<Long> getListaId()
        {
            List<Long> lista=new ArrayList<Long>();
            to.stream().forEach(x->
            {
                lista.add(x.getId());
            });
            return lista;
        }
        public List<Long> getListaId2()
        {
          List<Long> lista=new ArrayList<Long>();
         to.stream().forEach(x->
         {
              lista.add(x.getId());
          });
            lista.add(from);
          return lista;
        }
    public Message(Long from,List<Utilizator> lista ,String message,LocalDateTime date)
    {
        this.to=lista;
        this.from=from;
        this.message=message;
        this.date=date;
    }
    public Message(Long from,String message,LocalDateTime date)
    {
        this.to=new ArrayList<Utilizator>();
        this.from=from;
        this.message=message;
        this.date=date;
    }
        public Message(Long from,Long catre,String message)
        {
            this.from=from;
            this.catre=catre;
            this.message=message;
        }

        public String add_msg(String a)
        {
            //ce faci? : //bine tu? : bine si eu
            String message_nou=message+" : "+a;
            int cont=0;

            for(int i=0;i<message_nou.length();i++)
            {
                if(message_nou.charAt(i)==':')
                {
                    cont++;
                }
            }
            String message_final="";
            int ok=0;
            if(cont>1)
            {
                for(int i=0;i<message_nou.length();i++) {
                    if(ok==0)
                    {
                        //i++;
                    }
                    else
                    {
                        message_final=message_final+message_nou.charAt(i);
                    }
                    if(message_nou.charAt(i)==':')
                    {
                        ok++;
                    }
                }
            }
            else
            {
                message_final=message_nou;
            }
            return message_final;
        }
        public Long getFrom()
        {
            return this.from;
        }
        public Long getcatre()
        {
            return this.catre;
        }
        public List<Utilizator> getTo() {
            return this.to;
        };
        public void add_To(Utilizator a) {
            to.add(a);
        }
        public String getMessage()
        {
            return message;
        }
        public LocalDateTime getDate()
        {
            return date;
        }
        public String add_mesage(String a)
        {
            this.message+=a;
            return this.message;
        }
        public String ToString()
        {
         return this.getFrom()+" ; "+this.getcatre()+" ; "+this.getMessage()+ " ; "+this.getDate()+"\n";
        }
        public void delete_from_to(Utilizator a)
        {
            this.to.remove(a);
        }
}
