package by.bsu.up.chat.common.models;

import javax.json.JsonObject;
import java.io.Serializable;
import java.lang.Override;
import java.lang.String;

public class Message implements Serializable {

    private String id;
    private String author;
    private long timestamp;
    private String text;
    private String edit;

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", timestamp=" + timestamp +
                ", text=" + text +
                ", edit=" + edit +
                '}';
    }

    public Message(JsonObject temp) {
        this.author = temp.getString("author");
        this.timestamp = temp.getJsonNumber("timestamp").longValue();
        this.text = temp.getString("message");
        this.edit = temp.getString("edit");
        this.id = temp.getString("id");
    }

    public Message() {
        this.id = "";
        this.author = "";
        this.timestamp = 0;
        this.text = "";
        this.edit = "";
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


}