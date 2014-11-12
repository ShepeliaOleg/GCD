package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class AbstractNotification extends AbstractPageObject{

    protected static final String ROOT_XP = "//*[@class='info__content']";

    public AbstractNotification(String[] clickableBys){
        this(clickableBys, new String[]{});
    }

    public AbstractNotification(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
    }

    public AbstractNotification(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(WebDriverFactory.getPortalDriver(), ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
        if(clickableBys[0]!=null){
            WebDriverUtils.waitForElementToDisappear(clickableBys[0], 30);
        }
    }
}
