import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.PayPalDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierPayPalTest extends AbstractTest{

    private static final String AMOUNT = "0.10";


    @Test(groups = {"regression", "mobile"})
    public void payPalDepositInterfaceIsFunctional(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertPayPalInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawInterfaceIsFunctional(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertPayPalInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalCancelDeposit(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PayPalDepositPage payPalDepositPage = depositPage.depositPayPal(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = payPalDepositPage.cancelDeposit();
        transactionUnSuccessfulPopup.closePopup();
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawAssertPopupAndClose(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.PayPal, AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }


    @Test(groups = {"regression", "mobile"})
    public void payPalDepositWithdrawForExistingUser() {
        payPalDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.PayPal, AMOUNT);
        assertEquals("9.95", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForNewUser() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.PayPal, AMOUNT);
        assertEquals(" 9.90", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForExistingUserAddAccount() {
        payPalDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawAddingAccount(PaymentMethod.PayPal, AMOUNT);
        assertEquals("9.90", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForExistingUserAddAccountClose() {
        payPalDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.PayPal);
        withdrawPage.closeAddAccountField(PaymentMethod.PayPal);
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalCancelWithdrawForExistingUser() {
        payPalDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.PayPal, AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    private UserData payPalDeposit(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PayPalDepositPage payPalDepositPage = depositPage.depositPayPal(AMOUNT);
        payPalDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = payPalDepositPage.pay(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, depositPage.getBalanceAmount(), "Balance");
        return userData;
    }

}
