package grachat;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Searcher {
    private MyLoger log;
    private ArrayList<Message> history;
    private BufferedReader reader;

    Searcher(MyLoger l, ArrayList<Message> h) {
        log = l;
        history = h;
    }

    public void searchTime() {

        log.writeLog("\n" + "Command \".search.w\"");
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
            log.writeLog("\n" + "Exception: " + e.getMessage());

        }
        System.out.println("input [date2]: dd/mm/yyyy HH:mm:ss");
        Date date2 = null;
        try {
            date2 = format1.parse(sc.nextLine());
        } catch (ParseException e) {
            log.writeLog("\n" + "Exception: " + e.getMessage());
        }
        for (Message it : history) {
            if ((date1 != null) && (date2 != null)) {

                if (it.getDate().after(date1) && it.getDate().before(date2)) {
                    System.out.println(it.toString());
                    flag1 = true;
                    count++;

                }
            }
        }
        if (!flag1) {
            System.out.println("Can't found");

        } else {
            log.writeLog(" completed successfully, found: " + count + " messages ");
        }
    }

    public void searchReg() {
        try {
            System.out.println("Enter regular expression");
            reader = new BufferedReader(new InputStreamReader(System.in));
            String expression = reader.readLine();
            log.writeLog("\n" + "Command \".search.w\"");
            boolean flag1 = false;
            int count = 0;
            try {
                for (Message it2 : history) {
                    Pattern pattern = Pattern.compile(expression);
                    Matcher matcher = pattern.matcher(it2.getMessage());
                    if (matcher.find()) {
                        flag1 = true;
                        System.out.println(it2.toString());
                        count++;
                    }


                }
            } catch (PatternSyntaxException e) {
                log.writeLog("\n" + "Wrong reg: " + expression);
                System.out.println("Wrong reg: " + expression);
            }
            if (!flag1) {
                System.out.println("Can't found");
                log.writeLog(" Can't found");
            } else {
                log.writeLog(" completed successfully, found: " + count + " messages ");
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }

    public void searchWord() {
        try {
            System.out.println("Enter word for search: ");
            reader = new BufferedReader(new InputStreamReader(System.in));
            String wordSearch = reader.readLine();
            log.writeLog("\n" + "Command \".search.w\"");
            boolean flag1 = false;
            int count = 0;
            for (Message it2 : history) {
                if (it2.getMessage().contains(wordSearch)) {
                    System.out.println(it2.toString());
                    flag1 = true;
                    count++;
                }
            }
            if (!flag1) {
                System.out.println("Can't found");
                log.writeLog(" Can't found");
            } else {
                log.writeLog(" completed successfully, found: " + count + " messages ");
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }

    public void searchAuthor() {
        try {

            log.writeLog("\n" + "Command \".search.a\"");
            System.out.println("Enter name for search: ");
            reader = new BufferedReader(new InputStreamReader(System.in));
            String authSearch = reader.readLine();
            boolean flag1 = false;
            int count = 0;
            for (Message it2 : history) {
                if (it2.getAuthor().equals(authSearch)) {
                    System.out.println(it2.toString());
                    flag1 = true;
                    count++;
                }
            }

            if (!flag1) {
                System.out.println("Can't found");
                log.writeLog(" Can't found");
            } else {
                log.writeLog(" completed successfully, found: " + count + " messages ");
            }
        } catch (IOException e) {
            log.writeLog("\n" + "Buffer reader error" + e);
            System.out.println("Buffer reader error" + e);
        }
    }


}
