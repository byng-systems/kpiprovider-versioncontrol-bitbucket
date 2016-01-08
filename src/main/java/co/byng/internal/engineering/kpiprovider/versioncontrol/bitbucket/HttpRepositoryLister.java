/**
 * HttpRepositoryLister.java
 * Created 21-Dec-2015 09:26:25
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.RepositoryLister;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.Repository;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.RepositoryPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.RepositoryFilter;
import java.util.Collection;
import java.util.TreeSet;



/**
 * HttpRepositoryLister
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpRepositoryLister implements RepositoryLister<Repository> {

    protected final RepositoryPageRetriever pageRetriever;
    protected final RepositoryPageProcessor pageProcessor;

    public HttpRepositoryLister(
        RepositoryPageRetriever pageRetriever,
        RepositoryPageProcessor pageProcessor
    ) {
        this.pageRetriever = pageRetriever;
        this.pageProcessor = pageProcessor;
    }

    @Override
    public Collection<Repository> listRepositories(String account, RepositoryFilter[] filters) {
        RepositoryPage page = this.pageRetriever.getRepositoryPage(account, 1);

        Collection<Repository> repositories = new TreeSet<>(
            this.pageProcessor.processRepositoryPage(page, filters)
        );
        int totalPages = (int) Math.ceil(page.getTotal() / page.getPageLen());
        
        for (int i = 2; i <= totalPages; i++) {
            repositories.addAll(
                this.pageProcessor.processRepositoryPage(
                    this.pageRetriever.getRepositoryPage(account, i),
                    filters
                )
            );
        }

        return repositories;
    }

}
