import java.util.Scanner;

public class UI {

    public static String getFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce the bmp file path: ");
        return scanner.next();
    }

    public static void showReport(BMPReader reader) {
        System.out.println("======= BMP File Report =======");
        System.out.println("File Size: " + reader.getFileSize());
        System.out.println("Bitmap Info Header Size: " + reader.getBitMapInfoHeaderSize());
        System.out.println("Width: " + reader.getWidth());
        System.out.println("Height: " + reader.getHeight());
        System.out.println("Planes: " + reader.getNumPlanes());
        System.out.println("Bits Per Pixel: " + reader.getNumBitsPixel());
        System.out.println("Compression Type: " + reader.getCompressionType());
        System.out.println("Image Size: " + reader.getImgSize());
        System.out.println("Horizontal Resolution: " + reader.getHorizontalResolution());
        System.out.println("Vertical Resolution: " + reader.getVerticalResolution());
        System.out.println("Number Of Colors: " + reader.getNumColors());
        System.out.println("Number Of Important Colors: " + reader.getNumImportantColors());
        System.out.println("===============================");
    }
}
