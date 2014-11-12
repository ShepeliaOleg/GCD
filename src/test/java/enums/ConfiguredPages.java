package enums;

public enum ConfiguredPages{
    admin("admin"),
    balance("balance"),
    banner5seconds("banner_5_seconds"),
    bannerGame("banner_game"),
    bannerGameTwoSlides("banner_game_two_slides"),
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
    bingoLobbyFeed("bingo_schedule_lobby"),
    bingoScheduleFeed("bingo_schedule_schedule"),
    bonusPage("bonus"),
    updateMyDetails("update_my_details"),
    changeMyPassword("change_my_password"),
    deposit("deposit"),
    forgotPassword("forgot_password"),
    forgotUsername("forgot_password"),
    gamesCasinoPage("home"),
    gamesList("list"),
	gamesStyleOne("style1_slider"),
	gamesStyleTwo("style2"),
	gamesStyleThree("style3"),
	gamesStyleFour("style4"),
	gamesToFit("to_fit"),
	gamesMinimum ("minimum"),
	gamesFavourites ("favourites"),
	gamesNavigationStyleNone ("navigation_styles_none"),
	gamesNavigationStyleRefineBy ("navigation_styles_refine-by"),
	gamesNavigationStyleCategoryTabsTop ("navigation_styles_category_tabs_top"),
	gamesNavigationStyleCategoryTabsLeft ("navigation_styles_category_tabs_left"),
	gamesNavigationStyleCategoryTabsRefineByTop ("navigation_styles_category_tabs_refine_by_top"),
	gamesNavigationStyleCategoryTabsRefineByLeft ("navigation_styles_category_tabs_refine_by_left"),
    home("home"),
    inbox("inbox"),
    internalTags("internal_tags"),
    liveTableFinder("live_casino"),
    myAccount("my_account"),
    page_in_popup_a("page_in_popup_a"),
    page_in_popup_b("page_in_popup_b"),
    page_in_popup_disabled("page_in_popup_disabled"),
    page_in_popup_link_to_child("page_in_popup_link_to_child"),
    page_in_popup_child("page_in_popup_child"),
    page_in_popup_parent("page_in_popup_parent"),
    page_in_popup_scroll("page_in_popup_scroll"),
    permissions_page_admin("permissions_page_admin"),
    permissions_page_all("permissions_page_all"),
    permissions_page_guest("permissions_page_guest"),
    permissions_page_player("permissions_page_player"),
    permissions_page_player_guest("permissions_page_player_guest"),
    permissions_portlet("permissions_portlet"),
    register("register"),
    registerClientTypeCreferrer("register_client_type_creferrer"),
    registerNoClientType("register_no_client_type"),
    referAFriend("refer_a_friend"),
    responsibleGaming("deposit-limits"),
    selfExclusion("self_exclude"),
    webContentGame("web_content_game_launch"),
    withdraw("withdraw");

    private final String name;

    private ConfiguredPages(String s) {
        name = s;
    }

    public String toString(){
        return name;
    }
}
