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
        System.out.println("Enter name: ");
        String name = reader2.readLine();
        System.out.println("Enter message: ");
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
        System.out.println("Enter index for del: ");
        String idDel = reader2.readLine();

      Iterator <Message> it = history.iterator();
        while (it.hasNext()){
            if(it.next().getId().equals(idDel)) {
                it.remove();
                System.out.println("DELETE");
                break;
            }
        }
        for(Message it1: history){
            System.out.println(it1.toString());
        }
        System.out.println("Enter name for search: ");
        String authSearch = reader2.readLine();
        boolean flag1 = false;
        for(Message it2: history) {
            if (it2.getAuthor().equals(authSearch)) {
                System.out.println(it2.toString());
                flag1 = true;
            }
        }
            if(!flag1) System.out.println("Can't found");
        System.out.println("Enter word for search: ");
        String wordSearch = reader2.readLine();
        boolean flag2 = false;
        for(Message it2: history) {
            if (it2.getMessage().contains(wordSearch)) {
                System.out.println(it2.toString());
                flag2 = true;
            }
        }
        if(!flag2) System.out.println("Can't found");

    }

    }





