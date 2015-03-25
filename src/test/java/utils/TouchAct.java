package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TouchAct {

    private static String YUI_PATH = "http://yui.yahooapis.com/3.7.3/build/yui/yui.js";
    private static WebDriver webDriver;

    public TouchAct(WebDriver webDriver) {
        this.webDriver = webDriver;
        includeYUILibrary();
    }

    private void includeYUILibrary() {
        List<WebElement> importedScripts = webDriver.findElements(By.tagName("script"));
        boolean yuiIncluded = false;
        for (WebElement e : importedScripts) {
            if (e.getAttribute("src").contains(YUI_PATH)) {
                yuiIncluded = true;
                break;
            }
        }
        if (!yuiIncluded) {
            String IncludeYUI = "script = document.createElement('script');script.type = 'text/javascript';script.async = true;script.onload = function(){};script.src = '" + YUI_PATH + "';document.getElementsByTagName('head')[0].appendChild(script);";

            WebDriverUtils.executeScript(webDriver, IncludeYUI);
        }
    }

    public static void tap(String jqueryToElement) {
        String JavascriptToTap = "YUI().use('node-event-simulate', function(Y){var node = Y.one('" + jqueryToElement + "');node.simulateGesture('tap');});";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverUtils.executeScript(webDriver, JavascriptToTap);
    }
}
