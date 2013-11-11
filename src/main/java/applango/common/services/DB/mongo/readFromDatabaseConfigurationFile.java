package applango.common.services.DB.mongo;

import applango.common.SeleniumTestBase;
import applango.common.services.DB.mongo.beans.Database;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 06/11/13
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class readFromDatabaseConfigurationFile extends SeleniumTestBase {
    public static Database getDatabaseConfigurationFileByDbName(String dbName) throws IOException, ParserConfigurationException, SAXException {
        logger.info("Get database object values of " + dbName);
        Database database = new Database();
        try {
            logger.info("Get " + database + "database configuration data");
            File fXmlFile = new File("src/main/resources/data/database-configuration.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("database");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (((nNode.getNodeType() == Node.ELEMENT_NODE))
                        &&
                        (nNode.getAttributes().item(0).toString().contains(dbName)))
                {
                    logger.info("Init db object by database-configuration.xml");
                    Element eElement = (Element) nNode;
                    database.setDbName(nNode.getAttributes().item(0).toString());
                    database.setUrl(eElement.getElementsByTagName("url").item(0).getFirstChild().getTextContent());
                    database.setPort(Integer.parseInt(eElement.getElementsByTagName("port").item(0).getFirstChild().getTextContent()));
                    database.setPassword(eElement.getElementsByTagName("password").item(0).getFirstChild().getTextContent());
                    database.setUsername(eElement.getElementsByTagName("username").item(0).getFirstChild().getTextContent());

                }

            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        return database;
    }


    @Test
    public void test1() throws ParserConfigurationException, SAXException, IOException {
        Database db1 = new Database();
        db1 = getDatabaseConfigurationFileByDbName("omer-dev");
    }

}
