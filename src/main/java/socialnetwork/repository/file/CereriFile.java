package socialnetwork.repository.file;

import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CereriFile extends AbstractFileRepository<Long, Message> {
    public CereriFile(String fileName, Validator<Message> validator) {
        super(fileName, validator);
    }


    /**
     * this function extract an entity from file
     * @param attributes-the line from the file
     * @return the message
     */
    @Override
    public Message extractEntity(List<String> attributes) {
        Long pair = Long.parseLong(attributes.get(0));
        String ana =attributes.get(2);
        List<Utilizator> noi=new ArrayList<>();
        for(int i=1;i<ana.length();i++)
        {
            char c=ana.charAt(i);
            if(c>='0' && c<='9')
            {
                String doi="";
                while(c>='0' && c<='9' && i<ana.length())
                {
                    doi=doi+c;
                    i++;
                    c=ana.charAt(i);
                }
                Utilizator a=new Utilizator("","");
                a.setId(Long.parseLong(doi));
                noi.add(a);
            }
        }
        Message p = new Message(Long.parseLong(attributes.get(1)),noi,attributes.get(3),LocalDateTime.parse(attributes.get(4)));
        p.setId(pair);
        return p;
    }


    /**
     * this function return a string representing that message that will be written in the file
     * @param entity-the message
     * @return-the string
     */
    @Override
    protected String createEntityAsString(Message entity) {
        List<Long> lista=new ArrayList<Long>();
        entity.getTo().stream().forEach(x->
                {
                    System.out.println(x.getId());
                    lista.add(x.getId());
                }
        );
        return entity.getId()+";"+entity.getFrom()+";"+lista+";"+entity.getMessage()+";"+entity.getDate();
    }

}
