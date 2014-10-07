import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import springConstructors.UserData;
import utils.NavigationUtils;
import utils.core.AbstractTest;

public class PermissionsTest extends AbstractTest{
    private static final String ADMIN_TEXT =        "PERMISSIONS_ADMIN_ONLY";
    private static final String GUEST_TEXT =        "PERMISSIONS_GUEST_ONLY";
    private static final String PLAYER_TEXT =       "PERMISSIONS_PLAYER_ONLY";
    private static final String ADMIN_GUEST_TEXT =  "PERMISSIONS_ADMIN_AND_GUEST";
    private static final String ADMIN_PLAYER_TEXT = "PERMISSIONS_ADMIN_AND_PLAYER";
    private static final String GUEST_PLAYER_TEXT = "PERMISSIONS_PLAYER_AND_GUEST";
    private static final String ALL_TEXT =          "PERMISSIONS_ALL";

    @Autowired
    @Qualifier("userData")
    private UserData defaultUserData;

	/* 1. Portlet permissions for guest and player users */
	@Test(groups = {"regression"})
	public void permissionsPortlet() {
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.permissions_portlet);
        assertTextVisible(ALL_TEXT, "Portlet visible for all is displayed for guest.");
        assertTextInvisible(ADMIN_TEXT, "Portlet visible for admin is not displayed for guest.");
        assertTextVisible(GUEST_TEXT, "Portlet visible for guest is displayed for guest.");
        assertTextInvisible(PLAYER_TEXT, "Portlet visible for player is not displayed for guest.");
        assertTextVisible(ADMIN_GUEST_TEXT, "Portlet visible for admin and guest is displayed for guest.");
        assertTextInvisible(ADMIN_PLAYER_TEXT, "Portlet visible for admin and player is not displayed for guest.");
        assertTextVisible(GUEST_PLAYER_TEXT, "Portlet visible for guest and player is displayed for guest.");
        NavigationUtils.navigateToPage(PlayerCondition.player, ConfiguredPages.permissions_portlet, defaultUserData.getRegisteredUserData());
        assertTextVisible(ALL_TEXT, "Portlet visible for all is displayed for player.");
        assertTextInvisible(ADMIN_TEXT, "Portlet visible for admin is not displayed for player.");
        assertTextInvisible(GUEST_TEXT, "Portlet visible for guest is not displayed for player.");
        assertTextVisible(PLAYER_TEXT, "Portlet visible for player is displayed for player.");
        assertTextInvisible(ADMIN_GUEST_TEXT, "Portlet visible for admin and guest is not displayed for player.");
        assertTextVisible(ADMIN_PLAYER_TEXT, "Portlet visible for admin and player is displayed for player.");
        assertTextVisible(GUEST_PLAYER_TEXT, "Portlet visible for guest and player is displayed for player.");
	}

    /* 1. Portlet permissions for admin user */
    @Test(groups = {"admin"})
    public void permissionsPortletAdmin() {
        NavigationUtils.navigateToPage(PlayerCondition.admin,  ConfiguredPages.permissions_portlet);
        assertTextVisible(ALL_TEXT, "Portlet visible for all is displayed for admin.");
        assertTextVisible(ADMIN_TEXT, "Portlet visible for admin is displayed for admin.");
        assertTextVisible(GUEST_TEXT, "Portlet visible for guest is displayed for admin.");
        assertTextVisible(PLAYER_TEXT, "Portlet visible for player is displayed for admin.");
        assertTextVisible(ADMIN_GUEST_TEXT, "Portlet visible for admin and guest is displayed for admin.");
        assertTextVisible(ADMIN_PLAYER_TEXT, "Portlet visible for admin and player is displayed for admin.");
        assertTextVisible(GUEST_PLAYER_TEXT, "Portlet visible for guest and player is displayed for admin.");
    }
}
