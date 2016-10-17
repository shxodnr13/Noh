import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;



public class Guest extends Thread{
	String id; // args0 chat room number
	
	Server server;
	Socket socket;
	BufferedReader br;
	BufferedWriter bw;
	
	Guest(Server server, Socket socket) throws Exception {
		this.server = server;
		this.socket = socket;
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		
		OutputStream os =  socket.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		bw = new BufferedWriter(osw);
	}
	
	public void run() {
		try {
			while(true)
			{
				String line = br.readLine();
				System.out.println(line + "Read");
				String array[] = line.split("/");
				switch(array[0])
				{
				case "enter":
					id = array[1];
					server.makeGuestlist();
					server.broadcast(line);
					server.makeRoomlist();
					break;
					
				case "msg" :
					server.broadcastRoom(array[2], "msg/"+id+"/"+array[3]);
					break;
				
				case "exit" :
					String str2 = "exit/" +array[1];
					server.broadcast(str2);
					break;
					
				case "�ӼӸ�":
					String[] talk = array[1].split("&");
					server.talkMsg(talk[0], talk[1], talk[2]);
					break;
					
					//talk[0] : Send person
					//talk[1] : receive person
					//talk[2] : chat
				
				case "mkroom" :
					server.addRoom(array[1], this);
					server.removeGuest(this);
					server.makeGuestlist();
					server.makeguestlistRoom(array[1]);
					server.makeRoomlist();
					break;
					
				case "roomjoin" :
					server.removeGuest(this);
					server.makeGuestlist();
					server.addGuestRoom(array[2], this);
					server.makeguestlistRoom(array[2]);
					server.makeRoomlist();
					break;
				
				case "roomout" :
					server.removeGuestRoom(array[2], this);
					server.removeRoom(array[2]);
					server.makeGuestlist();
					server.makeRoomlist();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			
			try {
				
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
		}
	}

	public void sendMsg(String msg) throws Exception {
		bw.write(msg + "\n");
		bw.flush();
	}
}
