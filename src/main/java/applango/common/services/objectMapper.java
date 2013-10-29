package applango.common.services;

import applango.common.tests.SanityTest;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: omer.ovadia
 * Date: 22/10/13
 * Time: 14:41
 * To change this template use File | Settings | File Templates.
 */
public class objectMapper {



    public static Map getAppObjectMap() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL envURL = SanityTest.class.getClassLoader().getResource("mappers/appObjectsMap.json");
        return mapper.readValue(envURL, Map.class);
    }

    public static Map getConfigProperties() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL envURL = SanityTest.class.getClassLoader().getResource("data/config.properties");
        return mapper.readValue(envURL, Map.class);
    }



}
