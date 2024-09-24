import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BMPReader {

    private final String filePath;
    private final byte[] bmp = {(byte) 0x42, (byte) 0x4D};
    BMPReader(String filePath) {
        this.filePath = filePath;
    }

    private Boolean CheckExtension(byte[] extension) {
        return extension[0] == bmp[0] && extension[1] == bmp[1];
    }

    public void BMPReport() throws IOException{
        FileInputStream bmpFile = new FileInputStream("");
        byte[] signature = new byte[2];
        try {
            bmpFile = new FileInputStream(filePath);
            int bytesRead = bmpFile.read(signature);
            if (bytesRead < 2) {
                throw new IOException("File is too small");
            }
            if (CheckExtension(signature)) {

            }
            else {
                throw new IOException("Incorrect type of file");
            }

        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        finally {
            bmpFile.close();
        }
    }

    //Byte.toUnsignedInt(); --> Para que no haya problemas con el cálculo

}
