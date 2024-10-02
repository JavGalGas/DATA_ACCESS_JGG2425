import java.io.*;

public class FileMerger {

    public static final String FILE_NAME = "sorted.txt";
    public static final String ERROR_HAS_OCCURRED = "An error has occurred: ";

    public void mergeSortedFiles(String file1, String file2) {

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2));
             PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(FILE_NAME, false)))){

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null && line2 != null) {
                int comparison = line1.compareTo(line2);
                if (comparison > 0) {
                    printer.println(line2);
                    line2 = reader2.readLine();
                }
                else if (comparison < 0) {
                    printer.println(line1);
                    line1 = reader1.readLine();
                }
                else {
                    printer.println(line1);
                    line1 = reader1.readLine();
                    line2 = reader2.readLine();
                }
            }

            while (line1 != null) {
                printer.println(line1);
                line1 = reader1.readLine();
            }

            while (line2 != null) {
                printer.println(line2);
                line2 = reader2.readLine();
            }
        }
        catch (IOException exception) {
            System.out.println(ERROR_HAS_OCCURRED + exception.getMessage());
        }
    }

}
