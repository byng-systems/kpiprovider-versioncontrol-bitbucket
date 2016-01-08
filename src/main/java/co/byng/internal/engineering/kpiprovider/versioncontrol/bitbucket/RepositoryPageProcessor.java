/**
 * RepositoryPageProcessor.java
 * Created 23-Dec-2015 13:31:16
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.Repository;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.RepositoryPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.FilteredException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.RepositoryFilter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.RepositoryFilteredException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;



/**
 * RepositoryPageProcessor 
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public interface RepositoryPageProcessor {
    
    public Collection<Repository> processRepositoryPage(RepositoryPage page, RepositoryFilter[] filters);
    
    
    
    public class Impl implements RepositoryPageProcessor {

        @Override
        public Collection<Repository> processRepositoryPage(RepositoryPage page, RepositoryFilter[] filters) {
            Collection<Repository> repositories = new ArrayList<Repository>();
            
            if (filters.length == 0) {
                return Arrays.asList(page.getRepositories());
            }
            
            for (Repository repository : page.getRepositories()) {
                try {
                    repositories.add(this.processRepository(repository, filters));
                } catch (FilteredException ex) {
                    continue;
                }
            }
            
            return repositories;
        }
        
        protected Repository processRepository(Repository repository, RepositoryFilter[] filters) throws RepositoryFilteredException {
            
            for (RepositoryFilter filter : filters) {
                if (filter.accept(repository)) {
                    return repository;
                }
            }
            
            throw new RepositoryFilteredException();
        }
        
    }
    
}
