import enums.ConfiguredPages;
import enums.PaymentMethod;
import enums.PromoCode;
import org.testng.annotations.Test;
import pageObjects.bonus.OkBonusPopup;
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
        successfulDeposit(userData);
    }

    @Test(groups = {"regression", "mobile"})
    public void depositValidPromoCode(){
        for(UserData userData: getEnvoyUsers()) {
            try{
                PortalUtils.registerUser(userData);
                DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
                EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoyValidPromoCode(AMOUNT);
                TransactionSuccessfulPopup transactionSuccessfulPopup = envoyDepositPage.pay(AMOUNT, userData);
                transactionSuccessfulPopup.closePopup();
                new OkBonusPopup().closePopup();
                assertEquals(TypeUtils.calculateSum(AMOUNT, PromoCode.valid.getAmount()), new AbstractPortalPage().getBalanceAmount(), "Balance");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void depositInvalidPromoCode(){
        for(UserData userData: getEnvoyUsers()) {
            try{
                PortalUtils.registerUser(userData);
                DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
                depositPage = depositPage.depositInvalidPromoCode(PaymentMethod.Envoy, AMOUNT);
                assertEquals("Coupon code is not found or not available", depositPage.getPortletErrorMessage(), "Invalid bonus error message");
                assertEquals("0.00", depositPage.getBalanceAmount(), "Balance change after deposit");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void cancelDeposit(){
        for(UserData userData: getEnvoyUsers()) {
            try{
                PortalUtils.registerUser(userData);
                DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
                EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoy(AMOUNT);
                TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = envoyDepositPage.cancelDeposit();
                transactionUnSuccessfulPopup.closePopup();
                assertEquals("0.00", new AbstractPortalPage().getBalanceAmount(), "Balance");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void withdrawUnsuccessful(){
        for(UserData userData: getEnvoyUsers()) {
            try{
                PortalUtils.registerUser(userData, PromoCode.valid);
                WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
                TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = withdrawPage.withdrawEnvoy(AMOUNT);
                transactionUnSuccessfulPopup.closePopup();
                assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void cancelWithdraw(){
        for(UserData userData: getEnvoyUsers()) {
            try{
                PortalUtils.registerUser(userData, PromoCode.valid);
                WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
                withdrawPage.withdrawawConfirmationPopupClose(PaymentMethod.Envoy, AMOUNT);
                assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
            }catch (Exception e){
            }
        }
    }

    private void successfulDeposit(UserData userData){
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoy(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = envoyDepositPage.pay(AMOUNT, userData);
        transactionSuccessfulPopup.closePopup();
        assertEquals(AMOUNT, new AbstractPortalPage().getBalanceAmount(), "Balance");
    }

    private UserData getEnvoyUser(){
        UserData[] userDatas = getEnvoyUsers();
        int randomIndex = 5; //RandomUtils.generateRandomIntBetween(0, userDatas.length);
        return userDatas[randomIndex];
    }

    private UserData[] getEnvoyUsers(){
        UserData ideal =    setUserData("NL", "EUR");
        UserData prezelwy = setUserData("PL", "PLN");
        UserData eKonto =   setUserData("CZ", "CZK");
        UserData euteller = setUserData("FI", "EUR");
        UserData ewire =    setUserData("DK", "DKK");
        UserData sofort =   setUserData("DE", "EUR");
        UserData ibanq =    setUserData("JP", "USD");
        UserData moneta =   setUserData("RU", "USD");
        UserData poli =     setUserData("AU", "AUD");
        return new UserData[]{
                ideal,      //0
                prezelwy,   //1
                eKonto,     //2
                euteller,   //3
                ewire,      //4
                sofort,     //5
                ibanq,      //6
                moneta,     //7
                poli        //8
        };
    }

    private static UserData setUserData(String country, String currency) {
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setCountry(country);
        userData.setCurrency(currency);
        return userData;
    }
}
