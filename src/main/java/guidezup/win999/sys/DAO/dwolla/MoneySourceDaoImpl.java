package guidezup.win999.sys.DAO.dwolla;

import guidezup.win999.Utils;
import guidezup.win999.sys.DAO.MoneySource;
import guidezup.win999.sys.DAO.MoneySourceDao;
import guidezup.win999.sys.DAO.OperationException;
import io.swagger.client.api.FundingsourcesApi;
import io.swagger.client.model.CreateFundingSourceRequest;
import io.swagger.client.model.FundingSource;
import io.swagger.client.model.FundingSourceListResponse;
import io.swagger.client.model.RemoveBankRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static guidezup.win999.sys.DAO.dwolla.DwollaUtils.createApiClient;

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
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    public List<MoneySource> retrieveMoneySources(String playerId, boolean activeOnly) throws OperationException {
        try {
            FundingSourceListResponse fslr = getFundingsourcesApi().getCustomerFundingSources(playerId, !activeOnly);
            List<Map<String, String>> list = (List<Map<String, String>>) ((Map) fslr.getEmbedded()).get("funding-sources");
            List<MoneySource> result = new ArrayList<MoneySource>();
            for (Map<String, String> m : list) {
                MoneySource ms = new MoneySource();
                ms.setId(m.get("id"));
                ms.setName(m.get("name"));
                ms.setType(MoneySource.SourceType.valueOf(m.get("type")));
                ms.setStatus(MoneySource.AccountStatus.valueOf(m.get("status")));
                result.add(ms);
            }
            return result;
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    public MoneySource retrieveMoneySource(String id) throws OperationException {
        try {
            FundingSource fs = getFundingsourcesApi().id(id);
            MoneySource ms = new MoneySource();
            ms.setId(fs.getId());
            ms.setName(fs.getName());
            ms.setType(MoneySource.SourceType.valueOf(fs.getType()));
            ms.setStatus(MoneySource.AccountStatus.valueOf(fs.getStatus()));
            return ms;
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    public MoneySource removeMoneySource(String id) throws OperationException {
        try {
            RemoveBankRequest rbr = new RemoveBankRequest();
            rbr.setRemoved(true);
            FundingSource fs = getFundingsourcesApi().softDelete(rbr, id);
            MoneySource ms = new MoneySource();
            ms.setId(fs.getId());
            ms.setName(fs.getName());
            ms.setType(MoneySource.SourceType.valueOf(fs.getType()));
            ms.setStatus(MoneySource.AccountStatus.valueOf(fs.getStatus()));
            ms.setRemoved(fs.getRemoved());
            return ms;
        } catch (Exception e) {
            throw guidezup.win999.sys.DAO.dwolla.DwollaUtils.exceptionTreatment(log, e);
        }
    }

    @Override
    public MoneySource initMicroDeposite(String id) throws OperationException {
        String url = DwollaUtils.getMicroDepositeUrl(id);
        return null;
    }

    private static FundingsourcesApi getFundingsourcesApi() {
        return new FundingsourcesApi(createApiClient());
    }

    public static void main(String[] arg) {
        MoneySourceDaoImpl pdi = new MoneySourceDaoImpl();
        try {

            pdi.initMicroDeposite("104dbd31-3328-4f90-9227-43d9ca6e3be9");

//            MoneySource l = pdi.createMoneySource("2b03a9d0-199c-4a16-934d-7207aab9eca4", "222222226",
//                    "007776662", "mayBank", MoneySource.AccountType.checking);
////            MoneySource l = pdi.retrieveMoneySource("459ed320-7c2b-4d4f-940a-cb86ab706d43");
////            MoneySource l = pdi.removeMoneySource("335d9009-adfd-4964-8b7b-524857e4fe99");
//            VerifyMicroDepositsRequest vmdr = new VerifyMicroDepositsRequest();
//            Amount amount = new Amount();
//            Amount amount1 = new Amount();
//            amount.setValue("0.05");
//            amount.setCurrency("USD");
//            amount1.setValue("0.04");
//            amount1.setCurrency("USD");
//            vmdr.setAmount1(amount);
//            vmdr.setAmount2(amount);
//            MicroDeposits md = getFundingsourcesApi().microDeposits(vmdr, "459ed320-7c2b-4d4f-940a-cb86ab706d43");

//            MicroDepositsInitiated fs = getFundingsourcesApi().verifyMicroDepositsExist("459ed320-7c2b-4d4f-940a-cb86ab706d43");



            log.info("done");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
