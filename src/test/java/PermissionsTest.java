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

	/* 1. Portlet permissions for guest and player users */
	@Test(groups = {"regression"})
	public void permissionsPortlet() {
        assertPortletPermissions(PlayerCondition.guest, ConfiguredPages.permissions_portlet, ALL_TEXT, GUEST_TEXT, PLAYER_GUEST_TEXT);
        assertPortletPermissions(PlayerCondition.player, ConfiguredPages.permissions_portlet, ALL_TEXT, PLAYER_TEXT, PLAYER_GUEST_TEXT);
	}

    /* 1. Portlet permissions for admin user */
    @Test(groups = {"admin"})
    public void permissionsPortletAdmin() {
        assertPortletPermissions(PlayerCondition.admin, ConfiguredPages.permissions_portlet, ALL_TEXT, ADMIN_TEXT, GUEST_TEXT, PLAYER_TEXT, PLAYER_GUEST_TEXT);
    }

    /* 2. Page permissions for guest and player users */
    @Test(groups = {"regression"})
    public void permissionsPage() {
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_all,          ALL_TEXT,           true);
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_guest,        GUEST_TEXT,         true);
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_player,       PLAYER_TEXT,        false, false);
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_player_guest, PLAYER_GUEST_TEXT,  true);
        assertPagePermissions(PlayerCondition.guest, ConfiguredPages.permissions_page_admin,        ADMIN_TEXT,         false, false);

        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_all,         ALL_TEXT,           true);
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_guest,       GUEST_TEXT,         false);
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_player,      PLAYER_TEXT,        true);
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_player_guest,PLAYER_GUEST_TEXT,  true);
        assertPagePermissions(PlayerCondition.player, ConfiguredPages.permissions_page_admin,       ADMIN_TEXT,         false);
    }

    /* 2. Page permissions for admin user */
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
                assertTextVisible(text, message);
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
        WebDriverUtils.waitFor();
        String role = page.toString().replace("permissions_page_", "").replace("_", " and ");
        if (visibility) {
            assertEquals(DataContainer.getDriverData().getCurrentUrl() + page.toString(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is displayed for " + condition.toString() + ".");
            assertTextVisible(text, "Portlet on page visible for " + role + " is displayed for " + condition.toString() + ".");
        } else if (redirect) {
                assertEquals(DataContainer.getDriverData().getCurrentUrl(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is not displayed for " + condition.toString() + ".");
                assertTextInvisible(text, "Portlet on page visible for " + role + " is not displayed for " + condition.toString() + ".");
            } else {
                assertEquals(DataContainer.getDriverData().getCurrentUrl() + page.toString(), WebDriverUtils.getCurrentUrl(), "Page visible for " + role + " is displayed for " + condition.toString() + ".");
                new LoginPopup();
            }
    }


}
