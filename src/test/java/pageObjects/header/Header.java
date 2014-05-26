package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class Header extends AbstractPageObject{
	private static final String ROOT_XP=	"//*[@id='header']";
	public static final String LOGGED_IN_XP="//*[@id='welcome']";

	public Header(){
		super(new String[]{ROOT_XP});
	}

	public Header(String[] clickableBys){
		super(ArrayUtils.addAll(clickableBys, new String[]{ROOT_XP}));
	}

	public boolean isLoggedIn(){
		return WebDriverUtils.isVisible(LOGGED_IN_XP, 1);
	}

    public AbstractPage waitForLogout(){
        WebDriverUtils.waitForElementToDisappear(LOGGED_IN_XP, 30);
        return new AbstractPage();
    }



}
