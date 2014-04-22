package pageObjects.base;

/**
 * Created with IntelliJ IDEA.
 * User: darya.alymova
 * Date: 8/8/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */

public abstract class AbstractIframe extends AbstractPageObject{

	private static final String ERROR_XP="//*[contains(text(), '404')]";

	public AbstractIframe(String iframeId){
		super(null, new String[]{ERROR_XP}, iframeId);
	}
}
