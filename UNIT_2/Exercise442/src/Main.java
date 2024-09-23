import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.next();
        try (InputStream inputStream = new FileInputStream(filepath)) {
            byte[] signature = new byte[8];
            inputStream.read(signature);
            if (signature[0] == (byte) 0xFF && signature[1] == (byte) 0xD8 && signature[2] == (byte) 0xFF) {
                System.out.println("JPEG");
            }
            else if (signature[0] == (byte) 0x89 && signature[1] == (byte) 0x50 && signature[2] == (byte) 0x4E &&
                    signature[3] == (byte) 0x47 && signature[4] == (byte) 0x0D && signature[5] == (byte) 0x0A &&
                    signature[6] == (byte) 0x1A && signature[7] == (byte) 0x0A) {
                System.out.println("PNG");
            }
        }
        catch(IOException | NullPointerException e ) {
            System.out.println( e.getMessage() );
        }
        finally {
            if (fIn != null) {
                fIn.close();
            }
        }
    }
}
