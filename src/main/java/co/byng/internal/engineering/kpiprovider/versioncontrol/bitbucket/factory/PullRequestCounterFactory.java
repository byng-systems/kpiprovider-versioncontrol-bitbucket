/**
 * PullRequestCounterFactory.java
 * Created 29-Dec-2015 18:36:20
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.factory;

import co.byng.internal.engineering.kpiprovider.versioncontrol.PullRequestCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.ConcurrentPullRequestCounter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.PullRequestPageProcessor;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.PullRequestPageRetriever;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * PullRequestCounterFactory 
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public interface PullRequestCounterFactory <C extends PullRequestCounter> {

    public C buildPullRequestCounter();
    
    
    
    /*public class FixedThreadPoolImpl implements PullRequestCounterFactory<ConcurrentPullRequestCounter> {

        protected final PullRequestPageRetriever pageRetriever;
        protected final PullRequestPageProcessor pageProcessor;
        protected final int threadCount;
        protected final int threadTerminationTimeout;

        public FixedThreadPoolImpl(
            PullRequestPageRetriever pageRetriever,
            PullRequestPageProcessor pageProcessor,
            int threadCount,
            int threadTerminationTimeout
        ) {
            this.pageRetriever = pageRetriever;
            this.pageProcessor = pageProcessor;
            this.threadCount = threadCount;
            this.threadTerminationTimeout = threadTerminationTimeout;
        }

        @Override
        public ConcurrentPullRequestCounter buildPullRequestCounter() {
            return new ConcurrentPullRequestCounter(
                this.pageRetriever,
                this.pageProcessor,
                Executors.newFixedThreadPool(this.threadCount),
                this.threadTerminationTimeout
            );
        }
        
    }*/
    
}
