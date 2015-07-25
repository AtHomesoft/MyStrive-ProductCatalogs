package com.mystrive.products;

import org.apache.commons.io.FilenameUtils;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class GitHubLoader extends CsvProductCatalogsLoader {
    private String repoUrl;
    private String catalogDir;

    public GitHubLoader(String repoUrl, String catalogDir) throws IOException {
        this.repoUrl = repoUrl;
        this.catalogDir = catalogDir;
    }

    @Override
    public void load(ProductCatalogsManager manager) throws Exception {
        GitHub github = GitHub.connectAnonymously();
        GHRepository repo = github.getRepository(repoUrl);
        List<GHContent> directoryContent = repo.getDirectoryContent(catalogDir);

        for (GHContent businessDir : directoryContent) {
            String businessName = businessDir.getName();
            Business business = new Business(businessName, formatDirectoryAsName(businessName));
            PagedIterable<GHContent> catalogFiles = businessDir.listDirectoryContent();

            for (GHContent catalogFile : catalogFiles) {
                String name = FilenameUtils.getBaseName(catalogFile.getName());
                InputStream inputStream = catalogFile.read();
                ProductCatalog catalog = readProductCatalog(inputStream, manager, business, name);
                manager.getProductCatalogs().put(catalog.getId(), catalog);
            }
        }
    }
}
