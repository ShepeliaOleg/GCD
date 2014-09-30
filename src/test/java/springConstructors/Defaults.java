package springConstructors;

import utils.RandomUtils;
import utils.WebDriverUtils;
import java.util.ArrayList;
import java.util.List;

public class Defaults{
	List<String> countryList;
    List<String> currencyList;
    List<String> languageList;
    String defaultCountry;
    String defaultCurrency;
    String defaultLanguage;

    public String getDefaultCountry() {
        return defaultCountry;
    }

    public String getDefaultCountryName() {
        return getCountryName(getCountryByCode(getDefaultCountry()));
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

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    private List<String> getList(List<String> list, int index) {
        List <String> result = new ArrayList<String>();
        for (String item: list) {
            result.add(getPartByIndex(item, index));
        }
        return result;
    }

    public List<String> getCountryCodesList() {
        return getList(getCountryList(), 0);
    }
    public List<String> getLanguageCodesList() {
        return getList(getLanguageList(), 0);
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
        return getCountryCurrencyLogical(getCountryByCode(countryCode));
    }

    public String getLanguageUrlByLanguageCode(String languageCode) {
        return getLanguageUrlCodeLogical(getLanguageByCode(languageCode));
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
        return getCountryCode(getRandomItemFromList(getCountryList()));
    }

    public String getRandomLanguageCode() {
        return getLanguageCode(getRandomItemFromList(getLanguageList()));
    }

    private String getPartByIndex(String input, int partIndex) {
        String[] splitted = input.split("\\@");
        return splitted[partIndex];
    }

    private String getCountryCode(String country) {
        return getPartByIndex(country, 0);
    }

    private String getCountryName(String country) {
        return getPartByIndex(country, 1);
    }

    private String getCountryPhoneCode(String country) {
        return getPartByIndex(country, 2);
    }

    private String getCountryCurrency(String country) {
        return getPartByIndex(country, 3);
    }

    private String getLanguageCode(String language) {
        return getPartByIndex(language, 0);
    }

    private String getLanguageName(String language) {
        return getPartByIndex(language, 1);
    }

    private String getLanguageUrlCode(String language) {
        return getPartByIndex(language, 2);
    }

    public String getLanguageNameByCode(String languageCode) {
        return getLanguageName(getLanguageByCode(languageCode));
    }

    private String getCountryCurrencyLogical(String country) {
        String currency = getCountryCurrency(country);
        List<String> allowedCurrencies = getCurrencyList();
        if (allowedCurrencies.contains(currency)) {
            return currency;
        } else
            return getDefaultCurrency();
    }

    private String getLanguageUrlCodeLogical(String language) {
        String urlLanguageCode = getLanguageUrlCode(language);
        String languageCode    = getLanguageCode(language);
        if (languageCode.equals(getDefaultLanguage())) {
            return "";
        } else if (urlLanguageCode.equals("none")) {
            return languageCode.substring(0,2) + "/";
        } else {
            return urlLanguageCode + "/";
        }
    }

}