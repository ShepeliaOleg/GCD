package pageObjects.cashier.deposit;

import pageObjects.cashier.TransactionSuccessfulPopup;
import pageObjects.cashier.TransactionUnSuccessfulPopup;
import pageObjects.core.AbstractIframe;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class EnvoyDepositPage extends AbstractPage{

    private static final String BUTTON_CANCEL_XP = "//input[contains(text(), 'Cancel')]";
    private static final String BUTTON_PROCEED_XP = "//input[@value='Proceed']";
    private static final String TRUSTLY_XP = "//*[contains(@src, 'trustly')]";
    public static final String GIROPAY_XP = "//*[@alt='GIROPAY']";
    public static final String BANKDROPDOWN_XP = "//*[@id='bankdropdown']";

    public EnvoyDepositPage(){
        WebDriverUtils.waitFor();
        WebDriverUtils.waitForPageToLoad();
    }

    public TransactionUnSuccessfulPopup cancelDeposit(){
        WebDriverUtils.click(BUTTON_CANCEL_XP);
        return new TransactionUnSuccessfulPopup();
    }

    public TransactionSuccessfulPopup pay(String amount, UserData userData){
        assertAmount(amount);
        if(WebDriverUtils.isVisible(TRUSTLY_XP, 1)){
            new TrustlyIframe().pay();
        }else if(WebDriverUtils.isVisible(BUTTON_PROCEED_XP, 1)) {
            WebDriverUtils.setDropdownOptionByValue("//*[contains(@id, 'ddlBanks')]", "INGBNL2A");
            WebDriverUtils.click(BUTTON_PROCEED_XP);
            WebDriverUtils.waitForElement("//*[contains(@id, 'lblSimulatorCase')]");
            WebDriverUtils.click("//*[contains(@id, 'btnGo')]");
        }else if(WebDriverUtils.isVisible("//*[contains(@id, 'tblPOLI')]",1)){
            WebDriverUtils.click(BUTTON_PROCEED_XP);
            WebDriverUtils.waitForElement(BANKDROPDOWN_XP);
            WebDriverUtils.setDropdownOptionByValue(BANKDROPDOWN_XP, "iBankAU01");
            WebDriverUtils.click("//*[@id='proceed-button']");
            WebDriverUtils.waitForElement("//*[@id='BankFrame']", 60);
            new PolyIframe().pay();
        }else if(WebDriverUtils.isVisible(GIROPAY_XP)){
            WebDriverUtils.click(GIROPAY_XP);
            WebDriverUtils.waitForElement(BUTTON_PROCEED_XP);
            WebDriverUtils.clearAndInputTextToField("//*[contains(@id, 'txtCustomerName')]", "AAAAAA");
            WebDriverUtils.clearAndInputTextToField("//*[contains(@id, 'txtGiroSWIFTCode')]", "AAAAAA");
            WebDriverUtils.click(BUTTON_PROCEED_XP);
        }else {
            AbstractTest.failTest("Payment page did not load for '"+userData.getCountry()+"' '"+userData.getCurrencyName()+"'");
        }
        return new TransactionSuccessfulPopup();
    }

    private void assertAmount(String amount){
        AbstractTest.assertTextVisible(amount, "Amount");
    }

    private class TrustlyIframe extends AbstractIframe{

        private static final String LABEL_NORDEA_XP = "//*[contains(text(), 'Nordea')]";

        public TrustlyIframe() {
            super("ctl00_mainContent_iframeGP");
        }

        public void pay() {
            WebDriverUtils.click(LABEL_NORDEA_XP);
            WebDriverUtils.waitForElement("//*[@name='loginid']");
        }
    }

    private class PolyIframe extends AbstractIframe{

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
