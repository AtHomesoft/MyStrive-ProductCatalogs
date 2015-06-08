package com.mystrive.products;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String name;
    private ArrayList<ProductCatalogInfo> productCatalogInfoList = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductCatalogInfo> getProductCatalogInfoList() {
        return productCatalogInfoList;
    }
}
