import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 20/10/13
 * Time: 16:32
 * To change this template use File | Settings | File Templates.
 */
public class Logger {

    public static void main (String[] args) {

        java.util.logging.Logger logger = java.util.logging.Logger.getLogger("AutomaticTestsLog");
        FileHandler fh;

        try {
            fh = new FileHandler("c:/AutomationLog/Index.logger");
            logger.info(String.valueOf(fh));
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Test status logger");

        }


        catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
