package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.sys.DAO.OperationException;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import org.slf4j.Logger;

import java.text.MessageFormat;

import static guidezup.win999.Utils.UNKNOWN;

class DwollaUtils {

    static OperationException exceptionTreatment(Logger log, Exception e) {
        if (e instanceof ApiException) {
            log.error("errorCode={}, message={}", ((ApiException)e).getCode(), e.getMessage());
            return new OperationException(((ApiException)e).getCode(), e.getMessage());
        } else {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            return new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
    }

    public static ApiClient createApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(getBaseUrl());
        apiClient.setAccessToken(TokenRetrieve.getInstance().getToken());
        return apiClient;
    }

    public static String getParam1() {
        return DwollaProperties.getInstance().getParam1();
    }

    public static String getParam2() {
        return DwollaProperties.getInstance().getParam2();
    }

    public static String getAuthUrl() {
        return DwollaProperties.getInstance().getAuthUrl();
    }

    public static String getBaseUrl() {
        return DwollaProperties.getInstance().getBaseUrl();
    }

    public static String getMicroDepositeUrl(String moneySourceId) {
        String url = DwollaProperties.getInstance().getMicroDepositeUrl();
        if (!UNKNOWN.equals(url)) {
            url = MessageFormat.format(url, moneySourceId);
        }
        return url;
    }




}
