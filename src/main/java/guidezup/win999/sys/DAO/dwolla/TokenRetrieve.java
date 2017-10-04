package guidezup.win999.sys.DAO.dwolla;

import com.fasterxml.jackson.core.type.TypeReference;
import guidezup.win999.Utils;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;

import static guidezup.win999.Utils.UNKNOWN;
import static guidezup.win999.sys.DAO.dwolla.DwollaUtils.getAuthUrl;
import static guidezup.win999.sys.DAO.dwolla.DwollaUtils.getParam1;
import static guidezup.win999.sys.DAO.dwolla.DwollaUtils.getParam2;

public class TokenRetrieve {
    private static final Logger log = LoggerFactory.getLogger(TokenRetrieve.class);

    private static final String key = getParam1();
    private static final String sec = getParam2();
    private static final String url = getAuthUrl();

    private static volatile TokenRetrieve instance = null;

    private AtomicReference<String> token = new AtomicReference<String>(UNKNOWN);

    public static TokenRetrieve getInstance() {
        if (instance == null) {
            synchronized (TokenRetrieve.class) {
                if (instance == null) {
                    instance = new TokenRetrieve();
                }
            }
        }
        return instance;
    }

    public String getToken() {
        while (UNKNOWN.equals(token.get())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return token.get();
    }

    private TokenRetrieve() {
        Timer timer = new Timer();
        timer.schedule(new Retrieve(), 0, 45 * 60 * 1000);
    }

    private class Retrieve extends TimerTask {

        public void run() {
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
            final BoundRequestBuilder builder = asyncHttpClient.
                    preparePost(url).
                    addHeader("Content-Type", "application/x-www-form-urlencoded").
                    addFormParam("client_id", key).
                    addFormParam("client_secret", sec).
                    addFormParam("grant_type", "client_credentials");
            while(true)
            {
                try {
                    Response response = builder.execute().get();
                    Map<String, String> data = Utils.createObjectMapper().readValue(response.getResponseBody(),
                            new TypeReference<Map<String, String>>() {
                            });
                    if (data.containsKey("error")) {
                        log.error("error={}", data.get("error"));
                        Thread.sleep(500);
                    } else {
                        token.set(data.get("access_token"));
                        asyncHttpClient.close();
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

//    public static void main(String[] arg) {
//        log.info("token={}", TokenRetrieve.getInstance().getToken());
//    }

}
