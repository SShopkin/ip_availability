package socketexam;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	private static Scanner in;
	static List<String> currentlyLoggedUsers = new LinkedList<String>();
	static Map<String, Integer> usersToLoginCount = new HashMap<String, Integer>();
	
	public static void login_command(String name){
		if(usersToLoginCount.get(name)==null){
			usersToLoginCount.put(name,1);
			currentlyLoggedUsers.add(name);
			System.out.print("ok\n");
		} else if(currentlyLoggedUsers.contains(name)) {
			System.out.println("error:already logged");			
		} else {
			Integer a=usersToLoginCount.get(name);
			a+=1;
			usersToLoginCount.put(name,a);
			currentlyLoggedUsers.add(name);
			System.out.print("ok\n");
		}
	}
	
	public static void logout_command(String name){
		if(currentlyLoggedUsers.contains(name)){
			currentlyLoggedUsers.remove(name);
			System.out.print("ok\n");
		} else {
			System.out.println("error:notlogged");	
		}		
	}
	
	public static void info_command(String name,String lookingFor){
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
			System.out.println(displayInfo);
		} else {
			System.out.println("error:notlogged");	
		}	
	}
	
	public static void listavailable_command(String name){
		if(currentlyLoggedUsers.contains(name)){
			System.out.print("ok");
			for (String in : currentlyLoggedUsers) {											
				    System.out.print(":"+in);
			}	System.out.println();					
		} else {
			System.out.println("error:notlogged");	
		}
	}
	
	public static void shutdown_command(String name){
		if(currentlyLoggedUsers.contains(name)){
			System.out.println("ok");
			System.exit(0);
		} else {
			System.out.println("error:notlogged");
		}
		
	}
	public static void main(String[] args) {		
		while(true){
			System.out.print("Enter command\n");
			in = new Scanner(System.in);
			final String user ; 
			final String[] line=in.nextLine().split(":");				
			user=line[0];			
			final String command=line[1];
			switch(command){		
				case "login":
					login_command(user);
					break;
				case "logout":
					logout_command(user);
					break;
				case "info":
					String lookingFor=line[2];
					info_command(user,lookingFor);
					break;
				case "listavailable":
					listavailable_command(user);
					break;
				case "shutdown":
					shutdown_command(user);
					break;
				default:
					System.out.println("error:unknowncommand");
					break;				
			}
			in.close();
		}
	}	
}
