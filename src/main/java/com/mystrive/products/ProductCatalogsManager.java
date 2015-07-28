package com.mystrive.products;

import java.util.HashMap;
import java.util.Map;

public class ProductCatalogsManager {
    private final Map<String, ProductCatalogInfo> productCatalogInfos = new HashMap<>();
    private final Map<String, ProductCatalog> productCatalogs = new HashMap<>();

    public Map<String, ProductCatalogInfo> getProductCatalogInfos() {
        return productCatalogInfos;
    }

    public Map<String, ProductCatalog> getProductCatalogs() {
        return productCatalogs;
    }

    public boolean containsProductCatalog(String business, String locale) {
        return getProductCatalogs().containsKey(buildProductCatalogId(business, locale));
    }

    public ProductCatalog getProductCatalog(String business, String locale) {
        ProductCatalog productCatalog = null;

        String key = buildProductCatalogId(business, locale);

        if (getProductCatalogs().containsKey(key)) {
            productCatalog = getProductCatalogs().get(key);
        }

        return productCatalog;
    }

    public String buildProductCatalogId(String business, String locale) {
        return business + '/' + locale;
    }

    public ProductCatalog getProductCatalog(String productCatalogId) {
        ProductCatalog catalog = null;

        if (productCatalogs.containsKey(productCatalogId)) {
            catalog = productCatalogs.get(productCatalogId);
        }

        return catalog;
    }
}
