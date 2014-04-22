package pageObjects.base;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

import java.util.ArrayList;

/**
 * User: sergiich
 * Date: 7/5/13
 */

public abstract class AbstractPageObject extends WebDriverObject{

	public static final String VALIDATION_ERROR_XP="//span[contains(@class,'error')]";

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

//	private void validate(By[] clickableBys, By[] invisibleBys){
//		ArrayList conditions=new ArrayList<ExpectedCondition>();
//		ArrayList ignoredList=new ArrayList<Class<? extends Throwable>>();
//		WebDriverWait wait=new WebDriverWait(webDriver, TIMEOUT);
//
//		conditions = addBys(conditions, clickableBys, true);
//		conditions = addBys(conditions, invisibleBys, false);
//
//		ignoredList.add(IndexOutOfBoundsException.class);
//		ignoredList.add(NotFoundException.class);
//		ignoredList.add(NoSuchElementException.class);
//		wait.ignoreAll(ignoredList).until(CustomExpectedConditions.checkExpectedConditions(conditions));
//	}

    private void validate(String[] clickableBys, String[] invisibleBys){
        if(clickableBys!=null){
            for(String xpath:clickableBys){
                WebDriverUtils.waitForElement(xpath, 30);
            }
        }
        if(invisibleBys!=null){
            for(String xpath:invisibleBys){
                WebDriverUtils.waitForElementToDisappear(xpath,30);
            }
        }
    }

	private ArrayList<ExpectedCondition> addBys(ArrayList<ExpectedCondition> conditions, By[] bys, boolean clickables){
		if(bys != null && bys.length > 0){
			for(By by : bys){
				if(clickables==true){
					conditions.add(ExpectedConditions.elementToBeClickable(by));
				}else{
					conditions.add(ExpectedConditions.invisibilityOfElementLocated(by));
				}
			}
		}
		return conditions;
	}



}


