package applango.common.services.Gmail;

import applango.common.services.Mappers.objectMapper;
import applango.common.services.Mappers.readFromConfigurationFile;
import applango.common.services.beans.Gmail;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 26/03/14
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class genericGmailWebsiteActions {

    private static Map configPropertiesMapper;

    public static Gmail getGmailConfigurationXML() throws IOException, ParserConfigurationException, SAXException {
        configPropertiesMapper = objectMapper.getConfigProperties();
        //Getting the value of applango dashboard parameter from config.properties file
//        String applangoEnvironment = configPropertiesMapper.get(APPLANGO_ENVIRONMENT).toString();
        //Setting values applango-configuration.xml in applango object
        Gmail gmail = readFromConfigurationFile.getGmailConfigurationFile();
        return gmail;
    }
}
