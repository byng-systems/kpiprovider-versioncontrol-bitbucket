/**
 * RepositoryPage.java
 * Created 21-Dec-2015 09:33:20
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * RepositoryPage
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class RepositoryPage extends AbstractBitBucketPage<RepositoryPage> {

    @Expose
    @SerializedName("values")
    public Repository[] repositories;



    public Repository[] getRepositories() {
        return repositories;
    }

    public RepositoryPage setRepositories(Repository[] repositories) {
        this.repositories = repositories;
        
        return this;
    }
    
}
