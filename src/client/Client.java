package client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

import m2.MyGame;
import graphicslib3D.Vector3D;
import sage.networking.client.GameConnectionClient;

public class Client extends GameConnectionClient{
	
	private MyGame game;
	private UUID id;
	private Vector ghostAvatars;
	
	private ArrayList<UUID> otherPlayers;

	public Client(InetAddress remoteAddr, int remotePort, ProtocolType protocolType, MyGame game) throws IOException {
		super(remoteAddr, remotePort, protocolType);
		this.game = game;
		id = UUID.randomUUID();
		ghostAvatars = new Vector();
		
		otherPlayers = new ArrayList<UUID>();
	}
	
	protected void processPacket(Object msg){
		
		
		String message = (String) msg;
		String[] msgTokens = message.split(",");
	
	/*	UUID ghostID = UUID.fromString(msgTokens[1]);
		
		String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
		String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
		String[] cameraRot = {msgTokens[8], msgTokens[9], msgTokens[10]};
	*/	
		
		
		if(msgTokens[0].compareTo("join")==0)
		{
			if(msgTokens[1].compareTo("success")==0)
			{
				
				if(game.isConnected()==false)
				{
				game.setIsConnected(true);
			    sendCreateMessage(game.getPlayerPosition(), game.getCameraBoxPosition(), game.getCameraBoxRotation1(), game.getCameraBoxRotation2());
				System.out.println("\nJoined the Game Successfully.\n");
	
				}
			}
			if(msgTokens[1].compareTo("failure")==0){
				
				game.setIsConnected(false);
				System.out.println("Could Not Join the Game.\n");
			}	
		}
		
		
		if(msgTokens[0].compareTo("bye")==0)
		{
			
			UUID ghostID = UUID.fromString(msgTokens[1]);
			removeGhostAvatar(ghostID);
		}
		
		if(msgTokens[0].compareTo("newcomer")==0)
		{
			sendCreateMessage(game.getPlayerPosition(), game.getCameraBoxPosition(), game.getCameraBoxRotation1(), game.getCameraBoxRotation2());
		}
		
		
		
		if(msgTokens[0].compareTo("create")==0){

			
			UUID ghostID = UUID.fromString(msgTokens[1]);
			
			
			
			String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
			String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
			String[] cameraRot1 = {msgTokens[8], msgTokens[9], msgTokens[10]};
			String[] cameraRot2 = {msgTokens[11], msgTokens[12], msgTokens[13]};
			

			if(ghostID!=id)
			{
				boolean addMe = true;
				
				for(int i = 0; i<otherPlayers.size(); i++)
				{
					boolean thisGhost = otherPlayers.get(i).equals(ghostID);
					
					if(thisGhost)
					{
						addMe = false;
					}
					
				}
				
				if(addMe)
				{
				
					createGhostAvatar(ghostID, pos, cameraPos, cameraRot1, cameraRot2);
					otherPlayers.add(ghostID);
				}
			}
			
		}
		
				
		if(msgTokens[0].compareTo("move")==0){
	//		UUID ghostID = UUID.fromString(msgTokens[1]);
			
	//		String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
			
			UUID ghostID = UUID.fromString(msgTokens[1]);
			
			String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
			String[] cameraPos = {msgTokens[5], msgTokens[6], msgTokens[7]};
			String[] cameraRot1 = {msgTokens[8], msgTokens[9], msgTokens[10]};
			String[] cameraRot2 = {msgTokens[11], msgTokens[12], msgTokens[13]};
			

			
			if(ghostID!=id)
			{
			game.updateGhost(ghostID, pos, cameraPos, cameraRot1, cameraRot2);
			}
		}
	}
	

	
	
	
	private void createGhostAvatar(UUID ghostID, String[] pos, String[] cameraPos, String[] cameraRot1, String[] cameraRot2) {
		game.createGhostAvatar(ghostID, pos, cameraPos, cameraRot1, cameraRot2);
		
	}

	private void removeGhostAvatar(UUID ghostID) {
		game.removeGhost(ghostID);
		
	}

	
	public void sendCreateMessage(Vector3D pos, Vector3D cameraPos, Vector3D cameraRot1, Vector3D cameraRot2){
		try{
			String message = new String("create,"+ id.toString());
		 
			message += ","+pos.getX()+","+pos.getY()+","+pos.getZ();
			
			message += ","+cameraPos.getX()+","+cameraPos.getY()+","+cameraPos.getZ();
			
			message += ","+cameraRot1.getX()+","+cameraRot1.getY()+","+cameraRot1.getZ();
			
			message += ","+cameraRot2.getX()+","+cameraRot2.getY()+","+cameraRot2.getZ();
			
			sendPacket(message);
		}
		catch(IOException e){e.printStackTrace();}
	}
	
	public void sendMoveMessage(Vector3D pos, Vector3D cameraPos, Vector3D cameraRot1, Vector3D cameraRot2){
			try{
			
				String message = new String("move,"+id.toString());
				
				message += ","+pos.getX()+","+pos.getY()+","+pos.getZ();
				
				message += ","+cameraPos.getX()+","+cameraPos.getY()+","+cameraPos.getZ();
				
				message += ","+cameraRot1.getX()+","+cameraRot1.getY()+","+cameraRot1.getZ();
				
				message += ","+cameraRot2.getX()+","+cameraRot2.getY()+","+cameraRot2.getZ();
				
				sendPacket(message);
			}
			catch(IOException e){e.printStackTrace();}
		}
	
	
	public void sendJoinMessage(){
		try{
			sendPacket(new String("join,"+id.toString()));
		}catch(IOException e){e.printStackTrace();}
	}

	public void sendByeMessage(){
			
		try{
			sendPacket(new String("bye,"+id.toString()));
			this.shutdown();
		}catch(IOException e){e.printStackTrace();}
	}
		
	
	

	
	
}
