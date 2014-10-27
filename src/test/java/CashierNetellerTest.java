import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.account.AddCardPage;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;

public class CashierNetellerTest extends AbstractTest {

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String AMOUNT = "10.00";


    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterfaceExisting();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterfaceNew();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctional(){
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, getNetellerUser());
        withdrawPage.assertNetellerInterfaceExisting();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertNetellerInterfaceNew();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawClose(){
        netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositWithdrawForExistingUser() {
        netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawSuccessful(PaymentMethod.Neteller, AMOUNT);
        assertEquals(TypeUtils.calculateDiff(balance, AMOUNT), withdrawPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawForNewUser() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.Neteller, AMOUNT);
        assertEquals("0.00", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawForExistingUserAddAccount() {
        netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawAddingAccount(PaymentMethod.Neteller, AMOUNT);
        assertEquals(TypeUtils.calculateDiff(balance, AMOUNT), withdrawPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawForExistingUserAddAccountClose() {
        netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.Neteller);
        withdrawPage.closeAddAccountField(PaymentMethod.Neteller);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerCancelWithdrawForExistingUser() {
        UserData userData = netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.Neteller, AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositIncorrectAccount() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNetellerInvalidAccount(AMOUNT);
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositIncorrectPassword() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNetellerInvalidPassword(AMOUNT);
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectAccount() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawNetellerInvalidAccount(AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectEmail() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawNetellerInvalidEmail(AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    private UserData netellerDeposit(){
        UserData userData = getNetellerUser();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        TransactionSuccessfulPopup transactionSuccessfulPopup = depositPage.depositNeteller(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after withdraw");
        return userData;
    }

    private UserData getNetellerUser(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setUsername("greesnm2");
        userData.setPassword("123asdQ!");
        return userData;
    }
}