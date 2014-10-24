package pageObjects.pageInPopup;

import pageObjects.core.AbstractPage;
import pageObjects.core.AbstractPopup;
import utils.WebDriverUtils;

public class PageInPopupPage extends AbstractPage {

    private static final String LABEL_XP =  "//p[contains(text(), 'Page')]";
    private static final String BUTTON_XP = "//a[contains(@class, 'btn')]";

    public PageInPopupPage() {
        super(new String[]{LABEL_XP, BUTTON_XP});
    }

    public AbstractPopup clickButton() {
        WebDriverUtils.click(BUTTON_XP);
        return new PageInPopupPopup();
    }
}

