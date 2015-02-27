package pageObjects.cashier;

import enums.PaymentMethod;
import enums.PromoCode;
import pageObjects.account.AddCardPage;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.Arrays;
import java.util.List;

public class CashierPage extends AbstractPortalPage {
    protected static final String ROOT_XP =                 "//*[contains(@class, 'portlet-mobile-cashier')]";
    protected static final String BUTTON_XP =               "//*[contains(@class, 'btn')]";
    protected static final String METHOD_HEADER_BASE_XP =   "//*[contains(@class, 'info-list__field')]";
    protected static final String METHOD_HEADER_XP =        METHOD_HEADER_BASE_XP+"[contains(text(), '"+PLACEHOLDER+"')]";
    protected static final String METHOD_BODY_XP =          WebDriverUtils.getFollowingElement(METHOD_HEADER_XP);
    protected static final String BUTTON_ADD_CARD_XP =      BUTTON_XP+"[@data-url='/add-card']";
    protected static final String FIELD_AMOUNT_XP =         "//*[@name='amount']";
    protected static final String FIELD_PROMO_CODE_XP =     "//*[@name='promoCode']";
    protected static final String FIELD_ACCOUNT_KNOWN_XP =  "//select[@class='fn-change-account']";
    protected static final String FIELD_ACCOUNT_XP =        "//*[@class='fn-account-id']";
    protected static final String FIELD_CVV_XP =            "//*[@name='cvv2']";
    private   static final String ADD =                     "add";
    protected static final String FIELD_PASSWORD_XP =       "//*[@name='accountPwd']";
    protected static final String CASHIER_LOADER =          "//*[contains(@class, 'fn-loader')]";

    private static final String[] FIELDS = {FIELD_ACCOUNT_XP, FIELD_AMOUNT_XP, FIELD_PROMO_CODE_XP, FIELD_CVV_XP, FIELD_PASSWORD_XP};

    public CashierPage(){
        super(new String[]{ROOT_XP});
    }

    public CashierPage(String[] clickableBys) {
        super(clickableBys);
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
        String body = getMethodBodyXpath(name);
        String header = getMethodHeaderXpath(name);
        if(WebDriverUtils.isVisible(header, 0)){
            WebDriverUtils.click(header);
            WebDriverUtils.waitFor();
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
        addAccountByType(method, method.getSecondaryAccount());
    }

    public void addAccountByType(PaymentMethod method, String account) {
        String name = method.getName();
        String body = getMethodBodyXpath(name);
        if(!WebDriverUtils.isVisible(body, 1)){
            WebDriverUtils.click(getMethodHeaderXpath(name));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.setDropdownOptionByValue(body + FIELD_ACCOUNT_KNOWN_XP, ADD);
        WebDriverUtils.waitForElement(body+ FIELD_ACCOUNT_XP);
        WebDriverUtils.clearAndInputTextToField(body+ FIELD_ACCOUNT_XP, account);
    }

    public void closeAddAccountField(PaymentMethod method) {
        String body = getMethodBodyXpath(method.getName());
        WebDriverUtils.setDropdownOptionByValue(body+ FIELD_ACCOUNT_KNOWN_XP, method.getAccount());
        WebDriverUtils.waitForElementToDisappear(body+ FIELD_ACCOUNT_XP);
    }

    protected void processPaymentByType(PaymentMethod method, String amount){
        WebDriverUtils.click(fillFields(method, amount)+BUTTON_XP);
        WebDriverUtils.waitForElementToDisappear(INPUT_LOADER_XP, 20); // D-18917 10 seconds by default are not enough
    }

    protected void processPaymentByType(PaymentMethod method, String amount, boolean expired){
        WebDriverUtils.click(fillFields(method, amount, expired)+BUTTON_XP);
        WebDriverUtils.waitForElementToDisappear(INPUT_LOADER_XP, 20); // D-18917 10 seconds by default are not enough
    }

    protected void processPaymentByType(PaymentMethod method, String amount, String account){
        WebDriverUtils.click(fillFields(method, amount, account)+BUTTON_XP);
        WebDriverUtils.waitForElementToDisappear(INPUT_LOADER_XP, 20); // D-18917 10 seconds by default are not enough
    }
    protected void processPaymentByType(PaymentMethod method, String amount, String account, String password){
        WebDriverUtils.click(fillFields(method, amount, account, password)+BUTTON_XP);
        WebDriverUtils.waitForElementToDisappear(INPUT_LOADER_XP, 20); // D-18917 10 seconds by default are not enough
    }

    protected void processPaymentByType(PaymentMethod method, String amount, PromoCode promoCode){
        String body = fillFields(method, amount);
        String fieldPromoCode = body+FIELD_PROMO_CODE_XP;
        WebDriverUtils.inputTextToField(fieldPromoCode, promoCode.getCode());
        WebDriverUtils.click(body+BUTTON_XP);
        WebDriverUtils.waitForElementToDisappear(INPUT_LOADER_XP, 20); // D-18917 10 seconds by default are not enough
    }

    private String fillFields(PaymentMethod method, String amount, boolean expired){
        String account = method.getAccount();
        if(expired){
            account = method.getSecondaryAccount();
        }
        return fillFields(method, amount, account, method.getPassword());
    }

    private String fillFields(PaymentMethod method, String amount){
        return fillFields(method, amount, method.getAccount(), method.getPassword());
    }

    private String fillFields(PaymentMethod method, String amount, String account){
        return fillFields(method, amount, account, method.getPassword());
    }

    private String fillFields(PaymentMethod method, String amount, String account, String password){
        String name =               method.getName();
        String body =               getMethodBodyXpath(name);
        String fieldAccount =       body + FIELD_ACCOUNT_XP;
        String fieldAccountKnown =  body + FIELD_ACCOUNT_KNOWN_XP;
        String fieldPassword =      body + FIELD_PASSWORD_XP;
        String fieldCVV =           body + FIELD_CVV_XP;
        openMethodBodyIfClosed(method);
        WebDriverUtils.inputTextToField(body+FIELD_AMOUNT_XP, amount);
        if(WebDriverUtils.isVisible(fieldAccount, 0)){
            WebDriverUtils.clearAndInputTextToField(fieldAccount, account);
        }
        if(WebDriverUtils.isVisible(WebDriverUtils.getPrecedingElement(fieldAccountKnown), 0)){
            String selectedAccount = WebDriverUtils.getDropdownSelectedOptionValue(fieldAccountKnown);
            List<String> optionsValue = WebDriverUtils.getDropdownOptionsValue(fieldAccountKnown);
            if (method.equals(PaymentMethod.MasterCard) || method.equals(PaymentMethod.Visa)) {
                account = "*" + account;
            }
            if (!selectedAccount.equals(account)) {
                if (optionsValue.contains(account)) {
                    WebDriverUtils.setDropdownOptionByValue(fieldAccountKnown, account);
                } else {
                    addAccountByType(method, account);
                }
            }
        }
        if(WebDriverUtils.isVisible(fieldCVV, 0)){
            WebDriverUtils.clearAndInputTextToField(fieldCVV, "777");
        }
        if(WebDriverUtils.isVisible(fieldPassword, 0)){
            WebDriverUtils.clearAndInputTextToField(fieldPassword, password);
        }
        return body;
    }

    private String getMethodBodyXpath(String methodName) {
        return METHOD_BODY_XP.replace(PLACEHOLDER, methodName);
    }

    private String getMethodHeaderXpath(String methodName) {
        return METHOD_HEADER_XP.replace(PLACEHOLDER, methodName);
    }

    private void openMethodBodyIfClosed(PaymentMethod method) {
        String methodName = method.getName();
        String methodBodyXpath = getMethodBodyXpath(methodName);
        if(!WebDriverUtils.isVisible(methodBodyXpath, 0)){
            WebDriverUtils.click(getMethodHeaderXpath(methodName));
            WebDriverUtils.waitForElement(methodBodyXpath);
        }
        AbstractTest.assertTrue(WebDriverUtils.isVisible(methodBodyXpath), "Payment method opened");
    }

    public boolean isCardVisible(PaymentMethod method, String card) {
        if(!isMethodVisible(method)){
            return false;
        }else {
            openMethodBodyIfClosed(method);
            List<String> dropdownOptions = WebDriverUtils.getDropdownOptionsText(FIELD_ACCOUNT_KNOWN_XP);
            for(String value: dropdownOptions){
                value = value.replace("*", "").trim();
                if(card.endsWith(value)){
                    return true;
                }
            }
            return false;
        }
    }

    public boolean isMethodVisible(PaymentMethod method) {
        String header = getMethodHeaderXpath(method.getName());
        return WebDriverUtils.isVisible(header);
    }

    public void refresh() {
        WebDriverUtils.refreshPage();
        waitForCashierLoad();
    }

    private void waitForCashierLoad() {
        WebDriverUtils.waitForElement(CASHIER_LOADER);
        WebDriverUtils.waitForElementToDisappear(CASHIER_LOADER);
    }

    public String getBalanceAmount() {
        refresh();
        return super.getBalanceAmount();
    }
}
