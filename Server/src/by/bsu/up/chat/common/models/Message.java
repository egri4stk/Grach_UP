package by.bsu.up.chat.common.models;

import javax.json.JsonObject;
import java.io.Serializable;

public class Message implements Serializable {

    private String id;
    private String author;
    private long timestamp;
    private String text;
    private Boolean edited;
    private Boolean deleted;
    private String type;

    public Message() {
        this.id = "";
        this.author = "";
        this.timestamp = 0;
        this.text = "";
        this.edited = false;
        this.deleted = false;
        this.type ="new";
    }
    public Message(Message m){
        this.id = m.getId();
        this.author = m.getAuthor();
        this.timestamp = m.getTimestamp();
        this.text = m.getText();
        this.edited = m.getIsEdit();
        this.deleted = m.getDeleted();
        this.type = m.getType();
    }
    public Message(JsonObject temp) {
        this.author = temp.getString("author");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.edited = temp.getBoolean("edited");
        this.deleted = temp.getBoolean("deleted");
        this.id = temp.getString("id");
        this.type =temp.getString("type");
    }

    public Boolean getIsEdit() {
        return edited;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIsEdit(Boolean edit) {
        this.edited = edit;
    }

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
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDeleted() {
       return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", author='" + author + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}