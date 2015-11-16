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
	private boolean running;
	
	
	public Map<String, User> getallusers(){
		return allusers;
	}
	
	public Server(int port) {
		this.port = port;
	}

	public void startServer() throws IOException {
		setRunning(true);
		final ServerSocket serverSocket = new ServerSocket(port);
		while(running){
			final Socket socket = serverSocket.accept();		
			final Client client = new Client(socket,this);		
			new Thread(client).start();	
		}	
		serverSocket.close();
	}

	public synchronized void setRunning(boolean run) {
		running=run;		
	}
	
	

}