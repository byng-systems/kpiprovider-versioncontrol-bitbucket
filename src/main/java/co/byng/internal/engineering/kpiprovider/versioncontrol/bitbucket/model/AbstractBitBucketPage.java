/**
 * AbstractBitBucketPage.java
 * Created 21-Dec-2015 09:46:05
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model;

import co.byng.internal.engineering.kpiprovider.versioncontrol.model.VersionControlModellable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



/**
 * AbstractBitBucketPage
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class AbstractBitBucketPage <T extends AbstractBitBucketPage> implements VersionControlModellable, BitBucketModellable {
    
    @Expose
    @SerializedName("next")
    public String nextPage;

    @Expose
    @SerializedName("size")
    public int total;

    @Expose
    @SerializedName("pagelen")
    public int pageLen;

    
    
    public String getNextPage() {
        return nextPage;
    }

    public int getTotal() {
        return total;
    }

    public int getPageLen() {
        return pageLen;
    }

    public T setNextPage(String nextPage) {
        this.nextPage = nextPage;

        return (T) this;
    }

    public T setTotal(int total) {
        this.total = total;

        return (T) this;
    }

    public T setPageLen(int pageLen) {
        this.pageLen = pageLen;
        
        return (T) this;
    }
    
}
