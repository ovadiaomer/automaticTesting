package applango.common;

//import org.apache.commons.logging.impl.Log4JLogger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

//import java.util.logging.Logger;

public class SeleniumTestBase {

    public static final Logger logger = LogManager.getLogger(SeleniumTestBase.class.getName());


    @BeforeClass
    public static void setUpBase() {
//        Logger logger = Logger.getLogger(S.getClass());
//        String log4JPropertyFile = "C:/this/is/my/config/path/log4j.properties";
//        Properties p = new Properties();


//        try {
//            p.load(new FileInputStream(log4JPropertyFile));
//            PropertyConfigurator.configure(p);
//            logger.info("Wow! I'm configured!");
//        } catch (IOException e) {
//            //DAMN! I'm not....
//
//        }

// PropertyConfigurator.configure(getServletContext().getRealPath("/"));

        logger.info("SetUp");

    }

    @Test
    public void test1() {
        logger.info("TestNo1");
    }

    @AfterClass
    public static void tearDownBase() {
        logger.info("TearDown");
    }


}
