/**
 * PullRequestCounter.java
 * Created 16-Dec-2015 09:08:09
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.util.Counter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.FilteredException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.PullRequestCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.IntegerSummingFilterFailedException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository;



/**
 * HttpPullRequestCounter
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpPullRequestCounter implements PullRequestCounter {

    protected final PullRequestPageRetriever pageRetriever;
    protected final PullRequestPageProcessor pageProcessor;

    public HttpPullRequestCounter(
        PullRequestPageRetriever pageRetriever,
        PullRequestPageProcessor pageProcessor
    ) {
        this.pageRetriever = pageRetriever;
        this.pageProcessor = pageProcessor;
    }
    
    public HttpPullRequestCounter(
        PullRequestPageRetriever pageRetriever
    ) {
        this(pageRetriever, new PullRequestPageProcessor.Impl());
    }
    
    @Override
    public Integer getPullRequestCount(Repository repository, PullRequestState[] states, PullRequestFilter[] filters) {
        final PullRequestPage page = this.pageRetriever.getPullRequestPage(repository, states, 1);
        final Counter<Integer> counter = new Counter.IntegerImpl();

        try {
            counter.increment(this.pageProcessor.processPullRequestPage(page, filters));
        } catch (FilteredException ex) {
            counter.increment(page.getTotal());
        }
        
        counter.increment(
            this.getCountForPageRange(
                repository,
                states,
                filters,
                2,
                (int) Math.ceil(page.getTotal() / page.getPageLen())
            )
        );

        return counter.getTotal();
    }
    
    protected Integer getCountForPageRange(
        Repository repository,
        PullRequestState[] states,
        PullRequestFilter[] filters,
        int startPage,
        int totalPages
    ) {
        Counter<Integer> counter = new Counter.IntegerImpl();
        
        for (int i = startPage; i <= totalPages; i++) {
            PullRequestPage page = this.pageRetriever.getPullRequestPage(repository, states, i);

            try {
                counter.increment(
                    this.pageProcessor.processPullRequestPage(
                        page,
                        filters
                    )
                );
            } catch (IntegerSummingFilterFailedException ex) {
                counter.increment(ex.getTotal());
                break;
            } catch (FilteredException ex) {
                break;
            }
        }

        return counter.getTotal();
    }
    
}
 