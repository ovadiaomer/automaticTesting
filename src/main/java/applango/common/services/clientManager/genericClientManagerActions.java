package applango.common.services.clientManager;

import com.applango.rest.client.CustomerManagerClient;

import java.util.Random;

public class genericClientManagerActions {

    public static String getRandomCustomer(CustomerManagerClient client) {
        Random rand =  new Random();
        String newCustomerId = "testCust"+ rand.nextInt(1000);
        while (client.customerExists(newCustomerId)) {
            newCustomerId = "testCust"+ rand.nextInt(1000);
        }

        return newCustomerId;
    }
}