package applango.common.services.Mappers;

import applango.common.enums.jsonMaps;
import applango.common.tests.SanityTest;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class objectMapper {


    public static Map getObjectMap(jsonMaps jsonMapObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL envURL = SanityTest.class.getClassLoader().getResource(jsonMapObject.getValue().toString());
        return mapper.readValue(envURL, Map.class);
    }

    public static Map   getConfigProperties() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        URL envURL = SanityTest.class.getClassLoader().getResource("data/config.properties");
        return mapper.readValue(envURL, Map.class);
    }

}
