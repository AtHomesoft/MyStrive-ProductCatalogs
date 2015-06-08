package com.mystrive.products;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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
                manager.getCompanies().add(company);
                loadCompanyProductCatalogInfo(company, new File(catalogsDir, companyDir));
            }
        }
    }

    private void loadCompanyProductCatalogInfo(Company company, File companyDir) {
        String[] catalogFiles = companyDir.list((dir, name) -> name.endsWith(".csv"));

        if (catalogFiles != null) {
            for (String catalogFile : catalogFiles) {
                String locale = FilenameUtils.getBaseName(catalogFile);
                double version = readVersion(new File(companyDir, catalogFile));
                ProductCatalogInfo info = new ProductCatalogInfo(locale, version);
                company.getProductCatalogInfoList().add(info);
            }
        }
    }

    private double readVersion(File catalogFile) {
        double version = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(catalogFile));
            reader.readLine(); // Reads past the header
            version = Double.parseDouble(readVersionFromLine(reader.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return version;
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
