import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierMasterCardTest extends AbstractTest{

    private static final String AMOUNT = "1.00";

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertCardInterface(PaymentMethod.MasterCard);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardWithdrawInterfaceIsFunctional(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
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
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCard(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance), depositPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositValidBonusCode(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCardValidPromoCode(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT, PromoCode.valid.getAmount()), depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositInvalidBonusCode(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.MasterCard, AMOUNT);
        assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardWithdrawAssertPopupAndCancel() {
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredDeposit(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositCardExpired(PaymentMethod.MasterCard, AMOUNT);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredWithdraw(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawExpired(PaymentMethod.MasterCard, AMOUNT);
    }
}
