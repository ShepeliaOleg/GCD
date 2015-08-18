package pageObjects.registration;

import pageObjects.core.AbstractPortalPage;

public class AfterRegistrationPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[@id='layout-column__column-1']/div/div[contains(., 'After Registration')]";
//    private static final String ROOT_XP = "//*[@class='afterReg']";

    public AfterRegistrationPage(){
        super(new String[]{ROOT_XP});
    }
}
