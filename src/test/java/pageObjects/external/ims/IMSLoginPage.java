package pageObjects.external.ims;

import pageObjects.core.AbstractPage;
import springConstructors.IMS;
import utils.WebDriverUtils;

public class IMSLoginPage extends AbstractPage{

	public  static final String ROOT_XP=				"//*[@id='login']";
	private static final String FIELD_USERNAME_XP=	"//*[@id='username']";
	private static final String FIELD_PASSWORD_XP=	"//*[@id='pass']";
	private static final String BUTTON_SUBMIT_XP=	"//*[@id='Submit']";

	public IMSLoginPage(){
		super(new String[]{ROOT_XP});
	}

	private void setUsername(String xpath, String username){
		WebDriverUtils.clearAndInputTextToField(xpath, username);
	}

	private void setPassword(String xpath, String password){
		WebDriverUtils.clearAndInputTextToField(xpath, password);
	}

	private IMSHomePage clickSubmit(){
		WebDriverUtils.click(BUTTON_SUBMIT_XP);
		return new IMSHomePage();
	}

	public IMSHomePage logInToIMS(){
		setUsername(FIELD_USERNAME_XP, IMS.getImsLogin());
		setPassword(FIELD_PASSWORD_XP, IMS.getImsPass());
		return clickSubmit();
	}
}
