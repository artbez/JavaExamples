package com.mycompany.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import org.apache.http.ParseException;
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
	public static Statement stmt;
	public static final String tableName = "userEx1"; 
	
    public static void main( String[] args ) throws Exception
    {
    	 Class.forName("com.mysql.jdbc.Driver").newInstance();
         final Connection conn =
                 DriverManager.getConnection("jdbc:mysql://localhost/test");

        stmt = conn.createStatement();
    	
    	int port = 8080;
        SSLContext sslContext = null;
        
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
        		try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
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
			
			if (method.equals("POST") && request instanceof HttpEntityEnclosingRequest) {
				HttpEntity httpEntity = ((HttpEntityEnclosingRequest) request).getEntity();
				insertToTable(httpEntity);
			}
			
			String message = getMessage(method);
			
			HttpCoreContext coreContext = HttpCoreContext.adapt(context);
			HttpConnection conn = coreContext.getConnection(HttpConnection.class);
			response.setStatusCode(HttpStatus.SC_OK);
			response.setEntity(new StringEntity(message, ContentType.TEXT_PLAIN));
			System.out.println(conn + ": send message " + message);
		}
		
		void insertToTable(HttpEntity httpEntity) throws ParseException, IOException {
			String myEntityString = EntityUtils.toString(httpEntity);
			String[] pairs = myEntityString.split("\r\n");
			Map<String, String> map = new HashMap<String, String>();
			map.put(pairs[0].split(":\r")[0], pairs[0].split(":\r")[1]);
			map.put(pairs[1].split(":\r")[0], pairs[1].split(":\r")[1]);
			String sql = "INSERT INTO " + tableName + " (name, message) VALUES (\'" 
							+ map.get("login") + "\',\'" + map.get("message") + "\')";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String getMessage(String method) {
			
			String message = null;
			if (method.equals("GET")) {
				message = "";
				String sql = "SELECT * FROM " + tableName;
				try {
					ResultSet rs = stmt.executeQuery(sql);
					while(rs.next()){
		                String name = rs.getString("name");
		                String message2 = rs.getString("message");
		                message += name + ": " + message2 + "\n";
		            }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (method.equals("POST")) {
				message = "Added";
			}
			if (method.equals("DELETE")) {
				String sql = "TRUNCATE TABLE " + tableName;
				try {
					stmt.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				message = "Cleaned";
			}
			
			return message;
		}
    }
}
