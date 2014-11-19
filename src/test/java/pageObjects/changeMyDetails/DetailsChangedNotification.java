package pageObjects.changeMyDetails;

import pageObjects.core.AbstractNotification;

public class DetailsChangedNotification extends AbstractNotification{
    private static final String ROOT_XP = AbstractNotification.ROOT_XP + "[contains(text(), 'Details are successfully updated')]";

    public DetailsChangedNotification(){
        super(new String[]{ROOT_XP});
    }
}
