import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class fileSave {
    public void openWrite(String result)
    {
        try {
            File history = new File("history.txt");
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(history, true);
                BufferedWriter writer = new BufferedWriter(fileWriter);
                writer.write(result);
                writer.newLine();
                writer.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                fileWriter.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
