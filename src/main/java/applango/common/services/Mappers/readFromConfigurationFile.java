package applango.common.services.Mappers;

import applango.common.SeleniumTestBase;
import applango.common.services.beans.Applango;
import applango.common.services.beans.Database;
import applango.common.services.beans.Gmail;
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
                    database.setDbName(eElement.getElementsByTagName("dbName").item(0).getFirstChild().getTextContent());
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
                        (nNode.getAttributes().item(0).toString().toLowerCase().contains(salesforceEnvironmentId.toLowerCase())))
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
                    salesforce.setApplangoUserId(eElement.getElementsByTagName("applangoUserId").item(0).getFirstChild().getTextContent());
                    salesforce.setIsSandbox(eElement.getElementsByTagName("isSandbox").item(0).getFirstChild().getTextContent());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        return salesforce;
    }
    public static Applango getApplangoConfigurationFileByEnvironmentId(String applangoEnvironmentId) throws IOException, ParserConfigurationException, SAXException {
        Applango applango= new Applango();
        configurationXmlPath = "src/main/resources/data/applango-configuration.xml";
        try {

            Document doc = getDocument(configurationXmlPath);

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("environment");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (((nNode.getNodeType() == Node.ELEMENT_NODE))
                        &&
                        (nNode.getAttributes().item(0).toString().contains(applangoEnvironmentId)))
                {
                    logger.info("Init applango object by applango-configuration.xml");
                    Element eElement = (Element) nNode;
                    applango.setEnvironment(nNode.getAttributes().item(0).toString());
                    applango.setUrl(eElement.getElementsByTagName("url").item(0).getFirstChild().getTextContent());
                    applango.setUsername(eElement.getElementsByTagName("username").item(0).getFirstChild().getTextContent());
                    applango.setPassword(eElement.getElementsByTagName("password").item(0).getFirstChild().getTextContent());
                    applango.setPrimaryEmail(eElement.getElementsByTagName("primaryEmail").item(0).getFirstChild().getTextContent());
                    applango.setPrimaryEmailPassword(eElement.getElementsByTagName("primaryEmailPassword").item(0).getFirstChild().getTextContent());
                    applango.setUserForoAuth(eElement.getElementsByTagName("userForoAuth").item(0).getFirstChild().getTextContent());
                    applango.setPasswordForoAuth(eElement.getElementsByTagName("passwordForoAuth").item(0).getFirstChild().getTextContent());
                    applango.setCustomerForoAuth(eElement.getElementsByTagName("customerForoAuth").item(0).getFirstChild().getTextContent());
                    applango.setToolsJarServer(eElement.getElementsByTagName("toolsJarServer").item(0).getFirstChild().getTextContent());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        return applango;
    }
    public static Gmail getGmailConfigurationFile() throws IOException, ParserConfigurationException, SAXException {
        Gmail gmail= new Gmail();
        configurationXmlPath = "src/main/resources/data/gmail-configuration.xml";
        try {

            Document doc = getDocument(configurationXmlPath);

//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("environment");
//            nList.item(0).
//
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//
                Node nNode = nList.item(0);
//
//
//                if (((nNode.getNodeType() == Node.ELEMENT_NODE))
//                        &&
//                        (nNode.getAttributes().item(0).toString().contains(applangoEnvironmentId)))
//                {
                    logger.info("Init gmail object by gmail-configuration.xml");
                    Element eElement = (Element) nNode;
//                    applango.setEnvironment(nNode.getAttributes().item(0).toString());
                    gmail.setUrl(eElement.getElementsByTagName("url").item(0).getFirstChild().getTextContent());
                    gmail.setUsername(eElement.getElementsByTagName("username").item(0).getFirstChild().getTextContent());
                    gmail.setPassword(eElement.getElementsByTagName("password").item(0).getFirstChild().getTextContent());
//                    applango.setPrimaryEmail(eElement.getElementsByTagName("primaryEmail").item(0).getFirstChild().getTextContent());
//                    applango.setPrimaryEmailPassword(eElement.getElementsByTagName("primaryEmailPassword").item(0).getFirstChild().getTextContent());
//                    applango.setUserForoAuth(eElement.getElementsByTagName("userForoAuth").item(0).getFirstChild().getTextContent());
//                    applango.setPasswordForoAuth(eElement.getElementsByTagName("passwordForoAuth").item(0).getFirstChild().getTextContent());
//                    applango.setCustomerForoAuth(eElement.getElementsByTagName("customerForoAuth").item(0).getFirstChild().getTextContent());
//                }
//            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();

        }
        return gmail;
}



}
