package com.mystrive.products;

import java.util.HashMap;
import java.util.Map;

public class Company {
    private final String id;
    private final String name;
    private final Map<String, ProductCatalogInfo> productCatalogInfoList = new HashMap<>();

    public Company(String id) {
        this.id = id;
        this.name = formatIdAsName(id);
    }

    private String formatIdAsName(String id) {
        return id.replace('_', ' ');
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<String, ProductCatalogInfo> getProductCatalogInfoList() {
        return productCatalogInfoList;
    }
}
