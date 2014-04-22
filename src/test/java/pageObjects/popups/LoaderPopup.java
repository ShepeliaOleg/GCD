package pageObjects.popups;

import org.openqa.selenium.WebDriver;
import pageObjects.base.AbstractPopup;

/**
 * User: sergiich
 * Date: 9/6/13
 */

public class LoaderPopup extends AbstractPopup{

	public final static String ANIMATION_XP=ROOT_XP + "//*[@class='loading-animation']";

	public LoaderPopup(WebDriver webDriver){
		super(new String[]{ANIMATION_XP});
	}
}
