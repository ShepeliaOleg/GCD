import enums.ConfiguredPages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.withdraw.WithdrawConfirmationPopup;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.QIWIDepositPage;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.withdraw.WithdrawSuccessfulPopup;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class CashierQIWITest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String CURRENCY = "RUB";
    private static final String COUNTRY = "RU";
    private static final String AMOUNT = "5.0";


    @Test(groups = {"regression", "mobile"})
    public void QIWIDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getRussianUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertQIWIInterface();
    }

//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawInterfaceIsFunctionalNewUser(){
//        PortalUtils.registerUser(getRussianUser());
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        withdrawPage.assertQIWIInterface();
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawInterfaceIsFunctionalExistingUser(){
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        withdrawPage.assertQIWIInterface();
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWISuccessfulDeposit(){
//        PortalUtils.registerUser(getRussianUser());
//        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
//        QIWIDepositPage qiwiDepositPage = depositPage.depositQIWI(AMOUNT);
//        qiwiDepositPage.assertAccount();
//        qiwiDepositPage.assertAmount();
//        TransactionSuccessfulPopup transactionSuccessfulPopup = qiwiDepositPage.pay();
//        transactionSuccessfulPopup.closePopup();
//        assertEquals(CURRENCY+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
//    }
//
    @Test(groups = {"regression", "mobile"})
    public void QIWIUnSuccessfulDeposit(){
        PortalUtils.registerUser(getRussianUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        QIWIDepositPage qiwiDepositPage = depositPage.depositQIWI(AMOUNT);
        qiwiDepositPage.assertAccount();
        qiwiDepositPage.assertAmount();
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = qiwiDepositPage.payInvalid();
        transactionUnSuccessfulPopup.closePopup();
        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
    }
//
//    @Test(groups = {"regression", "mobile"})
//     public void QIWIWithdrawAssertPopup() {
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        WithdrawConfirmationPopup withdrawConfirmationPopup = withdrawPage.navigateToWithdrawQIWIConfirmationPopup(AMOUNT);
//        withdrawConfirmationPopup.closePopup();
//        assertEquals(CURRENCY+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawForExistingUser() {
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdrawQIWI(AMOUNT);
//        withdrawSuccessfulPopup.closePopup();
//        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawForExistingUserAddAccount() {
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdrawQIWIAddAccount(AMOUNT);
//        withdrawSuccessfulPopup.closePopup();
//        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawForExistingUserAddAccountClose() {
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        withdrawPage.addQIWIAccount();
//        withdrawPage.closeQIWIAccount();
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWICancelWithdrawForExistingUser() {
//        QIWISuccessfulDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        withdrawPage.cancelWithdrawQIWI(AMOUNT);
//        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
//    }
//
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIWithdrawForNewUser() {
//        /*TODO*/
//    }

    private UserData getRussianUser(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setCurrency(CURRENCY);
        userData.setCountry(COUNTRY);
        return userData;
    }

}
