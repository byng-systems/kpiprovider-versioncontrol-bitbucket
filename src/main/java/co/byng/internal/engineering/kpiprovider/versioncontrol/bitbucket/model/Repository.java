/**
 * Repository.java
 * Created 16-Dec-2015 07:50:11
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * Repository
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class Repository implements co.byng.internal.engineering.kpiprovider.versioncontrol.model.Repository, BitBucketModellable, Comparable<Repository> {

    @Expose
    @SerializedName("full_name")
    public String name;



    @Override
    public int compareTo(Repository o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    public Repository setName(String name) {
        this.name = name;
        
        return this;
    }
    
}
