package grachat;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.*;
import java.util.Date;


public class Chat {
    private ArrayList<Message> history;
    private BufferedReader reader;
    private Integer delCount;
    private Integer addCount;
    private MyLoger log;
    private Searcher search;

    public Chat() {
        delCount = 0;
        addCount = 0;
        history = new ArrayList<>();
        log = new MyLoger("logiprogi.txt");
        search = new Searcher(log, history);
        log.writeLog("---------------------------------------" + "" + "\n" +
                "Start GRACHCHAT " + new Timestamp(System.currentTimeMillis()) + "\n");

    }

    public void chooser() {
        try {
            BufferedReader chooseReader = new BufferedReader(new InputStreamReader(System.in));
            String choose = "";
            while (!choose.equals(".exit")) {
                System.out.print("GRACHAT:> ");
                choose = chooseReader.readLine();

                switch (choose) {
                    case ".help":
                        help();
                        break;
                    case ".load":
                        load();
                        break;
                    case ".save":
                        save();
                        break;
                    case ".add":
                        addMessage();
                        break;
                    case ".del":
                        delMessage();
                        break;
                    case ".show":
                        show();
                        break;
                    case ".search.a":
                        search.searchAuthor();
                        break;
                    case ".search.w":
                        search.searchWord();
                        break;
                    case ".search.r":
                        search.searchReg();
                        break;
                    case ".search.t":
                        search.searchTime();
                        break;
                    case ".exit":
                        System.out.println("Good luck!");
                        this.endChat();
                        break;
                    default:
                        System.out.println("\"" + choose + "\"" + " is not recognized as an internal or external command, operable program or batch file. " + "\n" +
                                "use .help");
                        log.writeLog("\n" + "\"" + choose + "\"" + " is not recognized as an internal " +
                                "or external command, operable program or batch file. ");
                        break;
                }
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }

    public void show() {
        log.writeLog("\n" + "Command \".show\"");
        for (Message it : history) {
            System.out.println(it.toString());
        }
        if (history.isEmpty()) {
            System.out.println("Empty history");
            log.writeLog(" Empty history");
        } else {
            log.writeLog(" completed successfully, show: " + (history.size() - 1) + " messages ");
        }
    }

    public void delMessage() {
        if (history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        log.writeLog("\n" + "Command \".del\"");
        try {
            System.out.println("Enter index for del: ");
            boolean flag = false;
            reader = new BufferedReader(new InputStreamReader(System.in));
            String idDel = reader.readLine();
            Iterator<Message> it = history.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(idDel)) {
                    it.remove();
                    System.out.println("Delete");
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                log.writeLog(" not found delete id: " + idDel + " message ");
            } else {
                log.writeLog(" completed successfully, delete id: " + idDel + " message ");
                delCount++;
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }

    public void addMessage() {

        try {
            Date date = new Date();
            log.writeLog("\n" + "Command \".add\"");
            System.out.println("Enter name: ");
            String name = "";
            boolean flag = false;
            reader = new BufferedReader(new InputStreamReader(System.in));
            while (name.equals("")) {
                name = reader.readLine();
                if (!name.equals("")) {
                    flag = true;
                }
                if (!flag) {
                    log.writeLog("\n" + " warning! Empty name! ");
                    System.out.println("Enter not empty name,pls: ");
                }
            }
            System.out.println("Enter message: ");
            String mes = reader.readLine();
            long timest = date.getTime();
            if (!history.isEmpty()) {
                Integer tempPer = Integer.parseInt(history.get(history.size() - 1).getId()) + 1;
                String tempId = tempPer.toString();
                history.add(new Message(tempId, name, timest, mes));
                log.writeLog(" completed successfully, add id: " + tempId + " message ");
                addCount++;
            } else {
                Integer tempPer = 1;
                String tempId = tempPer.toString();
                history.add(new Message(tempId, name, timest, mes));
                log.writeLog(" completed successfully, add id: " + tempId + " message ");
                addCount++;
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }

    public void save() {
        log.writeLog("\n" + "Command \".save\"");
        Gson gson = new GsonBuilder().create();
        try {
            Writer writerOut = new FileWriter("input.json");
            gson.toJson(history, writerOut);
            writerOut.close();
            log.writeLog(" completed successfully");
        } catch (IOException e) {
            log.writeLog("\n" + "Input stream (save) error");
            System.out.println("Input stream (save) error" + e);
        }

    }

    public void load() {
        log.writeLog("\n" + "Command \".load\"");
        try {
            history.clear();
            Gson gson = new GsonBuilder().create();
            Reader reader = new InputStreamReader(new FileInputStream("input.json"));
            Message[] mesArr = gson.fromJson(reader, Message[].class);
            Collections.addAll(history, mesArr);
        } catch (IOException e) {
            log.writeLog("\n" + "Input stream error " + e);
            System.out.println("Input stream error " + e);
        }

        if (!history.isEmpty()) {
            log.writeLog(" completed successfully" + ", size: " + (history.size() - 1));
            System.out.println("Load completed successfully" + " size: " + (history.size() - 1));
        } else {
            log.writeLog(" empty file");
            System.out.println("Empty file");
        }
    }

    public void startInfo() {
        System.out.print("---- Welcom to GRACHAT ----" + "\n" +
                "Type \".help\" and press \"enter\" for information " + "\n");
    }

    public void help() {
        log.writeLog("\n" + "Command \".help\"");
        System.out.println(".load  " + ".save  " + ".add  " + ".del  " + ".show  " +
                "\n" + ".search.a  " + ".search.w  " + ".search.r  " + ".search.t"
                + "\n" + ".help  " + ".exit  ");
    }

    public void startChat() {
        startInfo();
        chooser();
    }

    public void endChat() {
        log.writeLog("\n" + "-------" + "\n"
                + "Session statistic: " + "Delete: " + delCount + " messages | Add: " + addCount + " messages" +
                "\n" + "End GRACHCHAT " + "\n");
        log.closeLog();
    }
}











