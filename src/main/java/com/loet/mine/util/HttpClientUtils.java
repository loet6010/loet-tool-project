package com.loet.mine.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Http工具类
 */
public class HttpClientUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.67 Safari/537.31";

    /**
     * 发送POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String getPostUrlAsString(String url, Map<String, String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return null;
        }
        List<NameValuePair> naps = new ArrayList<>();
        for (Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                naps.add(pair);
            }
        }
        return getPostUrlAsString(url, naps);
    }

    /**
     * 发送POST请求
     *
     * @param url
     * @param naps
     * @return
     * @throws Exception
     */
    private static String getPostUrlAsString(String url, List<NameValuePair> naps) throws Exception {
        url = getUrl(url);
        String content = null;
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(30000).
                setConnectTimeout(30000).build();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setUserAgent(USER_AGENT);

        if (url.startsWith("https:")) {
            supportSSL(httpClientBuilder);
        }

        try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(naps, "UTF-8"));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    content = EntityUtils.toString(entity, "UTF-8");
                } else {
                    logger.error("getPostUrlAsString error,status[{}],url[{}]", status, url);
                }
            } finally {
                httpPost.abort();
            }
        }
        return content;
    }

    /**
     * 支持SSL，绕过SSL认证
     *
     * @param httpClientBuilder
     * @throws Exception
     */
    private static void supportSSL(HttpClientBuilder httpClientBuilder) throws Exception {
        SSLContext ctx = SSLContext.getInstance("TLS");
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        ctx.init(null, new TrustManager[]{tm}, null);
        httpClientBuilder.setSSLContext(ctx);
    }

    /**
     * 获取url
     *
     * @param url
     * @return
     */
    private static String getUrl(String url) {
        url = url.trim();
        return url;
    }

}
