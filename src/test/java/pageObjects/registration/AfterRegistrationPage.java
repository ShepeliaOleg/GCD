package pageObjects.registration;

import pageObjects.core.AbstractPage;

public class AfterRegistrationPage extends AbstractPage {

    private static final String ROOT_XP = "//*[@class='afterReg']";

    public AfterRegistrationPage(){
        super(new String[]{ROOT_XP});
    }
}
