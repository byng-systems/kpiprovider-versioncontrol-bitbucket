/**
 * HttpRepositoryPageFactory.java
 * Created 23-Dec-2015 13:22:53
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.factory;

import co.byng.internal.engineering.kpiprovider.util.HttpConnectionFactory;
import co.byng.internal.engineering.kpiprovider.util.serialization.ObjectDeserializer;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.RepositoryPage;



/**
 * HttpRepositoryPageFactory
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpRepositoryPageFactory extends AbstractPageFactory<RepositoryPage> {

    public HttpRepositoryPageFactory(ObjectDeserializer objectDeserializer, HttpConnectionFactory httpConnectionFactory) {
        super(objectDeserializer, httpConnectionFactory);
    }

    @Override
    protected Class<RepositoryPage> getModelClass() {
        return RepositoryPage.class;
    }
    
}
