package springConstructors;

import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

public class Defaults{
	List<String> countryList;
    String defaultCountry;
    List<String> languageList;
    List<String> currencyList;

    public String getDefaultCountry() {
        return defaultCountry;
    }

    public String getDefaultCountryName() {
        return getCountryName(getCountryInfoByCode(defaultCountry));
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

    public List<String> getCountryCodesList() {
        List <String> countries = new ArrayList<String>();
         for (String countryFullInfo: countryList) {
             countries.add(getCountryCode(countryFullInfo));
         }
        return countries;
    }

    private String getCountryInfoByCode(String countryCode) {
        String countryFullInfo = null;
        for (String countryFull: countryList) {
            if (countryFull.startsWith(countryCode)) {
                countryFullInfo = countryFull;
                break;
            }
        }
        if (countryFullInfo == null) {
			WebDriverUtils.runtimeExceptionWithUrl("\"" + countryCode + "\" country code could not be found in countries list");
        }else{
			return countryFullInfo;
		}
    	return countryFullInfo;
    }

    public String getPhoneCodeByCountryCode(String countryCode) {
        String countryFullInfo = getCountryInfoByCode(countryCode);

        return getCountryPhoneCode(countryFullInfo);
    }

    public String getCountryNameByCountryCode(String countryCode) {
        String countryFullInfo = getCountryInfoByCode(countryCode);

        return getCountryName(countryFullInfo);
    }

    public String getRandomCountryCode(){
        int randomIndex = RandomUtils.generateRandomIntBetween(0, (countryList.size() - 1));
        String countryFullInfo = countryList.get(randomIndex);

        return getCountryCode(countryFullInfo);
    }

    private String getPartOfFullCountry(String input, int partIndex) {
        String[] splitted = input.split("\\@");
        return splitted[partIndex];
    }

    private String getCountryCode(String countryFullInfo) {
        return getPartOfFullCountry(countryFullInfo, 0);
    }

    private String getCountryName(String countryFullInfo) {
        return getPartOfFullCountry(countryFullInfo, 1);
    }

    private String getCountryPhoneCode(String countryFullInfo) {
        return getPartOfFullCountry(countryFullInfo, 2);
    }
}