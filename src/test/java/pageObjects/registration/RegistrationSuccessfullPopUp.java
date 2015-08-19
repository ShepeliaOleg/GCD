package pageObjects.registration;

import pageObjects.core.AbstractPortalPopup;
import utils.WebDriverUtils;

/**
 * Created by olegs on 8/19/2015.
 */
public class RegistrationSuccessfullPopUp extends AbstractPortalPopup {

    public final static String DROPDPWN_DAILY_DEPOSIT_LIMIT_XP = "//*[contains(@name,'daydepositlimit')]";
    public final static String DROPDPWN_WEEKLY_DEPOSIT_LIMIT_XP = "//*[contains(@name,'weekdepositlimit')]";
    public final static String DROPDPWN_MONTHLY_DEPOSIT_LIMIT_XP = "//*[contains(@name,'monthdepositlimit')]";

    public final static String BUTTON_CONFIRM_XP = "//*[contains(@class,'btn btn_s')]";
    public final static String BUTTON_CONTINUE_TO_NEXT_STEP_XP = "//*[contains(@class,'btn btn_darkred btn_s')]";


    public void clickContinue (){
        WebDriverUtils.click(BUTTON_CONTINUE_TO_NEXT_STEP_XP);
    }
}
