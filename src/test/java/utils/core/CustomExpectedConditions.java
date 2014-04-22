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

	public static ExpectedCondition<Boolean> checkExpectedConditions(final List<ExpectedCondition> conditions){
		final Boolean checkResult;
		checkResult = checkConditions(conditions);

		return new ExpectedCondition<Boolean>(){
			@Override
			public Boolean apply(WebDriver webDriver){
				return checkResult;
			}
		};
	}

	private static Boolean checkConditions(List<ExpectedCondition> conditions){
		Object check;
		for(ExpectedCondition condition : conditions){
			check= condition.apply(webDriver);
			if(check == null || check.equals(false)){
				return false;
			}
		}
		return true;
	}

	public static ExpectedCondition<Boolean> pageLoadComplete(){
		return new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
	}

}
