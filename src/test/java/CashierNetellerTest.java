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

    private static final String AMOUNT = "1.00";


    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterface(false);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerDepositInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertNetellerInterface(true);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctional(){
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, getNetellerUser());
        withdrawPage.assertNetellerInterface(false);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawInterfaceIsFunctionalNewUser(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertNetellerInterface(true);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawClose(){
//        netellerDeposit();
        UserData userData = getNetellerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawConfirmationPopupClose(PaymentMethod.Neteller, AMOUNT);
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
//        skipTestWithIssues("D-18785");
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
        //assertEquals("Inserted Promotional Code does not exist", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals("We are sorry but your transaction has been declined.", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawForExistingUserAddAccount() {
//        netellerDeposit();
        UserData userData = getNetellerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawAddingAccount(PaymentMethod.Neteller, AMOUNT);
        assertEquals(TypeUtils.calculateDiff(balance, AMOUNT), withdrawPage.getBalanceAmount(), "Balance change after withdraw");
    }

//    @Test(groups = {"regression", "mobile"})
//    public void netellerWithdrawForExistingUserAddAccountEmail() {
//        netellerDeposit();
//        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
//        String balance = withdrawPage.getBalanceAmount();
//        withdrawPage.withdrawAddingAccount(PaymentMethod.Neteller, AMOUNT, PaymentMethod.Neteller.getEmail());
//        assertEquals(TypeUtils.calculateDiff(balance, AMOUNT), withdrawPage.getBalanceAmount(), "Balance change after withdraw");
//    }

    @Test(groups = {"regression", "mobile", "gala"})
    public void netellerWithdrawForExistingUserAddAccountClose() {
//        netellerDeposit();
        UserData userData = getNetellerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        withdrawPage.addAccountByType(PaymentMethod.Neteller);
        withdrawPage.closeAddAccountField(PaymentMethod.Neteller);
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerCancelWithdrawForExistingUser() {
//        netellerDeposit();
        UserData userData = getNetellerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
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
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD");
        PortalUtils.registerUser(userData);
        //PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNetellerInvalidPassword(AMOUNT);
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectAccount() {
        skipTestWithIssues("COR-721");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawNetellerInvalidAccount(AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void netellerWithdrawIncorrectEmail() {
        skipTestWithIssues("COR-721");
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawNetellerInvalidEmail(AMOUNT);
        assertEquals(AMOUNT, withdrawPage.getBalanceAmount(), "Balance");
    }

    /**
     * LAST USED CC/PM IS CHOSEN BEGIN
     * */

     /*Last used payment account is selected on deposit/withdraw page*/
    @Test(groups = {"regression", "mobile"})
    public void lastUsedNetellerAccountIsSelected(){
        PaymentMethod pm = PaymentMethod.Neteller;
        PortalUtils.loginUser(DataContainer.getUserData().getLastUsedCCUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositNeteller(AMOUNT);
        depositPage.refresh();
        assertEquals(pm.getAccount(), depositPage.getSelectedCCNumber(pm), "Deposit. Last used card '" + pm.getAccount() + "' is selected");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        assertEquals(pm.getAccount(), withdrawPage.getSelectedCCNumber(pm), "Open Withdraw after Deposit. Last used card '" + pm.getAccount() + "' is selected");
        withdrawPage.withdraw(pm, AMOUNT);
        withdrawPage.refresh();
        assertEquals(pm.getAccount(), withdrawPage.getSelectedCCNumber(pm), "Withdraw. Last used card '" + pm.getAccount() + "' is selected");
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        assertEquals(pm.getAccount(), depositPage.getSelectedCCNumber(pm), "Open Deposit after Withdraw. Last used card '" + pm.getAccount() + "' is selected");
    }

    /**
     * LAST USED CC/PM IS CHOSEN END
     * */

    private void netellerDeposit(){
        PortalUtils.loginUser(getNetellerUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        TransactionSuccessfulPopup transactionSuccessfulPopup = depositPage.depositNeteller(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance updated after deposit.");
    }

    private UserData getNetellerUser(){
        UserData userData = DataContainer.getUserData().getRegisteredUserData();
        userData.setUsername("greesnm2");
        userData.setPassword("123asdQ!");
        return userData;
    }
}