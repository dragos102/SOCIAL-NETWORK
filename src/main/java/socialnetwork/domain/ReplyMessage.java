package socialnetwork.domain;

import com.sun.tools.javac.util.List;

import java.time.LocalDateTime;

public class ReplyMessage extends Message {
    Message meg;
    public ReplyMessage(Message meg,String reply) {
        super(meg.getFrom(),meg.getTo(),meg.add_msg(reply),meg.getDate());
        this.meg=meg;
    }
    public void add_reply(String reply)
    {
        this.meg.add_mesage(reply);
    }
    public Long get_catre()
    {
        return meg.getcatre();
    }
}
