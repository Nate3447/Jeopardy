package Controller;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import Model.Jeopardy;
import Model.Phase;
import Model.Player;
import Model.Question;
import Model.Type;

public class UDPServer {
	
// MESSAGE FORMAT:
//  - Header
//		- Byte 1: Sequence number
	
// MESSAGE TYPES:
//	- Player joins 
//	- Ready to start
//	- View player details
//	- Pick question
//	- Press Buzzer
//	- Give Answer
//	- Chat message
	
	private static final int BUFFER_SIZE = 1024;
	private static final int PORT = 9876;
	
	private Jeopardy game;	
	private byte[] sendData;
	private byte[] receiveData;
	DatagramSocket socket;
	
	public void run() throws Exception {
		
		game = new Jeopardy();
		game.initGame();
		
		
		socket = new DatagramSocket(PORT);
		// wait for players
		while(game.getPlayers().size() < 3) {
			System.out.println("Players joined: 0");
			game.joinPlayer(playerJoins());
			System.out.println(game.getPlayer(game.getPlayers().size()-1).getName() + " has joined.\n");
			// if player joins, broadcast to all players
			broadcast(game.getPlayers().size() + " players ready...  \n");
		}
		broadcast("Game started!\n");
		// Give player data to other players
		broadcastPlayers();
		// First player that joined is first picker
		// Send picker categories
		sendData(game.getPlayer(0), "Choose a question!");
		// Picker chooses category for game
		receiveData();
			// get chosen category from message
		// Tell all players what category is being used
			// broadcast();
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
	
	public Player playerJoins() throws Exception {
		
		// RECEIVE DATA
		DatagramPacket packet = receiveData();
		// PARSE DATA (GET FLAGS FROM FIRST BYTES, RECEIVE THE REST)
		
		String message = new String(packet.getData()).trim();
		InetAddress ip = packet.getAddress();
		int port = packet.getPort();
		
		// DO STUFF TO DATA BASED ON FLAGS
		
		Player player = new Player(message, 0, ip, port);
		System.out.println("Name: " + player.getName() + ", IP: " + ip + ", PORT: " + port);
		
		// SEND DATA
		sendData(player, "You have joined the game! Please wait until all players are ready...\n");
		//sendData = capitalizedSentence.getBytes();
		//DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
		//socket.send(sendPacket);
		
		return player;
	}
	
	public void sendData(Player player, String message) throws Exception {
		sendData = new byte[BUFFER_SIZE];
		
		sendData = message.getBytes();
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, player.getIp(), player.getPort());
		socket.send(packet);
		System.out.println("Packet sent... ");
	}
	
	public void broadcast(String message) throws Exception {
		for(int i=0; i<game.getPlayers().size(); i++) {
			sendData(game.getPlayer(i), message);
		}
	}
	
	public void broadcastPlayers() throws Exception {
		broadcast("FILL");
	}
	
	public DatagramPacket receiveData() throws Exception {
		receiveData = new byte[BUFFER_SIZE];
		
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		socket.receive(packet);
		System.out.println("Packet received... ");
		
		return packet;
	}
	
}
