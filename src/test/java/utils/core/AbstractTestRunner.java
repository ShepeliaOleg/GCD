package utils.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import springConstructors.*;
import springConstructors.mail.MailService;
import utils.RandomUtils;

import java.util.ArrayList;

@ContextConfiguration(locations={"/spring-config.xml"})
@Listeners(Listener.class)
public class AbstractTestRunner extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("affiliate")
    private AffiliateData affiliateData;

    @Autowired
    @Qualifier("adminUserData")
    private AdminUserData adminUserData;

    @Autowired
    @Qualifier("defaults")
    private Defaults defaults;

    @Autowired
    @Qualifier("deviceData")
    private DeviceData deviceData;

    @Autowired
    @Qualifier("driverData")
    private DriverData driverData;

    @Autowired
    @Qualifier("iMS")
    private IMS iMS;

    @Autowired
    @Qualifier("mailinator")
    private MailService mailService;

    @Autowired
    @Qualifier("userData")
    private UserData userData;

    public static ArrayList<String> results = new ArrayList<>();
    protected static int counter;
    protected static String name;

    @BeforeClass(alwaysRun = true)
    protected void setUp() throws Exception{
        WebDriverFactory.initializeWebDrivers(driverData, deviceData);
        DataContainer.setAffiliateData(affiliateData);
        DataContainer.setAdminUserData(adminUserData);
        DataContainer.setDefaults(defaults);
        DataContainer.setDriverData(driverData);
        DataContainer.setDeviceData(deviceData);
        DataContainer.setIms(iMS);
        DataContainer.setMailService(mailService);
        DataContainer.setUserData(userData);
    }

    @BeforeMethod(alwaysRun = true)
    protected void clean(){
        results.clear();
        counter = 0;
        name = RandomUtils.generateString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 15);
    }

    @AfterClass(alwaysRun = true)
    protected void tearDown() throws Exception{
        WebDriverFactory.shutDown();
    }
}
