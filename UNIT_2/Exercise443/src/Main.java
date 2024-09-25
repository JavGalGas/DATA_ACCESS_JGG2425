import java.io.*;

public class Main {
    public static void main(String[] args)throws IOException{

        String file = UI.getFile();
        BMPReader b = new BMPReader(file);
        b.bmpReport();

    }
}