package pageObjects.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

public abstract class AbstractPageObject extends WebDriverObject {

	public static final String PORTLET_ERROR_XP= "//*[contains(@class,'error')] | //*[contains(@class, 'info__content')]";
    protected static final String PLACEHOLDER =     "$PLACEHOLDER$";
    private static final int TIMEOUT =              30;

    @Autowired
    private ApplicationContext applicationContext;

	public AbstractPageObject(String mainWindowHandle){
		WebDriverUtils.switchToPopup(mainWindowHandle);
		validate(null, null);
	}

	public AbstractPageObject(String mainWindowHandle, String[] clickableBys){
		WebDriverUtils.switchToPopup(mainWindowHandle);
		validate(clickableBys, null);
	}

	public AbstractPageObject(String[] clickableBys){
		this(clickableBys, null);
	}

	public AbstractPageObject(String[] clickableBys, String[] invisibleBys){
		validate(clickableBys, invisibleBys);
	}

	public AbstractPageObject(String[] clickableBys, String[] invisibleBys, String iframeId){
		WebDriverUtils.switchToIframeById(iframeId);
		validate(clickableBys, invisibleBys);
	}

    private void validate(String[] clickableBys, String[] invisibleBys){
        if(clickableBys!=null){
            for(String xpath:clickableBys){
                WebDriverUtils.waitForElement(xpath, TIMEOUT);
            }
        }
        if(invisibleBys!=null){
            for(String xpath:invisibleBys){
                WebDriverUtils.waitForElementToDisappear(xpath,TIMEOUT);
            }
        }
    }

    public boolean isErrorVisible(){
        return WebDriverUtils.isVisible(PORTLET_ERROR_XP);
    }
}


