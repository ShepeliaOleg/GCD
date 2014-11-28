package pageObjects.gamesPortlet;

import pageObjects.core.AbstractNotification;

public class AddedToFavouritesNotification extends AbstractNotification{

    private static final String ROOT_XP = AbstractNotification.ROOT_XP + "[contains(text(), 'Successfully added to favorites')]";

    public AddedToFavouritesNotification() {
        super(new String[]{ROOT_XP});
    }
}
