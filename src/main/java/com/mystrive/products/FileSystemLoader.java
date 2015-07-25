package com.mystrive.products;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSystemLoader extends CsvProductCatalogsLoader {
    private File catalogsDir;

    public FileSystemLoader(File catalogsDir) {
        this.catalogsDir = catalogsDir;
    }

    @Override
    public void load(ProductCatalogsManager manager) {
        String[] companyDirs = catalogsDir.list((dir, name) -> new File(dir, name).isDirectory());

        if (companyDirs != null) {
            for (String companyDir : companyDirs) {
                Business business = new Business(companyDir, formatDirectoryAsName(companyDir));
                File catalogFile = new File(catalogsDir, companyDir);
                loadCompanyProductCatalogLocales(business, catalogFile, manager);
            }
        }
    }

    private void loadCompanyProductCatalogLocales(Business business, File companyDir, ProductCatalogsManager manager) {
        String[] catalogFiles = companyDir.list((dir, name) -> name.endsWith(".csv"));

        if (catalogFiles != null) {
            for (String catalogFile : catalogFiles) {
                File csvFile = new File(companyDir, catalogFile);
                loadProductCatalog(business, csvFile, manager);
            }
        }
    }

    private void loadProductCatalog(Business business, File catalogFile, ProductCatalogsManager manager) {
        String locale = FilenameUtils.getBaseName(catalogFile.getName());
        manager.getProductCatalog(business.getId(), locale);

        if (!manager.containsProductCatalog(business.getId(), locale)) {
            try (FileInputStream fileInputStream = new FileInputStream(catalogFile)) {
                ProductCatalog catalog = readProductCatalog(fileInputStream, manager, business, locale);
                manager.getProductCatalogs().put(catalog.getId(), catalog);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
