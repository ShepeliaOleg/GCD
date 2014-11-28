package pageObjects.account;

import enums.*;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.DateUtils;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierPendingWithdrawTest extends AbstractTest{

    /*B-13842 Pending Withdrawals page*/
    /*#1. Cancel pending withdraw*/
    @Test(groups = {"regression"})
    public void cancelPendingWithdraw(){
        UserData userData=DataContainer.getUserData().getRandomUserData();
        PortalUtils.registerUser(userData, true, true, PromoCode.valid, Page.homePage); // real money 10
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.withdraw, userData);
        withdrawPage.withdrawSuccessful(PaymentMethod.PayPal,       "1.00");
        withdrawPage.withdrawSuccessful(PaymentMethod.WebMoney,     "2.00");
        withdrawPage.withdrawSuccessful(PaymentMethod.MasterCard,   "3.00");
        withdrawPage.withdrawSuccessful(PaymentMethod.Visa,         "4.00");

        PendingWithdrawPage pendingWithdrawPage = (PendingWithdrawPage) NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.pending_withdraw, userData);

        assertEquals(4, pendingWithdrawPage.getWithdrawRecordsCount(),  "All pending withdraw count.");
        assertWithdraw(pendingWithdrawPage, PaymentMethod.PayPal,       "1.00", 1, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.WebMoney,     "2.00", 2, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.MasterCard,   "3.00", 3, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.Visa,         "4.00", 4, userData);
        assertEquals("10.00", pendingWithdrawPage.getTotalAmount(),     "Sum of all pending withdrawals.");

        pendingWithdrawPage.cancelWithdraw(1);

        assertEquals(3, pendingWithdrawPage.getWithdrawRecordsCount(),  "Available pending withdraw records after withdraw cancel.");
        assertWithdraw(pendingWithdrawPage, PaymentMethod.WebMoney,     "2.00", 1, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.MasterCard,   "3.00", 2, userData);
        assertWithdraw(pendingWithdrawPage, PaymentMethod.Visa,         "4.00", 3, userData);

        assertEquals("1.00", new AbstractPortalPage().getBalanceAmount(), "Balance after withdraw cancel.");
        pendingWithdrawPage.loadMore();
        new WithdrawLoadMoreNotificationPopup();
    }

    private void assertWithdraw(PendingWithdrawPage pendingWithdrawPage, PaymentMethod paymentMethod, String amount, int withdrawIndex, UserData userData) {
        assertTrue(pendingWithdrawPage.getWithdrawDate(withdrawIndex).contains(String.valueOf(DateUtils.getCurrentDate())), paymentMethod.getName() + " withdraw date.");
        assertTrue(pendingWithdrawPage.getWithdrawDate(withdrawIndex).contains(String.valueOf(DateUtils.getCurrentYear())), paymentMethod.getName() + " withdraw year.");
        assertEquals(userData.getCurrencySign(), pendingWithdrawPage.getWithdrawCurrency(withdrawIndex), paymentMethod.getName() + " withdraw currency.");
        assertEquals(amount, pendingWithdrawPage.getWithdrawAmount(withdrawIndex), paymentMethod.getName() + " withdraw amount.");
        assertEquals("Pending", pendingWithdrawPage.getWithdrawStatus(withdrawIndex), paymentMethod.getName() + "  withdraw status.");
        assertEquals(paymentMethod.getAccount(), pendingWithdrawPage.getWithdrawAccount(withdrawIndex), paymentMethod.getName() + " withdraw account.");
        assertEquals(paymentMethod.getName(), pendingWithdrawPage.getWithdrawMethod(withdrawIndex), paymentMethod.getName() + " withdraw method.");
        assertEquals(1, pendingWithdrawPage.getOpenedWithdrawCount(), "One withdraw details could be opened at a time.");
    }
}
