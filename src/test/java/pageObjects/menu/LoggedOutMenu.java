package pageObjects.menu;

import pageObjects.registration.threeStep.RegistrationPageStepOne;
import utils.WebDriverUtils;

public class LoggedOutMenu extends Menu {

    private static final String REGISTER_XP =   ROOT_XP + "//*[contains(@class,'micon-getting-started')]";

	public LoggedOutMenu(){
		super(new String[]{REGISTER_XP});
	}

	public RegistrationPageStepOne clickRegister(){
		WebDriverUtils.click(REGISTER_XP);
		return new RegistrationPageStepOne();
	}
}
