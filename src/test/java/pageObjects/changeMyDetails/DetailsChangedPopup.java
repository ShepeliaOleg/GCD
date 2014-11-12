package pageObjects.changeMyDetails;

import pageObjects.core.AbstractPortalPopup;

public class DetailsChangedPopup extends AbstractPortalPopup{
    private static final String ROOT_XP = TITLE_XP + "[contains(text(), 'Details are successfully updated')]";

    public DetailsChangedPopup(){
        super(new String[]{ROOT_XP});
    }
}
