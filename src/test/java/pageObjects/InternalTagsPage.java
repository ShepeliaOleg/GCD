package pageObjects;

import pageObjects.core.AbstractPortalPage;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

import java.util.HashMap;

public class InternalTagsPage extends AbstractPortalPage {

	private static final String ROOT_XP=				"//*[@class='portlet-title-text']";
	private static final String ARTICLE_XP=				"//div[contains(@id, 'article')]";
	private static final String MULTIVIEW_ARTICLE_XP=	"//div[contains(@id, 'multiview')]";
	private static final String[] INTERNAL_TAGS = 		{
            "bonus-balance","bonus-pending-winnings","ringfenced-real-balance","bingo-bonus","all-games-bonus","games-bonus",
            "all-multiplayer-bonus","all-numbers-bonus","all-scratchcards-bonus","all-casino-bonus","casino-loyalty",
            "loyalty-level","loyalty-period-points","loyalty-redeemable-points","loyalty-next-level","loyalty-next-level-points",
            "loyalty-next-level-due-date","comp-points","user-ipointsbalance","pokerviplevel","points-required-current-levelname",
            "ipoints2nextlevel","nextviplevel","nextviplevelname","user-currency","casino-gaming-balance","credit-amount",
            "user-name","user-first-name","user-last-name","poker-launch-link","user-currency","pending-withdrawals",
            "real-gaming-balance","casino-table-real-balance","broken-games-real-balance","broken-games-bonus-balance",
            "main-real-balance","total-bonus-balance","unread-messages-count","is-credit-user","poker-online-players"
    };

	private static final HashMap<String,String> GUEST_TAGS = new HashMap(){{
        put(INTERNAL_TAGS[0], "-");put(INTERNAL_TAGS[1], "-");put(INTERNAL_TAGS[2], "-");
        put(INTERNAL_TAGS[3], "-");put(INTERNAL_TAGS[4], "-");put(INTERNAL_TAGS[5], "-");
        put(INTERNAL_TAGS[6], "-");put(INTERNAL_TAGS[7], "-");put(INTERNAL_TAGS[8], "-");
        put(INTERNAL_TAGS[9], "-");put(INTERNAL_TAGS[10], "-");put(INTERNAL_TAGS[11], "-");
        put(INTERNAL_TAGS[12], "-");put(INTERNAL_TAGS[13], "-");put(INTERNAL_TAGS[14], "-");
        put(INTERNAL_TAGS[15], "-");put(INTERNAL_TAGS[16], "-");put(INTERNAL_TAGS[17], "-");
        put(INTERNAL_TAGS[18], "-");put(INTERNAL_TAGS[19], "-");put(INTERNAL_TAGS[20], "-");
        put(INTERNAL_TAGS[21], "-");put(INTERNAL_TAGS[22], "-");put(INTERNAL_TAGS[23], "-");
        put(INTERNAL_TAGS[24], "-");put(INTERNAL_TAGS[25], "-");put(INTERNAL_TAGS[26], "-");
        put(INTERNAL_TAGS[27], "-");put(INTERNAL_TAGS[28], "-");put(INTERNAL_TAGS[29], "-");
        put(INTERNAL_TAGS[30], "-");put(INTERNAL_TAGS[31], "-");put(INTERNAL_TAGS[32], "-");
        put(INTERNAL_TAGS[33], "-");put(INTERNAL_TAGS[34], "-");put(INTERNAL_TAGS[35], "-");
        put(INTERNAL_TAGS[36], "-");put(INTERNAL_TAGS[37], "-");put(INTERNAL_TAGS[38], "-");
        put(INTERNAL_TAGS[39], "-");put(INTERNAL_TAGS[40], "-");put(INTERNAL_TAGS[41], "-");

    }};

	public InternalTagsPage(){
		super(new String[]{ROOT_XP});
	}

	private HashMap getTagsFromArticle(){
		return getTags(ARTICLE_XP);
	}

	private HashMap getTagsFromMultiview(){
		return getTags(MULTIVIEW_ARTICLE_XP);
	}

	private HashMap getTags(String base){
		HashMap map = new HashMap<String, String>();
		for(String tag:INTERNAL_TAGS){
			map.put(tag, getInternaltagValue(tag, base).replace(",", ""));
		}
		return map;
	}

	private String getInternaltagValue (String className, String base){
		if(WebDriverUtils.getXpathCount(base + "//span[@class='" + className + "']")>1){
			return WebDriverUtils.getElementText(base+"//span/span[@class='"+className+"']");
		}else{
			return WebDriverUtils.getElementText(base+"//span[@class='"+className+"']");
		}
	}

	public void compareTags(boolean article, HashMap tagsIms){
		HashMap tagsPage;
		if(article){
			tagsPage = getTagsFromArticle();
		}else {
			tagsPage= getTagsFromMultiview();
		}
		if(tagsIms==null){
			compareMaps(tagsPage);
		}else{
			compareMaps(tagsPage, tagsIms);
		}
	}

	private void compareMaps(HashMap tagsPage){
        compareMaps(tagsPage, GUEST_TAGS);
	}

	private void compareMaps(HashMap tagsPage, HashMap tagsIms) {
        for (String tag:INTERNAL_TAGS) {
            AbstractTest.assertEquals(tagsIms.get(tag), tagsPage.get(tag), "Internal tag '" + tag + "' -");
        }
    }
}
