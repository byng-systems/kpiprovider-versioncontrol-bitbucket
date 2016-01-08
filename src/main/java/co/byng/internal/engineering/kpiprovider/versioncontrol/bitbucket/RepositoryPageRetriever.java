/**
 * RepositoryPageRetriever.java
 * Created 21-Dec-2015 09:33:05
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.RepositoryPage;



/**
 * RepositoryPageRetriever 
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public interface RepositoryPageRetriever {

    public RepositoryPage getRepositoryPage(String account, int pageNumber);

}
