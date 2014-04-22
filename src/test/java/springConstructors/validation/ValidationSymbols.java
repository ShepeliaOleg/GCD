package springConstructors.validation;

/**
 * User: ivan
 * Date: 1/8/13
 */

public class ValidationSymbols{
	private String symbols;
	private int minLength;
	private int maxLength;

	public String getSymbols(){
		return symbols;
	}

	public void setSymbols(String symbols){
		this.symbols=symbols;
	}

	public int getMinLength(){
		return minLength;
	}

	public void setMinLength(int minLength){
		this.minLength=minLength;
	}

	public int getMaxLength(){
		return maxLength;
	}

	public void setMaxLength(int maxLength){
		this.maxLength=maxLength;
	}

}
