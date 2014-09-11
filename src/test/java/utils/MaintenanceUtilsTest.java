package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import springConstructors.IMS;
import utils.core.AbstractTest;

/**
 * Created by sergiich on 5/27/14.
 */
public class MaintenanceUtilsTest extends AbstractTest{

    @Autowired
    @Qualifier("iMS")
    private IMS iMS;

    @Test(groups = {"ims"})
    public void resetFailedLogins() {
        String[] usernames = {"autoSmoke", "autoSmoke1", "autoSmokeS", "autoSmokeS1", "autoReg", "autoReg1", "autoReg2", "autoRegS", "autoRegS1", "autoRegS2", "testx1"};
        for(String username:usernames){
            System.out.println("Resetting " + username);
            iMS.navigateToPlayedDetails(username).resetFailedLogins();
            System.out.println(username + " is reset");
        }
    }
}
