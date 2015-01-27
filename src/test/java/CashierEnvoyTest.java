import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.EnvoyDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.RandomUtils;
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierEnvoyTest extends AbstractTest{

    private static final String AMOUNT = "10.00";

    @Test(groups = {"regression", "mobile"})
    public void envoyDepositInterfaceIsFunctional(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertEnvoyInterface(userData);
    }

    @Test(groups = {"regression", "mobile"})
    public void envoyWithdrawInterfaceIsFunctional(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.assertEnvoyInterface(userData);
    }

    @Test(groups = {"regression", "mobile"})
    public void deposit(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        successfulDeposit(userData);
    }

    @Test(groups = {"regression", "mobile"})
    public void depositValidPromoCode(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoyValidPromoCode(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = envoyDepositPage.pay(userData, true);
        transactionSuccessfulPopup.closePopup();
//        new OkBonusPopup().closePopup();
        assertEquals(TypeUtils.calculateSum(AMOUNT, PromoCode.valid.getAmount()), new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void depositInvalidPromoCode(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.Envoy, AMOUNT);
        assertEquals("Inserted Promotional Code does not exist", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
        assertEquals("0.00", depositPage.getBalanceAmount(), "Balance change after deposit");
    }

    @Test(groups = {"regression", "mobile"})
    public void cancelDeposit(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoy(AMOUNT);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = envoyDepositPage.cancelDeposit(userData);
        transactionUnSuccessfulPopup.closePopup();
        assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void withdrawUnsuccessful(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = withdrawPage.withdrawEnvoy(AMOUNT);
        transactionUnSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    @Test(groups = {"regression", "mobile"})
    public void cancelWithdraw(){
        UserData userData = getEnvoyUser();
        PortalUtils.registerUser(userData, PromoCode.valid);
        WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
        withdrawPage.withdrawConfirmationPopupClose(PaymentMethod.Envoy, AMOUNT);
        assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    private void successfulDeposit(UserData userData){
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoy(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = envoyDepositPage.pay(userData);
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    private UserData getEnvoyUser(){
        UserData[] userDatas = getEnvoyUsers();
        int randomIndex = RandomUtils.generateRandomIntBetween(0, userDatas.length);
        return userDatas[randomIndex];
    }

    private UserData[] getEnvoyUsers(){
//        UserData ideal =    setUserData("NL", "EUR");
//        UserData prezelwy = setUserData("PL", "PLN");
//        UserData eKonto =   setUserData("CZ", "CZK");
//        UserData euteller = setUserData("FI", "EUR");
//        UserData ewire =    setUserData("DK", "DKK");
        UserData sofort =   setUserData("DE", "EUR");
//        UserData ibanq =    setUserData("JP", "USD");
//        UserData moneta =   setUserData("RU", "USD");
//        UserData poli =     setUserData("AU", "AUD");
        return new UserData[]{
//                ideal,      //0
//                prezelwy,   //1
//                eKonto,     //2
//                euteller,   //3
//                ewire,      //4
                sofort,     //5
//                ibanq,      //6
//                moneta,     //7
//                poli        //8
        };
    }

    private static UserData setUserData(String country, String currency) {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCountry(country);
        userData.setCurrency(currency);
        return userData;
    }
}
