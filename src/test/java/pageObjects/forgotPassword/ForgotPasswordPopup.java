package pageObjects.forgotPassword;

import springConstructors.UserData;
import springConstructors.ValidationRule;
import utils.ValidationUtils;
import utils.WebDriverUtils;

public class ForgotPasswordPopup extends ForgotPopup{

    private final static String FIELD_USERNAME_XP =             	ROOT_XP + "//*[@id='userName']";
    private final static String DROPDOWN_BIRTH_DAY =            	ROOT_XP + "//select[@id='birthDay']";
    private final static String DROPDOWN_BIRTH_MONTH =          	ROOT_XP + "//select[@id='birthMonth']";
    private final static String DROPDOWN_BIRTH_YEAR =         	  	ROOT_XP + "//select[@id='birthYear']";

    public ForgotPasswordPopup(){
        super(new String[]{ROOT_XP, FIELD_USERNAME_XP, FIELD_EMAIL_XP,
                DROPDOWN_BIRTH_DAY, DROPDOWN_BIRTH_MONTH, DROPDOWN_BIRTH_YEAR,
                BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP,
                LINK_REGISTRATION_XP, LINK_CONTACT_US_XP});
        forgotType = true;
    }

    public ForgotPasswordPopup(String page){
        super(new String[]{FIELD_USERNAME_XP, FIELD_EMAIL_XP,
                DROPDOWN_BIRTH_DAY, DROPDOWN_BIRTH_MONTH, DROPDOWN_BIRTH_YEAR,
                BUTTON_CANCEL_XP, BUTTON_APPROVE_XP, LINK_LOGIN_XP,
                LINK_REGISTRATION_XP, LINK_CONTACT_US_XP}, page);
        forgotType = true;
    }

    private static void fillUsername(String username){
		WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
	}

	private static void fillBirthDay(String birthDay){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_DAY, birthDay);
	}

	private static void fillBirthMonth(String birthMonth){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_MONTH, birthMonth);
	}

	private static void fillBirthYear(String birthYear){
		WebDriverUtils.setDropdownOptionByText(DROPDOWN_BIRTH_YEAR, birthYear);
	}

	public static void fillUserData(UserData userData){
		fillUsername(userData.getUsername());
		fillEmail(userData.getEmail());
		fillBirthDay(userData.getBirthDay());
		fillBirthMonth(userData.getBirthMonth());
		fillBirthYear(userData.getBirthYear());
	}

	public void validateUsername(ValidationRule rule) {
		ValidationUtils.validate(FIELD_USERNAME_XP, rule);
	}

}
