package springConstructors;

import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Defaults{
	List<String> countryList;
    String defaultCountry;
    List<String> languageList;
    List<String> currencyList;
    String defaultCurrency;

//    private static final String LANGUAGE_COOKIE_NAME = "";

    public String getDefaultCountry() {
        return defaultCountry;
    }

    public String getDefaultCountryName() {
        return getCountryName(getCountryByCode(defaultCountry));
    }

	public List getCountryList(){
		return countryList;
	}

    public List getLanguageList(){
        return languageList;
    }

    public List getCurrencyList(){
		return currencyList;
	}

	public void setCountryList(List countryList){
        this.countryList = countryList;
    }

    public void setLanguageList(List languageList){
        this.languageList = languageList;
    }

	public void setCurrencyList(List currencyList){
		this.currencyList = currencyList;
	}

    public void setDefaultCountry(String countryCode) {
        this.defaultCountry = countryCode;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    private List<String> getList(List<String> list, int index) {
        List <String> result = new ArrayList<String>();
        for (String item: list) {
            result.add(getPartByIndex(item, index));
        }
        return result;
    }

    public List<String> getCountryCodesList() {
        return getList(countryList, 0);
    }
    public List<String> getLanguageCodesList() {
        return getList(languageList, 0);
    }

    private String getFullByPrefix(List<String> list, String prefix) {
        String result = null;
        for (String item : list) {
            if (item.startsWith(prefix)) {
                result = item;
                break;
            }
        }
        if (result == null) {
            WebDriverUtils.runtimeExceptionWithUrl("\"" + prefix + "\" code could not be found in list");
        }
        return result;
    }

    private String getCountryByCode(String countryCode) {
        return getFullByPrefix(countryList, countryCode);
    }

    public String getCurrencyByCountryCode(String countryCode) {
        return getCountryCurrency(getCountryByCode(countryCode));
    }

    private String getLanguageByCode(String languageCode) {
        return getFullByPrefix(languageList, languageCode);
    }

    public String getPhoneCodeByCountryCode(String countryCode) {
        return getCountryPhoneCode(getCountryByCode(countryCode));
    }

    public String getCountryNameByCountryCode(String countryCode) {
        return getCountryName(getCountryByCode(countryCode));
    }

    private String getRandomItemFromList(List<String> list) {
        int randomIndex = RandomUtils.generateRandomIntBetween(0, (list.size() - 1));
        return list.get(randomIndex);
    }

    public String getRandomCountryCode(){
        return getCountryCode(getRandomItemFromList(countryList));
    }

    public String getRandomLanguageCode() {
        return getLanguageCode(getRandomItemFromList(languageList));
    }

    private String getPartByIndex(String input, int partIndex) {
        String[] splitted = input.split("\\@");
        return splitted[partIndex];
    }

    private String getCountryCode(String countryFull) {
        return getPartByIndex(countryFull, 0);
    }

    private String getLanguageCode(String language) {
        return getPartByIndex(language, 0);
    }

    private String getCountryName(String countryFull) {
        return getPartByIndex(countryFull, 1);
    }

    private String getCountryPhoneCode(String country) {
        return getPartByIndex(country, 2);
    }

    private String getCountryCurrency(String country) {
        String currency = getPartByIndex(country, 3);
        List<String> allowedCurrencies = getCurrencyList();
        if (allowedCurrencies.contains(currency)) {
            return currency;
        } else
            return getDefaultCurrency();
    }

//    public void addLanguageCookie(String languageCode) {
//        WebDriverUtils.addCookie(LANGUAGE_COOKIE_NAME, languageCode, "domain", "/", new Date(115,1,1));
//    }

    public void getLanguageValue(String languageCode) {
        getPartByIndex(getLanguageByCode(languageCode), 2);
    }

//    public boolean languageCookieExists() {
//        return WebDriverUtils.isCookieExists(LANGUAGE_COOKIE_NAME);
//    }
//
//    public String getLanguageCookieValue() {
//        return WebDriverUtils.getCookieValue(LANGUAGE_COOKIE_NAME);
//    }
}