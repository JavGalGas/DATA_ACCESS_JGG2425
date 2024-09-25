import java.io.FileInputStream;
import java.io.IOException;

public class BMPReader {

    private final String filePath;
    private int bmpFileSize;
    private int bitMapInfoHeaderSize;
    private int width;
    private int height;
    private int numPlanes;
    private int numBitsPixel;
    private String compressionType;
    private int imgSize;
    private int horizontalResolution;
    private int verticalResolution;
    private int numColors;
    private int numImportantColors;
    private final byte[] bmpSignature = {(byte) 0x42, (byte) 0x4D};

    BMPReader(String filePath) {
        this.filePath = filePath;
    }

    public void bmpReport() throws IOException{
        FileInputStream bmpFile = new FileInputStream("");
        byte[] header = new byte[56];
        try {
            bmpFile = new FileInputStream(filePath);
            bmpFile.read(header);
            if (isBMP(header))
            {
                bmpFileSize = byteArrayToInt(new byte[]{header[2], header[3], header[4], header[5]});
                bitMapInfoHeaderSize = byteArrayToInt(new byte[]{header[14], header[15], header[16], header[17]});
                width = byteArrayToInt(new byte[]{header[18], header[19], header[20], header[21]});
                height = byteArrayToInt(new byte[]{header[22], header[23], header[24], header[25]});
                numPlanes = byteArrayToInt(new byte[]{header[26], header[27]});
                numBitsPixel = byteArrayToInt(new byte[]{header[28], header[29]});
                compressionType = setCompressionType(new byte[]{header[30], header[31], header[32], header[33]});
                imgSize = byteArrayToInt(new byte[]{header[34], header[35], header[36], header[37]});
                horizontalResolution = byteArrayToInt(new byte[]{header[38], header[39], header[40], header[41]});
                verticalResolution = byteArrayToInt(new byte[]{header[42], header[43], header[44], header[45]});
                numColors = byteArrayToInt(new byte[]{header[46], header[47], header[48], header[49]});
                numImportantColors = byteArrayToInt(new byte[]{header[50], header[51], header[52], header[53]});
            }
        }
        catch (IOException exception) {
            System.out.println("A problem occurred: " + exception.getMessage());
        }
        finally {
            bmpFile.close();
        }
    }

    private Boolean isBMP(byte[] extension) {
        return extension[0] == bmpSignature[0] && extension[1] == bmpSignature[1];
    }

    //Byte.toUnsignedInt(); --> Para que no haya problemas con el cálculo

    private int byteArrayToInt(byte[] array){
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            int aux = Byte.toUnsignedInt(array[i]);
            aux = aux * (int) Math.pow(256,i);
            result += aux;
        }
        return result;
    }

    private String setCompressionType (byte[] array) {
        int aux = byteArrayToInt(array);
        return switch (aux) {
            case 0 -> "none";
            case 1 -> "RLE-8";
            case 2 -> "RLE-4";
            default -> "";
        };
    }

}
