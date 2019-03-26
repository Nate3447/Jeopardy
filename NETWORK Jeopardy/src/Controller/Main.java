package Controller;

import java.util.ArrayList;

import Model.*;

public class Main {
	
	public static void main(String[] args) {

		UDPClient client;
		UDPServer server;
		Jeopardy game;
		ArrayList<Question> questions;
		Player player1;
		Player player2;
		Player player3;
		
		
		boolean isServer = true;
		
		if (isServer) {
			server = new UDPServer();
			
			
		} else {
			
			client = new UDPClient();
			
		}
		
	}

}
