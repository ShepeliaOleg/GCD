import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.MoneyBookersDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierMoneyBookersTest extends AbstractTest{

    private static final String AMOUNT = "1.00";


    @Test(groups = {"regression", "mobile"})
    public void moneyBookersDepositInterfaceIsFunctional(){
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        depositPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawInterfaceIsFunctional(){
        UserData userData = getMoneyBookersUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        withdrawPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersCancelDeposit(){
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String balance = depositPage.getBalanceAmount();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = moneyBookersDepositPage.cancelDeposit();
        transactionUnSuccessfulPopup.closePopup();
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawAssertPopupAndClose(){
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawConfirmationPopupClose(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }


    @Test(groups = {"regression", "mobile"})
    public void moneyBookersDepositWithdrawForExistingUser() {
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawSuccessful(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(TypeUtils.calculateDiff(balance, AMOUNT), withdrawPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForNewUser() {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals("9.00", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForExistingUserAddAccount() {
        String initialBalance = moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawAddingAccount(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(initialBalance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForExistingUserAddAccountClose() {
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.addAccountByType(PaymentMethod.MoneyBookers);
        withdrawPage.closeAddAccountField(PaymentMethod.MoneyBookers);
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersCancelWithdrawForExistingUser() {
        String initialBalance = moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(TypeUtils.calculateSum(initialBalance, AMOUNT), new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void validPromoCodeTest(){
        skipTestWithIssues("D-18785");
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String balance = depositPage.getBalanceAmount();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookersValidPromoCode(AMOUNT);
        moneyBookersDepositPage.assertAmount(AMOUNT);
        AcceptDeclineBonusPopup acceptDeclineBonusPopup = (AcceptDeclineBonusPopup) moneyBookersDepositPage.pay(true);
        acceptDeclineBonusPopup.clickAccept();
        HomePage homePage = (HomePage) NavigationUtils.navigateToPage(ConfiguredPages.home);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT, PromoCode.valid.getAmount()), homePage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void invalidPromoCodeTest(){
        UserData userData = getMoneyBookersUser();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals("Inserted Promotional Code does not exist", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    private String moneyBookersDeposit(){
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String initialBalance = depositPage.getBalanceAmount();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        moneyBookersDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = (TransactionSuccessfulPopup) moneyBookersDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(TypeUtils.calculateSum(initialBalance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        return initialBalance;
    }

    private UserData getMoneyBookersUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("taiwan");
        return userData;
    }
}
