package socketexam;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Client implements Runnable{
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

	public String logout_command(String name){
		User user = server.getallusers().get(name);
		if(user.isItLoged()){
			user.stopSession();
			return "ok";
		} else {
			return"error:notlogged";	
		}		
	}
	
	public String shutdown_command(String name) throws IOException {
		User user = server.getallusers().get(name);
		if(user!=null){
			if(user.isItLoged()){
				user.stopSession();
				Map<String, User> users = server.getallusers();
				for(Map.Entry<String, User> i : users.entrySet() ){
					if(i.getValue().getSocket()!=null){
						i.getValue().getSocket().close();
					}
				}
				return "Successful shutdown";
			}				
		}		
		return"error:notlogged";
	}
	
	public String info_command(String name,String lookingFor){
		String result="";
		User user = server.getallusers().get(lookingFor);
		User mainUser = server.getallusers().get(name);
		if((user!=null)&&(mainUser!=null)&&(mainUser.isItLoged())){
			result = "ok:"+lookingFor+":"+user.isItLoged()+":"+user.getHowTimesIsLogin();			
			if(!user.isItLoged()) result+=":"+user.allTimes();
			return result;
			} else if((mainUser!=null)&&(mainUser.isItLoged())) {
				return "ok:"+lookingFor+":false:0";
			}
		return "error:notlogged";		
	}
	
	public String listabsent_command(String name){
		User user = server.getallusers().get(name);
		Map<String, User> users = server.getallusers();
		String result="ok";
		if(user!=null){
			if(user.isItLoged()){
				for(Map.Entry<String, User> i : users.entrySet() ){
					if(!i.getValue().isItLoged()){
						result+=":"+i.getValue().getName();
					}
				}
				return result;	
			}			
		}
		return "error:notlogged";		
	}
	
	public String listavailable_command(String name){
		User user = server.getallusers().get(name);
		Map<String, User> users = server.getallusers();
		String result="ok";
		if(user!=null){
			if(user.isItLoged()){
				for(Map.Entry<String, User> i : users.entrySet() ){
					if(i.getValue().isItLoged()){
						result+=":"+i.getValue().getName();
					}
				}
				return result;	
			}			
		}
		return "error:notlogged";		
	}
	
	public void run() {
		try {
			final PrintStream out =	new PrintStream(socket.getOutputStream());
			String result = "error:unknowncommand";
			final Scanner scanner =	new Scanner(socket.getInputStream());
			while (scanner.hasNextLine()) {
				final String line = scanner.nextLine();
				final String separetedLine[]=line.split(":");
				if(separetedLine.length>1){
					if (separetedLine[1].equals("login")) {
						result=login_command(separetedLine[0]);					
					} else if (separetedLine[1].equals("logout")){
						result=logout_command(separetedLine[0]);
					} else if (separetedLine[1].equals("shutdown")){
						result=shutdown_command(separetedLine[0]);
					} else if (separetedLine[1].equals("listabsent")){
						result=listabsent_command(separetedLine[0]);
					} else if (separetedLine[1].equals("listavailable")){
						result=listavailable_command(separetedLine[0]);
					} else if (separetedLine[1].equals("info")){
						result=info_command(separetedLine[0],separetedLine[2]);
					} 
				}
				if("Successful shutdown".equals(result)){					
					break;
				}
				out.println(result);
				result="error:unknowncommand";
			}
			server.setRunning(false);
			scanner.close();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}