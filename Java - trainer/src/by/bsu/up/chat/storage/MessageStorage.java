package by.bsu.up.chat.storage;

import by.bsu.up.chat.common.models.Message;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.List;

public interface MessageStorage {
    void loadHistory();

    void saveHistory(List<Message> messages ) throws IOException;

    List<Message> getPortion(Portion portion);

    void addMessage(Message message);

    boolean updateMessage(Message message);

    boolean removeMessage(String messageId);

    int size();
}