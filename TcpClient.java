import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.IOException; 
import java.io.InputStreamReader; 
import java.io.OutputStream; 
import java.io.OutputStreamWriter; 
import java.net.Socket; 

public class TcpClient { 
	
	public static void main(String[] args) { 
	
		String host = "localhost"; 
		int port = 9999; 
	
		Socket socket = null; 
		try { 
			socket = new Socket(host, port); 
		} catch (IOException e) { 
			System.out.println("Ошибка при создании сокета"); 
			System.exit(-1); 
		} 
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
		
		OutputStream out = null; 
		try { 
			out = socket.getOutputStream(); 
		} catch (IOException e) { 
			System.out.println("Ошибка потока вывода"); 
			System.exit(-1); 
		}
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out)); 
		String ln = null; 
		try { 
			while ((ln = reader.readLine()) != null) { 
				writer.write(ln + "\n"); 
				writer.flush(); 
			} 
		} catch (IOException e) { 
			System.out.println("Ошибка записи сообщения"); 
			System.exit(-1); 
		} 
	}

}