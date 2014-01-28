package applango.common.services.Mappers;

import applango.common.SeleniumTestBase;
import applango.common.services.beans.Database;
import applango.common.services.beans.Salesforce;
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

public class readFromConfigurationFile extends SeleniumTestBase {
    private static String configurationXmlPath;

    public static Database getDatabaseConfigurationFileByDbName(String dbName) throws IOException, ParserConfigurationException, SAXException {
        Database database = new Database();
        configurationXmlPath = "src/main/resources/data/database-configuration.xml";
        try {
            Document doc = getDocument(configurationXmlPath);

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

    private static Document getDocument(String configurationXmlPath) throws ParserConfigurationException, SAXException, IOException {
        logger.info("Get configuration data");
        File fXmlFile = new File(configurationXmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();
        return doc;
    }

    public static Salesforce getSalesfoceConfigurationFileByenvironmentId(String salesforceEnvironmentId) throws IOException, ParserConfigurationException, SAXException {
        Salesforce salesforce= new Salesforce();
        configurationXmlPath = "src/main/resources/data/salesforce-configuration.xml";
        try {

            Document doc = getDocument(configurationXmlPath);

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("environment");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (((nNode.getNodeType() == Node.ELEMENT_NODE))
                        &&
                        (nNode.getAttributes().item(0).toString().contains(salesforceEnvironmentId)))
                {
                    logger.info("Init salesforce object by salesforce-configuration.xml");
                    Element eElement = (Element) nNode;
                    salesforce.setEnvironment(nNode.getAttributes().item(0).toString());
                    salesforce.setUrl(eElement.getElementsByTagName("url").item(0).getFirstChild().getTextContent());
                    salesforce.setPassword(eElement.getElementsByTagName("password").item(0).getFirstChild().getTextContent());
                    salesforce.setUsername(eElement.getElementsByTagName("username").item(0).getFirstChild().getTextContent());
                    salesforce.setAccessToken(eElement.getElementsByTagName("accessToken").item(0).getFirstChild().getTextContent());
                    salesforce.setLoginUrl(eElement.getElementsByTagName("loginUrl").item(0).getFirstChild().getTextContent());
                    salesforce.setClientSecret(eElement.getElementsByTagName("clientSecret").item(0).getFirstChild().getTextContent());
                    salesforce.setClientId(eElement.getElementsByTagName("clientId").item(0).getFirstChild().getTextContent());
                    salesforce.setClientLogin(eElement.getElementsByTagName("clientLogin").item(0).getFirstChild().getTextContent());
                    salesforce.setSecurityToken(eElement.getElementsByTagName("securityToken").item(0).getFirstChild().getTextContent());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        return salesforce;
    }



}
