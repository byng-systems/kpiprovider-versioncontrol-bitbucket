/**
 * BitBucketHttpClient.java
 * Created 15-Dec-2015 19:13:02
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.factory;

import co.byng.internal.engineering.kpiprovider.util.HttpConnectionFactory;
import co.byng.internal.engineering.kpiprovider.util.serialization.ObjectDeserializer;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequestPage;



/**
 * HttpPullRequestPageFactory
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class HttpPullRequestPageFactory extends AbstractPageFactory<PullRequestPage> {

    public HttpPullRequestPageFactory(ObjectDeserializer objectDeserializer, HttpConnectionFactory httpConnectionFactory) {
        super(objectDeserializer, httpConnectionFactory);
    }

    @Override
    protected Class<PullRequestPage> getModelClass() {
        return PullRequestPage.class;
    }
    
}
