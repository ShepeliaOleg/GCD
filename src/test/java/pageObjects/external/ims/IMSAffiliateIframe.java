package pageObjects.external.ims;

import pageObjects.core.AbstractIframe;
import utils.WebDriverUtils;

public class IMSAffiliateIframe extends AbstractIframe {

    private static final String LABEL_PROFILE = "//table[@class='result']//tr[2]//td[4]//a";
    private static final String LABEL_BANNER =  "//table[@class='result']//tr[2]//td[5]";

    public IMSAffiliateIframe(String iframeId){
        super(iframeId);
    }

    public String getLabelProfile(){
        return WebDriverUtils.getElementText(LABEL_PROFILE);
    }

    public String getLabelBanner(){
        return WebDriverUtils.getElementText(LABEL_BANNER);
    }
}
