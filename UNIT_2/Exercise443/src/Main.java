import java.io.FileInputStream;
import java.util.Scanner;
import java.io.*;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.next();
        BMPReader b = new BMPReader(filePath);

    }
}