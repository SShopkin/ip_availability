package socketexam;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
	private static final String COMMAND_STOP_SERVER = "stopServer";
	static List<String> currentlyLoggedUsers = new LinkedList<String>();
	static Map<String, Integer> usersToLoginCount = new HashMap<String, Integer>();
	
	//private Map<String, User> allusers = Collections.synchronizedMap(new HashMap<String, User>()); 

	private Server server;
	

	private Socket socket;

	public Client(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}
	
	
	
	public String login_command(String name) {
		User user = server.getallusers().get(name);
		if(server.getallusers().containsKey(name)){
			user.stopSession();
			user.beginSession();
		} else {
			server.getallusers().put(name, new User(name,socket));
		}
		return "ok";
	}
	
	
	
	public String logout_command(String name) throws IOException{
		User user = server.getallusers().get(name);
		if(user.isItLoged()){
			user.stopSession();
			return "ok";
		} else {
			return"error:notlogged";	
		}		
	}
	/*
	public static void info_command(String name,String lookingFor) throws IOException{
		final PrintStream out =	new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.contains(name)){
			String displayInfo="ok:"+lookingFor;
			if(currentlyLoggedUsers.contains(lookingFor)){
				displayInfo+=":true:";
			} else {
				displayInfo+=":false:";
			};
			if(usersToLoginCount.get(lookingFor)==null){
				displayInfo+="0";
			} else {
				displayInfo+=usersToLoginCount.get(lookingFor);
			}
			out.println(displayInfo);
		} else {
			out.println("error:notlogged");	
		}	
	}*/
	
	public void run() {
		try {
			final PrintStream out =	new PrintStream(socket.getOutputStream());
			String result = "error:unknowncommand";
			final Scanner scanner =	new Scanner(socket.getInputStream());
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				if (COMMAND_STOP_SERVER.equals(line)) {
					break;
				}
				final String separetedLine[]=line.split(":");
				if (separetedLine[1].equals("login")) {
					result=login_command(separetedLine[0]);					
				} else if (separetedLine[1].equals("logout")){
					result=logout_command(separetedLine[0]);
				} else if (separetedLine[1].equals("info")){
					//info_command(separetedLine[0],separetedLine[2]);
				} 
				out.println(result);
				result="error:unknowncommand";
			}
			scanner.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}