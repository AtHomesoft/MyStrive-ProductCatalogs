package com.mystrive.products;

public class ProductCatalogInfo {
    private String locale;
    private double version;

    public ProductCatalogInfo(String locale, double version) {
        this.locale = locale;
        this.version = version;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }
}
