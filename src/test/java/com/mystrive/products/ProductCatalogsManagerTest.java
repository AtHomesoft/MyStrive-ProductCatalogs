package com.mystrive.products;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

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
    public void testGetMaryKayUsaProductCatalog() throws Exception {
        ProductCatalog catalog = manager.getProductCatalog(CompanyCatalog.MaryKay.KEY, CompanyCatalog.MaryKay.USA);
        assertNotNull("Could not find catalog.", catalog);
        assertTrue("Not enough products were found", catalog.getProducts().size() > 100);
    }

    @Test
    public void testGetCompanies() throws Exception {
        Map<String, Company> companies = manager.getCompanies();
        assertEquals("Incorrect number of companies found!", 1, companies.size());
        assertMaryKay(companies.get("Mary_Kay"));
    }

    private void assertMaryKay(Company company) {
        assertEquals("Incorrect value for id.", "Mary_Kay", company.getId());
        assertEquals("Incorrect value for name.", "Mary Kay", company.getName());

        Map<String, ProductCatalogInfo> infoList = company.getProductCatalogInfoList();
        assertEquals("Incorrect number of catalogs!", 1, infoList.size());
        assertProductCatalogInfo("USA", 1, infoList.get("USA"));
    }

    private void assertProductCatalogInfo(String locale, double version, ProductCatalogInfo catalogInfo) {
        assertEquals("Incorrect locale for product catalog!", locale, catalogInfo.getLocale());
        assertEquals("Incorrect version for product catalog!", version, catalogInfo.getVersion(), 0);
    }
}