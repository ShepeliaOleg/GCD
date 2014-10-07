package pageObjects.account.deposit;

import pageObjects.account.AddCardPopup;
import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

public class DepositPage extends AbstractPage{

    private static final String BUTTON_XP = "//*[contains(@class, 'btn')]";
    private static final String METHOD_HEADER_XP = "//*[contains(@class, 'info-list__field')][contains(text(), '"+PLACEHOLDER+"')]";
    private static final String METHOD_BODY_XP = WebDriverUtils.getFollowingElement(METHOD_HEADER_XP);
    private static final String BUTTON_ADD_CARD_XP = BUTTON_XP+"[@data-url='/add-card']";
    private static final String FIELD_AMOUNT_XP = "//*[@name='amount']";
    private static final String FIELD_PROMO_CODE_XP = "//*[@name='promoCode']";
    private static final String FIELD_ACCOUNT_XP = "//*[@name='accountId']";
    private static final String FIELD_PASSWORD_CODE_XP = "//*[@name='accountPwd']";

    private static final String MONEYBOOKERS = "Moneybookers";
    private static final String PAYPAL = "PayPal";
    private static final String WEBMONEY = "WebMoney";
    private static final String NETELLER = "NETeller";
    private static final String QIWI = "QIWI via Safecharge";

    private AddCardPopup clickAddCard(){
        WebDriverUtils.click(BUTTON_ADD_CARD_XP);
        return new AddCardPopup();
    }

    private void depositByType(String method, String amount, String promoCode, String account, String password){
        String body = METHOD_BODY_XP.replace(PLACEHOLDER, method);
        if(!WebDriverUtils.isVisible(body)){
            WebDriverUtils.click(METHOD_HEADER_XP.replace(PLACEHOLDER, method));
            WebDriverUtils.waitForElement(body);
        }
        WebDriverUtils.inputTextToField(body+FIELD_AMOUNT_XP, amount);
        WebDriverUtils.inputTextToField(body+FIELD_PROMO_CODE_XP, promoCode);
        if(account!=null){
            WebDriverUtils.inputTextToField(body+FIELD_ACCOUNT_XP, account);
        }
        if(password!=null){
            WebDriverUtils.inputTextToField(body+FIELD_PASSWORD_CODE_XP, password);
        }
        WebDriverUtils.click(body+BUTTON_XP);
    }

    public MoneyBookersDepositPage depositMoneybookers(String amount, String promoCode){
        depositByType(MONEYBOOKERS, amount, promoCode, null, null);
        return new MoneyBookersDepositPage();
    }

    public PayPalDepositPage depositPayPal(String amount, String promoCode){
        depositByType(PAYPAL, amount, promoCode, null, null);
        return new PayPalDepositPage();
    }

    public WebMoneyDepositPage depositWebmoney(String amount, String promoCode){
        depositByType(WEBMONEY, amount, promoCode, null, null);
        return new WebMoneyDepositPage();
    }

    public NETellerDepositPage depositNETeller(String amount, String promoCode, String account, String password){
        depositByType(NETELLER, amount, promoCode, account, password);
        return new NETellerDepositPage();
    }

    public QIWIDepositPage depositQIWI(String amount, String promoCode, String account){
        depositByType(QIWI, amount, promoCode, account, null);
        return new QIWIDepositPage();
    }



}
