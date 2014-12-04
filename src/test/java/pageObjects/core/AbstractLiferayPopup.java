package pageObjects.core;

import org.apache.commons.lang3.ArrayUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class AbstractLiferayPopup extends AbstractPopup{

    public    final static String ROOT_XP =         "//*[contains(@id,'popup')]";
    protected final static String HEADER_XP =       ROOT_XP + "//*[contains(@class,'header')]";
    protected final static String TITLE_XP =        HEADER_XP + "//*[@class='title']";
    protected final static String BUTTON_CLOSE_XP =	HEADER_XP + "//*[contains(@class, 'popupClose')]";


	public AbstractLiferayPopup(){
		this(null);
	}

	public AbstractLiferayPopup(String[] clickableBys){
		this(clickableBys, null);
	}

	public AbstractLiferayPopup(String[] clickableBys, String[] invisibleBys){
        this(clickableBys, invisibleBys, ROOT_XP);
	}

    public AbstractLiferayPopup(String[] clickableBys, String[] invisibleBys, String rootXp) {
        super(WebDriverFactory.getPortalDriver(), clickableBys, invisibleBys, rootXp);
        WebDriverUtils.waitFor();
    }

    private void clickClose(){
        WebDriverUtils.click(BUTTON_CLOSE_XP);
        WebDriverUtils.waitFor();
	}

    public void closePopup(){
        clickClose();
    }

    public String getPopupTitle(){
        return WebDriverUtils.getElementText(TITLE_XP);
    }

}


