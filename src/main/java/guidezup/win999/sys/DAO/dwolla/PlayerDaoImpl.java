package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.Utils;
import guidezup.win999.sys.DAO.MoneySource;
import guidezup.win999.sys.DAO.OperationException;
import guidezup.win999.sys.DAO.Player;
import guidezup.win999.sys.DAO.PlayerDao;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.CustomersApi;
import io.swagger.client.api.FundingsourcesApi;
import io.swagger.client.model.CreateCustomer;
import io.swagger.client.model.CreateFundingSourceRequest;
import io.swagger.client.model.Customer;
import io.swagger.client.model.FundingSource;
import io.swagger.client.model.Unit$;
import io.swagger.client.model.UpdateCustomer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static guidezup.win999.Utils.createApiClient;

public class PlayerDaoImpl implements PlayerDao {
    private static final Logger log = LoggerFactory.getLogger(PlayerDaoImpl.class);


    public Player createPlayer(String firstName, String lastName, String email) throws OperationException {
        Player player;
        CustomersApi customersApi = getCustomerApi();
        CreateCustomer createCustomer = new CreateCustomer();
        createCustomer.setEmail(email);
        createCustomer.setFirstName(firstName);
        createCustomer.setLastName(lastName);
        try {
            Unit$ response = customersApi.create(createCustomer);
            player = new Player(Utils.getIdFromUrlString(response.getLocationHeader()), firstName, lastName, email);
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
        return player;
    }

    public Player updatePlayer(String id, String firstName, String lastName, String email) throws OperationException {
        Player player = new Player(id, firstName, lastName, email);
        CustomersApi customersApi = getCustomerApi();
        try {
            UpdateCustomer uc = new UpdateCustomer();
            if (StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(email)) {
                return player;
            }
            if (!StringUtils.isEmpty(firstName)) {
                uc.setFirstName(firstName);
            }
            if (!StringUtils.isEmpty(lastName)) {
                uc.setFirstName(lastName);
            }
            if (!StringUtils.isEmpty(email)) {
                uc.setFirstName(email);
            }
            Customer customer = customersApi.updateCustomer(uc, id);
            player = new Player(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getStatus());
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
        return player;
    }

    public Player retrievePlayer(String id) throws OperationException {
        Player player;
        CustomersApi customersApi = getCustomerApi();
        try {
            Customer customer = customersApi.getCustomer(id);
            player = new Player(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getStatus());
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
        return player;
    }

    public Player deactivatePlayer(String id) throws OperationException {
        Player player;
        CustomersApi customersApi = getCustomerApi();
        try {
            UpdateCustomer uc = new UpdateCustomer();
            uc.setStatus("deactivated");
            Customer customer = customersApi.updateCustomer(uc, id);
            player = new Player(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getStatus());
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
        }
        return player;
    }

//    public Player createMoneySource(String id, MoneySource ms) throws OperationException {
//        Player player = retrievePlayer(id);
//        FundingsourcesApi fsApi = new FundingsourcesApi(createApiClient());
//        try {
//            CreateFundingSourceRequest request = new CreateFundingSourceRequest();
//            request.setRoutingNumber(ms.getRoutingNumber());
//            request.setAccountNumber(ms.getAccountNumber());
//            request.setType(ms.getType().name());
//            request.setName(ms.getName());
//            FundingSource fs = fsApi.createCustomerFundingSource(request, id);
//            ms.setId(Utils.getIdFromUrlString(fs.getLocationHeader()));
//            player.getMoneySources().add(ms);
//        } catch (ApiException e) {
//            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
//            throw new OperationException(e.getCode(), e.getMessage());
//        } catch (Exception e) {
//            log.error("errorCode={}, message={}", OperationException.SOME_PROBLEM_CODE, e.getMessage());
//            throw new OperationException(OperationException.SOME_PROBLEM_CODE, e.getMessage());
//        }
//        return player;
//    }
//
//    public Player retrieveMoneySources(String id, boolean activeOnly) throws OperationException {
//        return null;
//    }

    private CustomersApi getCustomerApi() {
        return new CustomersApi(createApiClient());
    }

//    public static void main(String[] arg) {
//        PlayerDaoImpl pdi = new PlayerDaoImpl();
//        try {
////            Player p = pdi.createPlayer("John", "Smith", "JS-6@gmail.com");
//            MoneySource ms = new MoneySource();
//            ms.setAccountNumber("222222227");
//            ms.setRoutingNumber("222222226");
//            ms.setName("test1");
//
//            Player p = pdi.createMoneySource("2b03a9d0-199c-4a16-934d-7207aab9eca4", ms);
//
//            log.info("done");
//        } catch (OperationException e) {
//            log.error(e.getMessage());
//        }
//    }

}
