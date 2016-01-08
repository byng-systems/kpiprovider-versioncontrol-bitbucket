/**
 * MultiRepositoryPullRequestCounter.java
 * Created 16-Dec-2015 07:54:57
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.factory.PullRequestCounterFactory;
import co.byng.internal.engineering.kpiprovider.versioncontrol.MultiRepositoryPullRequestsCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.MultiRepositoryPullRequestsTotalCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.PullRequestCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;



/**
 * ConcurrentMultiRepositoryPullRequestsCounter
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class ConcurrentMultiRepositoryPullRequestsCounter implements MultiRepositoryPullRequestsCounter, MultiRepositoryPullRequestsTotalCounter {

    protected final PullRequestCounterFactory pullRequestCounterFactory;
    protected final ExecutorService executorService;
    protected final int threadTerminationTimeout;

    public ConcurrentMultiRepositoryPullRequestsCounter(
        final PullRequestCounterFactory pullRequestCounterFactory,
        final ExecutorService executorService,
        final int threadTerminationTimeout
    ) {
        this.pullRequestCounterFactory = pullRequestCounterFactory;
        this.executorService = executorService;
        this.threadTerminationTimeout = threadTerminationTimeout;
    }
    
    public ConcurrentMultiRepositoryPullRequestsCounter(
        final PullRequestCounter pullRequestCounter,
        final ExecutorService executorService,
        final int threadTerminationTimeout
    ) {
        this.pullRequestCounterFactory = new PullRequestCounterFactory() {

            @Override
            public PullRequestCounter buildPullRequestCounter() {
                return pullRequestCounter;
            }
        };
        
        this.executorService = executorService;
        this.threadTerminationTimeout = threadTerminationTimeout;
    }

    @Override
    public Map<Repository, Integer> getPullRequestCounts(
        final Repository[] repositories,
        final PullRequestState[] states,
        final PullRequestFilter[] filters
    ) {
        final Map<Repository, Integer> repositoryTotals = new LinkedHashMap<>();

        for (final Repository repository : repositories) {
            this.executorService.submit(new Runnable() {

                @Override
                public void run() {
                    Integer count = pullRequestCounterFactory
                            .buildPullRequestCounter()
                            .getPullRequestCount(repository, states, filters)
                    ;

                    synchronized (repositoryTotals) {
                        repositoryTotals.put(repository, count);
                    }
                }
            });
        }

        try {
            this.executorService.shutdown();
            this.executorService.awaitTermination(
                threadTerminationTimeout,
                TimeUnit.SECONDS
            );
        } catch (InterruptedException ex) {
            
        }

        return repositoryTotals;
    }
    
    @Override
    public Integer getPullRequestCount(
        final Repository[] repositories,
        final PullRequestState[] states,
        final PullRequestFilter[] filters
    ) {
        int total = 0;
        for (Integer i : this.getPullRequestCounts(repositories, states, filters).values()) {
            total += i;
        }
        
        return total;
    }
    
}
