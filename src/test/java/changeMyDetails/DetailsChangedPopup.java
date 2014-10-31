package changeMyDetails;

import pageObjects.core.AbstractPopup;

public class DetailsChangedPopup extends AbstractPopup{
    private static final String ROOT_XP = TITLE_XP + "[contains(text(), 'Details are successfully updated')]";

    public DetailsChangedPopup(){
        super(new String[]{ROOT_XP});
    }
}
