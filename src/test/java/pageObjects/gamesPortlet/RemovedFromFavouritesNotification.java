package pageObjects.gamesPortlet;

import pageObjects.core.AbstractNotification;

public class RemovedFromFavouritesNotification extends AbstractNotification{

    private static final String ROOT_XP = AbstractNotification.ROOT_XP + "[contains(text(), 'Successfully removed from favorites')]";

    public RemovedFromFavouritesNotification() {
        super(new String[]{ROOT_XP});
    }
}
