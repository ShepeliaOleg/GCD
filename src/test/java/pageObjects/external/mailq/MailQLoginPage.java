package pageObjects.external.mailq;

import pageObjects.core.AbstractServerPage;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class MailQLoginPage extends AbstractServerPage{
	private static final String ROOT_XP=			"//*[@id='login_form']";
	public static final String BUTTON_SUBMIT_XP=	"//*[@id='login']";
	public static final String FIELD_USERNAME_XP=	"//*[@id='username']";
	public static final String FIELD_PASSWORD_XP=	"//*[@id='password']";

	private static final String LOGIN=				"Sergii_C";
	private static final String PASS=				"36987Res";

	public MailQLoginPage(){
		super(new String[]{ROOT_XP, BUTTON_SUBMIT_XP});
	}

	private void setUsername(String xpath, String username){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), xpath, username);
	}

	private void setPassword(String xpath, String password){
		WebDriverUtils.clearAndInputTextToField(WebDriverFactory.getServerDriver(), xpath, password);
	}

	private MailQHomePage clickSubmit(){
		WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_SUBMIT_XP);
		return new MailQHomePage();
	}

	public MailQHomePage logInToMailQ(){
		setUsername(FIELD_USERNAME_XP, LOGIN);
		setPassword(FIELD_PASSWORD_XP, PASS);
		return clickSubmit();
	}

}
