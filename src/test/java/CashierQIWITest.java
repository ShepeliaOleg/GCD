import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.CashierPage;
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
    public void qIWISuccessfulDeposit(){
        PortalUtils.registerUser(getRussianUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        QIWIDepositPage qiwiDepositPage = depositPage.depositQIWI(AMOUNT);
        qiwiDepositPage.assertAccount(PaymentMethod.QIWI.getAccount());
        qiwiDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = qiwiDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(CURRENCY+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
    }

    /*Case is not actual yet*/
//    @Test(groups = {"regression", "mobile"})
//    public void QIWIUnSuccessfulDeposit(){
//        PortalUtils.registerUser(getRussianUser());
//        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
//        QIWIDepositPage qiwiDepositPage = depositPage.depositQIWI(AMOUNT);
//        qiwiDepositPage.assertAccount();
//        qiwiDepositPage.assertAmount();
//        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = qiwiDepositPage.payInvalid();
//        transactionUnSuccessfulPopup.closePopup();
//        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
//    }

    @Test(groups = {"regression", "mobile"})
     public void qIWIWithdrawAssertPopup() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawConfirmationPopup withdrawConfirmationPopup = withdrawPage.navigateToWithdrawConfirmationPopup(PaymentMethod.QIWI, AMOUNT);
        withdrawConfirmationPopup.assertAccount(PaymentMethod.QIWI.getAccount());
        withdrawConfirmationPopup.assertAmount(AMOUNT);
        withdrawConfirmationPopup.closePopup();
        assertEquals(CURRENCY+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForExistingUser() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdraw(PaymentMethod.QIWI, AMOUNT);
        withdrawSuccessfulPopup.closePopup();
        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForExistingUserAddAccount() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdrawAddingAccount(PaymentMethod.QIWI, AMOUNT);
        withdrawSuccessfulPopup.closePopup();
        assertEquals(CURRENCY+" 0.00", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWIWithdrawForExistingUserAddAccountClose() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.QIWI);
        withdrawPage.closeAddAccountField(PaymentMethod.QIWI);
    }

    @Test(groups = {"regression", "mobile"})
    public void qIWICancelWithdrawForExistingUser() {
        qIWISuccessfulDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.QIWI, AMOUNT);
        assertEquals(CURRENCY+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
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

}
