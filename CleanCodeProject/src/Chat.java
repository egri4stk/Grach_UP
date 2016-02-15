import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class Chat {
    public static void main(final String[] args) throws IOException {
        startUp();
        ArrayList<Message> history = new ArrayList<Message>();
        FileWriter writer = new FileWriter("logiprogi.txt",true);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));

        writer.write("---------------------------------------"+"" +"\n"+
                "Start GRACHCHAT "+  new Timestamp(System.currentTimeMillis())+"\n");
       try{ chooser(history, reader2, writer);}
       catch (IOException e){
           writer.write("IOException"+e.getMessage()+"\n");
       }
        catch (ArrayIndexOutOfBoundsException e) {
            writer.write("ArrayIndex Exception "+"\n");
        }

        writer.write("\n"+"End GRACHCHAT "+"\n");
        writer.close();

        }



    public static void searchChose(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {
        System.out.println("Input .a /.w /.r or .t to choose type of searching" +
                "\n" + "(author, word, regularEx or time");
        BufferedReader chooseReader2 = new BufferedReader(new InputStreamReader(System.in));
        String choose ;
        System.out.println("GRACHAT:>Search_param:>");
        choose = chooseReader2.readLine();

            switch (choose) {
                case ".a":
                   searchAuthor(history,reader2,writer);
                    break;
                case ".w":
                   searchWord(history,reader2,writer);
                    break;
                case ".r":
                    searchReg(history,reader2, writer);
                    break;
                case ".t":
                    searchTime(history,writer);
                    break;
                default:
                    System.out.println("\"" + choose + "\"" + " is not recognized as an internal " +
                            "or external command, operable program or batch file. " + "\n" +
                            "use .a / .w / .r or .t");
                    writer.write("\"" + choose + "\"" + " is not recognized as an internal " +
                            "or external command, operable program or batch file. " + "\n" );
                    break;
            }
    }

    public static void chooser(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {
        BufferedReader chooseReader = new BufferedReader(new InputStreamReader(System.in));
        String choose="";
        while (!choose.equals(".exit")) {
            System.out.print("GRACHAT:> ");
            choose = chooseReader.readLine();

           switch (choose) {
                case ".help":
                    writer.write(help());
                    break;
                case ".load":
                    Gson gson = load(history,writer);
                    break;
                case ".save":
                    save(history, writer);
                    break;
                case ".add":
                    addMessage(history, reader2,writer);
                    break;
                case ".del":
                    delMessage(history, reader2,writer);
                    break;
                case ".show":
                    show(history,writer);
                    break;
                case ".search":
                    searchChose(history, reader2,writer);
                    break;
                case ".search.a":
                    searchAuthor(history, reader2,writer);
                    break;
                case ".search.w":
                    searchWord(history, reader2,writer);
                    break;
                case ".search.r":
                    searchReg(history, reader2,writer);
                    break;
                case ".search.t":
                    searchTime(history,writer);
                    break;
                case ".exit":
                    System.out.println("Good luck!");
                    break;
                default:
                    System.out.println("\"" + choose + "\"" + " is not recognized as an internal or external command, operable program or batch file. "+"\n"+
                    "use .help");
                    writer.write("\"" + choose + "\"" + " is not recognized as an internal " +
                            "or external command, operable program or batch file. " + "\n" );
                    break;
            }
        }
    }

    public static void searchTime(ArrayList<Message> history, FileWriter writer) throws IOException{

        writer.write("\n"+"Command \".search.w\"");
        boolean flag1 = false;
        int count = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Messages between [date1; date2]:");
        System.out.println("input [date1]: dd/mm/yyyy HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            date1 = format1.parse(sc.nextLine());
        } catch (ParseException e) {
            writer.write(e.getMessage()+"\n");


        }
        System.out.println("input [date2]: dd/mm/yyyy HH:mm:ss");
        Date date2 = null;
        try {date2 = format1.parse(sc.nextLine());
        } catch (ParseException e) {
            writer.write(e.getMessage()+"\n");
        }
        for (Message it : history) {
            if (it.getDate().after(date1) && it.getDate().before(date2)) {
                System.out.println(it.toString());
                flag1 = true;
                count ++;
            }
        }
        if (flag1 == false) {
            System.out.println("Can't found");
            writer.write("Can't found");
        }
        else {
            writer.write(" completed successfully, found: " + count + " messages ");
        }
    }

    public static void searchReg(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {

        System.out.println("Enter regular expression");

        String expression = reader2.readLine();
        writer.write("\n"+"Command \".search.w\"");
        boolean flag1 = false;
        int count = 0;
        for (Message it2 : history) {
            Pattern pa = Pattern.compile(expression);
            Matcher ma = pa.matcher(it2.getMessage());
            if (ma.find()) {
                flag1 = true;
                System.out.println(it2.toString());
                count++;
            }
        }
        if (flag1 == false) {
            System.out.println("Can't found");
            writer.write(" Can't found");
        }
        else {
            writer.write(" completed successfully, found: " + count + " messages ");
        }
    }

    public static void searchWord(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {

        System.out.println("Enter word for search: ");
        String wordSearch = reader2.readLine();
        writer.write("\n"+"Command \".search.w\"");
        boolean flag1 = false;
        int count = 0;
        for (Message it2 : history) {
            if (it2.getMessage().contains(wordSearch)) {
                System.out.println(it2.toString());
                flag1 = true;
                count ++;
            }
        }
        if (flag1 == false) {
            System.out.println("Can't found");
            writer.write(" Can't found");
        }
        else {
            writer.write(" completed successfully, found: " + count + " messages ");
        }
    }

    public static void searchAuthor(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {

        writer.write("\n"+"Command \".search.a\"");
        System.out.println("Enter name for search: ");
        String authSearch = reader2.readLine();
        boolean flag1 = false;
        int count = 0;
        for (Message it2 : history) {
            if (it2.getAuthor().equals(authSearch)) {
                System.out.println(it2.toString());
                flag1 = true;
                count ++;
            }
        }
        if (flag1 == false) {
            System.out.println("Can't found");
            writer.write(" Can't found");
        }
        else {
            writer.write(" completed successfully, found: " + count + " messages ");
        }
        }


    public static void show(ArrayList<Message> history, FileWriter writer) throws  IOException{
        writer.write("\n"+"Command \".show\"");
        for (Message it : history) {
            System.out.println(it.toString());
        }
        if(history.isEmpty()){
            System.out.println("Empty history");
            writer.write("Empty history");
        }
        else {
            writer.write(" completed successfully, show: " + (history.size()-1) + " messages ");
        }
        }


    public static void delMessage(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException {
        if(history.isEmpty()) {
            System.out.println("History is empty");
            return;
        }
        writer.write("\n"+"Command \".del\"");
        System.out.println("Enter index for del: ");
        boolean flag = false;
        String idDel = reader2.readLine();
        Iterator<Message> it = history.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(idDel)) {
                it.remove();
                System.out.println("Delete");
                flag = true;
                break;
            }
        }
            if(flag == false){
                writer.write(" not found delete id: "+idDel+" message ");
            }
            else writer.write(" completed successfully, delete id: " + idDel + " message ");
        }


    public static void addMessage(ArrayList<Message> history, BufferedReader reader2, FileWriter writer) throws IOException, NumberFormatException {
        Date date = new Date();
        writer.write("\n"+"Command \".add\"");
        System.out.println("Enter name: ");
        String name = reader2.readLine();
        System.out.println("Enter message: ");
        String mes = reader2.readLine();
        long timest = date.getTime();
        if(!history.isEmpty()) {
            Integer tempPer = Integer.parseInt(history.get(history.size() - 1).getId()) + 1;
            String tempId = tempPer.toString();
            history.add(new Message(tempId, name, timest, mes));
            writer.write(" completed successfully, add id: " + tempId + " message ");
        }
        else {
            Integer tempPer = 1;
            String tempId = tempPer.toString();
            history.add(new Message(tempId, name, timest, mes));
            writer.write(" completed successfully, add id: " + tempId + " message ");

        }

    }

    public static void save(ArrayList<Message> history, FileWriter writer) throws IOException {
        writer.write("\n"+"Command \".save\"");
        Writer writerOut = new FileWriter("input.json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(history, writerOut);
        writerOut.close();
        writer.write(" completed successfully");
    }

    public static Gson load(ArrayList<Message> history,FileWriter writer) throws FileNotFoundException, IOException {
        writer.write("\n"+"Command \".load\"");
        Reader reader = new InputStreamReader(new FileInputStream("input.json"));
        Gson gson = new GsonBuilder().create();
        Message[] p = gson.fromJson(reader, Message[].class);
        history.clear();
        for (int i = 0; i < p.length; i++) {

            history.add(p[i]);
        }

        if(!history.isEmpty()) {
            writer.write(" completed successfully" + " size: " + (history.size() - 1));
           System.out.println("Load completed successfully" + " size: " + (history.size() - 1));
        }
        else {
            writer.write(" empty file");
            System.out.println("Empty file");
        }
        return gson;

    }

    public static void startUp() {
        System.out.print("---- Welcom to GRACHAT ----" + "\n" +
                "Type \".help\" and press \"enter\" for information " + "\n");
    }

    public static String help() {
        System.out.println(".load  " + ".save  " + ".add  " + ".del  " + ".show  "  +
                ".search  " + "\n" + ".search.a  " + ".search.w  " + ".search.r  " + ".search.t"
                + "\n" + ".help  " + ".exit  ");
        return "Command \".help\"";
    }
}









