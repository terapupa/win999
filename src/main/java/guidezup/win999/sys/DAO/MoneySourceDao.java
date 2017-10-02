package guidezup.win999.sys.DAO;

import java.util.List;

public interface MoneySourceDao {
    public MoneySource createMoneySource(String id, String routingNumber, String accountNumber,
                                         String nickName, MoneySource.AccountType accountType) throws OperationException;

    public List<MoneySource> retrieveMoneySources(String playerId, boolean activeOnly) throws OperationException;

    public MoneySource retrieveMoneySource(String id) throws OperationException;

    }
