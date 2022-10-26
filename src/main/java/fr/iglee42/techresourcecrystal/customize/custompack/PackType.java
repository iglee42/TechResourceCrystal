package fr.iglee42.techresourcecrystal.customize.custompack;

public enum PackType {
    DATA("data"),
    RESOURCE("resource")
    ;
    private final String suffix;

    PackType(String suffix) {

        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}