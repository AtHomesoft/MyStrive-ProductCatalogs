package com.mystrive.products;

import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class CsvProductCatalogsLoader implements ProductCatalogsLoader {
    public String formatDirectoryAsName(String id) {
        return id.replace('_', ' ');
    }

    protected ProductCatalog readProductCatalog(InputStream inputStream, ProductCatalogsManager manager,
            Business business, String catalogName) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                CsvBeanReader csvBeanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
            reader.readLine(); // Skip version header
            double version = Double.parseDouble(readVersionFromLine(reader.readLine()));
            reader.readLine(); // Skip header row

            String catalogId = manager.buildProductCatalogId(business.getId(), catalogName);
            ProductCatalogInfo catalogInfo = new ProductCatalogInfo(catalogId, business, catalogName, version);
            manager.getProductCatalogInfos().put(catalogInfo.getId(), catalogInfo);

            ProductCatalog catalog = new ProductCatalog(catalogId, business, catalogName, version);
            String[] headers = { Product.NUMBER, Product.NAME, Product.RETAIL, Product.WHOLESALE, Product.SECTION,
                    Product.CATEGORY, Product.DISCONTINUED, Product.TAXABLE };
            CellProcessor[] processors = { new ProductNumberProcessor(), null, new ParseDouble(),
                    new ParseDouble(), new ParseInt(), null, new ParseBool(), new ParseBool() };

            Product product = csvBeanReader.read(Product.class, headers, processors);

            while (product != null) {
                catalog.getProducts().add(product);
                product = csvBeanReader.read(Product.class, headers, processors);
            }

            return catalog;
        }
    }

    protected String readVersionFromLine(String versionLine) {
        StringBuilder version = new StringBuilder();

        for (char character : versionLine.toCharArray()) {
            if (Character.isDigit(character)) {
                version.append(character);
            }
        }

        return version.toString();
    }
}
