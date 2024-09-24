import java.io.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException{
        Scanner scanner = new Scanner(System.in);
        String filepath = scanner.next();
        InputStream inputStream = new FileInputStream(filepath);
        try {

            byte[] signature = new byte[8];
            int bytesRead = inputStream.read(signature);

            if (bytesRead < 8) {
                throw new IOException("File is too small to be an image.");
            }

            if (signature[0] == (byte) 0xFF && signature[1] == (byte) 0xD8 && signature[2] == (byte) 0xFF) {
                System.out.println("JPEG");
            }
            else if (signature[0] == (byte) 0x89 && signature[1] == (byte) 0x50 && signature[2] == (byte) 0x4E &&
                    signature[3] == (byte) 0x47 && signature[4] == (byte) 0x0D && signature[5] == (byte) 0x0A &&
                    signature[6] == (byte) 0x1A && signature[7] == (byte) 0x0A) {
                System.out.println("PNG");
            }
            else if (signature[0] == (byte) 0x47 && signature[1] == (byte) 0x49 && signature[2] == (byte) 0x46 &&
                    signature[3] == (byte) 0x38 && (signature[4] == (byte) 0x39 || signature[4] == (byte) 0x37) &&
                    signature[5] == (byte) 0x61) {
                System.out.println("GIF");
            }
            else if (signature[0] == (byte) 0x42 && signature[1] == (byte) 0x4D) {
                System.out.println("BMP");
            }
            else if (signature[0] == (byte) 0x00 && signature[1] == (byte) 0x00 && signature[2] == (byte) 0x01 && signature[3] == (byte) 0x00) {
                System.out.println("ICO");
            }
            else {
                System.out.println("Unknown format");
            }
        }
        catch(IOException | NullPointerException e ) {
            System.out.println( e.getMessage() );
        }
        finally {
            inputStream.close();
        }
    }
}
