package applango.common.services.Mappers;

import applango.common.enums.generic.jsonMaps;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class objectMapper {
    private static String configPropertiesPath = "data/config.properties";

    public static Map getObjectMap(jsonMaps jsonMapObject) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String envUrl = jsonMapObject.getValue().toString();
        return mapper.readValue(Thread.currentThread().getContextClassLoader().getResource(jsonMapObject.getValue().toString()), Map.class);
    }

    public static Map getConfigProperties() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Thread.currentThread().getContextClassLoader().getResource(configPropertiesPath), Map.class);
    }

}
