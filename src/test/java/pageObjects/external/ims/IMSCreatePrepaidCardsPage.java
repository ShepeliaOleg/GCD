package pageObjects.external.ims;

import pageObjects.core.AbstractServerPage;
import utils.FileUtils;
import utils.WebDriverUtils;
import utils.core.WebDriverFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by serhiist on 3/16/2015.
 */
public class IMSCreatePrepaidCardsPage extends AbstractServerPage {
    private static final String ROOT_XP =                               "//*[contains(@class,'inlinephpscript main-content')]";
    private static final String DROPDOWN_CASINO_XP =                    "//*[@id='casino']";
    private static final String CHECKBOX_CARDS_WITHOUT_PIN_XP =         "//*[@name='pin']";
    private static final String FIELD_MONETRY_VALUE_XP =                "//*[@id='cardvalue']";
    private static final String DROPDOWN_CURRENCY_XP =                  "//*[@id='currencycode']";
    private static final String FIELD_EXPIRATION_DATE_XP =              "//*[@id='expdate']";
    private static final String FIELD_NUMBER_OF_CARDS_XP =              "//*[@id='cardamount']";
    private static final String BUTTON_GENERATE_PREPAID_CARDS_XP =      "//*[@id='submit']";
    private static final String LINK_GENERATED_CARD_ID_XP =             "//*[@id='success_message_container_1']/a";
    private static final String BUTTON_EXPORT_XP =                      "//*[@id='export']";

    private void setCasino(String casino){
        WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CASINO_XP, casino);
    }

    private void setCheckboxCardsWithoutPin(boolean isPinNecessary){
        WebDriverUtils.setCheckBoxState(WebDriverFactory.getServerDriver(), CHECKBOX_CARDS_WITHOUT_PIN_XP, !isPinNecessary);
    }

    private void setMonetary(String monetaryValue){
        WebDriverUtils.inputTextToField(WebDriverFactory.getServerDriver(), FIELD_MONETRY_VALUE_XP, monetaryValue);
    }

    private void setCurrency(String currency){
        WebDriverUtils.setDropdownOptionByValue(WebDriverFactory.getServerDriver(), DROPDOWN_CURRENCY_XP, currency);
    }

    private void setDate(Calendar date){
        WebDriverUtils.inputTextToField(WebDriverFactory.getServerDriver(), FIELD_EXPIRATION_DATE_XP, new SimpleDateFormat("yyyy-MM-dd").format(date.getTime()));
    }

    private void setNumberOfCards(int numberOfCards){
        WebDriverUtils.inputTextToField(WebDriverFactory.getServerDriver(), FIELD_NUMBER_OF_CARDS_XP, String.valueOf(numberOfCards));
    }

    private void clickGenerateButton(){
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_GENERATE_PREPAID_CARDS_XP);
    }

    private void clickGeneratedId(){
        WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), LINK_GENERATED_CARD_ID_XP);
        WebDriverUtils.navigateToURL(WebDriverUtils.getAttribute(WebDriverFactory.getServerDriver(), LINK_GENERATED_CARD_ID_XP, "href"));
//        WebDriverUtils.click(LINK_GENERATED_CARD_ID_XP);
    }

    private void clickExportButton(){
        WebDriverUtils.waitForElement(BUTTON_EXPORT_XP);
        WebDriverUtils.click(WebDriverFactory.getServerDriver(), BUTTON_EXPORT_XP);
        WebDriverUtils.waitForFileDownload(FileUtils.PREPAIDCARD_CVS_PATH);
    }

    public void inputDataToGeneratePrepaidCard(boolean isPinNecessary, int monetaryValue, String currency, int numberOfCards){
        deleteGeneratedFile();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        String casino = IMSLoginDatabasePage.getCasino();
        inputDataToGeneratePrepaidCard(casino, isPinNecessary, String.valueOf(monetaryValue), currency, cal, numberOfCards);
        WebDriverUtils.waitFor(200);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        WebDriverUtils.waitFor(200);
        WebDriverUtils.acceptJavaScriptAlert(WebDriverFactory.getServerDriver());
        clickGeneratedId();
        clickExportButton();
    }

    public void inputDataToGeneratePrepaidCard(String casino, boolean isPinNeccessary, String monetaryValue, String currency, Calendar date, int numberOfCards){
        WebDriverUtils.switchToIframeByXpath(WebDriverFactory.getServerDriver(), ROOT_XP);
        WebDriverUtils.waitForElement(WebDriverFactory.getServerDriver(), DROPDOWN_CASINO_XP);
        setCasino(casino);
        setCheckboxCardsWithoutPin(isPinNeccessary);
        setMonetary(monetaryValue);
        setCurrency(currency);
        setDate(date);
        setNumberOfCards(numberOfCards);
        clickGenerateButton();
    }

    public void deleteGeneratedFile(){
        File previousPrepaidCardFile = new File(FileUtils.PREPAIDCARD_CVS_PATH);
        if (previousPrepaidCardFile.exists())
            previousPrepaidCardFile.delete();
    }
}
