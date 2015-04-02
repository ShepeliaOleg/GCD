import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
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
        UserData userData = DataContainer.getUserData().getCardUserData();
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.deposit, userData);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCard(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), depositPage.getBalanceAmount(), "Balance change after deposit");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawSuccessful(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after withdraw");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositValidBonusCode(){
        //skipTestWithIssues("D-18311");
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage.depositCardValidPromoCode(PaymentMethod.MasterCard, AMOUNT);
//        new OkBonusPopup().closePopup();
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT, PromoCode.valid.getAmount()), depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardDepositInvalidBonusCode(){
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        String balance = depositPage.getBalanceAmount();
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.MasterCard, AMOUNT);
        assertEquals("Inserted Promotional Code does not exist", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals(balance, depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardWithdrawAssertPopupAndCancel() {
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        String balance = withdrawPage.getBalanceAmount();
        withdrawPage.withdrawConfirmationPopupClose(PaymentMethod.MasterCard, AMOUNT);
        assertEquals(balance, withdrawPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredDeposit(){
        //skipTestWithIssues("D-18404"); - FIXED
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositCardExpired(PaymentMethod.MasterCard, AMOUNT);
    }

    @Test(groups = {"regression", "mobile"})
    public void masterCardExpiredWithdraw(){
        //skipTestWithIssues("D-18404"); - FIXED
        PortalUtils.loginUser(DataContainer.getUserData().getCardUserData());
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawExpired(PaymentMethod.MasterCard, AMOUNT);
    }

    /**
     * LAST USED CC/PM IS CHOSEN BEGIN
     * */

    /*Last used card for deposit is chosen*/
    @Test(groups = {"regression", "mobile"})
    public void lastUsedMasterCardSelected(){
        PaymentMethod pm = PaymentMethod.MasterCardLastUsedCC;
        PortalUtils.loginUser(DataContainer.getUserData().getLastUsedCCUserData());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositLastUsedCC(pm, pm.getSecondaryAccount());
        depositPage.refresh();
        assertEquals(pm.getSecondaryAccount(), depositPage.getSelectedCCNumber(pm), "Deposit. Last used card '" + pm.getSecondaryAccount() + "' is selected");
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        assertEquals(pm.getSecondaryAccount(), withdrawPage.getSelectedCCNumber(pm), "Open Withdraw after Deposit. Last used card '" + pm.getSecondaryAccount() + "' is selected");
        withdrawPage.withdraw(pm, AMOUNT);
        withdrawPage.refresh();
        assertEquals(pm.getSecondaryAccount(), withdrawPage.getSelectedCCNumber(pm), "Withdraw. Last used card '" + pm.getSecondaryAccount() + "' is selected");
        depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        assertEquals(pm.getSecondaryAccount(), depositPage.getSelectedCCNumber(pm), "Open Deposit after Withdraw. Last used card '" + pm.getSecondaryAccount() + "' is selected");
    }

    /**
     * LAST USED CC/PM IS CHOSEN END
     * */

}
