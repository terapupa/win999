package guidezup.win999.sys.DAO;

import static guidezup.win999.sys.DAO.MoneySource.AccountType.checking;

public class MoneySource {

    private String id;
    private String routingNumber;
    private String accountNumber;
    private AccountType type = checking;
    private String name;

    public enum AccountType {checking, savings};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
