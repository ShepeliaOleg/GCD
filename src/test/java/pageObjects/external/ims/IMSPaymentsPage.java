package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

/**
 * Created by serhiist on 3/16/2015.
 */
public class IMSPaymentsPage extends AbstractServerPage{
    private static final String LINK_CREATE_PREPAID_CARDS_XP = "//*[contains(text(), 'Create prepaid cards')]";

    public IMSCreatePrepaidCardsPage clickCreatePrepaidCards(){
        WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), LINK_CREATE_PREPAID_CARDS_XP);
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), LINK_CREATE_PREPAID_CARDS_XP);
        return new IMSCreatePrepaidCardsPage();
    }
}
