package pageObjects.cashier.deposit;

import pageObjects.core.AbstractPortalPage;
import utils.TypeUtils;
import utils.WebDriverUtils;

public class WebMoneyDepositPage extends AbstractPortalPage{

    private static final String AMOUNT_XP = "//*[contains(text(), 'Amount')]";

    public WebMoneyDepositPage(){
        super(new String[]{AMOUNT_XP});
    }

    public String getAmount(){
        return TypeUtils.getBalanceAmount(WebDriverUtils.getElementText(WebDriverUtils.getFollowingElement(AMOUNT_XP)));
    }
}
