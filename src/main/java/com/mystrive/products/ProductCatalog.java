package com.mystrive.products;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalog extends ProductCatalogInfo {
    private List<Product> products = new ArrayList<>();

    public ProductCatalog(String locale, double version) {
        super(locale, version);
    }

    public List<Product> getProducts() {
        return products;
    }
}
