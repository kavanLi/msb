package com.mashibing.internal.common.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author gejunqing
 * @version 1.0
 * @date 2024/1/11
 */
public class TxTrustManager implements X509TrustManager
{
    private static volatile TxTrustManager instance;
    private SSLSocketFactory sslFactory;

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

    public SSLSocketFactory getSSLSocketFactory()
    {
        return this.sslFactory;
    }

    private TxTrustManager()
    {
    }

    public static TxTrustManager instance() throws NoSuchAlgorithmException, KeyManagementException
    {
        if (instance == null)
        {
            synchronized (TxTrustManager.class)
            {
                if (instance == null)
                {
                    instance = new TxTrustManager();
                    SSLContext sc = SSLContext.getInstance("TLSv1.2");
                    sc.init(null, new TxTrustManager[] { instance }, null);
                    instance.sslFactory = sc.getSocketFactory();
                }
            }
        }
        return instance;
    }
}
