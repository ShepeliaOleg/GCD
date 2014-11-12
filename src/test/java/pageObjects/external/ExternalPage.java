package pageObjects.external;

import pageObjects.core.AbstractPortalBrowserWindow;
import utils.WebDriverUtils;

public class ExternalPage extends AbstractPortalBrowserWindow {

    public ExternalPage(String mainWindowHandle) {
        super(mainWindowHandle);
        WebDriverUtils.waitForPageToLoad();
    }
}
