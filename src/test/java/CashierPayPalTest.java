import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.PayPalDepositPage;
import pageObjects.cashier.withdraw.WithdrawConfirmationPopup;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.cashier.withdraw.WithdrawSuccessfulPopup;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class CashierPayPalTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String AMOUNT = "0.05";


    @Test(groups = {"regression", "mobile"})
    public void payPalDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertPayPalInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawInterfaceIsFunctional(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertPayPalInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public UserData payPalValidDeposit(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PayPalDepositPage payPalDepositPage = depositPage.depositPayPal(AMOUNT);
        payPalDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = payPalDepositPage.pay(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        assertEquals(userData.getCurrencySign()+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
        return userData;
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawAssertPopup(){
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawConfirmationPopup withdrawConfirmationPopup = withdrawPage.navigateToWithdrawConfirmationPopup(PaymentMethod.PayPal, AMOUNT);
        withdrawConfirmationPopup.assertAccount(PaymentMethod.PayPal.getAccount());
        withdrawConfirmationPopup.assertAmount(AMOUNT);
        withdrawConfirmationPopup.closePopup();
        assertEquals(userData.getCurrencySign() + " " + AMOUNT, new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForExistingUser() {
        UserData userData = payPalValidDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdraw(PaymentMethod.PayPal, AMOUNT);
        withdrawSuccessfulPopup.closePopup();
        assertEquals(userData.getCurrencySign()+" 9.95", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForNewUser() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdraw(PaymentMethod.PayPal, AMOUNT);
        withdrawSuccessfulPopup.closePopup();
        assertEquals(userData.getCurrencySign()+" 0.00", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForExistingUserAddAccount() {
        UserData userData = payPalValidDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        WithdrawSuccessfulPopup withdrawSuccessfulPopup = withdrawPage.withdrawAddingAccount(PaymentMethod.PayPal, AMOUNT);
        withdrawSuccessfulPopup.closePopup();
        assertEquals(userData.getCurrencySign()+" 0.00", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalWithdrawForExistingUserAddAccountClose() {
        payPalValidDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.PayPal);
        withdrawPage.closeAddAccountField(PaymentMethod.PayPal);
    }

    @Test(groups = {"regression", "mobile"})
    public void payPalCancelWithdrawForExistingUser() {
        UserData userData = payPalValidDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.PayPal, AMOUNT);
        assertEquals(userData.getCurrencySign()+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
    }
    
}
