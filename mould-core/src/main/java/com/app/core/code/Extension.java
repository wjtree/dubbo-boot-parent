package com.app.core.code;

public enum Extension {
    JS(".js"), JSP(".jsp"), PROPERTIES(".properties"), XML(".xml"), JSON(".json"), TXT(".txt"), XLSX(".xlsx"), CSV(".csv");

    private final String extension;

    Extension(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}