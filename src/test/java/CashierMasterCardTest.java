import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class CashierMasterCardTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String AMOUNT = "1.00";

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertCardInterface(PaymentMethod.MasterCard);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardWithdrawInterfaceIsFunctional(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertCardInterface(PaymentMethod.MasterCard);
    }

    @Test(groups = {"regression", "mobile"})
    public void noCardForNewUserDeposit(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.isMethodVisible(PaymentMethod.MasterCard);
    }

    @Test(groups = {"regression", "mobile"})
    public void noCardForNewUserWithdraw(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.isMethodVisible(PaymentMethod.MasterCard);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardValidDepositAndWithdraw(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalance();
        depositPage.depositCard(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(AMOUNT.replace(".", ""), depositPage.getBalanceChange(balance), "Balance change after deposit");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        balance = withdrawPage.getBalance();
        withdrawPage.withdrawSuccessful(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(AMOUNT.replace(".", ""), depositPage.getBalanceChange(balance), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardWithdrawAssertPopupAndCancel() {
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalance();
        withdrawPage.assertWithdrawConfirmationPopupAndClose(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(balance, withdrawPage.getBalance(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredDeposit(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositCardExpired(PaymentMethod.MasterCard, AMOUNT);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredWithdraw(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawExpired(PaymentMethod.MasterCard, AMOUNT);
    }
}
