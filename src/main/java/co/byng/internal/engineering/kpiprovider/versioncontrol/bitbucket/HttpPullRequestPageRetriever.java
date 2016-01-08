/**
 * HttpPullRequestPageRetriever.java
 * Created 15-Dec-2015 19:49:24
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.util.UrlFactory;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.factory.ModelFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



/**
 * HttpPullRequestPageRetriever
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpPullRequestPageRetriever implements PullRequestPageRetriever {
    
    public static final int DEFAULT_PAGE_SIZE = 50;
    
    protected UrlFactory urlFactory;
    protected ModelFactory<PullRequestPage, URL> modelFactory;
    protected StateEncoder stateEncoder;
    protected int pageSize;

    public HttpPullRequestPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<PullRequestPage, URL> modelFactory,
        StateEncoder stateEncoder,
        int pageSize
    ) throws IllegalArgumentException {
        if (pageSize < 1 || pageSize > 50) {
            throw new IllegalArgumentException(
                "Page size must be given as an integer value between 1 and 50 inclusive"
            );
        }
        
        this.urlFactory = urlFactory;
        this.modelFactory = modelFactory;
        this.stateEncoder = stateEncoder;
        this.pageSize = pageSize;
    }
    
    public HttpPullRequestPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<PullRequestPage, URL> modelFactory,
        StateEncoder stateEncoder
    ) {
        this(urlFactory, modelFactory, stateEncoder, DEFAULT_PAGE_SIZE);
    }

    public HttpPullRequestPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<PullRequestPage, URL> modelFactory,
        int pageSize
    ) throws IllegalArgumentException {
        this(urlFactory, modelFactory, new StateEncoder.Impl(), pageSize);
    }
    
    public HttpPullRequestPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<PullRequestPage, URL> modelFactory
    ) {
        this(urlFactory, modelFactory, new StateEncoder.Impl());
    }

    @Override
    public PullRequestPage getPullRequestPage(
        Repository repository,
        PullRequestState[] states,
        int pageNumber
    ) {
        try {
            return this.modelFactory.build(
                this.urlFactory.buildUrl(
                    String.format(
                        "repositories/%s/pullrequests",
                        repository.getName()
                    ),
                    String.format(
                        "page=%d&pagelen=%d&state=%s",
                        pageNumber,
                        this.pageSize,
                        this.stateEncoder.encodeStates(states)
                    )
                )
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }



    public interface StateEncoder {

        public String encodeStates(PullRequestState[] states);



        public class Impl implements StateEncoder {

            @Override
            public String encodeStates(PullRequestState[] states) {
                List<String> stateNames = new ArrayList<>();

                for (PullRequestState state : states) {
                    stateNames.add(state.toString());
                }
                
                return String.join(",", stateNames);
            }
            
        }
        
    }
    
}
