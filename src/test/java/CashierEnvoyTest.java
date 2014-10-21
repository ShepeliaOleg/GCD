import enums.ConfiguredPages;
import enums.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.cashier.deposit.EnvoyDepositPage;
import pageObjects.cashier.withdraw.WithdrawPage;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;

public class CashierEnvoyTest extends AbstractTest{

    private static final String AMOUNT = "10.00";

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

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
    public void depositWithdrawIdeal(){
        for(UserData userData:getUsers()) {
            try{
                successfulDeposit(userData);
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
                assertEquals(userData.getCurrencySign() + " 0.00", new AbstractPage().getBalance(), "Balance");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void withdrawUnsuccessful(){
        for(UserData userData:getUsers()) {
            try{
                PortalUtils.registerUser(userData, "valid");
                WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
                TransactionUnSuccessfulPopup transactionUnSuccessfulPopup = withdrawPage.withdrawEnvoy(AMOUNT);
                transactionUnSuccessfulPopup.closePopup();
                assertEquals(userData.getCurrencySign() + " 10.00", new AbstractPage().getBalance(), "Balance");
            }catch (Exception e){
            }
        }
    }

    @Test(groups = {"regression", "mobile"})
    public void cancelWithdraw(){
        for(UserData userData:getUsers()) {
            try{
                PortalUtils.registerUser(userData, "valid");
                WithdrawPage withdrawPage = (WithdrawPage) NavigationUtils.navigateToPage(ConfiguredPages.withdraw);
                withdrawPage.assertWithdrawConfirmationPopupAndClose(PaymentMethod.Envoy, AMOUNT);
                assertEquals(userData.getCurrencySign() + " 10.00", new AbstractPage().getBalance(), "Balance");
            }catch (Exception e){
            }
        }
    }

    private void successfulDeposit(UserData userData){
        PortalUtils.registerUser(userData);
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        EnvoyDepositPage envoyDepositPage = depositPage.depositEnvoy(AMOUNT);
        envoyDepositPage.assertAmount(AMOUNT);
        TransactionSuccessfulPopup transactionSuccessfulPopup = envoyDepositPage.pay(AMOUNT);
        transactionSuccessfulPopup.closePopup();
        assertEquals(userData.getCurrencySign()+" "+AMOUNT, new AbstractPage().getBalance(), "Balance");
    }

    private UserData[] getUsers(){
        UserData ideal = defaultUserData.getRandomUserData();
        ideal.setCountry("NL");
        ideal.setCurrency("EUR@€");
        UserData prezelwy = defaultUserData.getRandomUserData();
        prezelwy.setCountry("PL");
        prezelwy.setCurrency("PLN");
        UserData eKonto = defaultUserData.getRandomUserData();
        eKonto.setCountry("CZ");
        eKonto.setCurrency("CZK");
        UserData euteller = defaultUserData.getRandomUserData();
        euteller.setCountry("FI");
        euteller.setCurrency("EUR");
        UserData ewire = defaultUserData.getRandomUserData();
        ewire.setCountry("DK");
        ewire.setCurrency("DKK");
        UserData giropay = defaultUserData.getRandomUserData();
        giropay.setCountry("DE");
        giropay.setCurrency("EUR");
        UserData ibanq = defaultUserData.getRandomUserData();
        ibanq.setCountry("JP");
        ibanq.setCurrency("USD");
        UserData moneta = defaultUserData.getRandomUserData();
        moneta.setCountry("RU");
        moneta.setCurrency("USD");
        UserData poli = defaultUserData.getRandomUserData();
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
