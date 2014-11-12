package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSLoginPage extends AbstractServerPage {

	public  static final String ROOT_XP=			"//*[@id='login']";
	private static final String FIELD_USERNAME_XP=	"//*[@id='username']";
	private static final String FIELD_PASSWORD_XP=	"//*[@id='pass']";
	private static final String BUTTON_SUBMIT_XP=	"//*[@id='Submit']";

	public IMSLoginPage(){
		super(new String[]{ROOT_XP});
	}

	private void setUsername(String xpath, String username){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), xpath, username);
	}

	private void setPassword(String xpath, String password){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), xpath, password);
	}

	private IMSHomePage clickSubmit(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_SUBMIT_XP);
		return new IMSHomePage();
	}

	public IMSHomePage logInToIMS(String login, String pass){
		setUsername(FIELD_USERNAME_XP, login);
		setPassword(FIELD_PASSWORD_XP, pass);
		return clickSubmit();
	}
}
