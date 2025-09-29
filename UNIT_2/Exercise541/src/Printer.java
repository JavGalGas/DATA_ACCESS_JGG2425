import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Printer {

    public static final String TXT = ".txt";
    public static final String NEW_LINE = "\\n";

    public void executePrinter() {
            String name = UI.askForFileName() + TXT;
            boolean overwrite = true;

            if(new File (name).exists()) {
                overwrite = UI.askForOverwrite();
            }

            try (PrintWriter printWriter =
                         new PrintWriter(new BufferedWriter(new FileWriter(name, overwrite)))) {
                String line = UI.askForLine();
                try(Stream<String> lines = Files.lines(Paths.get(name))) {
                    long numLines = lines.count();
                    while (!line.equals(NEW_LINE)) {
                        numLines++;
                        printWriter.println(numLines + "     " + line);
                        line = UI.askForLine();
                    }
                }
            }
            catch ( IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

}
