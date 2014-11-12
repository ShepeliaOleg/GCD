package pageObjects.core;

import utils.core.WebDriverFactory;

public class AbstractServerPage extends AbstractPage {

    public AbstractServerPage(String[] clickableBys, String[] invisibleBys) {
        super(WebDriverFactory.getServerDriver(), clickableBys, invisibleBys);
    }

    public AbstractServerPage(String[] clickableBys) {
        this(clickableBys, null);
    }

    public AbstractServerPage() {
        this(null, null);
    }
}




