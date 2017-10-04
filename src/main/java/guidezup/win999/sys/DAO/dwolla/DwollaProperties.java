package guidezup.win999.sys.DAO.dwolla;

import com.sun.jersey.core.util.Base64;
import guidezup.win999.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static guidezup.win999.Utils.UNKNOWN;

public class DwollaProperties {
    private static final Logger log = LoggerFactory.getLogger(DwollaProperties.class);
    private static final String authUrl = "authUrl";
    private static final String baseUrl = "baseUrl";
    private static final String param1 = "param1";
    private static final String param2 = "param2";
    private static final String microDepositeUrl = "microDepositeUrl";

    private static volatile DwollaProperties instance = null;

    private Properties properties;


    public static DwollaProperties getInstance() {
        if (instance == null) {
            synchronized (DwollaProperties.class) {
                if (instance == null) {
                    instance = new DwollaProperties();
                }
            }
        }
        return instance;
    }

    private DwollaProperties() {
        properties = new Properties();
        InputStream in = null;
        try {
            in = Utils.class.getResourceAsStream("/dwolla.properties");
            properties.load(in);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private String getParam(String param) {
        String prop = properties.getProperty(param, UNKNOWN);
        if (UNKNOWN.equals(prop)) {
            log.error("Property {} not found!!!", param);
            return prop;
        } else {
            return Base64.base64Decode(prop);
        }
    }

    public String getParam1() {
        return getParam(param1);
    }

    public String getParam2() {
        return getParam(param2);
    }

    public String getAuthUrl() {
        return getStringProp(authUrl);
    }

    public String getBaseUrl() {
        return getStringProp(baseUrl);
    }

    public String getMicroDepositeUrl() {
        return getStringProp(microDepositeUrl);
    }



    private String getStringProp(String propName) {
        String prop = properties.getProperty(propName, UNKNOWN);
        if (UNKNOWN.equals(prop)) {
            log.error("Property {} not found!!!", propName);
        }
        return prop;
    }


}
