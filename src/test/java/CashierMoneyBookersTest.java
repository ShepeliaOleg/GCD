import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.MoneyBookersDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierMoneyBookersTest extends AbstractTest{

    private static final String AMOUNT = "0.05";


    @Test(groups = {"regression", "mobile"})
    public void moneyBookersDepositInterfaceIsFunctional(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawInterfaceIsFunctional(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersCancelDeposit(){
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String balance = depositPage.getBalanceAmount();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = moneyBookersDepositPage.cancelDeposit();;
        transactionUnSuccessfulPopup.closePopup();
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawAssertPopupAndClose(){
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Visa, AMOUNT);
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
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals("9.95", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForExistingUserAddAccount() {
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawAddingAccount(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals("9.95", withdrawPage.getBalanceAmount(), "Balance");
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
        UserData userData = moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.cancelWithdraw(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(userData.getCurrencySign()+" "+AMOUNT, new AbstractPage().getBalanceAmount(), "Balance");
    }

    private UserData moneyBookersDeposit(){
        UserData userData = getMoneyBookersUser();
        PortalUtils.loginUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        moneyBookersDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = moneyBookersDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        return userData;
    }

    private UserData getMoneyBookersUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("test-mb");
        userData.setPassword("123asdQ");
        return userData;
    }
}
