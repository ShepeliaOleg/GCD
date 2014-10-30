import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.QIWIDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierQIWITest extends AbstractTest{

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

    private UserData getRussianUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
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
