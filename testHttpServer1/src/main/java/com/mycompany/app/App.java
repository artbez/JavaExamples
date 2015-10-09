package com.mycompany.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.ConnectionClosedException;
import org.apache.http.ExceptionLogger;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.bootstrap.HttpServer;
import org.apache.http.impl.bootstrap.ServerBootstrap;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.util.EntityUtils;

public class App 
{
	
	public static String[] database;
	public static int currentIndex;
	
    public static void main( String[] args ) throws Exception
    {
        int port = 8080;
        SSLContext sslContext = null;
        database = new String[100];
        currentIndex = 0;
        
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoTimeout(15000)
                .setTcpNoDelay(true)
                .build();
        
        final HttpServer server = ServerBootstrap.bootstrap()
        		.setListenerPort(port)
        		.setServerInfo("Test/1.1")
        		.setSocketConfig(socketConfig)
        		.setSslContext(sslContext)
        		.setExceptionLogger(new StdErrorExceptionLogger())
        		.registerHandler("*", new MyHttpHandler())
        		.create();
        
        server.start();
        server.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
        	public void run() {
        		server.shutdown(5, TimeUnit.SECONDS);
        	}
        });
    }
    
    static class StdErrorExceptionLogger implements ExceptionLogger {

        public void log(final Exception ex) {
            if (ex instanceof SocketTimeoutException) {
                System.err.println("Connection timed out");
            } else if (ex instanceof ConnectionClosedException) {
                System.err.println(ex.getMessage());
            } else {
                ex.printStackTrace();
            }
        }

    }

    
    static class MyHttpHandler implements HttpRequestHandler {

    	public MyHttpHandler() {
    		super();
    	}
    	
		public void handle(HttpRequest request, HttpResponse response, HttpContext context)
				throws HttpException, IOException {
			// TODO Auto-generated method stub
			String method = request.getRequestLine().getMethod().toUpperCase(Locale.ROOT);
			if (!method.equals("GET") && !method.equals("POST") 
					&& !method.equals("HEAD") && !method.equals("DELETE")) {
				throw new MethodNotSupportedException(method + " method not supported");
			}
			
			String target = request.getRequestLine().getUri();
			
			if (request instanceof HttpEntityEnclosingRequest) {
				HttpEntity httpEntity = ((HttpEntityEnclosingRequest) request).getEntity();
				String myEntityString = EntityUtils.toString(httpEntity);
				System.out.println("Entity is " + myEntityString);
				App.database[App.currentIndex] = myEntityString;
				App.currentIndex++;
			}
			
			String message = getMessage(method);
			
			HttpCoreContext coreContext = HttpCoreContext.adapt(context);
			HttpConnection conn = coreContext.getConnection(HttpConnection.class);
			response.setStatusCode(HttpStatus.SC_OK);
			response.setEntity(new StringEntity(message, ContentType.TEXT_PLAIN));
			System.out.println(conn + ": send message " + message);
		}
		
		String getMessage(String method) {
			
			String message = null;
			if (method.equals("GET")) {
				message = "";
				for (int i = 0; i < App.currentIndex; ++i) {
					message += App.database[i] + "\n";
				}
			}
			if (method.equals("POST")) {
				message = "Added";
			}
			if (method.equals("DELETE")) {
				App.currentIndex = 0;
				message = "Cleaned";
			}
			
			return message;
		}
    }
}
