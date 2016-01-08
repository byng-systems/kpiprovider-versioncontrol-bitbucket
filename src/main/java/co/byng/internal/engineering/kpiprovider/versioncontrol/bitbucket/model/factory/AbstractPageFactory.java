/**
 * AbstractPageFactory.java
 * Created 23-Dec-2015 13:23:37
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.factory;

import co.byng.internal.engineering.kpiprovider.util.HttpConnectionFactory;
import co.byng.internal.engineering.kpiprovider.util.serialization.ObjectDeserializer;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.BitBucketModellable;
import co.byng.internal.engineering.kpiprovider.versioncontrol.model.factory.UrlLoadedModelFactory;
import java.io.IOException;
import java.net.URL;



/**
 * AbstractPageFactory
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public abstract class AbstractPageFactory <T extends BitBucketModellable> implements UrlLoadedModelFactory<T> {

    protected final ObjectDeserializer objectDeserializer;
    protected final HttpConnectionFactory httpConnectionFactory;



    public AbstractPageFactory(
        ObjectDeserializer objectDeserializer,
        HttpConnectionFactory httpConnectionFactory
    ) {
        this.objectDeserializer = objectDeserializer;
        this.httpConnectionFactory = httpConnectionFactory;
    }
    
    @Override
    public final T build(URL source) {
        try {
            return this.objectDeserializer.deserialize(
                this.httpConnectionFactory.createConnection(source).getInputStream(),
                this.getModelClass()
            );
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected abstract Class<T> getModelClass();
    
}
