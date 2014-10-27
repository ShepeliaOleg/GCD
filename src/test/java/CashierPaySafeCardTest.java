import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.PaySafeCardDepositPage;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class CashierPaySafeCardTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String CURRENCY = "EUR@€";
    private static final String COUNTRY = "NL";
    private static final String AMOUNT = "1.00";


    @Test(groups = {"regression", "mobile"})
    public void paySafeCardDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getEURUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertPaySafeCardInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void paySafeCardSuccessfulDeposit(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PaySafeCardDepositPage paySafeCardDepositPage = depositPage.depositPaySafeCard(AMOUNT);
        paySafeCardDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = paySafeCardDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, depositPage.getBalanceAmount(), "Balance");
    }


    @Test(groups = {"regression", "mobile"})
    public void paySafeCardCancelDeposit(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PaySafeCardDepositPage paySafeCardDepositPage = depositPage.depositPaySafeCard(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = paySafeCardDepositPage.cancelDeposit();
        transactionUnSuccessfulPopup.closePopup();
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    private UserData getEURUser(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setCurrency(CURRENCY);
        userData.setCountry(COUNTRY);
        return userData;
    }

}
