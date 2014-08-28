package pageObjects.forgotPassword;

import springConstructors.UserData;
import utils.WebDriverUtils;

public class ForgotPasswordPopup extends ForgotPopup{

    private final static String FIELD_USERNAME_XP =             	ROOT_XP + "//*[@name='username']";
    private final static String DROPDOWN_BIRTH_DAY =            	ROOT_XP + "//select[@id='birthDay']";
    private final static String DROPDOWN_BIRTH_MONTH =          	ROOT_XP + "//select[@id='birthMonth']";
    private final static String DROPDOWN_BIRTH_YEAR =         	  	ROOT_XP + "//select[@id='birthYear']";
    private final static String[] ELEMENTS =                        new String[]{FIELD_USERNAME_XP, FIELD_EMAIL_XP,
                                                                    DROPDOWN_BIRTH_DAY, DROPDOWN_BIRTH_MONTH,
                                                                    DROPDOWN_BIRTH_YEAR, BUTTON_APPROVE_XP};

    public ForgotPasswordPopup(){
        super(ELEMENTS);
        forgotType = true;
    }

    public ForgotPasswordPopup(String page){
        super(ELEMENTS, page);
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

//	public void validateUsername(ValidationRule rule) {
//		ValidationUtils.validate(FIELD_USERNAME_XP, rule);
//	}

}
