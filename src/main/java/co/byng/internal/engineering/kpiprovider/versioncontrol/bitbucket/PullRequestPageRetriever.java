/**
 * PullRequestLoader.java
 * Created 11-Dec-2015 23:11:38
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository;



/**
 * PullRequestPageRetriever 
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public interface PullRequestPageRetriever {

    public PullRequestPage getPullRequestPage(Repository repository, PullRequestState[] states, int pageNumber);

}
