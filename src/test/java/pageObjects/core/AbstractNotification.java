package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class AbstractNotification extends AbstractPageObject{

    private static final String ROOT_XP =       "//*[@class='info__content']";
    private static       String MESSAGE_XP =    ROOT_XP + "[contains(text(), '" + PLACEHOLDER + "')]";

    public AbstractNotification(String message) {
        this(new String[]{getNotificationXpath(message)});
    }

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

    private static String getNotificationXpath(String message) {
        return MESSAGE_XP.replace(PLACEHOLDER, message);
    }
}
