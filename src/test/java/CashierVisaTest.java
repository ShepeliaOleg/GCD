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
import utils.TypeUtils;
import utils.core.AbstractTest;

public class CashierVisaTest extends AbstractTest{

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

    private static final String AMOUNT = "1.00";

    @Test(groups = {"regression", "mobile"})
    public void visaDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertCardInterface(PaymentMethod.Visa);
    }

    @Test(groups = {"regression", "mobile"})
    public void visaWithdrawInterfaceIsFunctional(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertCardInterface(PaymentMethod.Visa);
    }

    @Test(groups = {"regression", "mobile"})
    public void noCardForNewUserDeposit(){
        PortalUtils.registerUser();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.isMethodVisible(PaymentMethod.Visa);
    }

    @Test(groups = {"regression", "mobile"})
    public void noCardForNewUserWithdraw(){
        PortalUtils.registerUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.isMethodVisible(PaymentMethod.Visa);
    }

    @Test(groups = {"regression", "mobile"})
     public void visaValidDepositAndWithdraw(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCard(PaymentMethod.Visa, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaWithdrawAssertPopupAndCancel() {
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaExpiredDeposit(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositCardExpired(PaymentMethod.Visa, AMOUNT);
    }

    @Test(groups = {"regression", "mobile"})
    public void visaExpiredWithdraw(){
        PortalUtils.loginUser(defaultUserData.getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawExpired(PaymentMethod.Visa, AMOUNT);
    }

}
