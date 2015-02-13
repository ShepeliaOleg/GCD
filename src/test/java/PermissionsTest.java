import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.login.LoginPopup;
import utils.NavigationUtils;
import utils.WebDriverUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

import java.util.Arrays;
import java.util.List;

public class PermissionsTest extends AbstractTest{

    private static final String ADMIN_TEXT =        "PERMISSIONS_ADMIN_ONLY";
    private static final String GUEST_TEXT =        "PERMISSIONS_GUEST_ONLY";
    private static final String PLAYER_TEXT =       "PERMISSIONS_PLAYER_ONLY";
    private static final String PLAYER_GUEST_TEXT = "PERMISSIONS_PLAYER_AND_GUEST";
    private static final String ALL_TEXT =          "PERMISSIONS_ALL";

    private static final List<String> portletText = Arrays.asList(ADMIN_TEXT, GUEST_TEXT, PLAYER_TEXT, PLAYER_GUEST_TEXT, ALL_TEXT);

//        skipTestWithIssues("D-16282, D-17742, D-14716");

    /* 1. Portlet permissions for guest user */
    @Test(groups = {"regression"})
    public void permissionsPortletGuest() {
        assertPortletPermissions(PlayerCondition.guest, ConfiguredPages.permissions_portlet, ALL_TEXT, GUEST_TEXT, PLAYER_GUEST_TEXT);
	}

    /* 2. Portlet permissions for player user */
    @Test(groups = {"regression"})
    public void permissionsPortletPlayer() {
        assertPortletPermissions(PlayerCondition.player, ConfiguredPages.permissions_portlet, ALL_TEXT, PLAYER_TEXT, PLAYER_GUEST_TEXT);
    }

    /* 3. Portlet permissions for admin user */
    @Test(groups = {"admin"})
    public void permissionsPortletAdmin() {
        assertPortletPermissions(PlayerCondition.admin, ConfiguredPages.permissions_portlet, ALL_TEXT, ADMIN_TEXT, GUEST_TEXT, PLAYER_TEXT, PLAYER_GUEST_TEXT);
    }

    /* 4. Page permissions for guest user on page visible for all */
    @Test(groups = {"regression"})
    public void permissionsPageGuestOnAllPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_all, ALL_TEXT, true);
    }

    /* 5. Page permissions for guest user on page visible for guest */
    @Test(groups = {"regression"})
    public void permissionsPageGuestOnGuestPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_guest,        GUEST_TEXT,         true);
    }

    /* 6. Page permissions for guest user on page visible for player */
    @Test(groups = {"regression"})
    public void permissionsPageGuestOnPlayerPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_player,       PLAYER_TEXT,        false, false);
    }

    /* 7. Page permissions for guest user on page visible for player and guest */
    @Test(groups = {"regression"})
    public void permissionsPageGuestOnPlayerGuestPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_player_guest, PLAYER_GUEST_TEXT,  true);
    }

    /* 8. Page permissions for guest user on page visible for admin */
    @Test(groups = {"regression"})
    public void permissionsPageGuestOnAdminPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_admin,        ADMIN_TEXT,         false, false);
    }

    /* 9. Page permissions for player user on page visible for all */
    @Test(groups = {"regression"})
    public void permissionsPagePlayerOnAllPage() {
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_all, ALL_TEXT, true);
    }
    /* 10. Page permissions for player user on page visible for guest */
    @Test(groups = {"regression"})
    public void permissionsPagePlayerOnGuestPage() {
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_guest, GUEST_TEXT, false);
    }

    /* 11. Page permissions for player user on page visible for player */
    @Test(groups = {"regression"})
    public void permissionsPagePlayerOnPlayerPage() {
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_player, PLAYER_TEXT, true);
    }

    /* 12. Page permissions for player user on page visible for player and guest */
    @Test(groups = {"regression"})
    public void permissionsPagePlayerOnPlayerGuestPage() {
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_player_guest, PLAYER_GUEST_TEXT, true);
    }

    /* 13. Page permissions for player user on page visible for admin */
    @Test(groups = {"regression"})
    public void permissionsPagePlayerOnAdminPage() {
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_admin,       ADMIN_TEXT,         false);
    }

    /* 14. Page permissions for admin user */
    @Test(groups = {"admin"})
    public void permissionsPageAdmin() {
        assertPagePermissions(PlayerCondition.admin, ConfiguredPages.permissions_page_all,          ALL_TEXT ,          true);
        assertPagePermissions(PlayerCondition.admin, ConfiguredPages.permissions_page_guest,        GUEST_TEXT,         true);
        assertPagePermissions(PlayerCondition.admin, ConfiguredPages.permissions_page_player,       PLAYER_TEXT,        true);
        assertPagePermissions(PlayerCondition.admin, ConfiguredPages.permissions_page_player_guest, PLAYER_GUEST_TEXT,  true);
        assertPagePermissions(PlayerCondition.admin, ConfiguredPages.permissions_page_admin,        ADMIN_TEXT,         true);
    }

    private void assertPortletPermissions(PlayerCondition condition, ConfiguredPages page, String ... texts) {
        List<String> fullList = portletText;
        List<String> visibleList = Arrays.asList(texts);
        NavigationUtils.navigateToPage(condition, page);
        for (String text : fullList) {
            String role = text.toLowerCase().replace("permissions_", "").replace("_only", "").replace("_", " ");
            String message = "Portlet visible for " + role + " is displayed for " + condition.toString() + ".";
            if (visibleList.contains(text)) {
                assertTextVisible(text, message, 4);
            } else {
                assertTextInvisible(text, message);
            }
        }
    }

    private void assertPagePermissions(PlayerCondition condition, ConfiguredPages page, String text, boolean visibility) {
        assertPagePermissions(condition, page, text, visibility, true);
    }

    private void assertPagePermissions(PlayerCondition condition, ConfiguredPages page, String text, boolean visibility, boolean redirect) {
        NavigationUtils.navigateToPage(condition, page);
        String role = page.toString().replace("permissions_page_", "").replace("_", " and ");
        if (visibility) {
            assertEquals(DataContainer.getDriverData().getCurrentUrl() + page.toString(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is displayed for " + condition.toString() + ".");
            assertTextVisible(text, "Portlet on page visible for " + role + " is displayed for " + condition.toString() + ".", 4);
        } else if (redirect) {
            assertEquals(DataContainer.getDriverData().getCurrentUrl(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is not displayed for " + condition.toString() + ".");
            assertTextInvisible(text, "Portlet on page visible for " + role + " is not displayed for " + condition.toString() + ".");
        } else {
            assertEquals(DataContainer.getDriverData().getCurrentUrl() + page.toString(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is displayed for " + condition.toString() + ".");
            new LoginPopup();
        }
    }
}
