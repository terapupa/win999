package guidezup.win999.sys.DAO;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String status = "unverified";
    private List<MoneySource> moneySources = new ArrayList<MoneySource>();

    public Player(String id, String firstName, String lastName, String email, String status) {
        this(id, firstName, lastName, email);
        this.status = status;
    }

    public Player(String id, String firstName, String lastName, String emails) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MoneySource> getMoneySources() {
        return moneySources;
    }

    public void setMoneySources(List<MoneySource> moneySources) {
        this.moneySources = moneySources;
    }
}
