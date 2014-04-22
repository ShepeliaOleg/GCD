package enums;

/**
 * User: sergiich
 * Date: 8/16/13
 */
public enum ConfiguredPages{
	gamesList("list"),
	gamesStyleOne("style1_slider"),
	gamesStyleTwo("style2"),
	gamesStyleThree("style3"),
	gamesStyleFour("style4"),
	gamesToFit("tofit"),
	gamesMinimum ("minimum"),
	gamesFavourites ("favourites"),
	gamesNavigationStyleNone ("navigation-styles/none"),
	gamesNavigationStyleRefineBy ("navigation-styles/refine-by"),
	gamesNavigationStyleCategoryTabsTop ("navigation-styles/category-tabs-top"),
	gamesNavigationStyleCategoryTabsLeft ("navigation-styles/category-tabs-left"),
	gamesNavigationStyleCategoryTabsRefineByTop ("navigation-styles/category-tabs-refine-by-top"),
	gamesNavigationStyleCategoryTabsRefineByLeft ("navigation-styles/category-tabs-refine-by-left"),
	bingoLobbyFeed("bingo-schedule-lobby"),
	bingoScheduleFeed("bingo-schedule-schedule"),
	internalTags("internaltags"),
	bonusPage("bonus");

    private final String name;

    private ConfiguredPages(String s) {
        name = s;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
        return name;
    }
}
