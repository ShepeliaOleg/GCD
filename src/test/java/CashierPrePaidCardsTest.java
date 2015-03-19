import enums.ConfiguredPages;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.bonus.BonusPage;
import pageObjects.cashier.CongratulationsPopup;
import pageObjects.cashier.deposit.DepositPage;
import pageObjects.external.ims.IMSCreatePrepaidCardsPage;
import springConstructors.UserData;
import utils.*;
import utils.core.AbstractTest;
import utils.core.DataContainer;
import utils.core.WebDriverFactory;
import utils.validation.ValidationUtils;

/**
 * Created by serhiist on 3/16/2015.
 */
public class CashierPrePaidCardsTest extends AbstractTest {
    private final int MONETARY_VALUE = 10;

    /*1. Check fields presence*/
    @Test(groups = {"regression", "mobile"})
    public void prePaidCardsDepositInterfaceIsFunctional(){
        PortalUtils.loginUser(getPrePaidCardsUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.assertPrePaidcardInterface();
    }

    /*2. Check whether PrePaidNumber is a mandatory field*/
    @Test(groups = {"regression", "mobile"})
    public void CheckMandatoryFields(){
        PortalUtils.loginUser(getPrePaidCardsUser());
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.depositPrePaidCardWithEmptyFields();
        ValidationUtils.assertTooltipStatus("accountId", ValidationUtils.STATUS_FAILED, "accountId");
        ValidationUtils.assertTooltipStatus("accountPwd",ValidationUtils.STATUS_NONE, "accountPwd");
        ValidationUtils.assertTooltipStatus("promoCode",ValidationUtils.STATUS_NONE, "promoCode");
        assertEquals("This field is required", ValidationUtils.getTooltipText("accountId"), "Wrong tooltip for empty 'Pre Paid Card Number' field");
    }

    /*3. Input valid Сard Number and proceed with deposit*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithValidPrePaidCardNumber(){
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, false);
        PortalUtils.loginUser(userData);
        Float initialBalance = Float.parseFloat(new HomePage().getBalanceAmount());
        DepositPage depositPage = navigateToDepositAndInputNumberPin(numberAndPin);
        //close popup
        assertTrue(Float.compare(Float.parseFloat(depositPage.getBalanceAmount()) - initialBalance, (float) MONETARY_VALUE) == 0, "Money was not added to balance after using prepaid card ");
    }

    /*4. Input valid Сard Number and proceed with deposit with valid pin*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithValidPrePaidCardNumberAndPin() {
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, true);
        PortalUtils.loginUser(userData);
        Float initialBalance = Float.parseFloat(new HomePage().getBalanceAmount());
        DepositPage depositPage = navigateToDepositAndInputNumberPin(numberAndPin);
        //close popup
        assertTrue(Float.compare(Float.parseFloat(depositPage.getBalanceAmount()) - initialBalance, (float) MONETARY_VALUE) == 0, "Money was not added to balance after using prepaid card ");
    }


    /*5. Input valid Сard Number and proceed without pin*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithValidPrePaidCardNumberWithoutPin() {
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, true);
        PortalUtils.loginUser(userData);
        DepositPage depositPage = navigateToDepositAndInputNumberWithoutPin(numberAndPin);
        assertEquals("Please enter valid credentials", depositPage.getErrorMsg(), "Wrong error message is displayed for proceeding without Pin");
    }

    /*6. Input valid Сard Number and invalid pin*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithValidPrePaidCardNumberAndInvalidPin() {
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, true);
        PortalUtils.loginUser(userData);
        DepositPage depositPage = navigateToDepositAndInputNumberAndInvalidPin(numberAndPin);
        assertEquals("Please enter valid credentials", depositPage.getErrorMsg(), "Wrong error message is displayed for proceeding with invalid Pin");
    }

    /*7. Deposit with correct Promo code*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithValidPromocode() {
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, true);
        PortalUtils.loginUser(userData);
        Float initialBalance = Float.parseFloat(new HomePage().getBalanceAmount());
        DepositPage depositPage = navigateToDepositAndInputNumberPinAndPromo(numberAndPin, true);
        Float finalBalance = Float.parseFloat(depositPage.getBalanceAmount());
        WebDriverUtils.refreshPage();
        assertTrue(Float.compare(finalBalance - initialBalance, (float) MONETARY_VALUE) == 0, "Money was not added to balance after using prepaid card with valid promocode ");
    }

    /*8. Deposit with wrong Promo code*/
    @Test(groups = {"regression", "mobile"})
    public void depositeWithInvalidPromocode() {
        UserData userData = getPrePaidCardsUser();
        String[] numberAndPin = getValidPrePaidCardNumberAndPin(userData, true);
        PortalUtils.loginUser(userData);
        DepositPage depositPage = navigateToDepositAndInputNumberPinAndPromo(numberAndPin, false);
        assertEquals("Inserted Promotional Code does not exist", depositPage.getErrorMsg(), "Wrong error message is displayed for proceeding with invalid promocode");
    }

    private UserData getPrePaidCardsUser(){
        WebDriverFactory.setServerDriver(WebDriverFactory.getPortalDriver());
        UserData userData = DataContainer.getUserData().getRandomUserData();
        userData.setUsername("prepaid");
        userData.setPassword("Password1");
        userData.setCurrency("USD");
        return userData;
    }

    private String[] getValidPrePaidCardNumberAndPin(UserData userData, boolean isPinNecessary){
        IMSCreatePrepaidCardsPage prepaidCardsPage = IMSUtils.navigateToPrePaidCardsPage();
        prepaidCardsPage.inputDataToGeneratePrepaidCard(isPinNecessary, MONETARY_VALUE, userData.getCurrency(), 1);
        String[] result = FileUtils.getPrePaidCardNumberAndPinFromExportedFile();
        prepaidCardsPage.deleteGeneratedFile();
        return result;
    }

    private DepositPage navigateToDepositAndInputNumberPin(String[] numberAndPin){
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.processPrePaidCard(numberAndPin[0], numberAndPin[1], "");
        CongratulationsPopup congratulationsPopup = new CongratulationsPopup();
        return depositPage;
    }

    private DepositPage navigateToDepositAndInputNumberWithoutPin(String[] numberAndPin){
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.processPrePaidCard(numberAndPin[0], "", "");
        return depositPage;
    }

    private DepositPage navigateToDepositAndInputNumberAndInvalidPin(String[] numberAndPin){
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.processPrePaidCard(numberAndPin[0], "1111", "");
        return depositPage;
    }

    private DepositPage navigateToDepositAndInputNumberPinAndPromo(String[] numberAndPin, boolean isPromoValid){
        String promo = (isPromoValid) ? BonusPage.PROMOCODE : "111";
        DepositPage depositPage = (DepositPage) NavigationUtils.navigateToPage(ConfiguredPages.deposit);
        depositPage.processPrePaidCard(numberAndPin[0], numberAndPin[1], promo);
        return depositPage;
    }
}
