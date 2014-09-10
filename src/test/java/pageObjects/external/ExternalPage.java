package pageObjects.external;

import pageObjects.base.AbstractBrowserWindow;
import utils.WebDriverUtils;

public class ExternalPage extends AbstractBrowserWindow{

    public ExternalPage(String mainWindowHandle) {
        super(mainWindowHandle);
        WebDriverUtils.waitForPageToLoad();
    }
}
