import java.io.FileInputStream;
import java.io.IOException;

public class BMPReader {

    private final String filePath;
    private int fileSize;
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

    private static final byte[] BMP_SIGNATURE = {(byte) 0x42, (byte) 0x4D};
    private static final int FILE_SIZE_OFFSET = 2;
    private static final int BITMAP_INFO_HEADER_SIZE_OFFSET = 14;
    private static final int WIDTH_OFFSET = 18;
    private static final int HEIGHT_OFFSET = 22;
    private static final int PLANES_OFFSET = 26;
    private static final int BITS_PER_PIXEL_OFFSET = 28;
    private static final int COMPRESSION_TYPE_OFFSET = 30;
    private static final int IMG_SIZE_OFFSET = 34;
    private static final int HORIZONTAL_RESOLUTION_OFFSET = 38;
    private static final int VERTICAL_RESOLUTION_OFFSET = 42;
    private static final int NUM_COLORS_OFFSET = 46;
    private static final int NUM_IMPORTANT_COLORS_OFFSET = 50;

    BMPReader(String filePath) {
        this.filePath = filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getBitMapInfoHeaderSize() {
        return bitMapInfoHeaderSize;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumPlanes() {
        return numPlanes;
    }

    public int getNumBitsPixel() {
        return numBitsPixel;
    }

    public String getCompressionType() {
        return compressionType;
    }

    public int getImgSize() {
        return imgSize;
    }

    public int getHorizontalResolution() {
        return horizontalResolution;
    }

    public int getVerticalResolution() {
        return verticalResolution;
    }

    public int getNumColors() {
        return numColors;
    }

    public int getNumImportantColors() {
        return numImportantColors;
    }

    public void bmpReport() throws IOException{
        FileInputStream bmpFile = new FileInputStream("");
        byte[] header = new byte[56];
        try {
            bmpFile = new FileInputStream(filePath);
            bmpFile.read(header);
            if (isBMP(header))
            {
                fileSize = byteArrayToInt(new byte[]{header[FILE_SIZE_OFFSET],
                        header[FILE_SIZE_OFFSET + 1], header[FILE_SIZE_OFFSET + 2],
                        header[FILE_SIZE_OFFSET + 3]});
                bitMapInfoHeaderSize = byteArrayToInt(new byte[]{header[BITMAP_INFO_HEADER_SIZE_OFFSET],
                        header[BITMAP_INFO_HEADER_SIZE_OFFSET + 1],
                        header[BITMAP_INFO_HEADER_SIZE_OFFSET + 2],
                        header[BITMAP_INFO_HEADER_SIZE_OFFSET + 3]});
                width = byteArrayToInt(new byte[]{header[WIDTH_OFFSET], header[WIDTH_OFFSET + 1],
                        header[WIDTH_OFFSET + 2], header[WIDTH_OFFSET]});
                height = byteArrayToInt(new byte[]{header[HEIGHT_OFFSET], header[HEIGHT_OFFSET + 1],
                        header[HEIGHT_OFFSET + 2], header[HEIGHT_OFFSET + 3]});
                numPlanes = byteArrayToInt(new byte[]{header[PLANES_OFFSET], header[PLANES_OFFSET + 1]});
                numBitsPixel = byteArrayToInt(new byte[]{header[BITS_PER_PIXEL_OFFSET],
                        header[BITS_PER_PIXEL_OFFSET + 1]});
                compressionType = setCompressionType(new byte[]{header[COMPRESSION_TYPE_OFFSET],
                        header[COMPRESSION_TYPE_OFFSET + 1], header[COMPRESSION_TYPE_OFFSET + 2],
                        header[COMPRESSION_TYPE_OFFSET + 3]});
                imgSize = byteArrayToInt(new byte[]{header[IMG_SIZE_OFFSET], header[IMG_SIZE_OFFSET + 1],
                        header[IMG_SIZE_OFFSET + 2], header[IMG_SIZE_OFFSET + 3]});
                horizontalResolution = byteArrayToInt(new byte[]{header[HORIZONTAL_RESOLUTION_OFFSET],
                        header[HORIZONTAL_RESOLUTION_OFFSET + 1], header[HORIZONTAL_RESOLUTION_OFFSET + 2],
                        header[HORIZONTAL_RESOLUTION_OFFSET + 3]});
                verticalResolution = byteArrayToInt(new byte[]{header[VERTICAL_RESOLUTION_OFFSET],
                        header[VERTICAL_RESOLUTION_OFFSET + 1], header[VERTICAL_RESOLUTION_OFFSET + 2],
                        header[VERTICAL_RESOLUTION_OFFSET + 3]});
                numColors = byteArrayToInt(new byte[]{header[NUM_COLORS_OFFSET],
                        header[NUM_COLORS_OFFSET + 1], header[NUM_COLORS_OFFSET + 2],
                        header[NUM_COLORS_OFFSET + 3]});
                numImportantColors = byteArrayToInt(new byte[]{header[NUM_IMPORTANT_COLORS_OFFSET],
                        header[NUM_IMPORTANT_COLORS_OFFSET + 1], header[NUM_IMPORTANT_COLORS_OFFSET + 2],
                        header[NUM_IMPORTANT_COLORS_OFFSET + 3]});
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
        return extension[0] == BMP_SIGNATURE[0] && extension[1] == BMP_SIGNATURE[1];
    }

    //Byte.toUnsignedInt(); --> Para que no haya problemas con el cálculo

    private int byteArrayToInt(byte[] array){
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            int aux = Byte.toUnsignedInt(array[i]);
            result += aux << (8 * i);
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
