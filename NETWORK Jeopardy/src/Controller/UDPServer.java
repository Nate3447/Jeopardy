package Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Model.Jeopardy;
import Model.Phase;
import Model.Player;
import Model.Question;

public class UDPServer {
	
// MESSAGE TYPES:
//	- Player joins 
//	- Ready to start
//	- View player details
//	- Pick question
//	- Press Buzzer
//	- Give Answer
//	- Chat message
	
	private Jeopardy game;
	private ArrayList<Question> questions;
	
	
	public void startServer() throws Exception {
		
		game = new Jeopardy();
		game.initGame();
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		// wait for players
		while(game.getPlayers().size() < 3) {
			receiveData(serverSocket);
		}
		// Give player data to other players
		broadcastPlayers();
		// First player that joined is first picker
		// Send picker categories
		sendData(game.getPlayer(0));
		// Picker chooses category for game
		// Tell all players what category is being used
		while(!game.hasWinner()) {
			game.setCurrentPhase(Phase.QUESTION);
			// Tell picker to choose question
			// Listen for chosen question
			// Tell all players chosen question
			// Tell players to buzz
			// Tell first buzzer to answer
			// Get answer
			// If correct, change picker, add points
			
		}
		
		
		
		System.out.println(game.getWinner());
	}
	
	public void sendData(Player player) {
		
		
		
	}
	
	public void broadcast(String message) {
		
	}
	
	public void broadcastPlayers() {
		
	}
	
	public void receiveData(DatagramSocket serverSocket) throws Exception {
		
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1014];
		
		
		// RECEIVE DATA
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		serverSocket.receive(receivePacket);
		
		// PARSE DATA (GET FLAGS FROM FIRST BYTES, RECEIVE THE REST)
		
		String sentence = new String(receivePacket.getData());
		InetAddress IPAddress = receivePacket.getAddress();
		
		int port = receivePacket.getPort();
		
		// DO STUFF TO DATA BASED ON FLAGS
		
		String capitalizedSentence = sentence.toUpperCase();
		
		// SEND DATA
		
		sendData = capitalizedSentence.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		
		serverSocket.send(sendPacket);
		
	}
	
}
