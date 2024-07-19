package com.mashibing.internal.common.utils;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gejunqing
 * @version 1.0
 * @date 2024/1/11
 */
public class X509TrustSingleton implements X509TrustManager
{
    private static volatile X509TrustSingleton instance;
    private static volatile SSLSocketFactory sslFactory;

    private X509TrustSingleton() {
    }


    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException
    {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers()
    {
        return new X509Certificate[0];
    }


    public static X509TrustSingleton getInstance() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
        if (instance == null) {
            synchronized (X509TrustSingleton.class) {
                if (instance == null) {
                    TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                            TrustManagerFactory.getDefaultAlgorithm());
                    trustManagerFactory.init((KeyStore) null);
                    TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                    if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                        throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                    }
                    X509TrustManager trustManager = (X509TrustManager) trustManagers[0];

                    SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
                    sslContext.init(null, new TrustManager[]{trustManager}, null);
                    SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
                    instance = new X509TrustSingleton();
                    sslFactory = sslSocketFactory;
                }
            }
        }
        return instance;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return sslFactory;
    }
}
