/**
 * PullRequestPage.java
 * Created 11-Dec-2015 19:41:55
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * PullRequestPage
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class PullRequestPage extends AbstractBitBucketPage<PullRequestPage> {
    
    @Expose
    @SerializedName("values")
    public PullRequest[] pullRequests;

    public PullRequest[] getPullRequests() {
        return pullRequests;
    }

    public PullRequestPage setPullRequests(PullRequest[] pullRequests) {
        this.pullRequests = pullRequests;

        return this;
    }

}
