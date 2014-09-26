package enums;

public enum ConfiguredPages{
    admin("admin"),
    balance("myaccount1/balance"),
    banner5seconds("banner_5_seconds"),
    bannerGame("banner_game"),
    bannerHtml("banner_html"),
    bannerImage("banner_image"),
    bannerInRotation("banner_in_rotation"),
    bannerLink("banner_link"),
    bannerMixed("banner_mixed"),
    bannerNavigationArrows("banner_arrows"),
    bannerNavigationArrowsBullets("banner_arrows_bullets"),
    bannerNavigationBullets("banner_bullets"),
    bannerNavigationButtons("banner_buttons"),
    bannerWebContent("banner_webcontent"),
    bannerWebContentGame("banner_webcontent_game"),
    bannerProfileNoProfileOneSlide("profile_id_0_1"),
    bannerProfileNoProfileTwoSlides("profile_id_0_2"),
    bannerProfileSingleProfileOneSlide("profile_id_single_1_1"),
    bannerProfileMultiProfileOneSlide("profile_id_multi_1_1"),
    bannerProfileSingleSameProfileTwoSlides("profile_id_single_same_2_2"),
    bannerProfileSingleProfileOneOfTwoSlides("profile_id_single_same_1_2"),
    bannerProfileSingleDiffProfilesTwoSlides("profile_id_single_diff_2_2"),
    bannerProfileMultiProfileTwoSlides("profile_id_multi_2_2"),
    bannerProfileMultiProfileOneOfTwoSlides("profile_id_multi_1_2"),
    bingoLobbyFeed("bingo-schedule-lobby"),
    bingoScheduleFeed("bingo-schedule-schedule"),
    bonusPage("bonus"),
    changeMyDetails("update-my-details"),
    changeMyPassword("myaccount/change-my-password"),
    forgotPassword("forgot-password"),
    forgotUsername("forgot-password"),
    gamesCasinoPage("home"),
    gamesList("list"),
	gamesStyleOne("style1_slider"),
	gamesStyleTwo("style2"),
	gamesStyleThree("style3"),
	gamesStyleFour("style4"),
	gamesToFit("to-fit"),
	gamesMinimum ("minimum"),
	gamesFavourites ("favourites"),
	gamesNavigationStyleNone ("navigation-styles/none"),
	gamesNavigationStyleRefineBy ("navigation-styles/refine-by"),
	gamesNavigationStyleCategoryTabsTop ("navigation-styles/category-tabs-top"),
	gamesNavigationStyleCategoryTabsLeft ("navigation-styles/category-tabs-left"),
	gamesNavigationStyleCategoryTabsRefineByTop ("navigation-styles/category-tabs-refine-by-top"),
	gamesNavigationStyleCategoryTabsRefineByLeft ("navigation-styles/category-tabs-refine-by-left"),
    home("home"),
    inbox("myaccount1/inbox"),
    internalTags("internal_tags"),
    liveTableFinder("live-casino"),
    myAccount("myaccount1"),
	register("register"),
    registerClientTypeCreferrer("register_client_type_creferrer"),
    registerNoClientType("register_no_client_type"),
    referAFriend("myaccount1/refer-a-friend"),
    responsibleGaming("myaccount1/responsible-gaming"),
    selfExclusion("myaccount1/responsible-gaming"),
    webContentGame("web_content_game_launch");

    private final String name;

    private ConfiguredPages(String s) {
        name = s;
    }

    public String toString(){
        return name;
    }
}
