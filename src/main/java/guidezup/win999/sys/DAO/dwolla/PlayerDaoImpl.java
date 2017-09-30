package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.Utils;
import guidezup.win999.sys.DAO.OperationException;
import guidezup.win999.sys.DAO.Player;
import guidezup.win999.sys.DAO.PlayerDao;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.CustomersApi;
import io.swagger.client.model.CreateCustomer;
import io.swagger.client.model.Customer;
import io.swagger.client.model.Unit$;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerDaoImpl implements PlayerDao {
    private static final Logger log = LoggerFactory.getLogger(PlayerDaoImpl.class);


    public Player createPlayer(String firstName, String lastName, String email) throws OperationException {
        Player player = null;
        ApiClient client = createApiClient();
        CustomersApi customersApi = new CustomersApi(client);
        CreateCustomer createCustomer = new CreateCustomer();
        createCustomer.setEmail(email);
        createCustomer.setFirstName(firstName);
        createCustomer.setLastName(lastName);
        try {
            Unit$ response = customersApi.create(createCustomer);
            client.setAccessToken(TokenRetrieve.getInstance().getToken());
            Customer customer = customersApi.getCustomer(response.getLocationHeader());
            player = new Player(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail());
        } catch (ApiException e) {
            log.error("errorCode={}, message={}", e.getCode(), e.getMessage());
            throw new OperationException(e.getCode(), e.getMessage());
        }

        return player;
    }

    public Player updatePlayer(String id, String firstName, String lastName, String email) throws OperationException {
        return null;
    }

    public Player retrievePlayer(String id) throws OperationException {
        return null;
    }

    public Player deactivatePlayer(String id) throws OperationException {
        return null;
    }

    private ApiClient createApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(Utils.getBaseUrl());
        apiClient.setAccessToken(TokenRetrieve.getInstance().getToken());
        return apiClient;
    }

    public static void main(String[] arg) {
        PlayerDaoImpl pdi = new PlayerDaoImpl();
        try {
            Player p = pdi.createPlayer("John", "Smith", "JS-3@gmail.com");
            log.info("done");
        } catch (OperationException e) {
            log.error(e.getMessage());
        }
    }

}
