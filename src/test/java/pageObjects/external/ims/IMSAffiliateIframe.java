package pageObjects.external.ims;

import pageObjects.base.AbstractIframe;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

/**
 * Created by sergiich on 5/21/14.
 */

public class IMSAffiliateIframe extends AbstractIframe {

    private static final String LABEL_BANNER = "//table[@class='result']//tr[2]//td[5]";

    public IMSAffiliateIframe(String iframeId){
        super(iframeId);
    }

    public String getLabelBanner(){
        return WebDriverUtils.getElementText(LABEL_BANNER);
    }
}
