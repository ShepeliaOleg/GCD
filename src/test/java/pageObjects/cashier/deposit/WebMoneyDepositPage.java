package pageObjects.cashier.deposit;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;

public class WebMoneyDepositPage extends AbstractPortalPage{

    private static final String AMOUNT = "//*[contains(text(), 'Amount')]";

    public WebMoneyDepositPage(){
        super(new String[]{AMOUNT});
    }

    public String getAmount(){
        return WebDriverUtils.getElementText(WebDriverUtils.getFollowingElement(AMOUNT)).split(" ")[0];
    }
}
