import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.*;
import java.time.*;


public class Chat {
    public static void main(final String[] args) throws IOException {
            ArrayList<Message> history = new ArrayList<Message>();
            Reader reader = new InputStreamReader(new FileInputStream("input.json"));
          //  Writer writer = new OutputStreamWriter(new FileOutputStream("output.json"));
            Writer writer = new FileWriter("Output.json");
            Gson gson = new GsonBuilder().create();
            Message[] p = gson.fromJson(reader, Message[].class);
            for(int i=0; i< p.length; i++){
              System.out.println(p[i]);
               history.add(p[i]);
            }

            Date date = new Date();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));

        String name = reader2.readLine();
        String mes = reader2.readLine();

            long timest = date.getTime();
        Integer tempPer =  Integer.parseInt(history.get(history.size()-1).getId()) +1;

        String tempId = tempPer.toString();
            history.add(new Message(tempId,name,timest,mes));
        gson.toJson(history,writer);
        writer.close();

        for(Message it: history){
            System.out.println(it.toString());
        }

    }
    }



