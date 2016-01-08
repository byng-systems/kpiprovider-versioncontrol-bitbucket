/**
 * HttpRepositoryPageRetriever.java
 * Created 21-Dec-2015 09:37:01
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket;

import co.byng.internal.engineering.kpiprovider.util.UrlFactory;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.RepositoryPage;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.factory.ModelFactory;
import java.io.IOException;
import java.net.URL;



/**
 * HttpRepositoryPageRetriever
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpRepositoryPageRetriever implements RepositoryPageRetriever {

    public static final int DEFAULT_PAGE_SIZE = 50;
    
    protected final UrlFactory urlFactory;
    protected final ModelFactory<RepositoryPage, URL> modelFactory;
    protected final int pageLen;


 
    public HttpRepositoryPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<RepositoryPage, URL> modelFactory,
        int pageLen
    ) throws IllegalArgumentException {
        if (pageLen < 1 || pageLen > 50) {
            throw new IllegalArgumentException("Page length must be given as an integer value between 1 and 50 inclusive");
        }
        
        this.urlFactory = urlFactory;
        this.modelFactory = modelFactory;
        this.pageLen = pageLen;
    }
    
    public HttpRepositoryPageRetriever(
        UrlFactory urlFactory,
        ModelFactory<RepositoryPage, URL> modelFactory
    ) throws IllegalArgumentException {
        this(urlFactory, modelFactory, DEFAULT_PAGE_SIZE);
    }
    
    @Override
    public RepositoryPage getRepositoryPage(String account, int pageNumber) throws RuntimeException {
        try {
            return this.modelFactory.build(
                this.urlFactory.buildUrl(
                    String.format(
                        "repositories/%s",
                        account
                    ),
                    String.format(
                        "page=%d&pagelen=%d",
                        pageNumber,
                        this.pageLen
                    )
                )
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
