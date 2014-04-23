package pageObjects;

import pageObjects.base.AbstractPage;
import utils.WebDriverUtils;

import java.util.HashMap;

/**
 * User: sergiich
 * Date: 7/25/13
 */

public class InternalTagsPage extends AbstractPage{

	private static final String ROOT_XP=				"//*[@class='portlet-title-text']";
	private static final String ARTICLE_XP=				"//*[@class='journal-content-article']";
	private static final String MULTIVIEW_ARTICLE_XP=	"//*[@class='journal-content-article']";
	private static final String[] INTERNAL_TAGS = 		{"casino-gaming-balance", "bingo-gaming-balance",
														"poker-gaming-balance", "sportsbook-gaming-balance", "total-bonus-balance", "withdrawable-balance",
														"internal-wallet-total-real-balance", "user-currency", "user-name", "messages", "comp-points", "first-name"};
	private static final String[] GUEST_TAGS = 			{"[CASINO_GAMING_BALANCE]","[BINGO_GAMING_BALANCE]",
														"[POKER_GAMING_BALANCE]","[SPORTSBOOK_GAMING_BALANCE]","[TOTAL_BONUS_BALANCE]","[WITHDRAWABLE_BALANCE]",
														"[INTERNAL_WALLET_TOTAL_REAL_BALANCE]","£","-","-","-","-"};

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
			map.put(tag, getInternaltagValue(tag, base));
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
		String result="";
		boolean hasErrors = false;
		for(int i=0;i<INTERNAL_TAGS.length;i++){
			if(!tagsPage.get(INTERNAL_TAGS[i]).equals(GUEST_TAGS[i])){
				result = result + " Expected: " + GUEST_TAGS[i] + " Actual result: " + tagsPage.get(INTERNAL_TAGS[i]);
				hasErrors = true;
			}
		}
		if(hasErrors){
			WebDriverUtils.runtimeExceptionWithLogs(result);
		}
	}

	private void compareMaps(HashMap tagsPage, HashMap tagsIms){
		String result="";
		boolean hasErrors = false;
		for(int i=0;i<INTERNAL_TAGS.length;i++){
			if(!tagsPage.get(INTERNAL_TAGS[i]).equals(tagsIms.get(INTERNAL_TAGS[i]))){
				result = result + " Expected: " + tagsIms.get(INTERNAL_TAGS[i]) + " Actual result: " + tagsPage.get(INTERNAL_TAGS[i]);
				hasErrors = true;
			}
		}
		if(hasErrors){
			WebDriverUtils.runtimeExceptionWithLogs(result);
		}
	}
}
