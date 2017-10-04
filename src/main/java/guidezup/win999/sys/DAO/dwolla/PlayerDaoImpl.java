package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.Utils;
import guidezup.win999.sys.DAO.OperationException;
import guidezup.win999.sys.DAO.Player;
import guidezup.win999.sys.DAO.PlayerDao;
import io.swagger.client.api.CustomersApi;
import io.swagger.client.model.CreateCustomer;
import io.swagger.client.model.Customer;
import io.swagger.client.model.Unit$;
import io.swagger.client.model.UpdateCustomer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static guidezup.win999.sys.DAO.dwolla.DwollaUtils.createApiClient;

public class PlayerDaoImpl implements PlayerDao {
    private static final Logger log = LoggerFactory.getLogger(PlayerDaoImpl.class);


    public Player createPlayer(String firstName, String lastName, String email) throws OperationException {
        try {
            CustomersApi customersApi = getCustomerApi();
            CreateCustomer createCustomer = new CreateCustomer();
            createCustomer.setEmail(email);
            createCustomer.setFirstName(firstName);
            createCustomer.setLastName(lastName);
            Unit$ response = customersApi.create(createCustomer);
            return new Player(Utils.getIdFromUrlString(response.getLocationHeader()), firstName, lastName, email);
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    public Player updatePlayer(String id, String firstName, String lastName, String email) throws OperationException {
        Player player = new Player(id, firstName, lastName, email);
        try {
            CustomersApi customersApi = getCustomerApi();
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
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
        return player;
    }

    public Player retrievePlayer(String id) throws OperationException {
        try {
            CustomersApi customersApi = getCustomerApi();
            Customer customer = customersApi.getCustomer(id);
            return new Player(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getStatus());
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    public Player deactivatePlayer(String id) throws OperationException {//        Player player;
        try {
            CustomersApi customersApi = getCustomerApi();
            UpdateCustomer uc = new UpdateCustomer();
            uc.setStatus("deactivated");
            Customer customer = customersApi.updateCustomer(uc, id);
            return new Player(customer.getId(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), customer.getStatus());
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    @Override
    public String getIavToken(String id) throws OperationException {
        try {
            CustomersApi customersApi = getCustomerApi();
            return customersApi.getCustomerIavToken(id).getToken();
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    private CustomersApi getCustomerApi() {
        return new CustomersApi(createApiClient());
    }

//    public static void main(String[] arg) {
//        PlayerDaoImpl pdi = new PlayerDaoImpl();
//        try {
////            Player p = pdi.createPlayer("John", "Smith", "JS-6@gmail.com");
////            MoneySource ms = new MoneySource();
////            ms.setAccountNumber("222222227");
////            ms.setRoutingNumber("222222226");
////            ms.setName("test1");
//            String p = pdi.getIavToken("2b03a9d0-199c-4a16-934d-7207aab9eca4");
//
//            log.info("done");
//        } catch (OperationException e) {
//            log.error(e.getMessage());
//        }
//    }

}
