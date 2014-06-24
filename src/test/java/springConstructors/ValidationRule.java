package springConstructors;

import utils.RandomUtils;

public class ValidationRule {
    private String regexp;
    private int minLength;
    private int maxLength;

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

    public String getAllNotAllowedSymbols() {
        return RandomUtils.getAllExcept(getAllAllowedSymbols());
    }

    public String generateValidString() {
        return RandomUtils.generateStringByRegexp(regexp);
    }

    public String generateValidString(int length) {

        String regexpTemp = extendRegExpToMax (regexp);
        int startQuantifierIndex = regexpTemp.lastIndexOf("{") + 1;
        int endQuantifierIndex = regexpTemp.lastIndexOf("}");


        String quantifier = regexpTemp.substring(startQuantifierIndex, endQuantifierIndex);
        String[] numbers = quantifier.split(",");
        int min = Integer.parseInt(numbers[0]);
        int max = Integer.parseInt(numbers[1]);


        String extendedRegexp = regexpTemp.substring(0,startQuantifierIndex).concat(String.valueOf(min+length)).concat(",").concat(String.valueOf(max+length)).concat(regexpTemp.substring(endQuantifierIndex));

        return RandomUtils.generateStringByRegexp(extendedRegexp);
    }

    private String extendRegExpToMax (String regexp) {
        String extdendedRegExp = regexp;
        int fromIndex = 0;
        //We search for each {min,max} occurrence in regexp and replace min with max in it
        while (fromIndex < extdendedRegExp.lastIndexOf("{")){
            int startQuantifierIndex = extdendedRegExp.indexOf("{", fromIndex) + 1;
            int endQuantifierIndex = extdendedRegExp.indexOf("}", startQuantifierIndex);
            String quantifier = extdendedRegExp.substring(startQuantifierIndex, endQuantifierIndex);
            String[] numbers = quantifier.split(",");
            if (Integer.parseInt(numbers[0]) != Integer.parseInt(numbers[1])){
                String end = "";
                if (endQuantifierIndex != extdendedRegExp.lastIndexOf("}")){
                    end = extdendedRegExp.substring(endQuantifierIndex+1);
                }
                extdendedRegExp = extdendedRegExp.substring(0, startQuantifierIndex-1).concat("{").concat(numbers[1].toString()).concat(",").concat(numbers[1].toString()).concat("}").concat(end);
            }

            //next cycle should start from index after current {} occurrence
            fromIndex = startQuantifierIndex;
        }

        return extdendedRegExp;
    }

    public String generateMaximumValue (){
        return RandomUtils.generateStringByRegexp(extendRegExpToMax (regexp));
    }

    public String getAllAllowedSymbols() {
        // remove all quantifiers in {} with brackets itself
        String string = regexp.replaceAll("\\{[0-9,]*\\}", "");
        // remove all [] brackets
        string = string.replaceAll("\\[|\\]", "");

        return string;
    }


}
