package pageObjects.responsibleGaming;

import pageObjects.core.AbstractNotification;

public class SuccessfullyChangedNotification extends AbstractNotification{

    private static final String ROOT_XP = AbstractNotification.ROOT_XP+"[contains(text(), 'Successfully changed')]";

    public SuccessfullyChangedNotification() {
        super(new String[]{ROOT_XP});
    }
}
