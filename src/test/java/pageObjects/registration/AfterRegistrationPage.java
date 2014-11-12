package pageObjects.registration;

import pageObjects.core.AbstractPortalPage;

public class AfterRegistrationPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[@class='afterReg']";

    public AfterRegistrationPage(){
        super(new String[]{ROOT_XP});
    }
}
