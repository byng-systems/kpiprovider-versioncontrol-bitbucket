/**
 * PullRequestPageProcessor.java
 * Created 18-Dec-2015 12:36:20
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequest;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.FilterFailedException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.FilteredException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.IntegerSummingFilterFailedException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.NoFiltersException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;



/**
 * PullRequestPageProcessor 
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public interface PullRequestPageProcessor {

    public int processPullRequestPage(PullRequestPage page, PullRequestFilter[] filters) throws FilteredException;
    
    
    
    public class Impl implements PullRequestPageProcessor {
        
        public int processPullRequestPage(PullRequestPage page, PullRequestFilter[] filters) throws NoFiltersException, FilterFailedException {
            if (filters.length == 0) {
                throw new NoFiltersException();
            }

            int total = 0;
            
            try {
                PullRequest[] pullRequests = page.getPullRequests();
                for (PullRequest pullRequest : pullRequests) {
                    total += processPullRequest(pullRequest, filters);
                }

                return total;
            } catch (FilteredException ex) {
                throw new IntegerSummingFilterFailedException(total);
            }
        }

        protected int processPullRequest(PullRequest pullRequest, PullRequestFilter[] filters) throws FilterFailedException {
            for (PullRequestFilter filter : filters) {
                if (!filter.accept(pullRequest)) {
                    throw new FilterFailedException();
                }
            }

            return 1;
        }       
           
    }
    
}
