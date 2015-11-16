package socketexam;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private final int port;
	private Map<String, User> allusers = Collections.synchronizedMap(new HashMap<String, User>()); 
	
	
	public Map<String, User> getallusers(){
		return allusers;
	}
	
	public Server(int port) {
		this.port = port;
	}

	public void startServer() throws IOException {
		final ServerSocket serverSocket = new ServerSocket(port);
		final Socket socket = serverSocket.accept();		
		final Client client = new Client(socket,this);
		
		client.run();		
		serverSocket.close();
	}

}