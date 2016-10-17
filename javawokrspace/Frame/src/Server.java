import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

 class Server {

	ArrayList<Guest> list;
	HashMap<String, ArrayList<Guest>> map;
	ArrayList<Guest> arraylist;
	ArrayList<Guest> peoplelist;
	void initNet() throws Exception {
		map = new HashMap<String, ArrayList<Guest>>();
		arraylist = new ArrayList<Guest>();
		list = new ArrayList<Guest>();
		ServerSocket ss = new ServerSocket(8888);
		while(true) {
			Socket s = ss.accept();
			Guest g = new Guest(this, s);
			g.start();
			addGuest(g);
		}
	}
	
	///////////////////////////////////////////////////
	void removeRoom(String rn)
	{
		if(map.get(rn).size()==0)
		{
			map.remove(rn);
		}
	}
	
	void removeGuestRoom(String rn, Guest guest) throws Exception
	{
		map.get(rn).remove(guest);
		map.get(rn);
		broadcastRoom(rn, "out/"+guest.id);
	}
	
	////////////////////////////////////////////////////
	void addGuestRoom(String rn, Guest guest)
	{
		ArrayList<Guest> list2 = map.get(rn);
		list2.add(guest);
		System.out.print("roomname" + rn + ",");
		System.out.println("People Count : " + list2.size());
	}
	
	void addRoom(String roomname, Guest guest)
	{
		ArrayList<Guest> arraylist2 = new ArrayList<Guest>();
		arraylist2.add(guest);
		map.put(roomname, arraylist2);
		System.out.println("Created Room: " + roomname);
		System.out.println("Number of Users: " + arraylist2.size());
		
		
	}
	
	void addGuest(Guest g)
	{
		list.add(g);
		System.out.println("Number of connectors: "+ list.size());
	}
	
	void makeRoomlist() throws Exception {
		Set<String> roomlist = map.keySet();
		StringBuffer buffer = new StringBuffer("roomlist/");
		for(String t : roomlist ) 
		{
			buffer.append(t + "/");
		}
		broadcast(buffer.toString());
		Roomnumber(roomlist); 
	}
	
	void Roomnumber(Set<String> roomlist) throws Exception {
		
		StringBuffer buffer2 = new StringBuffer("roomnum/");
		for(String t : roomlist)
		{
			buffer2.append(map.get(t).size()+"/");
		}
		
		broadcast(buffer2.toString());
	}
	////////////////////////////////////////////////////////////
	public void talkMsg(String talk, String talk2, String talk3)
	{
		for(Guest g : list)
		{
			if(g.id.equals(talk2)){
				
			
			try {
				g.sendMsg("private/" + talk + "&" + talk2 + "&" + talk3);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error is occured" + e.getMessage());
			}
		}
		}
	}
	
	void removeGuest(Guest g)
	{
		list.remove(g);
		System.out.println("number of connectors" + list.size());
	}
	void broadcast(String msg) throws Exception {
		try {
			for (Guest g : list)
			{
				g.sendMsg(msg);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	void broadcastRoom(String rn, String msg) throws Exception {
		ArrayList<Guest> list2 = map.get(rn);
		for(Guest g : list2)
		{
			g.sendMsg(msg);
			
		}
	}
	
	void makeGuestlist() throws Exception
	{
		StringBuffer buffer = new StringBuffer("guestlist/"); //guestlist/
		for (Guest g : list) {
			buffer.append(g.id + "/");
		}
		broadcast(buffer.toString());

	}
	
	void makeguestlistRoom(String rn) throws Exception
	{
		ArrayList<Guest> list2 = map.get(rn);
		StringBuffer buffer = new StringBuffer("guestlistRoom/"); //guestlistRoom/이름/이름2
		peoplelist=list2;
		for (Guest g : list2) {
			buffer.append(g.id + "/");
		}
		broadcastRoom(rn, buffer.toString()); // 다방 , guestlistRoom/이름/이름2

	}
	
	
	public static void main(String args[]) throws Exception
	{
		Server server = new Server();
		server.initNet();
	}
}
