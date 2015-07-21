package com.mystrive.products;

public class ProductCatalogInfo {
    private final String id;
    private final String locale;
    private final double version;
    private Business business;

    public ProductCatalogInfo(String id, Business business, String locale, double version) {
        this.id = id;
        this.business = business;
        this.locale = locale;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public Business getBusiness() {
        return business;
    }

    public String getLocale() {
        return locale;
    }

    public double getVersion() {
        return version;
    }
}
