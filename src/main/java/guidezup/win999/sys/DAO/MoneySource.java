package guidezup.win999.sys.DAO;

public class MoneySource {

    private String id;
    private SourceType type = SourceType.bank;
    private String name;
    private String status;

    public enum AccountType {checking, savings};

    public enum SourceType {bank, balance};

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
