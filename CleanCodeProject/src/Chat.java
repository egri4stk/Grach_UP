import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


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
        Writer writer = new FileWriter("Output.json");
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        chooser(history, reader2);

    }

    public static void chooser(ArrayList<Message> history, BufferedReader reader2) throws IOException {
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
                    Gson gson = load(history);
                    break;
                case ".save":
                    save(history);
                    break;
                case ".add":
                    addMessage(history, reader2);
                    break;
                case ".del":
                    delMessage(history, reader2);
                    break;
                case ".show":
                    show(history);
                    break;

                case ".search":

                    break;
                case ".search.a":
                    searchAuthor(history, reader2);
                    break;
                case ".search.w":
                    searchWord(history, reader2);
                    break;
                case ".search.r":
                    searchReg(history, reader2);
                    break;
                case ".search.t":
                    searchTime(history);
                    break;
                case ".exit":
                    System.out.println("Good luck!");
                    break;
                default:
                    System.out.println("\"" + choose + "\"" + " is not recognized as an internal or external command, operable program or batch file. "+"\n"+
                    "use .help");
                    break;

            }

        }
    }

    public static void searchTime(ArrayList<Message> history) {
        boolean ifFind = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("input start time in format: MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat start = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date stDat = null;
        try {
            stDat = start.parse(sc.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("input end time in format: MM/dd/yyyy HH:mm:ss");
        SimpleDateFormat end = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date enDat = null;
        try {
            enDat = end.parse(sc.nextLine());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Message iter : history) {
            if (iter.getDate().after(stDat) && iter.getDate().before(enDat)) {
                System.out.println(iter.toString());
                ifFind = true;
            }
        }
        if (ifFind)
            System.out.println("Successfully done");
        else System.out.println("No message of this period");
    }

    public static void searchReg(ArrayList<Message> history, BufferedReader reader2) throws IOException {
        System.out.println("Enter regular expression");

        String expression = reader2.readLine();
        boolean find = false;
        for (Message it2 : history) {
            Pattern pa = Pattern.compile(expression);
            Matcher ma = pa.matcher(it2.getMessage());
            if (ma.find()) {
                find = true;
                System.out.println(it2.toString());
            }
        }
        if (find == false) {
            System.out.println("History hasn't messages with this regular expression");
        }
    }

    public static void searchWord(ArrayList<Message> history, BufferedReader reader2) throws IOException {
        System.out.println("Enter word for search: ");
        String wordSearch = reader2.readLine();
        boolean flag2 = false;
        for (Message it2 : history) {
            if (it2.getMessage().contains(wordSearch)) {
                System.out.println(it2.toString());
                flag2 = true;
            }
        }
        if (!flag2) System.out.println("Can't found");
    }

    public static void searchAuthor(ArrayList<Message> history, BufferedReader reader2) throws IOException {
        System.out.println("Enter name for search: ");
        String authSearch = reader2.readLine();
        boolean flag1 = false;
        for (Message it2 : history) {
            if (it2.getAuthor().equals(authSearch)) {
                System.out.println(it2.toString());
                flag1 = true;
            }
        }
        if (!flag1) System.out.println("Can't found");
    }

    public static void show(ArrayList<Message> history) {
        for (Message it : history) {
            System.out.println(it.toString());
        }
    }

    public static void delMessage(ArrayList<Message> history, BufferedReader reader2) throws IOException {
        System.out.println("Enter index for del: ");
        String idDel = reader2.readLine();

        Iterator<Message> it = history.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(idDel)) {
                it.remove();
                System.out.println("DELETE");
                break;
            }
        }
    }

    public static void addMessage(ArrayList<Message> history, BufferedReader reader2) throws IOException {
        Date date = new Date();

        System.out.println("Enter name: ");
        String name = reader2.readLine();
        System.out.println("Enter message: ");
        String mes = reader2.readLine();

        long timest = date.getTime();
        Integer tempPer = Integer.parseInt(history.get(history.size() - 1).getId()) + 1;

        String tempId = tempPer.toString();
        history.add(new Message(tempId, name, timest, mes));
    }

    public static void save(ArrayList<Message> history) throws IOException {
        Writer writerOut = new FileWriter("input.json");
        Gson gson = new GsonBuilder().create();
        gson.toJson(history, writerOut);
        writerOut.close();
    }

    public static Gson load(ArrayList<Message> history) throws FileNotFoundException {
        Reader reader = new InputStreamReader(new FileInputStream("input.json"));
        Gson gson = new GsonBuilder().create();
        Message[] p = gson.fromJson(reader, Message[].class);
        history.clear();
        for (int i = 0; i < p.length; i++) {

            history.add(p[i]);
        }
        return gson;
    }

    public static void startUp() {
        System.out.print("---- Welcom to GRACHAT ----" + "\n" +
                "Type \".help\" and press \"enter\" for information " + "\n");
    }

    public static void help() {
        System.out.println(".load  " + ".save  " + ".add  " + ".del  " + ".show  "  +
                ".search  " + "\n" + ".search.a  " + ".search.w  " + ".search.r  " + ".search.t"
                + "\n" + ".help  " + ".exit  " + "\n");
    }
}









