package Controller;

import java.util.ArrayList;
import java.util.Scanner;

import Model.*;

public class Main {
	
	public static void main(String[] args) throws Exception {

		UDPClient client;
		UDPServer server;
		
		System.out.println("Host a game (Press Y)\nJoin a match (Press N)\n");	

		Scanner scan = new Scanner (System.in);
		String input;
		boolean isServer = false;
		boolean inputCorrect = false;
		
		while(!inputCorrect) {
			 input = scan.nextLine();
			 if (input.equals("Y") || input.equals("y")) {
				 isServer = true;
			 } else if (input.equals("N") || input.contentEquals("n")) {
				 isServer = false;
			 } else {
				 System.out.println("\nIncorrect command.\n\n");
				 System.out.println("Host a game (Press Y)\nJoin a match (Press N)\n");
			 }
		}
		
		if (isServer) {
			server = new UDPServer();
			server.startServer();
		} else {
			client = new UDPClient();
			client.startClient();
		}
		
	}

}
