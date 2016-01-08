/**
 * ConcurrentPullRequestCounter.java
 * Created 18-Dec-2015 09:39:07
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.util.Counter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.PullRequestCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.FilteredException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.IntegerSummingFilterFailedException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.NoFiltersException;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;



/**
 * ConcurrentPullRequestCounter
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class ConcurrentPullRequestCounter implements PullRequestCounter {

    protected final PullRequestPageRetriever pageRetriever;
    protected final PullRequestPageProcessor pageProcessor;
    protected final ExecutorService executorService;
    protected final int threadTerminationTimeout;



    public ConcurrentPullRequestCounter(
        PullRequestPageRetriever pageRetriever,
        PullRequestPageProcessor pageProcessor,
        ExecutorService executorService,
        int threadTerminationTimeout    
    ) {
        this.pageRetriever = pageRetriever;
        this.pageProcessor = pageProcessor;
        this.executorService = executorService;
        this.threadTerminationTimeout = threadTerminationTimeout;
    }

    @Override
    public Integer getPullRequestCount(
        final Repository repository,
        final PullRequestState[] states,
        final PullRequestFilter[] filters
    ) {
        final PullRequestPage page = this.pageRetriever.getPullRequestPage(repository, states, 1);
        final Counter<Integer> counter = new Counter.IntegerImpl();

        try {
            counter.increment(this.pageProcessor.processPullRequestPage(page, filters));
            final int totalPages = (int) Math.floor(page.getTotal() / page.getPageLen());

            for (int i = 2; i <= totalPages; i++) {
                final int pageNumber = i;

                this.executorService.submit(
                    new Runnable() {

                        @Override
                        public void run() {
                            PullRequestPage page = pageRetriever.getPullRequestPage(
                                repository,
                                states,
                                pageNumber
                            );

                            try {
                                counter.increment(
                                    pageProcessor.processPullRequestPage(page, filters)
                                );
                                
                            } catch (IntegerSummingFilterFailedException ex) {
                                counter.increment(ex.getTotal());
                            } catch (NoFiltersException ex) {
                                counter.increment(page.getTotal());
                            } catch (FilteredException ex) {
                                
                            }
                        }
                    }
                );
            }

            this.executorService.shutdown();
            this.executorService.awaitTermination(this.threadTerminationTimeout, TimeUnit.SECONDS);

            return counter.getTotal();
        } catch (IntegerSummingFilterFailedException ex) {
            return ex.getTotal();
        } catch (FilteredException | InterruptedException ex) {
            return 0;
        }
    }
    
}
