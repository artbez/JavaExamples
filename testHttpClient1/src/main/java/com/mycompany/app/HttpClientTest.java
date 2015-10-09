package com.mycompany.app;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by artemiibezguzikov on 08.10.15.
 */

class HttpClientTest {

    private CloseableHttpClient client;
    private String host = "http://localhost:8080/";

    public HttpClientTest() throws Exception {
        client = HttpClients.createDefault();
     }

    protected void finalize ( ) {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String str) throws Exception {

        HttpPost httpPost = new HttpPost(host);
        StringEntity myEntity = new StringEntity(str,
                ContentType.create("text/plain", "UTF-8"));
        httpPost.setEntity(myEntity);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println(responseString);

        }
    }

    public void getAll() throws Exception {
        HttpGet httpGet = new HttpGet(host);

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println(responseString);
        }
    }

    public void deleteAll() throws Exception {
        HttpDelete httpDelete = new HttpDelete(host);

        CloseableHttpResponse response = client.execute(httpDelete);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);
            System.out.println(responseString);
        }
    }
}
