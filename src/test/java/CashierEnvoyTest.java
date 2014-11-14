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
import utils.TypeUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class CashierEnvoyTest extends AbstractTest{

    private static final String AMOUNT = "10.00";

    @Test(groups = {"regression", "mobile"})
    public void envoyDepositInterfaceIsFunctional(){
        for(UserData userData:getUsers()){
            try{
                PortalUtils.registerUser(userData);
                DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
                depositPage.assertEnvoyInterface(userData);
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void envoyWithdrawInterfaceIsFunctional(){
        for(UserData userData:getUsers()) {
            try{
                PortalUtils.registerUser(userData);
                WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
                withdrawPage.assertEnvoyInterface(userData);
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void deposit(){
        for(UserData userData:getUsers()) {
            try{
                successfulDeposit(userData);
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void depositValidPromoCode(){
        for(UserData userData:getUsers()) {
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
        for(UserData userData:getUsers()) {
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
        for(UserData userData:getUsers()) {
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
        for(UserData userData:getUsers()) {
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
        for(UserData userData:getUsers()) {
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

    private UserData[] getUsers(){
        UserData ideal = DataContainer.getUserData().getRandomUserData();
        ideal.setCountry("NL");
        ideal.setCurrency("EUR@€");
        UserData prezelwy = DataContainer.getUserData().getRandomUserData();
        prezelwy.setCountry("PL");
        prezelwy.setCurrency("PLN");
        UserData eKonto = DataContainer.getUserData().getRandomUserData();
        eKonto.setCountry("CZ");
        eKonto.setCurrency("CZK");
        UserData euteller = DataContainer.getUserData().getRandomUserData();
        euteller.setCountry("FI");
        euteller.setCurrency("EUR");
        UserData ewire = DataContainer.getUserData().getRandomUserData();
        ewire.setCountry("DK");
        ewire.setCurrency("DKK");
        UserData giropay = DataContainer.getUserData().getRandomUserData();
        giropay.setCountry("DE");
        giropay.setCurrency("EUR");
        UserData ibanq = DataContainer.getUserData().getRandomUserData();
        ibanq.setCountry("JP");
        ibanq.setCurrency("USD");
        UserData moneta = DataContainer.getUserData().getRandomUserData();
        moneta.setCountry("RU");
        moneta.setCurrency("USD");
        UserData poli = DataContainer.getUserData().getRandomUserData();
        poli.setCountry("AU");
        poli.setCurrency("AUD");
        return new UserData[]{
                ideal,      //0
                prezelwy,   //1
                eKonto,     //2
                euteller,   //3
                ewire,      //4
                giropay,    //5
                ibanq,      //6
                moneta,     //7
                poli        //8
        };
    }
}
