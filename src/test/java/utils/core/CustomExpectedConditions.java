package utils.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * User: sergiich
 * Date: 4/9/14
 */

public final class CustomExpectedConditions extends WebDriverObject{

	public static ExpectedCondition<Boolean> pageLoadComplete(){
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
	}

    public static ExpectedCondition<Boolean> numberOfElementsEquals(final int number, final String xpath){
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return driver.findElements(By.xpath(xpath)).size()==number;
            }
        };
    }
}
