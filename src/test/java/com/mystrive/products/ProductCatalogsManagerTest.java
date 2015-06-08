package com.mystrive.products;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class ProductCatalogsManagerTest {
    private ProductCatalogsManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new ProductCatalogsManager();
        FileSystemLoader loader = new FileSystemLoader(new File("catalogs"));
        loader.load(manager);
    }

    @Test
    public void testGetCompanies() throws Exception {
        List<Company> companies = manager.getCompanies();
        assertEquals("Incorrect number of companies found!", 1, companies.size());
        assertMaryKay(companies.get(0));
    }

    private void assertMaryKay(Company company) {
        List<ProductCatalogInfo> infoList = company.getProductCatalogInfoList();
        assertEquals("Incorrect number of catalogs!", 1, infoList.size());
        assertProductCatalogInfo("USA", 1, infoList.get(0));
    }

    private void assertProductCatalogInfo(String locale, double version, ProductCatalogInfo catalogInfo) {
        assertEquals("Incorrect locale for product catalog!", locale, catalogInfo.getLocale());
        assertEquals("Incorrect version for product catalog!", version, catalogInfo.getVersion(), 0);
    }
}