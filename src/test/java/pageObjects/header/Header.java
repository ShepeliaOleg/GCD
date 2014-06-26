package pageObjects.header;

import org.apache.commons.lang3.ArrayUtils;
import pageObjects.base.AbstractPage;
import pageObjects.base.AbstractPageObject;
import utils.WebDriverUtils;

public class Header extends AbstractPageObject{
	private static final String ROOT_XP=	"//*[@class='main-header']";
	public static final String LOGGED_IN_XP="//*[contains(@class, 'main-header__balance')]";

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
