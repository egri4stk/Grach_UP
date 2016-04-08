package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;
import by.bsu.up.chat.logging.Logger;
import by.bsu.up.chat.logging.impl.Log;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.json.*;

public class InMemoryMessageStorage implements MessageStorage {

    private static final String DEFAULT_PERSISTENCE_FILE = "messages.srg";

    private static final Logger logger = Log.create(InMemoryMessageStorage.class);

    private List<Message> messages = new ArrayList<>();

    @Override
    public synchronized List<Message> getPortion(Portion portion) {
        int from = portion.getFromIndex();
        if (from < 0) {
            throw new IllegalArgumentException(String.format("Portion from index %d can not be less then 0", from));
        }
        int to = portion.getToIndex();
        if (to != -1 && to < portion.getFromIndex()) {
            throw new IllegalArgumentException(String.format("Porting last index %d can not be less then start index %d", to, from));
        }
        to = Math.max(to, messages.size());
        return messages.subList(from, to);
    }
    public static JsonObject toJson(Message history) {
        return Json.createObjectBuilder().add("id", history.getId())
                .add("author", history.getAuthor())
                .add("timestamp", history.getTimestamp())
                .add("message",history.getText())
                .add("edit",history.getEdit()).build();

    }
    public static JsonArray getJson() throws IOException {

        List<String> list= Files.readAllLines(Paths.get("history.json"));
        String JSONData = list.toString();
        JsonReader read = Json.createReader(new StringReader(JSONData));
        JsonArray arr = read.readArray();
        read.close();
        return arr;
    }

    @Override
    public  void loadHistory(){
        try { JsonArray arr = getJson();
            if (!arr.isEmpty()){
                JsonArray array = arr.getJsonArray(0);
                messages.clear();

                for (int k = 0; k < array.size(); k++) {
                    JsonObject obj = array.getJsonObject(k);
                    Message msg = new Message(obj);
                    messages.add(msg);
                }
            }
        } catch (IOException e){e.printStackTrace();}
    }

    @Override
    public void saveHistory(List<Message> messages ) throws  IOException{

        if (!messages.isEmpty()) {
            FileWriter writeJson = new FileWriter("history.json");
            JsonWriter writeHistory = Json.createWriter(writeJson);
            JsonArrayBuilder historyJ = Json.createArrayBuilder();
            for (Message i : messages) {
                historyJ.add(toJson(i));
            }
            JsonArray arr = historyJ.build();

            writeHistory.writeArray(arr);
            writeHistory.close();

            writeJson.close();
        }
    }
    @Override
    public void addMessage(Message message)  {
        messages.add(message);
        try{saveHistory(messages);}
        catch (IOException e){e.printStackTrace();}
    }

    @Override
    public boolean updateMessage(Message message) {

        for (int k = 0; k<messages.size();k++){

            if (messages.get(k).getId().equals(message.getId())){

                String text = message.getText();
                messages.get(k).setText(text);
                messages.get(k).setEdit("edit");
                try {saveHistory(messages);}
                catch (IOException e){e.printStackTrace();}
                return true;
            }
        }
        return false;
    }

    @Override
    public synchronized boolean removeMessage(String messageId) {
        for (int k = 0; k<messages.size(); k++){

            if (messages.get(k).getId().equals(messageId)){

                messages.remove(k);
                try {saveHistory(messages);}
                catch (IOException e){e.printStackTrace();}
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return messages.size();
    }





}