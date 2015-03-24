import enums.ConfiguredPages;
import enums.PlayerCondition;
import org.testng.annotations.Test;
import pageObjects.banner.BannerPageProfileID;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.core.AbstractTest;
import utils.core.DataContainer;

public class BannerProfileIDTest extends AbstractTest{

    /*profile ID - Player with profileID*/
    @Test(groups = {"regression", "banner"})
    public void profileIDPlayerWith() {
        skipTestWithIssues("D-19983");
        PortalUtils.loginUser(DataContainer.getUserData().getRegisteredUserDataWithProfileID());
        checkProfileID(ConfiguredPages.bannerProfileNoProfileOneSlide, BannerPageProfileID.NO_PROFILE_ID, null, null, null);
        checkProfileID(ConfiguredPages.bannerProfileNoProfileTwoSlides, BannerPageProfileID.NO_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneSlide, BannerPageProfileID.VALID_PROFILE_ID, null, null, null);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneSlide, BannerPageProfileID.VALID_PROFILE_ID, null, null, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleSameProfileTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneOfTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleDiffProfilesTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, null, BannerPageProfileID.INVALID_PROFILE_1_ID);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneOfTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
    }

    /*profile ID - Player without profileID*/
    @Test(groups = {"regression", "banner"})
    public void profileIDPlayerWithout() {
        skipTestWithIssues("D-19983");
        PortalUtils.loginUser(DataContainer.getUserData().getRegisteredUserData());
        checkProfileID(ConfiguredPages.bannerProfileNoProfileOneSlide, BannerPageProfileID.NO_PROFILE_ID, null, null, null);
        checkProfileID(ConfiguredPages.bannerProfileNoProfileTwoSlides, BannerPageProfileID.NO_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneSlide, null, BannerPageProfileID.VALID_PROFILE_ID, null, null);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneSlide, null,BannerPageProfileID.VALID_PROFILE_ID, null, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleSameProfileTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneOfTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleDiffProfilesTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.INVALID_PROFILE_1_ID);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneOfTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, BannerPageProfileID.NO_PROFILE_ID, null);
    }

    /*profile ID - Guest */
    @Test(groups = {"regression", "banner"})
    public void profileIDGuest() {
        skipTestWithIssues("D-19983");
        NavigationUtils.navigateToPage(PlayerCondition.guest, ConfiguredPages.home);
        checkProfileID(ConfiguredPages.bannerProfileNoProfileOneSlide, BannerPageProfileID.NO_PROFILE_ID, null, null, null);
        checkProfileID(ConfiguredPages.bannerProfileNoProfileTwoSlides, BannerPageProfileID.NO_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneSlide, null, BannerPageProfileID.VALID_PROFILE_ID, null, null);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneSlide, null,BannerPageProfileID.VALID_PROFILE_ID, null, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleSameProfileTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID);
        checkProfileID(ConfiguredPages.bannerProfileSingleProfileOneOfTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(ConfiguredPages.bannerProfileSingleDiffProfilesTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.INVALID_PROFILE_1_ID);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID);
        checkProfileID(ConfiguredPages.bannerProfileMultiProfileOneOfTwoSlides, null, BannerPageProfileID.VALID_PROFILE_ID, BannerPageProfileID.NO_PROFILE_ID, null);
    }

    /*profile ID - Admin */
    @Test(groups = {"admin"})
    public void profileIDAdmin() {
        skipTestWithIssues("D-19983");
        PortalUtils.loginAdmin();
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileNoProfileOneSlide, BannerPageProfileID.NO_PROFILE_ID, null, null, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileNoProfileTwoSlides, BannerPageProfileID.NO_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileSingleProfileOneSlide, BannerPageProfileID.VALID_PROFILE_ID, null, null, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileMultiProfileOneSlide, BannerPageProfileID.VALID_PROFILE_ID, null, null, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileSingleSameProfileTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileSingleProfileOneOfTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileSingleDiffProfilesTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.INVALID_PROFILE_1_ID, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileMultiProfileTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.VALID_PROFILE_ID, null);
        checkProfileID(PlayerCondition.admin, ConfiguredPages.bannerProfileMultiProfileOneOfTwoSlides, BannerPageProfileID.VALID_PROFILE_ID, null, BannerPageProfileID.NO_PROFILE_ID, null);
    }

    private void checkProfileID(ConfiguredPages page, String present, String notPresent, String present2,  String notPresent2){
        checkProfileID(PlayerCondition.any, page, present, notPresent, present2, notPresent2);
    }

    private void checkProfileID(PlayerCondition playerCondition, ConfiguredPages page, String present, String notPresent, String present2,  String notPresent2){
        String pageName = " is Visible on page "+ page.toString();
        String first = "_1";
        String second = "_2";
        String firstPage = first+pageName;
        String secondPage = second+pageName;
        BannerPageProfileID bannerPage = (BannerPageProfileID) NavigationUtils.navigateToPage(playerCondition, page);
        if(present==null&&present2==null){
            assertFalse(BannerPageProfileID.isBannerPresent(), "Banner "+pageName);
        }else {
            if(present!=null){
                assertTrue(BannerPageProfileID.slideProfileVisible(present+first), present+firstPage);
            }
            if(notPresent!=null){
                assertFalse(BannerPageProfileID.slideProfileVisible(notPresent+first), notPresent+firstPage);
            }
            if(BannerPageProfileID.isNextArrowVisible()){
                bannerPage.showNextSlide();
            }
            if(present2!=null){
                assertTrue(BannerPageProfileID.slideProfileVisible(present2+second), present2+secondPage);
            }
            if(notPresent2!=null){
                assertFalse(BannerPageProfileID.slideProfileVisible(notPresent2+second), notPresent2+secondPage);
            }
        }
    }

}
