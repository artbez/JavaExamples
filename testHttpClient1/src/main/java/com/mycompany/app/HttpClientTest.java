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
import java.util.HashMap;
import java.util.Map;
/**
 * Created by artemiibezguzikov on 08.10.15.
 */

class HttpClientTest {

    private CloseableHttpClient client;
    private String host = "http://localhost:8080/";
    private String name;

    public HttpClientTest(String name) throws Exception {
        client = HttpClients.createDefault();
        this.name = name;
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
        StringBuilder sb = new StringBuilder();
        Map<String, String> env = new HashMap<String, String>();
        env.put("login", name);
        env.put("message", str);

        for (Map.Entry<String, String> envEntry : env.entrySet()) {
            sb.append(envEntry.getKey())
                    .append(":\r").append(envEntry.getValue())
                    .append("\r\n");
        }

        StringEntity myEntity = new StringEntity(sb.toString(),
                ContentType.create("text/plain", "UTF-8"));
        httpPost.setEntity(myEntity);

        CloseableHttpResponse response = client.execute(httpPost);
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
    }
}
