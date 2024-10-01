import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Printer {
        public void executePrinter() {
            String name = UI.askForFileName() + ".txt";
            boolean overwrite = true;

            if(new File (name).exists()) {
                overwrite = UI.askForOverwrite();
            }

            try (PrintWriter printWriter =
                         new PrintWriter(new BufferedWriter(new FileWriter(name, overwrite)))) {
                String line = UI.askForLine();
                long numLine = Files.lines(Paths.get(name)).count();

                while (!line.equals("\\n")) {
                    numLine++;
                    printWriter.println(numLine + "     " + line);
                    line = UI.askForLine();
                }
            }
            catch ( IOException exception) {
                System.out.println(exception.getMessage());
            }

        }

}
