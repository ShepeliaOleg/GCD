package pageObjects.account;

import pageObjects.cashier.CardAddedNotification;
import pageObjects.core.AbstractPortalPage;
import springConstructors.UserData;
import utils.WebDriverUtils;
import utils.core.AbstractTest;

public class AddCardPage extends AbstractPortalPage {

    private static final String ROOT_XP = "//*[contains(@class, 'cashier-add-card')]";
    private static final String FIELD_CARD_XP = "//*[@name='cardNumber']";
    private static final String DROPDOWN_MONTH_XP = "//*[@id='expirationMonth']";
    private static final String DROPDOWN_YEAR_XP = "//*[@id='expirationYear']";
    private static final String CHECKBOX_FILL_USER_DATA_XP = "//*[@id='fillUserData']";
    private static final String FIELD_FIRST_NAME_XP = "//*[@id='firstName']";
    private static final String FIELD_LAST_NAME_XP = "//*[@id='lastName']";
    private static final String DROPDOWN_COUNTRY_XP = "//*[@id='countryCode']";
    private static final String FIELD_POSTCODE_XP = "//*[@id='zip']";
    private static final String FIELD_ADDRESS_XP = "//*[@id='billingAddress']";
    private static final String FIELD_CITY_XP = "//*[@id='city']";
    private static final String BUTTON_ADD_XP = "//*[contains(@class, 'fn-submit')]";

    public static final String INVALID_CARD = "1111222233334444";
    public static final String UNSUPPORTED_CARD = "6011178977000908";

    private static final int TIMEOUT = 30;

    public AddCardPage(){
        super(new String[] {ROOT_XP});
    }

    public void fillData(UserData userData){
        fillFirstName(userData.getFirstName());
        fillLastName(userData.getLastName());
        setDropdownCountry(userData.getCountry());
        fillPostcode(userData.getPostCode());
        fillAddress(userData.getFullAddress());
        fillCity(userData.getCity());
    }

    public AddCardPage addInvalidCard(){
        addCard(INVALID_CARD);
        return new AddCardPage();
    }

    public AddCardPage addUnsupportedCard(){
        addCard(UNSUPPORTED_CARD);
        return new AddCardPage();
    }

    public void addValidCard(String card){
        addCard(card);
        new CardAddedNotification();
    }

    public void addCard(String card){
        fillCardNumber(card);
        setDropdownMonth("01");
        setDropdownYear("2017");
        setCheckboxFillUserData(true);
        clickAdd();
    }

    private void fillCardNumber(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_CARD_XP, text);
    }

    private String getCardNumber(){
        return WebDriverUtils.getInputFieldText(FIELD_CARD_XP);
    }

    private void setDropdownMonth(String value){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_MONTH_XP, value);
    }

    private String getDropdownMonth(){
        return WebDriverUtils.getDropdownSelectedOptionValue(DROPDOWN_MONTH_XP);
    }

    private void setDropdownYear(String value){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_YEAR_XP, value);
    }

    private String getDropdownYear(){
        return WebDriverUtils.getDropdownSelectedOptionValue(DROPDOWN_YEAR_XP);
    }

    public void setCheckboxFillUserData(boolean state){
        WebDriverUtils.setCheckBoxState(CHECKBOX_FILL_USER_DATA_XP, state);
        int counter=0;
        if(state){
            while (getFirstName().equals("")){
                if(counter==TIMEOUT){
                    AbstractTest.failTest("Billing data is not filled after '"+TIMEOUT+"' sec");
                }
                WebDriverUtils.waitFor();
                counter++;
            }
        }else {
            while (!getFirstName().equals("")){
                if(counter==TIMEOUT){
                    AbstractTest.failTest("Billing data is not deleted after '"+TIMEOUT+"' sec");
                }
                WebDriverUtils.waitFor();
                counter++;
            }
        }

    }

    public boolean getCheckboxFillUserData(){
        return WebDriverUtils.getCheckBoxState(CHECKBOX_FILL_USER_DATA_XP);
    }

    private void fillFirstName(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_FIRST_NAME_XP, text);
    }

    private String getFirstName(){
        return WebDriverUtils.getInputFieldText(FIELD_FIRST_NAME_XP);
    }

    private void fillLastName(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_LAST_NAME_XP, text);
    }

    private String getLastName(){
        return WebDriverUtils.getInputFieldText(FIELD_LAST_NAME_XP);
    }

    private void setDropdownCountry(String value){
        WebDriverUtils.setDropdownOptionByValue(DROPDOWN_COUNTRY_XP, value);
    }

    private String getDropdownCountry(){
        return WebDriverUtils.getDropdownSelectedOptionValue(DROPDOWN_COUNTRY_XP);
    }

    private void fillPostcode(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_POSTCODE_XP, text);
    }

    private String getPostcode(){
        return WebDriverUtils.getInputFieldText(FIELD_POSTCODE_XP);
    }

    private void fillAddress(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_ADDRESS_XP, text);
    }

    private String getAddress(){
        return WebDriverUtils.getInputFieldText(FIELD_ADDRESS_XP);
    }

    private void fillCity(String text){
        WebDriverUtils.clearAndInputTextToField(FIELD_CITY_XP, text);
    }

    private String getCity(){
        return WebDriverUtils.getInputFieldText(FIELD_CITY_XP);
    }

    private void clickAdd(){
        WebDriverUtils.click(BUTTON_ADD_XP);
    }

    public void assertUserData(UserData userData) {
        AbstractTest.assertEquals(userData.getFirstName(), getFirstName(), "First name");
        AbstractTest.assertEquals(userData.getLastName(), getLastName(), "Last name");
        AbstractTest.assertEquals(userData.getCountry(), getDropdownCountry(), "Country");
        AbstractTest.assertEquals(userData.getPostCode().toUpperCase(), getPostcode(), "Postcode");
        AbstractTest.assertEquals(userData.getFullAddress(), getAddress(), "Address");
        AbstractTest.assertEquals(userData.getCity(), getCity(), "City");
    }

    public void assertUserDataIsEmpty() {
        AbstractTest.assertEquals("", getCardNumber(), "First name");
        AbstractTest.assertEquals("", getDropdownMonth(), "Last name");
        AbstractTest.assertEquals("", getDropdownYear(), "Country");
        AbstractTest.assertEquals("", getFirstName(), "First name");
        AbstractTest.assertEquals("", getLastName(), "Last name");
        AbstractTest.assertEquals("", getDropdownCountry(), "Country");
        AbstractTest.assertEquals("", getPostcode(), "Postcode");
        AbstractTest.assertEquals("", getAddress(), "Address");
        AbstractTest.assertEquals("", getCity(), "City");
    }


    public void assertInvalidMessage() {
        assertMessage("Invalid credit card number or expiration date");
    }

    public void assertUnsupportedMessage() {
        assertMessage("Invalid credit card number or expiration date");
    }

    public void assertCardUsedMessage() {
        assertMessage("Invalid credit card number or expiration date");
    }

    private void assertMessage(String message){
        if(!isPortletErrorVisible()){
            AbstractTest.failTest("Message did not appear");
        }else {
            AbstractTest.assertEquals(message, getPortletErrorMessage(), "Error message");
        }
    }


}
