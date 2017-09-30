package guidezup.win999;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.api.*;
import io.swagger.client.model.*;

public class HelloWorld {

    public void createClient() {

        RootApi root = new RootApi();
//        root.setApiClient();

        ApiClient a = new ApiClient();
        a.setBasePath("https://api-sandbox.dwolla.com");
        a.setAccessToken("CFQhG5iIiQCObuSXAtqkr3oC5jmcurd7WoRNK4i3KCEGMmeRBo__");
        a.getAuthentications();
        CustomersApi c = new CustomersApi(a);
        CreateCustomer myNewCust = new CreateCustomer();
        myNewCust.setEmail("name@mail.com");
        myNewCust.setFirstName("First");
        myNewCust.setLastName("Last");


        Unit$ r = null;
        try {
            r = c.create(myNewCust);
//            c.updateCustomer()
        } catch (ApiException e) {
            e.printStackTrace();
        }

        System.out.println(r.getLocationHeader());        System.out.println("HW");

    }

    public static void main(String[] args)
    {
        HelloWorld hw = new HelloWorld();
        hw.createClient();
        System.out.println("--HW--");
    }
}
