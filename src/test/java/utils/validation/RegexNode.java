package utils.validation;

import utils.TypeUtils;

/**
 * Created by sergiich on 8/27/2014.
 */
public class RegexNode {

    private static final String TEXT_START = "[";
    private static final String TEXT_END = "]";
    private static final String NUMBERS_START = "{";
    private static final String NUMBERS_END = "}";

    int max;

    int min;
    String symbols;
    public RegexNode(String node){
        max = parseMax(node);
        min = parseMin(node);
        symbols = parseSymbols(node);
    }

    private String parseSymbols(String node){
        int start = node.indexOf(TEXT_START);
        int end = node.indexOf(TEXT_END)+1;
        if(start!=-1){
            return (node.substring(start, end)).replace(TEXT_START, "").replace(TEXT_END, "");
        }else{
            throw new RuntimeException("Node: '"+node+"' had no symbols info");
        }
    }

    private int parseMin(String node){
        return Integer.parseInt(TypeUtils.splitNumbers(parseLength(node))[0]);
    }

    private int parseMax(String node){
        return Integer.parseInt(TypeUtils.splitNumbers(parseLength(node))[1]);
    }

    private String parseLength(String node){
        int start = node.indexOf(NUMBERS_START);
        int end = node.indexOf(NUMBERS_END)+1;
        if(start!=-1){
            return (node.substring(start, end));
        }else{
            throw new RuntimeException("Node: '"+node+"' had no length info");
        }
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String toString(){
        return TEXT_START + getSymbols()+ TEXT_END + NUMBERS_START + getMin() + "," + getMax()+ NUMBERS_END;
    }


}
