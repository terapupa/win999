package guidezup.win999;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);
    public static final String UNKNOWN = "unknown";


    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        return mapper;
    }

    public static String getParam1() {
        return ServerProperties.getInstance().getParam1();
    }

    public static String getParam2() {
        return ServerProperties.getInstance().getParam2();
    }

    public static String getAuthUrl() {
        return ServerProperties.getInstance().getAuthUrl();
    }

    public static String getBaseUrl() {
        return ServerProperties.getInstance().getBaseUrl();
    }

    public static String getIdFromUrlString(String urlString) {
        return StringUtils.substringAfterLast(urlString, "/");
    }


}
