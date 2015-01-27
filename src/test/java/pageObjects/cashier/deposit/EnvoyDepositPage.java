package pageObjects.cashier.deposit;

import pageObjects.bonus.AcceptDeclineBonusPopup;
import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractPortalIframe;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class EnvoyDepositPage extends AbstractPortalPage {

    private static final String BUTTON_CANCEL_XP =          "//input[@value='Cancel']";
    private static final String BUTTON_PROCEED_XP =         "//input[@value='Proceed']";
    private static final String BUTTON_CONFIRM_XP =         "//input[@value='Confirm']";
    //iDeal
    private static final String BUTTON_IDEAL_CONFIRM_XP =     "//input[@value='Confirm Transaction']";
    // Sofort
    private static final String INPUT_SOFORT_SORT_CODE_XP = "//*[@id='TransactionsSessionSenderBankCode']";
    private static final String BUTTON_SOFORT_NEXT_XP =     "//*[@id='WizardForm']//button";
    private static final String INPUT_SOFORT_ACCOUNT_XP =   "//*[@id='BackendFormLOGINNAMEUSERID']";
    private static final String INPUT_SOFORT_PIN_XP =       "//*[@id='BackendFormUSERPIN']";
    private static final String RADIO_SOFORT_ACCOUNT_XP =   "//*[@id='TransactionsSessionSenderAccountNumber12345678']";
    private static final String INPUT_SOFORT_TAN_XP =       "//*[@id='BackendFormTan']";
    private static final String SOFORT_XP =                 "//*[@alt='SOFORT']";

//    private static final String TRUSTLY_XP =                "//*[contains(@src, 'trustly')]";
//    private static final String BANKDROPDOWN_XP =           "//*[@id='bankdropdown']";

    public EnvoyDepositPage(){
        WebDriverUtils.waitFor();
        WebDriverUtils.waitForPageToLoad();
    }

    public TransactionUnSuccessfulPopup cancelDeposit(UserData userData){
        String userCountry = userData.getCountry();
        String userCurrency = userData.getCurrency();
        switch (userCountry) {
            case "NL":
                if (WebDriverUtils.isVisible(BUTTON_PROCEED_XP, 1)) {
                    //TODO
                } else {
                    AbstractTest.failTest("iDeal payment method is not available for Envoy for user from country " + userCountry + " and currency " + userCurrency);
                }
                break;
            case "DE":
                if(WebDriverUtils.isVisible(SOFORT_XP)){
                    WebDriverUtils.click(SOFORT_XP);
                } else {
                    AbstractTest.failTest("Sofort payment method is not available for Envoy for user from country " + userCountry + " and currency " + userCurrency);
                }
                break;
            default:
                AbstractTest.failTest("User with unsupported country for Envoy payment method: " + userCountry);
        }
        WebDriverUtils.click(BUTTON_CANCEL_XP);
        return new TransactionUnSuccessfulPopup();
    }

    public TransactionSuccessfulPopup pay(UserData userData){
        return pay(userData, false);
    }

    public TransactionSuccessfulPopup pay(UserData userData, boolean withPromoCode){
        String userCountry = userData.getCountry();
        String userCurrency = userData.getCurrency();
        switch (userCountry) {
            case "NL":
                if(WebDriverUtils.isVisible(BUTTON_PROCEED_XP, 1)) {
                    WebDriverUtils.setDropdownOptionByValue("//*[contains(@id, 'ddlBanks')]", "INGBNL2A");
                    WebDriverUtils.click(BUTTON_PROCEED_XP);
                    WebDriverUtils.click(BUTTON_IDEAL_CONFIRM_XP);
                } else {
                    AbstractTest.failTest("iDeal payment method is not available for Envoy for user from country " + userCountry + " and currency " + userCurrency);
                }
                break;
            case "DE":
                if(WebDriverUtils.isVisible(SOFORT_XP)){
                    processSofort();
                } else {
                    AbstractTest.failTest("Sofort payment method is not available for Envoy for user from country " + userCountry + " and currency " + userCurrency);
                }
                break;
            default:
                AbstractTest.failTest("User with unsupported country for Envoy payment method: " + userCountry);
        }
        if (withPromoCode) {
            AcceptDeclineBonusPopup acceptDeclineBonusPopup = new AcceptDeclineBonusPopup();
            acceptDeclineBonusPopup.clickAccept();
        }
        return new TransactionSuccessfulPopup();
    }

    private void processSofort() {
        WebDriverUtils.click(SOFORT_XP);
        WebDriverUtils.waitForElement(BUTTON_CONFIRM_XP);
        WebDriverUtils.click(BUTTON_CONFIRM_XP);
        WebDriverUtils.waitForElement(INPUT_SOFORT_SORT_CODE_XP);
        WebDriverUtils.clearAndInputTextToField(INPUT_SOFORT_SORT_CODE_XP, "88888888");
        WebDriverUtils.waitForElement(BUTTON_SOFORT_NEXT_XP);
        WebDriverUtils.click(BUTTON_SOFORT_NEXT_XP);
        WebDriverUtils.waitForElement(INPUT_SOFORT_ACCOUNT_XP);
        WebDriverUtils.clearAndInputTextToField(INPUT_SOFORT_ACCOUNT_XP, "123456");
        WebDriverUtils.clearAndInputTextToField(INPUT_SOFORT_PIN_XP, "1234");
        WebDriverUtils.click(BUTTON_SOFORT_NEXT_XP);
        WebDriverUtils.waitForElement(RADIO_SOFORT_ACCOUNT_XP);
        WebDriverUtils.click(RADIO_SOFORT_ACCOUNT_XP);
        WebDriverUtils.click(BUTTON_SOFORT_NEXT_XP);
        WebDriverUtils.waitForElement(INPUT_SOFORT_TAN_XP);
        WebDriverUtils.clearAndInputTextToField(INPUT_SOFORT_TAN_XP, "12345");
        WebDriverUtils.click(BUTTON_SOFORT_NEXT_XP);
    }

    private void assertAmount(String amount){
        AbstractTest.assertTextVisible(amount, "Amount value is displayed '" + amount + "'.");
    }

    private class TrustlyIframe extends AbstractPortalIframe {

        private static final String LABEL_NORDEA_XP = "//*[contains(text(), 'Nordea')]";

        public TrustlyIframe() {
            super("ctl00_mainContent_iframeGP");
        }

        public void pay() {
            WebDriverUtils.click(LABEL_NORDEA_XP);
            WebDriverUtils.waitForElement("//*[@name='loginid']");
        }
    }

    private class PolyIframe extends AbstractPortalIframe{

        private static final String LABEL_NORDEA_XP = "//div[contains(text(), 'Nordea')]";
        public static final String BUTTON_PROCEED_XP = "//*[@id='btnProceed']";
        public static final String BUTTON_CONFIRM_XP = "//*[@id='btnConfirm']";


        public PolyIframe() {
            super("BankFrame");
        }

        public void pay() {
            WebDriverUtils.clearAndInputTextToField("//*[@id='UserName']", "DemoShopper");
            WebDriverUtils.clearAndInputTextToField("//*[@id='Password']", "DemoShopper");
            WebDriverUtils.click("//*[@id='btnLogin']");
            WebDriverUtils.waitForElement(BUTTON_PROCEED_XP, 60);
            WebDriverUtils.click(BUTTON_PROCEED_XP);
            WebDriverUtils.waitForElement(BUTTON_CONFIRM_XP);
            WebDriverUtils.click(BUTTON_CONFIRM_XP);
        }
    }
}
