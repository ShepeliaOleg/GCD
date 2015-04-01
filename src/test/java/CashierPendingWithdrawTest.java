import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.account.PendingWithdrawPage;
import pageObjects.account.WithdrawLoadMoreNotification;
import pageObjects.cashier.withdraw.WithdrawPage;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierPendingWithdrawTest extends AbstractTest{

    private static final String AMOUNT = "1.00";

    /*B-13842 Pending Withdrawals page*/
    /*#1. pending withdraw visible for player */
    @Test(groups = {"regression"})
    public void pageAvailableForPlayer() {
        PortalUtils.loginUser();
        PendingWithdrawPage pendingWithdrawPage = (PendingWithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.pending_withdraw);
    }

    /*#2. load more button on pending withdraw page */
    @Test(groups = {"regression"})
    public void pendingWithdrawLoadMore(){
        skipTestWithIssues("D-19084");
        PendingWithdrawPage pendingWithdrawPage = (PendingWithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.pending_withdraw);
        pendingWithdrawPage.loadMore();
        new WithdrawLoadMoreNotification();
    }

    /*#3. Cancel pending withdraw*/
    @Test(groups = {"regression"})
    public void pendingWithdrawCancel(){
        UserData userData = addPendingWithdraw();
        PendingWithdrawPage pendingWithdrawPage = (PendingWithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.pending_withdraw, userData);
        String balance = pendingWithdrawPage.getBalanceAmount();

        assertEquals(1, pendingWithdrawPage.getWithdrawRecordsCount(),  "All pending withdraw count.");
        assertWithdraw(pendingWithdrawPage, PaymentMethod.PayPal, AMOUNT, 1, userData);
        assertEquals(AMOUNT, pendingWithdrawPage.getTotalAmount(), "Sum of all pending withdrawals.");

        pendingWithdrawPage.cancelWithdraw(1);

        assertEquals(0, pendingWithdrawPage.getWithdrawRecordsCount(),  "Available pending withdraw records after withdraw cancel.");
        assertEquals(TypeUtils.calculateSum(balance, AMOUNT), pendingWithdrawPage.getBalanceAmount(), "Balance after withdraw cancel.");
    }

    /*#4. numerous pending withdraw record + total sum */
    @Test(groups = {"regression"})
    public void pendingWithdrawNumerous(){
        UserData userData = addPendingWithdraw(PaymentMethod.PayPal, PaymentMethod.MoneyBookers, PaymentMethod.Visa);
        PendingWithdrawPage pendingWithdrawPage = (PendingWithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.pending_withdraw, userData);
        assertEquals(3, pendingWithdrawPage.getWithdrawRecordsCount(),  "All pending withdraw count.");
        assertWithdraw(pendingWithdrawPage, PaymentMethod.Visa,         AMOUNT, 1, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.MoneyBookers, AMOUNT, 2, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.PayPal,       AMOUNT, 3, userData);
        assertEquals(TypeUtils.calculateSum(AMOUNT, AMOUNT, AMOUNT), pendingWithdrawPage.getTotalAmount(), "Sum of all pending withdrawals.");
        pendingWithdrawPage.cancelAllWithdraw();
    }

    private UserData addPendingWithdraw() {
        return addPendingWithdraw(PaymentMethod.PayPal);
    }

    private UserData addPendingWithdraw(PaymentMethod ... paymentMethods) {
        UserData userData = DataContainer.getUserData().getCardUserData();
        //PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage); // real money 10
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        for (PaymentMethod paymentMethod : paymentMethods) {
            withdrawPage.withdrawSuccessful(paymentMethod, AMOUNT);
        }
        WebDriverUtils.waitFor(5000); // 5 seconds awaiting for receiving pending withdraw records
        return userData;
    }

    private void assertWithdraw(PendingWithdrawPage pendingWithdrawPage, PaymentMethod paymentMethod, String amount, int withdrawIndex, UserData userData) {
        assertTrue(pendingWithdrawPage.getWithdrawDate(withdrawIndex).contains(String.valueOf(DateUtils.getCurrentDate())), paymentMethod.getName() + " withdraw date.");
        assertTrue(pendingWithdrawPage.getWithdrawDate(withdrawIndex).contains(String.valueOf(DateUtils.getCurrentYear())), paymentMethod.getName() + " withdraw year.");
        assertEquals(userData.getCurrencySign(), pendingWithdrawPage.getWithdrawCurrency(withdrawIndex), paymentMethod.getName() + " withdraw currency.");
        assertEquals(amount, pendingWithdrawPage.getWithdrawAmount(withdrawIndex), paymentMethod.getName() + " withdraw amount.");
        assertEquals("Pending", pendingWithdrawPage.getWithdrawStatus(withdrawIndex), paymentMethod.getName() + " withdraw status.");
        assertEquals(paymentMethod.getAccount(), pendingWithdrawPage.getWithdrawAccount(withdrawIndex), paymentMethod.getName() + " withdraw account.");
        assertEquals(paymentMethod.getName().toLowerCase(), pendingWithdrawPage.getWithdrawMethod(withdrawIndex).toLowerCase(), paymentMethod.getName() + " withdraw method.");
        assertEquals(1, pendingWithdrawPage.getOpenedWithdrawCount(), "One withdraw details could be opened at a time.");
    }
}
