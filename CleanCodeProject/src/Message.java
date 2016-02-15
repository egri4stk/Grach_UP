
import java.security.Timestamp;
import java.sql.Time;
import java.time.ZoneId;
import java.util.Date;
public class Message {
    private String id;
    private String author;
    private long timestamp;
    private String message;

    public Message(String id,String author,  long timestamp, String message) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.author = author;
    }
    public Message(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        long ms = timestamp;
        Date date = new Date(ms);
        ZoneId zone;
        String strD = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
        String strT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().toString();
        return strD + "  " + strT;
    }

    public Date getDate() {
        long ms = timestamp;

        Date date = new Date(ms);
        return date;
    }

    @Override
    public String toString() {
        return "" +
                "id: " + id  + ' ' +
                getTime()+
                " " + author +
                ": " + message;
    }
}