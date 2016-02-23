package grachat;

import java.io.*;

public class MyLoger {

    private FileWriter writer;

    MyLoger(String fileName) {
        try {
            writer = new FileWriter(fileName, true);
        } catch (IOException e) {
            System.out.println("Loger error: writer is not created");
        }
    }

    public void writeLog(String str) {
        try {
            writer.write(str);
        } catch (IOException e) {
            System.out.println("FileWriter exception " + e);
        }
    }

    public void closeLog() {
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("FileWriter exception " + e);
        }
    }


}
