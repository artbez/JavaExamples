package com.mycompany.app;

public class App
{

    public static void main( String[] args ) throws Exception
    {
        HttpClientTest httpTestClient = new HttpClientTest();
        httpTestClient.send("1 mes");
        httpTestClient.send("2 mes");
        httpTestClient.getAll();
        httpTestClient.deleteAll();
        Thread.sleep(10000);
        httpTestClient.send("1 tre");
        httpTestClient.send("2 tre");
        httpTestClient.getAll();
    }
}

