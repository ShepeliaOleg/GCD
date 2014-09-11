package pageObjects.external.mailq;

import pageObjects.core.AbstractPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 8/2/13
 */

public class MailQLoginPage extends AbstractPage{
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
		WebDriverUtils.clearAndInputTextToField(xpath, username);
	}

	private void setPassword(String xpath, String password){
		WebDriverUtils.clearAndInputTextToField(xpath, password);
	}

	private MailQHomePage clickSubmit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
		return new MailQHomePage();
	}

	public MailQHomePage logInToMailQ(){
		setUsername(FIELD_USERNAME_XP, LOGIN);
		setPassword(FIELD_PASSWORD_XP, PASS);
		return clickSubmit();
	}

}
