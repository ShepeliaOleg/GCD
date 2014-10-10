package springConstructors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import utils.WebDriverUtils;
import utils.core.WebDriverObject;

import java.util.ArrayList;

public class UserData extends WebDriverObject{

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

    @Autowired
    @Qualifier("usernameDesktopValidationRule")
    private ValidationRule usernameDesktopValidationRule;

	@Autowired
	@Qualifier("phoneValidationRule")
	private ValidationRule phoneValidationRule;

    @Autowired
    @Qualifier("mobilePhoneValidationRule")
    private ValidationRule mobilePhoneValidationRule;

    @Autowired
    @Qualifier("mobileCountryPhoneCodeValidationRule")
    private ValidationRule mobileCountryPhoneCodeValidationRule;

	@Autowired
	@Qualifier("countryPhoneCodeValidationRule")
	private ValidationRule countryPhoneCodeValidationRule;

	@Autowired
	@Qualifier("addressValidationRule")
	private ValidationRule addressValidationRule;

    @Autowired
    @Qualifier("addressDesktopValidationRule")
    private ValidationRule addressDesktopValidationRule;

	@Autowired
	@Qualifier("houseValidationRule")
	private ValidationRule houseValidationRule;

	@Autowired
	@Qualifier("postcodeValidationRule")
	private ValidationRule postcodeValidationRule;

    @Autowired
    @Qualifier("postcodeDesktopValidationRule")
    private ValidationRule postcodeDesktopValidationRule;

	@Autowired
	@Qualifier("cityValidationRule")
	private ValidationRule cityValidationRule;

    @Autowired
    @Qualifier("cityDesktopValidationRule")
    private ValidationRule cityDesktopValidationRule;

	@Autowired
	@Qualifier("defaults")
	private Defaults countryList;

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
    private String defaultUsername;
    private String profileUsername;
    private String defaultProfileUsername;
	private String password;
	private String verificationQuestion;
	private String verificationAnswer;
	private String currency;
	private String coupon;
	private String balanceMain;
	private String balanceTotal;

    public String getCoupon(){
		return coupon;
	}

	public void setCoupon(String coupon){
		this.coupon=coupon;
	}

	public String getCurrency(){
		return currency;
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

	public void setPassword(String password){
		this.password=password;
	}

	public String getUsername(){
        if(username!=null){
            return username;
        }else{
            return getDefaultUsername();
        }
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

    public String getDefaultUsername(){
        return defaultUsername;
    }

    public void setDefaultUsername(String username){
        this.defaultUsername=username;
    }

    public String getProfileUsername(){
        if(profileUsername!=null) {
            return profileUsername;
        }else {
            return getDefaultProfileUsername();
        }
    }

    public void setProfileUsername(String profileUsername){
        this.profileUsername=profileUsername;
    }

    public String getDefaultProfileUsername(){
        return defaultProfileUsername;
    }

    public void setDefaultProfileUsername(String defaultProfileUsername){
        this.defaultProfileUsername=defaultProfileUsername;
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

	public void setBalanceMain(String balanceMain){
		this.balanceMain=balanceMain;
	}

	public String getBalanceMain(){
		return balanceMain;
	}

	public void setBalanceTotal(String balanceTotal){
		this.balanceTotal=balanceTotal;
	}

	public String getBalanceTotal(){
		return balanceTotal;
	}

	private UserData cloneUserData(){
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
		clonedUserData.coupon=          getCoupon();
		clonedUserData.balanceMain=     getBalanceMain();
		clonedUserData.balanceTotal=    getBalanceTotal();
        clonedUserData.gender=          getGender();
		return clonedUserData;
	}

    public ArrayList<String> getRegisterData () {
        ArrayList<String> data=new ArrayList<>();
        data.add(getTitle().toLowerCase());
        data.add(getFormattedGender());
        data.add(getFirstName());
        data.add(getLastName());
        data.add(getFormattedBirthDate());
        data.add(getEmail());
        data.add(getCountry());
        data.add(getCity());
        data.add(getFullAddress());
        data.add(getPostCode().toLowerCase());
        data.add(getPhoneAreaCode() + getPhone());
//        data.add(getMobileAreaCode() + getMobile());
        data.add(getUsername());
//        data.add(getPassword());
        data.add(getVerificationQuestion());
        data.add(getVerificationAnswer());
        data.add(getCurrency());
        return data;
    }

	private String getFormattedBirthDate(){
		return getBirthYear()+"-"+getBirthMonth()+"-"+getBirthDay();
	}

    private String getFormattedGender(){
        String gender = getGender();
        if(gender.equals("M")){
            return "MALE";
        }else {
            return "FEMALE";
        }
    }

	public UserData getRegisteredUserData() {
		return cloneUserData();
	}

    public UserData getRegisteredUserDataWithProfileID() {
        UserData userData = getRegisteredUserData();
        userData.setUsername(getProfileUsername());
        return userData;
    }

	public UserData getForgotPasswordUserData() {
		UserData userData = getRegisteredUserData();
		userData.setUsername("autoForgot");
		return userData;
	}

    public UserData getRandomUserData() {
        ValidationRule validationRule;
        UserData userData = getRegisteredUserData();
        if(platform.equals(PLATFORM_DESKTOP)){
            validationRule = usernameDesktopValidationRule;
        }else {
            validationRule = usernameValidationRule;
        }
        userData.setUsername(validationRule.generateValidString());
        userData.setCountry(countryList.getRandomCountryCode());
        if(platform.equals(PLATFORM_DESKTOP)){
            validationRule = cityDesktopValidationRule;
        }else {
            validationRule = cityValidationRule;
        }
        userData.setCity(validationRule.generateValidString());
        if(platform.equals(PLATFORM_DESKTOP)){
            validationRule = postcodeDesktopValidationRule;
        }else {
            validationRule = postcodeValidationRule;
        }
        userData.setPostCode(validationRule.generateValidString());
        if(platform.equals(PLATFORM_DESKTOP)){
            validationRule = addressDesktopValidationRule;
        }else {
            validationRule = addressValidationRule;
        }
        userData.setAddress(validationRule.generateValidString());
        userData.setHouse(houseValidationRule.generateValidString());
        userData.setPhone(phoneValidationRule.generateValidString());
        userData.setPhoneAreaCode(countryPhoneCodeValidationRule.generateValidString());
        userData.setMobile(mobilePhoneValidationRule.generateValidString());
        userData.setMobileAreaCode(mobileCountryPhoneCodeValidationRule.generateValidString());

        return userData;
    }

    public String print(){
        return "<div>"+getTitle()+"</div><div>"+getGender()+"</div><div>"+getFirstName()+"</div><div>"+getLastName()+"</div><div>"
                +getCountry()+"</div><div>"+getFullAddress()+"</div><div>"+getCity()+"</div><div>"+getPostCode()+"</div><div>"
                +getPhoneAreaCode()+getPhone()+"</div><div>"+getMobileAreaCode()+getMobile()+"</div><div>"+getEmail()+"</div>";
    }

}
