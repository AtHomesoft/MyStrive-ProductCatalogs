package com.mystrive.products;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ProductCatalogsManagerTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() throws IOException {
        List<Object[]> paramters = new ArrayList<>();
        paramters.add(new Object[] { new FileSystemLoader(new File("catalogs")) });
        paramters.add(new Object[] { new GitHubLoader("AtHomesoft/MyStrive-ProductCatalogs", "catalogs") });
        return paramters;
    }

    private final ProductCatalogsLoader loader;
    private ProductCatalogsManager manager;

    public ProductCatalogsManagerTest(ProductCatalogsLoader loader) {
        this.loader = loader;
    }

    @Before
    public void setUp() throws Exception {
        manager = new ProductCatalogsManager();
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