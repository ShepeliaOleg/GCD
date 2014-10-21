import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import utils.core.AbstractTest;

public class CashierMoneyBookersTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String AMOUNT = "0.05";


    @Test(groups = {"regression", "mobile"})
    public void moneyBookersDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawInterfaceIsFunctional(){
        PortalUtils.registerUser(defaultUserData.getRandomUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertMoneyBookersInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersCancelDeposit(){
        UserData userData = getMoneyBookersUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String balance = depositPage.getBalance();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = moneyBookersDepositPage.cancelDeposit();;
        transactionUnSuccessfulPopup.closePopup();
        assertEquals(balance, depositPage.getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawAssertPopupAndClose(){
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalance();
        withdrawPage.assertWithdrawConfirmationPopupAndClose(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, withdrawPage.getBalance(), "Balance");
    }


    @Test(groups = {"regression", "mobile"})
    public void moneyBookersDepositWithdrawForExistingUser() {
        moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalance();
        withdrawPage.withdrawSuccessful(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(AMOUNT.replace(".", ""), withdrawPage.getBalanceChange(balance), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForNewUser() {
        UserData userData = defaultUserData.getRandomUserData();
        PortalUtils.registerUser(userData, "valid");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(userData.getCurrencySign() + " 9.95", new AbstractPage().getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void moneyBookersWithdrawForExistingUserAddAccount() {
        UserData userData = moneyBookersDeposit();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawAddingAccount(PaymentMethod.MoneyBookers, AMOUNT);
        assertEquals(userData.getCurrencySign()+" 9.95", new AbstractPage().getBalance(), "Balance");
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
        assertEquals(userData.getCurrencySign()+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
    }

    private UserData moneyBookersDeposit(){
        UserData userData = getMoneyBookersUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalance();
        MoneyBookersDepositPage moneyBookersDepositPage = depositPage.depositMoneyBookers(AMOUNT);
        moneyBookersDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = moneyBookersDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT.replace(".", ""), depositPage.getBalanceChange(balance), "Balance change after deposit");
        return userData;
    }

    private UserData getMoneyBookersUser(){
        UserData userData = defaultUserData.getRandomUserData();
        userData.setUsername("test-mb");
        userData.setPassword("123asdQ");
        return userData;
    }
}
