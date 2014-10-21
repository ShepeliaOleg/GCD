package pageObjects.cashier;

import enums.PaymentMethod;
import pageObjects.account.AddCardPage;
import pageObjects.core.AbstractPage;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.Arrays;
import java.util.List;

public class CashierPage extends AbstractPage{
    protected static final String ROOT_XP = "//*[contains(@class, 'portlet-mobile-cashier')]";
    protected static final String BUTTON_XP = "//*[contains(@class, 'btn')]";
    protected static final String METHOD_HEADER_BASE_XP = "//*[contains(@class, 'info-list__field')]";
    protected static final String METHOD_HEADER_XP = METHOD_HEADER_BASE_XP+"[contains(text(), '"+PLACEHOLDER+"')]";
    protected static final String METHOD_BODY_XP = WebDriverUtils.getFollowingElement(METHOD_HEADER_XP);
    protected static final String BUTTON_ADD_CARD_XP = BUTTON_XP+"[@data-url='/add-card']";
    protected static final String FIELD_AMOUNT_XP = "//*[@name='amount']";
    protected static final String FIELD_PROMO_CODE_XP = "//*[@name='promoCode']";
    protected static final String FIELD_ACCOUNT_XP = "//input[@name='accountId']";
    protected static final String DROPDOWN_ACCOUNT_XP = "//select[@name='accountId']";
    protected static final String FIELD_CVV_XP = "//*[@name='cvv2']";
    private static final String ADD = "add";
    protected static final String FIELD_ACCOUNT_ADD_XP = "//*[@name='"+ADD+"']";
    protected static final String FIELD_PASSWORD_CODE_XP = "//*[@name='accountPwd']";
    private static final String[] FIELDS = {FIELD_ACCOUNT_XP, DROPDOWN_ACCOUNT_XP, FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP, FIELD_CVV_XP, FIELD_PASSWORD_CODE_XP};

    public CashierPage(){
        super(new String[]{ROOT_XP, BUTTON_ADD_CARD_XP, METHOD_HEADER_BASE_XP});
    }

    public AddCardPage clickAddCard(){
        WebDriverUtils.click(BUTTON_ADD_CARD_XP);
        return new AddCardPage();
    }

    public void addCard(){
        AddCardPage addCardPage = clickAddCard();
    }

    protected void assertInterfaceByType(PaymentMethod method, String[] fields, UserData userData){
        String user = "";
        if(userData!=null){
            user = "for user '"+userData.getCountry()+"-"+userData.getCurrencyName()+"'";
        }
        List<String> visibleFields = Arrays.asList(fields);
        String name = method.getName();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        String header = METHOD_HEADER_XP.replace(PLACEHOLDER, name);
        if(WebDriverUtils.isVisible(header, 0)){
            WebDriverUtils.click(header);
            AbstractTest.assertTrue(WebDriverUtils.isVisible(body), "Payment method opened");
            for(String field:FIELDS){
                if(visibleFields.contains(field)){
                    AbstractTest.assertTrue(WebDriverUtils.isVisible(body+field, 0), "'"+field+"' is present on '"+method+"' form");
                }else {
                    AbstractTest.assertFalse(WebDriverUtils.isVisible(body+field, 0), "'"+field+"' is present on '"+method+"' form");
                }
            }
            AbstractTest.assertTrue(WebDriverUtils.isVisible(body+BUTTON_XP, 0), "'"+BUTTON_XP+"' is present on '"+method+"' form");
            WebDriverUtils.click(header);
            WebDriverUtils.waitForElementToDisappear(body);
        }else {
            AbstractTest.addError("'"+header+"' "+user+" was not found");
        }
    }

    protected void assertInterfaceByType(PaymentMethod method, String[] fields){
        assertInterfaceByType(method, fields, null);
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
        WebDriverUtils.click(fillFields(method, amount)+BUTTON_XP);
    }

    protected void processPaymentByType(PaymentMethod method, String amount, boolean expired){
        WebDriverUtils.click(fillFields(method, amount, expired)+BUTTON_XP);
    }

    protected void processPaymentByType(PaymentMethod method, String amount, String promocode){
        String body = fillFields(method, amount);
        String fieldPromoCode = body+FIELD_PROMO_CODE_XP;
        WebDriverUtils.inputTextToField(fieldPromoCode, promocode);
        WebDriverUtils.click(body+BUTTON_XP);
    }

    private String fillFields(PaymentMethod method, String amount){
        return fillFields(method, amount, false);
    }

    private String fillFields(PaymentMethod method, String amount, boolean expired){
        String name = method.getName();
        String account = method.getAccount();
        if(expired){
            account = method.getSecondaryAccount();
        }
        String password = method.getPassword();
        String promoCode = method.getPromoCode();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        String fieldPromoCode = body+FIELD_PROMO_CODE_XP;
        String fieldAccount = body+FIELD_ACCOUNT_XP;
        String dropdownAccount = body+DROPDOWN_ACCOUNT_XP;
        String fieldPassword = body+FIELD_PASSWORD_CODE_XP;
        String fieldCVV = body+FIELD_CVV_XP;
        if(!WebDriverUtils.isVisible(body, 0)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, name));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.inputTextToField(body+FIELD_AMOUNT_XP, amount);
        if(WebDriverUtils.isVisible(fieldAccount, 0)){
            WebDriverUtils.inputTextToField(fieldAccount, account);
        }
        if(WebDriverUtils.isVisible(dropdownAccount, 0)){
            WebDriverUtils.setDropdownOptionByValue(dropdownAccount, account);
        }
        if(WebDriverUtils.isVisible(fieldCVV, 0)){
            WebDriverUtils.inputTextToField(fieldCVV, "777");
        }
        if(WebDriverUtils.isVisible(fieldPassword, 0)){
            WebDriverUtils.inputTextToField(fieldPassword, password);
        }
        return body;
    }


    public boolean isCardVisible(PaymentMethod method, String card) {
        String name = method.getName();
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, name);
        String header = METHOD_HEADER_XP.replace(PLACEHOLDER, name);
        if(!isMethodVisible(method)){
            return false;
        }else {
            WebDriverUtils.click(header);
            AbstractTest.assertTrue(WebDriverUtils.isVisible(body), "Payment method opened");
            for(String value:WebDriverUtils.getDropdownOptionsValue(FIELD_ACCOUNT_XP)){
                if(card.endsWith(value)){
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isMethodVisible(PaymentMethod method) {
        String name = method.getName();
        String header = METHOD_HEADER_XP.replace(PLACEHOLDER, name);
        return WebDriverUtils.isVisible(header);
    }
}
