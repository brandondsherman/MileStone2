//Kian Faroughi
//Csc165 - Assignment 1
//Doctor Gordon
//CSUS Fall 2015

package m2;



import java.io.IOException;

import java.util.Scanner;
public class Starter {
	


	public static void main(String[] args) throws IOException
	{
		
		
		/*
		Scanner scanner = new Scanner(System.in);
		
		
		System.out.println("\n\nWelcome to War (Working Title). This is the Client Application.");
		System.out.println("\nPlease Enter the IP Address of the Server Application:");
		String serverAddress = scanner.nextLine();
		System.out.println("\nPlease enter the Server TCP Port Number:");
		int serverPort = scanner.nextInt();
	

		MyGame game = new MyGame(serverAddress,serverPort);
		
		
		scanner.close();
		
		System.out.println();
		*/
		MyGame game = new MyGame("192.168.1.99", 80);
		game.start();
		
		
		
		

	
	
}

	

}
