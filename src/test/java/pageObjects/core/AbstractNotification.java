package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;

public class AbstractNotification extends AbstractPageObject{

    protected static final String ROOT_XP = "//*[@class='info__content']";

    public AbstractNotification(String[] clickableBys){
        this(clickableBys, new String[]{});
    }

    public AbstractNotification(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
    }

    public AbstractNotification(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
        WebDriverUtils.waitFor(1000);
    }
}
