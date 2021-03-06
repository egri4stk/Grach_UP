package grachat;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private String id;
    private String author;
    private long timestamp;
    private String message;

    public Message(String id, String author, long timestamp, String message) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        long ms = timestamp;
        Date tempDate = new Date(ms);
        Timestamp tempTime = new Timestamp(tempDate.getTime());
        return tempTime.toString();
    }

    public Date getDate() {
        long ms = timestamp;
        return new Date(ms);
    }

    @Override
    public String toString() {
        return "" +
                "id: " + id + ' ' +
                getTime() +
                " " + author +
                ": " + message;
    }
}