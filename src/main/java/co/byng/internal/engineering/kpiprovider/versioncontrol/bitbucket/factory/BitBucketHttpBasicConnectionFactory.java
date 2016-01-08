/**
 * BasicAuthHttpConnectionFactory.java
 * Created 15-Dec-2015 19:47:19
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.factory;

import co.byng.internal.engineering.kpiprovider.util.HttpConnectionFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * BitBucketHttpBasicConnectionFactory
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class BitBucketHttpBasicConnectionFactory extends HttpConnectionFactory.Impl {

    protected String authorization;

    public BitBucketHttpBasicConnectionFactory(String authorization) {
        this.authorization = authorization;
    }
    
    @Override
    public HttpURLConnection createConnection(URL url) throws IOException, ClassCastException {
        HttpURLConnection conn = super.createConnection(url);

        conn.setRequestProperty("Authorization", "Basic " + this.authorization);

        return conn;
    }
    
}
