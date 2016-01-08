/**
 * PullRequest.java
 * Created 11-Dec-2015 19:40:16
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model;

import co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequestState;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;



/**
 * PullRequest
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class PullRequest implements co.byng.internal.engineering.kpiprovider.versioncontrol.model.PullRequest<PullRequest.State>, BitBucketModellable {

    @Expose
    @SerializedName("created_on")
    public DateTime dateCreated;

    @Expose
    @SerializedName("state")
    public State state;



    @Override
    public DateTime getDateCreated() {
        return dateCreated;
    }

    @Override
    public State getState() {
        return state;
    }

    public PullRequest setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;

        return this;
    }

    public PullRequest setState(State state) {
        this.state = state;

        return this;
    }


        
    public enum State implements PullRequestState {
        MERGED, OPEN, REJECTED;
    }
    
}
