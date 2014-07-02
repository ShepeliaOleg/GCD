package springConstructors;

import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

public class Defaults{
	List<String> countryFullList;
	List<String> currencyList;
    String defaultCountryCode;

    public String getDefaultCountryCode() {
        return defaultCountryCode;
    }

    public String getDefaultCountryName() {
        return getCountryName(getFullInfoByCountryCode(defaultCountryCode));
    }

	public List getCountryFullList(){
		return countryFullList;
	}

    public List getCurrencyList(){
		return currencyList;
	}

	public void setCountryFullList(List countryFullList){
        this.countryFullList = countryFullList;
    }

	public void setCurrencyList(List currencyList){
		this.currencyList = currencyList;
	}

    public void setDefaultCountryCode(String countryCode) {
        this.defaultCountryCode = countryCode;
    }

    public List<String> getCountriesCodesList() {
        List <String> countries = new ArrayList<String>();
         for (String countryFullInfo:countryFullList) {
             countries.add(getCountryCode(countryFullInfo));
         }
        return countries;
    }

    private String getFullInfoByCountryCode(String countryCode) {
        String countryFullInfo = null;
        for (String countryFull:countryFullList) {
            if (countryFull.startsWith(countryCode)) {
                countryFullInfo = countryFull;
                break;
            }
        }
        if (countryFullInfo == null) {
			WebDriverUtils.runtimeExceptionWithLogs("\"" +countryCode + "\" country code could not be found in countries list");
        }else{
			return countryFullInfo;
		}
    	return countryFullInfo;
    }

    public String getPhoneCodeByCountryCode(String countryCode) {
        String countryFullInfo = getFullInfoByCountryCode(countryCode);

        return getCountryPhoneCode(countryFullInfo);
    }

    public String getCountryNameByCountryCode(String countryCode) {
        String countryFullInfo = getFullInfoByCountryCode(countryCode);

        return getCountryName(countryFullInfo);
    }

    public String getRandomCountryCode(){
        int randomIndex = RandomUtils.generateRandomIntBetween(0, (countryFullList.size() - 1));
        String countryFullInfo = countryFullList.get(randomIndex);

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