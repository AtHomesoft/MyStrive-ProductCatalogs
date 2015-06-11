package com.mystrive.products;

import java.util.HashMap;
import java.util.Map;

public class ProductCatalogsManager {
    private final Map<String, Company> companies = new HashMap<>();
    private final Map<String, ProductCatalog> productCatalogs = new HashMap<>();

    public Map<String, Company> getCompanies() {
        return companies;
    }

    public Map<String, ProductCatalog> getProductCatalogs() {
        return productCatalogs;
    }

    public boolean containsProductCatalog(String company, String locale) {
        return getProductCatalogs().containsKey(buildProductCatalogKey(company, locale));
    }

    public ProductCatalog getProductCatalog(String company, String locale) {
        ProductCatalog productCatalog = null;

        String key = buildProductCatalogKey(company, locale);

        if (getProductCatalogs().containsKey(key)) {
            productCatalog = getProductCatalogs().get(key);
        }

        return productCatalog;
    }

    private String buildProductCatalogKey(String company, String locale) {
        return company + '/' + locale;
    }

    public void putProductCatalog(String company, String locale, ProductCatalog catalog) {
        getProductCatalogs().put(buildProductCatalogKey(company, locale), catalog);
    }
}
