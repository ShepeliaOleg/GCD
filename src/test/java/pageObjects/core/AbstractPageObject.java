package pageObjects.core;

import utils.WebDriverUtils;
import utils.core.AbstractTest;

public abstract class AbstractPageObject{

	public static final String PORTLET_ERROR_XP= "//*[contains(@class,'error') or contains(@class, 'info__content')]";
    private static final String MAIN_SITE_LOADER = "//*[@class='main-site-loader']";
    protected static final String PLACEHOLDER =     "$PLACEHOLDER$";
    private static final int TIMEOUT =              30;

	public AbstractPageObject(String mainWindowHandle){
		WebDriverUtils.switchToOtherWindow(mainWindowHandle);
		validate(null, null);
	}

	public AbstractPageObject(String mainWindowHandle, String[] clickableBys){
		WebDriverUtils.switchToOtherWindow(mainWindowHandle);
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

    protected void validate(String[] clickableBys, String[] invisibleBys, String name){
        try{
            validate(clickableBys, invisibleBys);
        }catch (Exception e){
            AbstractTest.failTest(name + " did not load");
        }
    }

    public boolean isPortletErrorVisible(){
        return WebDriverUtils.isVisible(PORTLET_ERROR_XP,2);
    }

    public String getPortletErrorMessage() {
        if(isPortletErrorVisible()){
            return WebDriverUtils.getElementText(PORTLET_ERROR_XP);
        }else {
            AbstractTest.failTest("Expected error message, but it did not appear");
            return null;
        }
    }

    public String getTranslationText() {
        return null;
    }

    public void copyAndPasteEmail(){

    }
}



