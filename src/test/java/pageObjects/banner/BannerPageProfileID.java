package pageObjects.banner;

import utils.WebDriverUtils;

/**
 * Created by sergiich on 9/24/2014.
 */
public class BannerPageProfileID extends BannerPage{

    private static final String PROFILE_IDENTIFIER =                "//*[@class='"+PLACEHOLDER+"']";
    public static final String VALID_PROFILE_ID =                  "AUTO1";
    public static final String INVALID_PROFILE_1_ID =              "AUTO2";
    public static final String NO_PROFILE_ID =                     "NO_PROFILE";

    public BannerPageProfileID(){
        super(new String[]{});
    }

    public static boolean isBannerPresent(){
        return WebDriverUtils.isVisible(ROOT_XP, 2);
    }

    public static boolean slideProfileVisible(String profile){
        return WebDriverUtils.isVisible(PROFILE_IDENTIFIER.replace(PLACEHOLDER, profile), 4);
    }
}
