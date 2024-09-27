import java.io.*;

public class Main {
    public static void main(String[] args)throws IOException{
        String file = UI.getFile();
        BMPReader bmpReader = new BMPReader(file);
        bmpReader.bmpReport();
        UI.showReport(bmpReader);
    }
}