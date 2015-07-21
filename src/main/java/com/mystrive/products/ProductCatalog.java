package com.mystrive.products;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalog extends ProductCatalogInfo {
    private List<Product> products = new ArrayList<>();

    public ProductCatalog(String id, Business business, String locale, double version) {
        super(id, business, locale, version);
    }

    public List<Product> getProducts() {
        return products;
    }
}
