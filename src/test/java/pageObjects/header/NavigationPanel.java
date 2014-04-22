package pageObjects.header;

import pageObjects.HomePage;
import pageObjects.gamesPortlet.GamesPortletPage;
import pageObjects.liveCasino.LiveCasinoPage;
import utils.WebDriverUtils;

/**
 * User: sergiich
 * Date: 4/10/14
 */

public class NavigationPanel extends Header{

	private static final String ROOT_XP=			"//*[@id='navigation']";
	private static final String HOME_LINK_XP=		ROOT_XP+"//a[contains(@href, '/home')]";
	private static final String CASINO_LINK_XP=		ROOT_XP+"//a[contains(@href, '/casino')]";
	private static final String LIVE_CASINO_LINK_XP=ROOT_XP+"//a[contains(@href, '/live-casino')]";
	private static final String BINGO_LINK_XP=		ROOT_XP+"//a[contains(@href, '/bingo')]";
	private static final String GAMES_LINK_XP=		ROOT_XP+"//a[contains(@href, '/games')]";
	private static final String POKER_LINK_XP=		ROOT_XP+"//a[contains(@href, '/poker')]";
	private static final String MOBILE_LINK_XP=		ROOT_XP+"//a[contains(@href, '/mobile')]";
	private static final String SPORTS_LINK_XP=		ROOT_XP+"//a[contains(@href, '/promotion')]";

	public NavigationPanel(){
		super(new String[]{ROOT_XP, HOME_LINK_XP, CASINO_LINK_XP,
				LIVE_CASINO_LINK_XP, BINGO_LINK_XP, GAMES_LINK_XP,
				POKER_LINK_XP, MOBILE_LINK_XP, SPORTS_LINK_XP});
	}

	public HomePage selectHomeTab(){
		WebDriverUtils.click(HOME_LINK_XP);
		return new HomePage();
	}

	public GamesPortletPage selectCasinoTab(){
		WebDriverUtils.click(CASINO_LINK_XP);
		return new GamesPortletPage();
	}

	public LiveCasinoPage selectLiveCasinoTab(){
		WebDriverUtils.click(LIVE_CASINO_LINK_XP);
		return new LiveCasinoPage();
	}

	public void selectBingoTab(){
		WebDriverUtils.click(BINGO_LINK_XP);
	}

	public GamesPortletPage selectGamesTab(){
		WebDriverUtils.click(GAMES_LINK_XP);
		return new GamesPortletPage();
	}

	public void selectPokerTab(){
		WebDriverUtils.click(POKER_LINK_XP);
	}

	public void selectMobileTab(){
		WebDriverUtils.click(MOBILE_LINK_XP);
	}

	public void selectSportsTab(){
		WebDriverUtils.click(SPORTS_LINK_XP);
	}
}
