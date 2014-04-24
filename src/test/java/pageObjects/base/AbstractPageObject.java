package pageObjects.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

/**
 * User: sergiich
 * Date: 7/5/13
 */
@ContextConfiguration(locations={"/spring-config.xml"})
public abstract class AbstractPageObject extends WebDriverObject {

	public static final String VALIDATION_ERROR_XP="//span[contains(@class,'error')]";
    private static final int TIMEOUT = 30;

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
		WebDriverUtils.switchToIframe(iframeId);
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

}


