

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	static Map<String, Integer> nameToTime = new HashMap<String, Integer>();
	public static void main(String[] args) {
		while(true){
				System.out.print("Enter your name:");
				final Scanner in = new Scanner(System.in);
				final String user ; 
				final String[] line=in.nextLine().split(":");				
				user=line[0];			
				final String command=line[1];
				
				if(command.equals("info")){
					Integer c=nameToTime.get(user);
					if((c==null)||(c%2==0)){
						System.out.println("error:notlogged");
					} else {
						final String name=line[2];
						Integer a=nameToTime.get(name);
						Boolean b;
						if((a%2)==0){
							b=false;
							a=a/2;
						}else {
							b=true;		
							a=(a/2)+1;
						}						
						System.out.println("ok:"+name+":"+b+":"+a);
					}
				} else if(command.equals("listavailable")) {
					Integer a=nameToTime.get(user);
					if((a==null)||(a%2==0)){
						System.out.println("error:notlogged");
					} else {
						System.out.print("ok");
						for (String key: nameToTime.keySet()) {
							Integer b=nameToTime.get(key);
							if((b%2)!=0){								
							    System.out.print(":"+key);
							}						
						}
					}
					System.out.println();
				} else if(command.equals("shutdown")){
					Integer a=nameToTime.get(user);
					if((a==null)||(a%2==0)){
						System.out.println("error:notlogged");
					} else {
						in.close();
						System.out.println("ok");
						break;
						
					}
				} else  {
						if ((command.equals("login"))||(command.equals(" login"))){					
							in(user);
						} else if ((command.equals("logout"))||(command.equals(" logout"))){
							out(user);
						} else {
							System.out.println("Wrong command");
						}
					}
			
		}
	}
	
	public static void in(String name){
			if(nameToTime.get(name)==null){
				nameToTime.put(name,1);
				System.out.print("ok\n");
			} else {
				Integer b=nameToTime.get(name);
				if(b%2!=0){
					System.out.println("error:already logged");
				} else {
					Integer a;
					a=nameToTime.get(name);
					a=a+1;
					nameToTime.put(name,a);
					System.out.print("ok\n");
				}
			}		
	  }
	
	public static void out(String name){
		if(nameToTime.get(name)!=0){
			Integer a=nameToTime.get(name);
			a=a+1;		
			nameToTime.put(name,a);
			System.out.println("ok");
		}else{
			System.out.println("error:notlogged");
		}
	  }

}
  
