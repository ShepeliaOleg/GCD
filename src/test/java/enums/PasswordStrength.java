package enums;

public enum PasswordStrength {
    zero("strength-0", "Too short"),
    one("strength-1", "Very weak"),
    two("strength-2", "Weak"),
    three("strength-3", "Fair"),
    four("strength-4", "Good"),
    five("strength-5", "Strong");

    String name;
    String tooltip;

    PasswordStrength(String name, String tooltip){
        this.name = name;
        this.tooltip = tooltip;
    }

    public String toString(){
        return name;
    }

    public String getTooltip(){
        return tooltip;
    }
}
