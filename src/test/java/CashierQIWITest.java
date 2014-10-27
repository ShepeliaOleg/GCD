import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.QIWIDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
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
    private static final String AMOUNT = "5.00";


    @Test(groups = {"regression", "mobile"})
    public void qIWIDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getRussianUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertQIWIInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawInterfaceIsFunctionalExistingUser(){
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertQIWIInterface();
    }

    @Test(groups = {"regression", "mobile"})
     public void qIWIWithdrawAssertPopupAndClose() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Visa, AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIDepositWithdrawForExistingUser() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.QIWI, AMOUNT);
        assertEquals("0.00", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForExistingUserAddAccount() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawAddingAccount(PaymentMethod.QIWI, AMOUNT);
        assertEquals("0.00", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForExistingUserAddAccountClose() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.QIWI);
        withdrawPage.closeAddAccountField(PaymentMethod.QIWI);
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForNewUser() {
        PortalUtils.registerUser(getRussianUser());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        assertTrue(withdrawPage.isQIWINewUserNotificationPresent(), "User informed that he should deposit first");
    }

    private UserData getRussianUser(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setCurrency(CURRENCY+"@");
        userData.setCountry(COUNTRY);
        return userData;
    }

    private void qIWISuccessfulDeposit(){
        PortalUtils.registerUser(getRussianUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        QIWIDepositPage qiwiDepositPage = depositPage.depositQIWI(AMOUNT);
        qiwiDepositPage.assertAccount(PaymentMethod.QIWI.getAccount());
        qiwiDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = qiwiDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, depositPage.getBalanceAmount(), "Balance");
    }

}
