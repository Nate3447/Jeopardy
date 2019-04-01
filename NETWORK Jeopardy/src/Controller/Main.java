package Controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;

import Model.*;

public class Main {
	
	public static void main(String[] args) throws Exception {

		UDPClient client;
		UDPServer server;
		
		InetAddress ip = InetAddress.getLocalHost();
		System.out.print("Your address: " + ip + "\n\n");
		
		System.out.println("Host a game (Press Y)\nJoin a match (Press N)\n");	

		Scanner scan = new Scanner (System.in);
		String input;
		boolean isServer = false;
		boolean inputCorrect = false;
		
		while(!inputCorrect) {
			System.out.println("Input: ");
			 input = scan.nextLine();
			 if (input.equals("Y") || input.equals("y")) {
				 isServer = true;
				 inputCorrect = true;
			 } else if (input.equals("N") || input.contentEquals("n")) {
				 isServer = false;
				 inputCorrect = true;
			 } else {
				 System.out.println("\nIncorrect command.\n\n");
				 System.out.println("Host a game (Press Y)\nJoin a match (Press N)\n");
			 }
		}
		
		if (isServer) {
			server = new UDPServer();
			System.out.println("Starting server...");
			server.run();
		} else {
			String address;
			int serverPort;
			int clientPort;
			Jeopardy.instruct();
			System.out.println("Address of server? ");
			//address = scan.nextLine();
			address = "localhost";
			System.out.println("Port of server? ");
			serverPort = scan.nextInt();
			System.out.println("Your port? ");
			clientPort = scan.nextInt();
			client = new UDPClient(address, serverPort, clientPort);
			System.out.println("Starting client...");
			client.run();
		}
		
	}

}
