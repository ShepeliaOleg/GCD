import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.bonus.OkBonusPopup;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.PaySafeCardDepositPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierPaySafeCardTest extends AbstractTest{

    private static final String CURRENCY = "EUR@€";
    private static final String COUNTRY = "NL";
    private static final String AMOUNT = "1.00";


    @Test(groups = {"regression", "mobile"})
    public void paySafeCardDepositInterfaceIsFunctional(){
        PortalUtils.registerUser(getEURUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertPaySafeCardInterface();
    }

    @Test(groups = {"regression", "mobile"})
    public void paySafeCardSuccessfulDeposit(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PaySafeCardDepositPage paySafeCardDepositPage = depositPage.depositPaySafeCard(AMOUNT);
        paySafeCardDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = paySafeCardDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void paySafeCardDepositValidPromoCode(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PaySafeCardDepositPage paySafeCardDepositPage = depositPage.depositPaySafeCardValidPromoCode(AMOUNT);
        paySafeCardDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = paySafeCardDepositPage.pay();
        transactionSuccessfulPopup.closePopup();
        new OkBonusPopup().closePopup();
        assertEquals(TypeUtils.calculateSum(AMOUNT, PromoCode.valid.getAmount()), depositPage.getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void paySafeCardDepositInvalidPromoCode(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.PaySafeCard, AMOUNT);
        assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance change after deposit");
    }


    @Test(groups = {"regression", "mobile"})
    public void paySafeCardCancelDeposit(){
        UserData userData = getEURUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        PaySafeCardDepositPage paySafeCardDepositPage = depositPage.depositPaySafeCard(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = paySafeCardDepositPage.cancelDeposit();
        transactionUnSuccessfulPopup.closePopup();
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance");
    }

    private UserData getEURUser(){
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCurrency(CURRENCY);
        userData.setCountry(COUNTRY);
        return userData;
    }

}
