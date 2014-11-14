import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.WebMoneyDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierWebMoneyTest extends AbstractTest{

    private static final String AMOUNT = "10.00";


    @Test(groups = {"regression", "mobile"})
    public void webMoneyDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getWebMoneyUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertWebMoneyInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void webMoneyWithdrawInterfaceIsFunctional(){
        PortalUtils.registerUser(getWebMoneyUser());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertWebMoneyInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void webMoneyAssertAmount(){
        PortalUtils.registerUser(getWebMoneyUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        WebMoneyDepositPage webMoneyDepositPage = depositPage.depositWebMoney(AMOUNT);
        assertEquals(AMOUNT, webMoneyDepositPage.getAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void webMoneyWithdrawAssertPopupAndClose(){
        UserData userData = getWebMoneyUser();
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.WebMoney, AMOUNT);
        assertEquals("0.00", withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void webMoneyDepositInvalidPromoCode() {
        PortalUtils.registerUser(getWebMoneyUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.WebMoney, AMOUNT);
        assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void webMoneyWithdrawForNewUser() {
        UserData userData = getWebMoneyUser();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.WebMoney, AMOUNT);
        assertEquals("0.00", withdrawPage.getBalanceAmount(), "Balance");
    }
    
    private UserData getWebMoneyUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency("USD@$");
        return userData;
    }

}
