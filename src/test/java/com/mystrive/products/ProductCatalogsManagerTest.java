package com.mystrive.products;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
    public void testGetProductCatalogInfo() throws Exception {
        Map<String, ProductCatalogInfo> catalogInfos = manager.getProductCatalogInfos();
        String key = manager.buildProductCatalogId(CompanyCatalog.MaryKay.KEY, CompanyCatalog.MaryKay.USA);
        assertTrue("Did not find Mary Kay USA product catalog.", catalogInfos.containsKey(key));
        ProductCatalogInfo catalogInfo = catalogInfos.get(key);
        assertMaryKayUSA(catalogInfo);
    }

    private void assertMaryKayUSA(ProductCatalogInfo catalogInfo) {
        Business business = catalogInfo.getBusiness();
        assertEquals("Incorrect value for id.", CompanyCatalog.MaryKay.KEY, business.getId());
        assertEquals("Incorrect value for name.", "Mary Kay", business.getName());
        assertProductCatalogInfo(CompanyCatalog.MaryKay.USA, 1, catalogInfo);
    }

    private void assertProductCatalogInfo(String locale, double version, ProductCatalogInfo catalogInfo) {
        assertEquals("Incorrect locale for product catalog!", locale, catalogInfo.getLocale());
        assertEquals("Incorrect version for product catalog!", version, catalogInfo.getVersion(), 0);
    }
}