package springConstructors;

import utils.RandomUtils;
import utils.validation.RegexNode;
import utils.validation.ValidationUtils;

import java.util.ArrayList;

public class ValidationRule {
    private String regexp;
    private int minLength;
    private int maxLength;

    private String tooltipPositive;
    private String tooltipNegativeEmpty;
    private String tooltipNegativeShort;
    private String tooltipNegativeLong;
    private String tooltipNegativeInvalid;

    private String isMandatory;

    public String getTooltipPositive() {
        return tooltipPositive;
    }

    public String getTooltipNegativeEmpty() {
        return tooltipNegativeEmpty;
    }

    public String getTooltipNegativeShort() {
        return tooltipNegativeShort;
    }

    public String getTooltipNegativeLong() {
        return tooltipNegativeLong;
    }

    public String getTooltipNegativeInvalid() {
        return tooltipNegativeInvalid;
    }

    public void setTooltipPositive(String tooltipPositive) {
        this.tooltipPositive = tooltipPositive;
    }

    public void setTooltipNegativeEmpty(String tooltipNegativeEmpty) {
        this.tooltipNegativeEmpty = tooltipNegativeEmpty;
    }

    public void setTooltipNegativeShort(String tooltipNegativeShort) {
        this.tooltipNegativeShort = tooltipNegativeShort;
    }

    public void setTooltipNegativeLong(String tooltipNegativeLong) {
        this.tooltipNegativeLong = tooltipNegativeLong;
    }

    public void setTooltipNegativeInvalid(String tooltipNegativeInvalid) {
        this.tooltipNegativeInvalid = tooltipNegativeInvalid;
    }

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getIsMandatory() {
        return isMandatory;
    }

    public void setIsMandatory(String isMandatory) {
        this.isMandatory = isMandatory;
    }

    public String getAllNotAllowedSymbols() {
        return RandomUtils.getAllExcept(getAllAllowedSymbols());
    }

    public String generateValidString() {
        return RandomUtils.generateStringByRegexp(regexp);
    }

    public String generateValidString(char a) {
        int counter=0;
        String symbols;
        String character = String.valueOf(a);
        ArrayList<RegexNode> nodes = ValidationUtils.splitToNodes(regexp);
        for(RegexNode node:nodes){
            if(node.getSymbols().contains(String.valueOf(getValidChar()))&&counter==0){
                node.setMin(node.getMin()+2);
                counter++;
            }
            symbols = node.getSymbols();
            if(character.equals(" ")){
                if(symbols.equals(" ")){
                    node.setMin(node.getMax());
                }else {
                    node.setMax(node.getMin());
                }
            }else{
                node.setMax(node.getMin());
                if(!symbols.equals(".")&&!symbols.equals("@")&&!symbols.equals("+")){
                    node.setSymbols(character);
                }
            }
        }
        return RandomUtils.generateStringByRegexp(ValidationUtils.generateRegExpFromNodes(nodes));
    }

    public String generateValidMinLengthUnifiedString(){
        return generateValidString(getValidChar());
    }

    public char getValidChar(){
        return regexp.charAt(4);
    }

    public String generateValidStringOverMaxSymbols(){
        String allAllowedSymbols = getAllAllowedSymbols();
        return generateValidStringWithMaxSymbols()+allAllowedSymbols.substring(allAllowedSymbols.length() - 1);
    }

    private String generateValidStringWithMaxSymbols() {
        ArrayList<RegexNode> nodes = ValidationUtils.splitToNodes(regexp);
        for(RegexNode node:nodes){
            node.setMin(node.getMax());
        }
        return RandomUtils.generateStringByRegexp(ValidationUtils.generateRegExpFromNodes(nodes));
    }

    public String generateValidStringWithMinSymbols() {
        String tempRegexp = regexp;
        if(tempRegexp.contains("[@]")){
            tempRegexp = tempRegexp.replace("{1,6}", "{0,6}");
        }
        ArrayList<RegexNode> nodes = ValidationUtils.splitToNodes(tempRegexp);
        for(RegexNode node:nodes){
            node.setMax(node.getMin());
        }
        return RandomUtils.generateStringByRegexp(ValidationUtils.generateRegExpFromNodes(nodes));
    }

    public String getAllAllowedSymbols() {
        // remove all quantifiers in {} with brackets itself
        String string = regexp.replaceAll("\\{[0-9]*[,][0-9]*\\}", "");
        // remove all [] brackets
        string = string.replaceAll("\\[|\\]", "");
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<Character> filteredCharacters = new ArrayList<>();
        for(char a : string.toCharArray()){
            characters.add(a);
        }
        for(char a : characters){
            if(!filteredCharacters.contains(a)){
                filteredCharacters.add(a);
            }
        }
        string = "";
        for(char a : filteredCharacters){
            string+=a;
        }
        return string;
    }

    public String[] getDropdownValues() {
        return getRegexp().split("@");
    }
}
