import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierVisaTest extends AbstractTest{

    private static final String AMOUNT = "1.00";

    @Test(groups = {"regression", "mobile"})
    public void visaDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertCardInterface(PaymentMethod.Visa);
    }

    @Test(groups = {"regression", "mobile"})
    public void visaWithdrawInterfaceIsFunctional(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
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
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCard(PaymentMethod.Visa, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaDepositValidBonusCode(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCardValidPromoCode(PaymentMethod.Visa, AMOUNT);
        new OkBonusPopup().closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT, PromoCode.valid.getAmount()), depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaDepositInvalidBonusCode(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.Visa, AMOUNT);
        assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaWithdrawAssertPopupAndCancel() {
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Visa, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void visaExpiredDeposit(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositCardExpired(PaymentMethod.Visa, AMOUNT);
    }

    @Test(groups = {"regression", "mobile"})
    public void visaExpiredWithdraw(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawExpired(PaymentMethod.Visa, AMOUNT);
    }

}
