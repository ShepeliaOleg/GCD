import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierNetellerTest extends AbstractTest {

    private static final String AMOUNT = "10.00";


    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctional(){
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, getNetellerUser());
        withdrawPage.assertNetellerInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertNetellerInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawClose(){
        netellerDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Neteller, AMOUNT);
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
    public void netellerDepositValidPromoCode() {
        PortalUtils.loginUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        TransactionSuccessfulPopup transactionSuccessfulPopup = depositPage.depositNetellerValidPromoCode(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        new OkBonusPopup().closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT, PromoCode.valid.getAmount()), depositPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInvalidPromoCode() {
        PortalUtils.loginUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.Neteller, AMOUNT);
        assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");    }

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
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.cancelWithdraw(PaymentMethod.Neteller, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositIncorrectAccount() {
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNetellerInvalidAccount(AMOUNT);
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositIncorrectPassword() {
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNetellerInvalidPassword(AMOUNT);
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectAccount() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawNetellerInvalidAccount(AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectEmail() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, PromoCode.valid);
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
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("greesnm2");
        userData.setPassword("123asdQ!");
        return userData;
    }
}