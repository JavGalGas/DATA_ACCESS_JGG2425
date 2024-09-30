import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Printer {
        public void executePrinter() {
            String name = UI.askForFileName() + ".txt";
            boolean overwrite = true;
            File file =  new File(name);

            if(file.exists()) {
                overwrite = UI.askForOverwrite();
                String filePath = file.getPath();
            }

            try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(name, overwrite)))) {
                String line = UI.askForLine();
                long numLine = Files.lines(file.toPath()).count();

                while (!line.equals("\\n")) {
                    printWriter.println(numLine + "     " + line);
                    numLine++;
                    line = UI.askForLine();
                }

            }
            catch ( IOException exception) {
                System.out.println(exception.getMessage());
            }

        }

}
