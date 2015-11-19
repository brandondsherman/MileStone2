package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import graphicslib3D.Vector3D;
import sage.networking.server.GameConnectionServer;
import sage.networking.server.IClientInfo;



public class Server extends GameConnectionServer<UUID>
{


private ArrayList<UUID> currentClients = new ArrayList<UUID>();



	public Server(int localPort) throws IOException{
		super(localPort, ProtocolType.TCP);
		System.out.println("\nServer Created.\nServer IP Address: " + Inet4Address.getLocalHost().getHostAddress() + "\nServer Port: " + localPort);
	

	}
	
	
	
	public void acceptClient(IClientInfo ci, Object o){
		String message = (String) o;
		String[] messageTokens = message.split(",");
		
		if(messageTokens.length>0){
			if(messageTokens[0].compareTo("join")==0){
			
				UUID clientID = UUID.fromString(messageTokens[1]);
				boolean addClient = true;
				for(int i =0; i<currentClients.size();i++)
				{
					if(clientID==currentClients.get(i))
							{
						addClient = false;
							}
					else
					{
						
					}
				}
				if(addClient)
				{
				currentClients.add(clientID);
				addClient(ci, clientID);
				sendJoinedMessage(clientID,true);
				
				}
				
			}
		}
	}
	
	public void processPacket(Object o, InetAddress senderIP, int sndPort){
	
		super.processPacket(o, senderIP,  sndPort);
		
		if(o!=null)
		{
		String message = (String) o;
		
		if(message!=null)
		{
		String[] msgTokens = message.split(",");
	
		
			if(msgTokens.length>0){
				
				UUID clientID = UUID.fromString(msgTokens[1]);
			
				
				
				if(msgTokens[0].compareTo("bye")==0){
					sendByeMessages(clientID);
					removeClient(clientID);
					
					while(true)
					{
						for(int i = 0; i<currentClients.size(); i++)
						{
							boolean thisClient = currentClients.get(i).equals(clientID);
							
							if(thisClient)
							{
								currentClients.remove(i);
								System.out.println("\nServer: A Player Has Disconnected From the Session.");								
								break;
							}
						}
						break;
						
					}
					
					if(currentClients.size()==0)
					{
						/*
						System.out.println("All of the clients have disconnected from the session. Would you like to shutdown the server? (y/n)");
						Scanner scanner = new Scanner(System.in);
						String s = scanner.nextLine();
						scanner.close();
						if(s.charAt(0)=='y' || s.charAt(0)=='Y')
						{
							try {
								this.shutdown();
								System.exit(0);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else if(s.charAt(0)=='n' || s.charAt(0)=='N')
						{
							System.out.println("\nThe Server Will Remain Running.");
						}
						else
						{
							System.out.println("\nDid not understand the input. Will continue running server.");
						}
						*/
						
						System.out.println("\nAll clients have closed their connections. The server is going to shutdown...");
						try {
							this.shutdown();
							System.out.println("...Server Shutdown Succesffully.");
							System.exit(0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						System.out.println("There are still " + currentClients.size() + " clients connected to the session.");

					}
					
					

				}
				
				if(msgTokens[0].compareTo("create")==0){
					
					String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
					String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
					String[] cameraRot1 = {msgTokens[8], msgTokens[9], msgTokens[10]};
					String[] cameraRot2 = {msgTokens[11], msgTokens[12], msgTokens[13]};
					
					
					sendCreateMessages(clientID, pos, cameraPos, cameraRot1, cameraRot2);
			
				}
				
				if(msgTokens[0].compareTo("dsfr")==0){

				}
				
				if(msgTokens[0].compareTo("move")==0){

					
					String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
					String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
					String[] cameraRot1 = {msgTokens[8], msgTokens[9], msgTokens[10]};
					String[] cameraRot2 = {msgTokens[11], msgTokens[12], msgTokens[13]};
					
					sendMoveMessages(clientID, pos, cameraPos, cameraRot1, cameraRot2);
				}
				
				
				if(msgTokens[0].compareTo("")==0){
		//			UUID clientID = UUID.fromString(msgTokens[1]);
					
				}
			}
		}
	}
}



	private void sendCreateMessages(UUID clientID, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2) {
		try{
			String message = new String("create," + clientID);
			message += "," + pos[0];
			message += "," + pos[1];
			message += "," + pos[2];
			message += "," + cameraPos[0];
			message += "," + cameraPos[1];
			message += "," + cameraPos[2];
			message += "," + cameraRot1[0];
			message += "," + cameraRot1[1];
			message += "," + cameraRot1[2];
			message += "," + cameraRot2[0];
			message += "," + cameraRot2[1];
			message += "," + cameraRot2[2];
			
			
			forwardPacketToAll(message, clientID);
		}catch(IOException e){e.printStackTrace();}
	}

	private void sendMoveMessages(UUID clientID, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2) {
		try{
			String message = new String("move," + clientID);
			message += "," + pos[0];
			message += "," + pos[1];
			message += "," + pos[2];
			message += "," + cameraPos[0];
			message += "," + cameraPos[1];
			message += "," + cameraPos[2];
			message += "," + cameraRot1[0];
			message += "," + cameraRot1[1];
			message += "," + cameraRot1[2];
			message += "," + cameraRot2[0];
			message += "," + cameraRot2[1];
			message += "," + cameraRot2[2];
			
			
			forwardPacketToAll(message, clientID);
		}catch(IOException e){e.printStackTrace();}
	}
	
	private void sendJoinedMessage(UUID clientID, boolean success) {
		try{
			String message = new String("join,");
			if(success) 
				{
				message += "success";
				sendPacket(message,clientID);
				
				System.out.println("\nServer: A New Client Has Joined the Session as Player " + currentClients.size() + ".\nClientID: " + clientID);
				
				
				String notifyJoin = new String("newcomer");
				forwardPacketToAll(notifyJoin, clientID);
				
				
				}
			else
				message += "failure";
			sendPacket(message,clientID);
		}catch(IOException e) {e.printStackTrace();}
		
	}
	
	private void sendByeMessages(UUID clientID){
		
				String message = new String("bye," + clientID);
				
				
				try{
				
				forwardPacketToAll(message, clientID);
				
				}catch(IOException e){e.printStackTrace();}
				
				

		
		
	}
	
	private void sendWantsDetailsMessages(UUID clientID) {
		// TODO Auto-generated method stub
		
	}
	
	private void sndDetailsMsg(UUID clientID, UUID remoteID, String[] position){
		
	}

	


	public static void main(String[] args) throws IOException
	{
		
		
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("\nWelcome to War (Working Title). This is the server application.\n\nPlease enter the port number you wish to use for the TCP connection:");
		
		int port = scanner.nextInt();
		scanner.close();
		
		Server server = new Server(port);
		
		
		
		
	}

}
