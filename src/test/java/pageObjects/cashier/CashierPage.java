package pageObjects.cashier;

import enums.PaymentMethod;
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

    public CashierPage(){
        super(new String[]{ROOT_XP, BUTTON_ADD_CARD_XP, METHOD_HEADER_BASE_XP});
    }

    private AddCardPopup clickAddCard(){
        WebDriverUtils.click(BUTTON_ADD_CARD_XP);
        return new AddCardPopup();
    }

    protected void assertInterfaceByType(PaymentMethod method, String[] fields){
        String name = method.getName();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        String header = METHOD_HEADER_XP.replace(PLACEHOLDER, name);
        WebDriverUtils.click(header);
        AbstractTest.assertTrue(WebDriverUtils.isVisible(body), "Payment method opened");
        for(String field:fields){
            AbstractTest.assertTrue(WebDriverUtils.isVisible(body+field, 0), "'"+field+"' is present on '"+method+"' form");
        }
        AbstractTest.assertTrue(WebDriverUtils.isVisible(body+BUTTON_XP, 0), "'"+BUTTON_XP+"' is present on '"+method+"' form");
        WebDriverUtils.click(header);
        WebDriverUtils.waitForElementToDisappear(body);
    }

    public void addAccountByType(PaymentMethod method) {
        String name = method.getName();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        if(!WebDriverUtils.isVisible(body)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, name));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.setDropdownOptionByValue(body+FIELD_ACCOUNT_XP, ADD);
        WebDriverUtils.waitForElement(body+FIELD_ACCOUNT_ADD_XP);
        WebDriverUtils.clearAndInputTextToField(body+FIELD_ACCOUNT_ADD_XP, method.getSecondaryAccount());
    }

    public void closeAddAccountField(PaymentMethod method) {
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method.getName());
        WebDriverUtils.setDropdownOptionByValue(body+FIELD_ACCOUNT_XP, method.getAccount());
        WebDriverUtils.waitForElementToDisappear(body+FIELD_ACCOUNT_ADD_XP);
    }

    protected void processPaymentByType(PaymentMethod method, String amount){
        String name = method.getName();
        String account = method.getAccount();
        String password = method.getPassword();
        String promoCode = method.getPromoCode();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        String fieldPromoCode = body+FIELD_PROMO_CODE_XP;
        String fieldAccount = body+FIELD_ACCOUNT_XP;
        String fieldPassword = body+FIELD_PASSWORD_CODE_XP;
        if(!WebDriverUtils.isVisible(body, 0)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, name));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.inputTextToField(body+FIELD_AMOUNT_XP, amount);
        if(WebDriverUtils.isVisible(fieldPromoCode, 0)){
            WebDriverUtils.inputTextToField(fieldPromoCode, promoCode);
        }
        if(WebDriverUtils.isVisible(fieldAccount, 0)){
            WebDriverUtils.inputTextToField(fieldAccount, account);
        }
        if(WebDriverUtils.isVisible(fieldPassword, 0)){
            WebDriverUtils.inputTextToField(fieldPassword, password);
        }
        WebDriverUtils.click(body+BUTTON_XP);
    }




}
