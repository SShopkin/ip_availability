package socketexam;

import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
	private String name;
	private boolean isItLogin;
	private int howTimesIsLogin;
	private Interval whenIsIn;
	private Socket socket;
	private List<Interval> invited = new LinkedList<Interval>();
	
	public User(String name, Socket socket){
		this.name=name;
		this.socket=socket;
		isItLogin = true;
		howTimesIsLogin = 1;
		whenIsIn = new Interval();
		whenIsIn.setFrom(new Date(System.currentTimeMillis()));
	}
	
}
