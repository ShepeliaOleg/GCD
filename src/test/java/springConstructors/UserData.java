package springConstructors;

import enums.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import utils.NavigationUtils;
import utils.PortalUtils;
import utils.RandomUtils;
import utils.core.DataContainer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserData{

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

    @Autowired
    @Qualifier("genderValidationRule")
    private ValidationRule genderValidationRule;

    @Autowired
    @Qualifier("titleValidationRule")
    private ValidationRule titleValidationRule;

    @Autowired
    @Qualifier("emailValidationRule")
    private ValidationRule emailValidationRule;

    @Autowired
    @Qualifier("mobileCountryPhoneCodeValidationRule")
    private ValidationRule mobileCountryPhoneCodeValidationRule;

    @Autowired
    @Qualifier("mobilePhoneValidationRule")
    private ValidationRule mobilePhoneValidationRule;

	@Autowired
	@Qualifier("countryPhoneCodeValidationRule")
	private ValidationRule countryPhoneCodeValidationRule;

    @Autowired
    @Qualifier("phoneValidationRule")
    private ValidationRule phoneValidationRule;

	@Autowired
	@Qualifier("addressValidationRule")
	private ValidationRule addressValidationRule;

	@Autowired
	@Qualifier("houseValidationRule")
	private ValidationRule houseValidationRule;

	@Autowired
	@Qualifier("postcodeValidationRule")
	private ValidationRule postcodeValidationRule;

	@Autowired
	@Qualifier("cityValidationRule")
	private ValidationRule cityValidationRule;

    private String gender;
    private String title;
	private String firstName;
	private String lastName;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String email;
	private String country;
	private String city;
	private String address;
	private String address2;
	private String house;
	private String postCode;
	private String phoneAreaCode;
	private String phone;
	private String mobileAreaCode;
	private String mobile;
	private String username;
    private String profileUsername;
	private String password;
	private String verificationQuestion;
	private String verificationAnswer;
	private String currency;

    public ArrayList<String> getRegisterData () {
        ArrayList<String> data=new ArrayList<>();
        data.add(getTitle().toLowerCase());
        data.add(getFormattedGender());
        data.add(getFirstName());
        data.add(getLastName());
        data.add(getBirthYear()+"-"+getBirthMonth()+"-"+getBirthDay());
        data.add(getEmail());
        data.add(getCountry());
        data.add(getCity());
        data.add(getFullAddress());
        data.add(getPostCode().toLowerCase());
        data.add(getPhoneAreaCode() + getPhone());
        //CHANGE 23/03/15 by VADIM
        //? data.add(getMobileAreaCode() + getMobile());
        data.add(getUsername());
//        data.add(getPassword());
//        data.add(getVerificationQuestion());
//        data.add(getVerificationAnswer());
        data.add(getCurrencyName());
        return data;
    }

    public ArrayList<String> getUMDData () {
        ArrayList<String> data=new ArrayList<>();
        data.add(getTitle().toLowerCase());
        data.add(getFirstName());
        data.add(getLastName());
        data.add(getEmail());
        data.add(getCountry());
        data.add(getCity());
        data.add(getFullAddress());
        data.add(getPostCode().toLowerCase());
        data.add(getPhoneAreaCode() + getPhone());
//        data.add(getMobileAreaCode() + getMobile());
        return data;
    }

    private String getFormattedGender(){
        String gender = getGender();
        if(gender.equals("M")){
            return "MALE";
        }else {
            return "FEMALE";
        }
    }

    public UserData getRegisteredUserData(){
        UserData clonedUserData=new UserData();
        clonedUserData.title=           getTitle();
        clonedUserData.firstName=       getFirstName();
        clonedUserData.lastName=        getLastName();
        clonedUserData.birthDay=        getBirthDay();
        clonedUserData.birthMonth=      getBirthMonth();
        clonedUserData.birthYear=       getBirthYear();
        clonedUserData.email=           getEmail();
        clonedUserData.country=         getCountry();
        clonedUserData.city=            getCity();
        clonedUserData.address=         getAddress();
        clonedUserData.address2=        getAddress2();
        clonedUserData.house=           getHouse();
        clonedUserData.postCode=        getPostCode();
        clonedUserData.phoneAreaCode=   getPhoneAreaCode();
        clonedUserData.phone=           getPhone();
        clonedUserData.mobileAreaCode=  getMobileAreaCode();
        clonedUserData.mobile=          getMobile();
        clonedUserData.username=        getUsername();
        clonedUserData.password=        getPassword();
        clonedUserData.verificationQuestion=getVerificationQuestion();
        clonedUserData.verificationAnswer=getVerificationAnswer();
        clonedUserData.currency=        getCurrency();
        clonedUserData.gender=          getGender();
        return clonedUserData;
    }

    public UserData getRegisteredUserDataWithProfileID() {
        UserData userData = getRegisteredUserData();
        userData.setUsername(getProfileUsername());
        return userData;
    }

    public UserData getCardUserData() {
        UserData userData = getRegisteredUserData();
//        userData.setUsername("greesnm");
        userData.setUsername("carduser");
        userData.setCurrency("USD");
        return userData;
    }

    public UserData getLastUsedCCUserData() {
        UserData userData = getRegisteredUserData();
        userData.setUsername("lastusedpm");
        return userData;
    }

    public UserData getFrozenUserData() {
        UserData userData = getRandomUserData();
        userData.setFirstName("freezeMe");
        return userData;
    }

    public UserData getBonusUserData() {
        UserData userData = getRegisteredUserData();
        userData.setUsername("okbonus");
        return userData;
    }

    public UserData getFreeSpinUserData() {
        UserData userData = getInternalRandomUserData();
        userData.setUsername("freespins");
        return userData;
    }

    public UserData getRandomUserData() {
        UserData userData = getRegisteredUserData();
        userData.setGender(RandomUtils.getRandomElementsFromList(getGenderList(), 1).get(0));
        userData.setTitle(RandomUtils.getRandomElementsFromList(getTitleList(), 1).get(0));
        //userData.setCurrency(RandomUtils.getRandomElementsFromList(getCurrencyList(), 1).get(0));
        userData.setCurrency(userData.getCurrency());
        userData.setUsername(usernameValidationRule.generateValidString().toUpperCase());
        //userData.setEmail(emailValidationRule.generateValidString());
        userData.setEmail(userData.getEmail());
        //userData.setCountry(DataContainer.getDefaults().getRandomCountryCode());
        userData.setCountry(userData.getCountry());
        userData.setCity(cityValidationRule.generateValidString());
        userData.setPostCode(postcodeValidationRule.generateValidString());
        userData.setAddress(addressValidationRule.generateValidString());
        userData.setHouse(houseValidationRule.generateValidString());
        userData.setPhone(phoneValidationRule.generateValidString());
        userData.setPhoneAreaCode(countryPhoneCodeValidationRule.generateValidString());
        userData.setMobile(mobilePhoneValidationRule.generateValidString());
        userData.setMobileAreaCode(mobileCountryPhoneCodeValidationRule.generateValidString());
        return userData;
    }

    public UserData getInternalRandomUserData() {
        UserData userData = getRandomUserData();
        userData.setEmail("test@playtech.com");
        return userData;
    }

    private List<String> getGenderList(){
        return Arrays.asList(genderValidationRule.getRegexp().split("@"));
    }

    private List<String> getCurrencyList(){
        return DataContainer.getDefaults().getCurrencyCodesList();
    }

    private List<String> getTitleList(){
        return Arrays.asList(titleValidationRule.getRegexp().split("@"));
    }

    public String getCurrency(){
        return currency;
    }

    public String getCurrencyName(){
        return getCurrency().split("@")[0];
    }

    public String getCurrencySign(){
        String fullCurrency = DataContainer.getDefaults().getCurrencyByCode(getCurrency());
        String[] splittedCurrency = fullCurrency.split("@");
        if(splittedCurrency.length>1){
            return splittedCurrency[1];
        }else {
            return splittedCurrency[0];
        }
    }

    public void setCurrency(String currency){
        this.currency=currency;
    }

    public String getVerificationAnswer(){
        return verificationAnswer;
    }

    public void setVerificationAnswer(String verificationAnswer){
        this.verificationAnswer=verificationAnswer;
    }

    public String getVerificationQuestion(){
        return verificationQuestion;
    }

    public void setVerificationQuestion(String verificationQuestion){
        this.verificationQuestion=verificationQuestion;
    }

    public String getPassword(){
        return password;
    }

    public String getPasswordMixedcase() {
        int half = getPassword().length()/2;
        return  getPassword().toLowerCase().substring(0, half)+getPassword().toUpperCase().substring(half);
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String getUsername(){
        return username;
    }

    public String getUsernameUppercase() {
        return getUsername().toUpperCase();
    }

    public String getUsernameLowercase() {
        return getUsername().toLowerCase();
    }

    public String getUsernameMixedcase() {
        int half = getUsername().length()/2;
        return  getUsernameLowercase().substring(0,half)+getUsernameUppercase().substring(half);
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getProfileUsername(){
        return profileUsername;
    }

    public void setProfileUsername(String profileUsername){
        this.profileUsername=profileUsername;
    }

    public String getMobile(){
        return mobile;
    }

    public void setMobile(String mobile){
        this.mobile=mobile;
    }

    public String getMobileAreaCode(){
        return mobileAreaCode;
    }

    public void setMobileAreaCode(String mobileAreaCode){
        this.mobileAreaCode=mobileAreaCode;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getPhoneAreaCode(){
        return phoneAreaCode;
    }

    public void setPhoneAreaCode(String phoneAreaCode){
        this.phoneAreaCode=phoneAreaCode;
    }

    public String getPostCode(){
        return postCode;
    }

    public void setPostCode(String postCode){
        this.postCode=postCode;
    }

    public String getHouse(){
        return house;
    }

    public void setHouse(String house){
        this.house=house;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public String getAddress2(){
        return address2;
    }

    public void setAddress2(String address){
        this.address2=address;
    }

    public String getFullAddress (){
        String fullAddress = getAddress();
//        if (getHouse()!=null){
//            fullAddress =  getHouse().concat(" ").concat(fullAddress);
//        }
//        if (getAddress2()!=null){
//            fullAddress = fullAddress.concat(" ").concat(getAddress2());
//        }
        return fullAddress;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city=city;
    }

    public String getCountry(){
        return country;
    }

    public void setCountry(String country){
        this.country=country;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public String getBirthYear(){
        return birthYear;
    }

    public void setBirthYear(String birthYear){
        this.birthYear=birthYear;
    }

    public String getBirthMonth(){
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth){
        this.birthMonth=birthMonth;
    }

    public String getBirthDay(){
        return birthDay;
    }

    public void setBirthDay(String birthDay){
        this.birthDay=birthDay;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender=gender;
    }
}
