package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.Utils;
import guidezup.win999.sys.DAO.MoneySource;
import guidezup.win999.sys.DAO.MoneySourceDao;
import guidezup.win999.sys.DAO.OperationException;
import io.swagger.client.ApiException;
import io.swagger.client.api.FundingsourcesApi;
import io.swagger.client.model.CreateFundingSourceRequest;
import io.swagger.client.model.FundingSource;
import io.swagger.client.model.FundingSourceListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static guidezup.win999.Utils.createApiClient;

public class MoneySourceDaoImpl implements MoneySourceDao {
    private static final Logger log = LoggerFactory.getLogger(MoneySourceDaoImpl.class);


    public MoneySource createMoneySource(String playerId, String routingNumber, String accountNumber,
                                         String nickName, MoneySource.AccountType accountType) throws OperationException {
        try {
            CreateFundingSourceRequest request = new CreateFundingSourceRequest();
            request.setRoutingNumber(routingNumber);
            request.setAccountNumber(accountNumber);
            request.setType(accountType.name());
            request.setName(nickName);
            FundingSource fs = getFundingsourcesApi().createCustomerFundingSource(request, playerId);
            return retrieveMoneySource(Utils.getIdFromUrlString(fs.getLocationHeader()));
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
    }

    public List<MoneySource> retrieveMoneySources(String playerId, boolean activeOnly) throws OperationException {
        try {
            FundingSourceListResponse fslr = getFundingsourcesApi().getCustomerFundingSources(playerId, !activeOnly);
            List list = (List)((Map)fslr.getEmbedded()).get("funding-sources");
            //todo - continue to implement it...

        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
        return null;
    }

    public MoneySource retrieveMoneySource(String id) throws OperationException {
        try {
            FundingSource fs = getFundingsourcesApi().id(id);
            MoneySource ms = new MoneySource();
            ms.setId(fs.getId());
            ms.setName(fs.getName());
            ms.setType(MoneySource.SourceType.valueOf(fs.getType()));
            ms.setStatus(fs.getStatus());
            return ms;
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
    }

    private FundingsourcesApi getFundingsourcesApi() {
        return new FundingsourcesApi(createApiClient());
    }

    public static void main(String[] arg) {
        MoneySourceDaoImpl pdi = new MoneySourceDaoImpl();
        try {

            MoneySource ms = pdi.retrieveMoneySource("335d9009-adfd-4964-8b7b-524857e4fe99");

            log.info("done");
        } catch (OperationException e) {
            log.error(e.getMessage());
        }
    }

}
