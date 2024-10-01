import java.io.*;

public class FileMerger {

    public static final String FILE_NAME = "sorted.txt";

    public void mergeSortedFiles(String file1, String file2) {

        try (BufferedReader reader1 = new BufferedReader(new FileReader(file1));
             BufferedReader reader2 = new BufferedReader(new FileReader(file2));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))){

            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null || line2 != null) {
                if (line1 != null && line2 != null) {
                    if (line1.compareTo(line2) < 0) {
                        writer.write(line1);
                        writer.newLine();
                        line1 = reader1.readLine();
                    }
                    else if (line1.compareTo(line2) == 0) {
                        writer.write(line1);
                        writer.newLine();
                        line1 = reader1.readLine();
                        line2 = reader2.readLine();
                    }
                    else {
                        writer.write(line2);
                        writer.newLine();
                        line2 = reader2.readLine();
                    }
                }
                else if (line1 != null) {
                    writer.write(line1);
                    writer.newLine();
                    line1 = reader1.readLine();
                }
                else {
                    writer.write(line2);
                    writer.newLine();
                    line2 = reader2.readLine();
                }
            }
        }
        catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
