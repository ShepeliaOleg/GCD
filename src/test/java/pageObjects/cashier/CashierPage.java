package pageObjects.cashier;

import pageObjects.account.AddCardPopup;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class CashierPage extends AbstractPage{
    protected static final String ROOT_XP = "//*[contains(@class, 'portlet-mobile-cashier')]";
    protected static final String BUTTON_XP = "//*[contains(@class, 'btn')]";
    protected static final String METHOD_HEADER_BASE_XP = "//*[contains(@class, 'info-list__field')]";
    protected static final String METHOD_HEADER_XP = METHOD_HEADER_BASE_XP+"[contains(text(), '"+PLACEHOLDER+"')]";
    protected static final String METHOD_BODY_XP = WebDriverUtils.getFollowingElement(METHOD_HEADER_XP);
    protected static final String BUTTON_ADD_CARD_XP = BUTTON_XP+"[@data-url='/add-card']";
    protected static final String FIELD_AMOUNT_XP = "//*[@name='amount']";
    protected static final String FIELD_PROMO_CODE_XP = "//*[@name='promoCode']";
    protected static final String FIELD_ACCOUNT_XP = "//*[@name='accountId']";
    private static final String ADD = "add";
    protected static final String FIELD_ACCOUNT_ADD_XP = "//*[@name='"+ADD+"']";
    protected static final String FIELD_PASSWORD_CODE_XP = "//*[@name='accountPwd']";

    protected static final String MONEYBOOKERS = "Moneybookers";
    protected static final String PAYPAL = "PayPal";
    protected static final String WEBMONEY = "WebMoney";
    protected static final String NETELLER = "NETeller";
    protected static final String QIWI = "QIWI via Safecharge";

    protected static final String QIWI_ACCOUNT = "9260366832";
    protected static final String QIWI_ACCOUNT_2 = "9852887726";

    public CashierPage(){
        super(new String[]{ROOT_XP, BUTTON_ADD_CARD_XP, METHOD_HEADER_BASE_XP});
    }

    private AddCardPopup clickAddCard(){
        WebDriverUtils.click(BUTTON_ADD_CARD_XP);
        return new AddCardPopup();
    }

    private void assertInterfaceByType(String method, String[] fields){
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method);
        String header = METHOD_HEADER_XP.replace(PLACEHOLDER, method);
        WebDriverUtils.click(header);
        AbstractTest.assertTrue(WebDriverUtils.isVisible(body), "Payment method opened");
        for(String field:fields){
            AbstractTest.assertTrue(WebDriverUtils.isVisible(body+field, 0), "'"+field+"' is present on '"+method+"' form");
        }
        AbstractTest.assertTrue(WebDriverUtils.isVisible(body+BUTTON_XP, 0), "'"+BUTTON_XP+"' is present on '"+method+"' form");
        WebDriverUtils.click(header);
        WebDriverUtils.waitForElementToDisappear(body);
    }

    protected void addAccountByType(String method, String account) {
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method);
        if(!WebDriverUtils.isVisible(body)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, method));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.setDropdownOptionByValue(body+FIELD_ACCOUNT_XP, ADD);
        WebDriverUtils.waitForElement(body+FIELD_ACCOUNT_ADD_XP);
        WebDriverUtils.clearAndInputTextToField(body+FIELD_ACCOUNT_ADD_XP, account);
    }

    protected void closeAddAccountField(String method, String account) {
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method);
        WebDriverUtils.setDropdownOptionByValue(body+FIELD_ACCOUNT_XP, account);
        WebDriverUtils.waitForElementToDisappear(body+FIELD_ACCOUNT_ADD_XP);
    }

    protected void processPaymentByType(String method, String amount, String promoCode, String account, String password){
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method);
        if(!WebDriverUtils.isVisible(body)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, method));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.inputTextToField(body+FIELD_AMOUNT_XP, amount);
        if(promoCode!=null){
            WebDriverUtils.inputTextToField(body+FIELD_PROMO_CODE_XP, promoCode);
        }
        if(account!=null){
            WebDriverUtils.inputTextToField(body+FIELD_ACCOUNT_XP, account);
        }
        if(password!=null){
            WebDriverUtils.inputTextToField(body+FIELD_PASSWORD_CODE_XP, password);
        }
        WebDriverUtils.click(body+BUTTON_XP);
    }

    public void assertQIWIInterface(){
        assertInterfaceByType(QIWI, new String[]{FIELD_AMOUNT_XP, FIELD_ACCOUNT_XP, FIELD_PROMO_CODE_XP});
    }

}
