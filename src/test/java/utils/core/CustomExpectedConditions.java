package utils.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

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
}
