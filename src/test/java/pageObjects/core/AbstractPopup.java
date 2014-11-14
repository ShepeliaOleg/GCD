package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.WebDriver;
import utils.WebDriverUtils;

public class AbstractPopup extends AbstractPageObject{

	public final static String ROOT_XP=			    "//*[contains(@class, 'popup-modal__inner') and not(contains(@class, 'hide-popup'))]";

	public AbstractPopup(WebDriver webDriver){
		this(webDriver, null);
	}

	public AbstractPopup(WebDriver webDriver, String[] clickableBys){
		this(webDriver, clickableBys, new String[]{});
	}

	public AbstractPopup(WebDriver webDriver, String[] clickableBys, String[] invisibleBys){
        this(webDriver, clickableBys, invisibleBys, ROOT_XP);
	}

    public AbstractPopup(WebDriver webDriver, String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(webDriver, ArrayUtils.addAll(clickableBys, new String[]{rootXp}), invisibleBys);
        WebDriverUtils.waitFor();
    }

}


