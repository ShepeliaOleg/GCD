package pageObjects.forgotPassword;

import enums.Licensee;
import pageObjects.core.AbstractNotification;
import pageObjects.core.AbstractPopup;
import pageObjects.core.AbstractPortalPage;
import pageObjects.core.AbstractPortalPopup;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.DataContainer;

import java.util.List;

public class ForgotPasswordPopup extends AbstractPortalPopup{

    public final static String ROOT_XP =            				"//*[contains(@class, 'fn-forgotpassword-form')]";
    private final static String FIELD_USERNAME_XP =             	ROOT_XP + "//*[@name='username']";
    public final static String FIELD_EMAIL_XP =                	    ROOT_XP + "//*[@name='email']";
    private final static String DROPDOWN_BIRTH_DAY =            	ROOT_XP + "//*[@id='birthDay']";
    private final static String DROPDOWN_BIRTH_MONTH =          	ROOT_XP + "//*[@id='birthMonth']";
    private final static String DROPDOWN_BIRTH_YEAR =         	  	ROOT_XP + "//*[@id='birthYear']";
    public final static String BUTTON_APPROVE_XP =             	    ROOT_XP + "//*[@class='btn btn_green btn_s']";
    private final static String[] ELEMENTS =                        new String[]{FIELD_USERNAME_XP, FIELD_EMAIL_XP, BUTTON_APPROVE_XP};

    public ForgotPasswordPopup(){
        super(ELEMENTS);
    }

    public ForgotPasswordPopup(String page){
        super(ELEMENTS, null, ROOT_XP);
    }

    private static void fillUsername(String username){
        WebDriverUtils.clearAndInputTextToField(FIELD_USERNAME_XP, username);
    }

    public static void fillEmail(String email){
        WebDriverUtils.clearAndInputTextToField(FIELD_EMAIL_XP, email);
    }

	private static void fillBirthDay(String birthDay){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTH_DAY, birthDay);
	}

	private static void fillBirthMonth(String birthMonth){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTH_MONTH, birthMonth);
	}

	private static void fillBirthYear(String birthYear){
		WebDriverUtils.setDropdownOptionByValue(DROPDOWN_BIRTH_YEAR, birthYear);
	}

    public void clickSubmit(){
        WebDriverUtils.click(BUTTON_APPROVE_XP);
    }

    public static void fillUserData(UserData userData){
		fillUsername(userData.getUsername());
		fillEmail(userData.getEmail());
		fillBirthDay(userData.getBirthDay());
		fillBirthMonth(userData.getBirthMonth());
		fillBirthYear(userData.getBirthYear());
	}

    public void submitValidData(){
        clickSubmit();
        if(DataContainer.getDriverData().getLicensee().equals(Licensee.core)){
            new ForgotPasswordConfirmationNotification();
        } else {
            ForgotPasswordConfirmationPopup forgotPasswordConfirmationPopup = new ForgotPasswordConfirmationPopup();
            forgotPasswordConfirmationPopup.closePopup();
        }
    }

    public void recoverPasswordValid(UserData userData){
        fillUserData(userData);
        submitValidData();
    }

    public ForgotPasswordPopup recoverPasswordInvalid(UserData userData){
        fillUserData(userData);
        clickSubmit();
        return new ForgotPasswordPopup();
    }

    public AbstractPortalPage fillDataAndClosePopup(UserData userData){
        fillUserData(userData);
        closePopup();
        return new AbstractPortalPage();
    }

    public List<String> getBirthYearList() {
        return WebDriverUtils.getDropdownOptionsText(DROPDOWN_BIRTH_YEAR);
    }
}
