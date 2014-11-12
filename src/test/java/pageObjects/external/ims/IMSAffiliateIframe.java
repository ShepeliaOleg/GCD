package pageObjects.external.ims;

import pageObjects.core.AbstractServerIframe;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

public class IMSAffiliateIframe extends AbstractServerIframe {

    private static final String LABEL_PROFILE = "//table[@class='result']//tr[2]//td[4]//a";
    private static final String LABEL_BANNER =  "//table[@class='result']//tr[2]//td[5]";

    public IMSAffiliateIframe(String iframeId){
        super(iframeId);
    }

    public String getLabelProfile(){
        return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), LABEL_PROFILE);
    }

    public String getLabelBanner(){
        return WebDriverUtils.getElementText(WebDriverFactory.getServerDriver(), LABEL_BANNER);
    }
}
