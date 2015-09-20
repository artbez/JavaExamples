import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.net.ServerSocket; 
import java.net.Socket; 

public class TcpServer { 
	public static void main(String[] args) { 
		
		int port = 9999;
	
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("порт: " + port + "занят");
			System.exit(-1);
		}

		Socket clientSocket = null;
		try {
			clientSocket = serverSocket.accept();
		} catch(Exception e) {
			System.out.println("Не удалось подключиться");
		}
		
		InputStream in = null;
		try {
			in = clientSocket.getInputStream();
		} catch(Exception e) {
			System.out.println("Ошибка потока ввода");
			System.exit(-1);
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String ln = null;
		try {
			while ((ln = reader.readLine()) != null) {
				System.out.println(ln);
				System.out.flush();
			}
		} catch (Exception e) {
			System.out.println("Ошибка при чтении сообщения");
			System.exit(-1);
		}
	}
}