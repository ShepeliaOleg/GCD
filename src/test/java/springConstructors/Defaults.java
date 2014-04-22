package springConstructors;

import utils.RandomUtils;
import utils.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

public class Defaults{
	List<String> countryFullList;
	List<String> currencyList;
    String defaultCountry;

    public String getDefaultCountry() {
        return defaultCountry;
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

    public void setDefaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public List<String> getCountryList() {
        List <String> countries = new ArrayList<String>();
         for (String countryFullInfo:countryFullList) {
             countries.add(getCountryName(countryFullInfo));
         }
        return countries;
    }

    private String getFullInfoByCountryName(String countryName) {
        String countryFullInfo = null;
        for (String countryFull:countryFullList) {
            if (countryFull.startsWith(countryName)) {
                countryFullInfo = countryFull;
                break;
            }
        }
        if (countryFullInfo == null) {
			WebDriverUtils.runtimeExceptionWithLogs(countryName + " country could not be found in countries list");
        }else{
			return countryFullInfo;
		}
    	return countryFullInfo;
    }

    public String getPhoneCodeByCountryName(String countryName) {
        String countryFullInfo = getFullInfoByCountryName(countryName);

        return getCountryPhoneCode(countryFullInfo);
    }

    public String getRandomCountryName(){
        int randomIndex = RandomUtils.generateRandomIntBetween(0, (countryFullList.size() - 1));
        String countryFullInfo = countryFullList.get(randomIndex);

        return getCountryName(countryFullInfo);
    }

    private String getPartOfFullCountry(String input, int partIndex) {
        String[] splitted = input.split("\\@");
        return splitted[partIndex];
    }

    private String getCountryName(String countryFullInfo) {
        return getPartOfFullCountry(countryFullInfo, 0);
    }

    private String getCountryPhoneCode(String countryFullInfo) {
        return getPartOfFullCountry(countryFullInfo, 1);
}   }