package pageObjects.pageInPopup;

import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

public class PageInPopupPage extends AbstractPortalPage {

    private static final String LABEL_XP =  "//p[contains(text(), 'Page')]";
    private static final String BUTTON_XP = "//a[contains(@class, 'btn')]";

    public PageInPopupPage() {
        super(new String[]{LABEL_XP, BUTTON_XP});
    }

    public AbstractPortalPopup clickButton() {
        WebDriverUtils.click(BUTTON_XP);
        return new PageInPopupPopup();
    }
}

