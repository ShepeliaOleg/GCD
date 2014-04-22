package springConstructors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import springConstructors.validation.ValidationRule;

import java.util.ArrayList;

public class UserData{

	@Autowired
	@Qualifier("usernameValidationRule")
	private ValidationRule usernameValidationRule;

	@Autowired
	@Qualifier("emailValidationRule")
	private ValidationRule emailValidationRule;

	@Autowired
	@Qualifier("phoneValidationRule")
	private ValidationRule phoneValidationRule;

	@Autowired
	@Qualifier("countryPhoneCodeValidationRule")
	private ValidationRule countryPhoneCodeValidationRule;

	@Autowired
	@Qualifier("fullPhoneValidationRule")
	private ValidationRule fullPhoneValidationRule;

	@Autowired
	@Qualifier("fullAddressValidationRule")
	private ValidationRule fullAddressValidationRule;

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

	@Autowired
	@Qualifier("nameValidationRule")
	private ValidationRule nameValidationRule;

	@Autowired
	@Qualifier("defaults")
	private Defaults countryList;

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
		return username;
	}

	public void setUsername(String username){
		this.username=username;
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
        if ((getHouse().equals("")==false)&& (getHouse().equals(null)==false)){
            fullAddress =  getHouse().concat(" ").concat(fullAddress);
        }
        if ((getAddress2().equals("")==false) && (getAddress2().equals(null)==false)){
            fullAddress = fullAddress.concat(" ").concat(getAddress2());
        }
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

	public UserData cloneUserData(){
		UserData clonedUserData=new UserData();

		clonedUserData.title=title;
		clonedUserData.firstName=firstName;
		clonedUserData.lastName=lastName;
		clonedUserData.birthDay=birthDay;
		clonedUserData.birthMonth=birthMonth;
		clonedUserData.birthYear=birthYear;
		clonedUserData.email=email;
		clonedUserData.country=country;
		clonedUserData.city=city;
		clonedUserData.address=address;
        clonedUserData.address2=address2;
		clonedUserData.house=house;
		clonedUserData.postCode=postCode;
		clonedUserData.phoneAreaCode=phoneAreaCode;
		clonedUserData.phone=phone;
		clonedUserData.mobileAreaCode=mobileAreaCode;
		clonedUserData.mobile=mobile;
		clonedUserData.username=username;
		clonedUserData.password=password;
		clonedUserData.verificationQuestion=verificationQuestion;
		clonedUserData.verificationAnswer=verificationAnswer;
		clonedUserData.currency=currency;
		clonedUserData.coupon=coupon;
		clonedUserData.balanceMain=balanceMain;
		clonedUserData.balanceTotal=balanceTotal;

		return clonedUserData;
	}

    public ArrayList<String> getRegisterData () {
        ArrayList<String> data=new ArrayList<String>();
        data.add(getTitle().toLowerCase());
        data.add("Male");
        data.add(getFirstName());
        data.add(getLastName());
        data.add(getNumericBirthDate());
        data.add(getEmail());
        data.add(getCountry());
        data.add(getCity());
        data.add(getFullAddress());
        data.add(getPostCode().toLowerCase());
        data.add(getPhoneAreaCode() + getPhone());
        data.add(getMobileAreaCode() + getMobile());
        data.add(getUsername());
        data.add(getPassword());
        data.add(getVerificationQuestion());
        data.add(getVerificationAnswer());
        data.add(getCurrency());
        return data;
    }

	private String getNumericBirthDate(){
		String month=null;
		if(getBirthMonth().equals("January")){
			month="01";
		}else if(getBirthMonth().equals("February")){
			month="02";
		}else if(getBirthMonth().equals("March")){
			month="03";
		}else if(getBirthMonth().equals("April")){
			month="04";
		}else if(getBirthMonth().equals("May")){
			month="05";
		}else if(getBirthMonth().equals("June")){
			month="06";
		}else if(getBirthMonth().equals("July")){
			month="07";
		}else if(getBirthMonth().equals("August")){
			month="08";
		}else if(getBirthMonth().equals("September")){
			month="09";
		}else if(getBirthMonth().equals("October")){
			month="10";
		}else if(getBirthMonth().equals("November")){
			month="11";
		}else if(getBirthMonth().equals("December")){
			month="12";
		}
		return getBirthYear() + "-" + month + "-" + getBirthDay();
	}

	public UserData getRegisteredUserData() {
		return cloneUserData();
	}

	public UserData getForgotPasswordUserData() {
		UserData userData = getRegisteredUserData();
		userData.setUsername("autoForgot");
		return userData;
	}

    public UserData getRandomUserData() {
        // get userdata of registered user
        UserData userData = getRegisteredUserData();
        // generate and change unique fields
        userData.setUsername(usernameValidationRule.generateValidString());
        userData.setCountry(countryList.getRandomCountryName());
        userData.setCity(cityValidationRule.generateValidString());
        userData.setPostCode(postcodeValidationRule.generateValidString());
        userData.setAddress(addressValidationRule.generateValidString());
        userData.setAddress2(addressValidationRule.generateValidString());
        userData.setHouse(houseValidationRule.generateValidString());
        userData.setPhone(phoneValidationRule.generateValidString());
        userData.setPhoneAreaCode(countryPhoneCodeValidationRule.generateValidString());
        userData.setMobile(phoneValidationRule.generateValidString());
        userData.setMobileAreaCode(countryPhoneCodeValidationRule.generateValidString());

        return userData;
    }

//	public User generateUser() {
//		// get userdata of registered user
//		UserData userData = getRegisteredUser().getUserData().cloneUserData();
//		// generate and change unique fields
//		userData.setUsername(RandomUtils.generateString("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", 9));
//		//
//		User newUser = new User();
//		newUser.setUserData(userData);
//
//		return newUser;
//	}
}
