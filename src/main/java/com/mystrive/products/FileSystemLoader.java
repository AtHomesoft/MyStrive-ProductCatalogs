package com.mystrive.products;

import org.apache.commons.io.FilenameUtils;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileSystemLoader implements ProductCatalogsLoader {
    private File catalogsDir;

    public FileSystemLoader(File catalogsDir) {
        this.catalogsDir = catalogsDir;
    }

    @Override
    public void load(ProductCatalogsManager manager) {
        String[] companyDirs = catalogsDir.list((dir, name) -> new File(dir, name).isDirectory());

        if (companyDirs != null) {
            for (String companyDir : companyDirs) {
                Company company = new Company(companyDir);
                manager.getCompanies().put(companyDir, company);
                File catalogFile = new File(catalogsDir, companyDir);
                loadCompanyProductCatalogLocales(company, catalogFile, manager);
            }
        }
    }

    private void loadCompanyProductCatalogLocales(Company company, File companyDir, ProductCatalogsManager manager) {
        String[] catalogFiles = companyDir.list((dir, name) -> name.endsWith(".csv"));

        if (catalogFiles != null) {
            for (String catalogFile : catalogFiles) {
                File csvFile = new File(companyDir, catalogFile);
                loadProductCatalog(company, csvFile, manager);
            }
        }
    }

    private void loadProductCatalog(Company company, File catalogFile, ProductCatalogsManager manager) {
        String locale = FilenameUtils.getBaseName(catalogFile.getName());
        manager.getProductCatalog(company.getId(), locale);

        if (!manager.containsProductCatalog(company.getId(), locale)) {
            try (FileInputStream fileInputStream = new FileInputStream(catalogFile);
                    InputStreamReader isr = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(isr);
                    CsvBeanReader csvBeanReader = new CsvBeanReader(reader, CsvPreference.STANDARD_PREFERENCE)) {
                reader.readLine(); // Skip version header
                double version = Double.parseDouble(readVersionFromLine(reader.readLine()));
                reader.readLine(); // Skip header row

                ProductCatalog catalog = new ProductCatalog(locale, version);
                Product product;

                do {
                    String[] headers = { Product.NUMBER, Product.NAME, Product.RETAIL, Product.SECTION,
                            Product.CATEGORY, Product.DISCONTINUED, Product.TAXABLE };
                    CellProcessor[] processors = { new ProductNumberProcessor(), null, new ParseDouble(),
                            new ParseInt(), null, new ParseBool(), new ParseBool() };
                    product = csvBeanReader.read(Product.class, headers, processors);
                    catalog.getProducts().add(product);
                } while (product != null);

                company.getProductCatalogInfoList().put(locale, catalog);
                manager.putProductCatalog(company.getId(), locale, catalog);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private String readVersionFromLine(String versionLine) {
        StringBuilder version = new StringBuilder();

        for (char character : versionLine.toCharArray()) {
            if (Character.isDigit(character)) {
                version.append(character);
            }
        }

        return version.toString();
    }
}
