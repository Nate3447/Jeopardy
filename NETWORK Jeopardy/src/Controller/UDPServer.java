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
//		- Char 0: message type
//		- Char 1: sequence number (if list)
//		- Data: String (normally) or list size (if first message of list)
	
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
	private static final int TELL = 0;
	private static final int ASK = 1;
	private static final int LIST = 2;
	
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
		Player picker = game.getPlayer(0);
		// Send picker categories
		sendData(picker, "Choose a category!");
		ArrayList<String> categories = new ArrayList<String>();
		for(int i=0; i<game.getCategories().size(); i++) {
			categories.add((i+1) + " - " + game.getCategories().get(i));
		}
		sendList(picker, categories);
		
		boolean valid = false;
		ArrayList<Question> questions = new ArrayList<Question>();
		// Picker chooses category for game
		int choice = 0;
		while(!valid) {
			String response = receiveFrom(picker);
			choice = Integer.getInteger(response);
			if(choice > 0 && choice <= categories.size()) {
				valid = true;
				questions = game.getQuestionsByCategory(game.getCategories().get(choice-1));
			} else {
				sendData(picker, "Invalid input. Choose again. ");
			}
		}
		
		
		// Tell all players what category is being used
		broadcast(picker.getName() + " has chosen the category: " + game.getCategories().get(choice-1));

		
		ArrayList<String> questionOptions = new ArrayList<String>();
		
		while(!game.hasWinner()) {
			// game.setCurrentPhase(Phase.QUESTION);

			broadcast("The following point values are available for this category: ");
			broadcast(picker.getName() + " will now pick a question. When you receive the question, you may buzz by sending any input. Get ready! ");
			questionOptions = game.getQuestionOptions(questions);
			broadcastList(questionOptions);
			// Tell picker to choose question
			
			sendData(picker, "What value would you like to try to answer? ");
			
			// Listen for chosen value
			
			String chosenValue = receiveFrom(picker);
			
			
			// Tell all players chosen question
			
			broadcast(picker.getName() + "chose to answer the " + chosenValue + " point question. ");
			
			Question currentQuestion = game.getQuestionByPoints(questions, Integer.getInteger(chosenValue));
			broadcast(currentQuestion.getQuestion());
			
			// Wait for buzz			
			Player buzzPlayer = receiveBuzz();
			
			// Tell first buzzer to answer
			broadcast(buzzPlayer + " was the first to hit the buzzer! Give your answer!");
			sendData(buzzPlayer, "Your answer is: ");
			
			// Get answer
			String answer = receiveFrom(buzzPlayer);

			// If correct, change picker, add points
			if(currentQuestion.getAnswer().equals(answer.toLowerCase())) {
				picker = game.getPlayer(game.getPlayerIndexByName(buzzPlayer.getName()));
				picker.addPoints(currentQuestion.getPoints());;
			}
		}
		
		broadcast("A player has reached 1000+ points!\n");
		
		broadcast("The winner is " + game.getWinner() + "!\n");
		
	}
	
	public Player playerJoins() throws Exception {
		
		DatagramPacket packet = receiveData();
		
		String message = new String(packet.getData()).trim();
		InetAddress ip = packet.getAddress();
		int port = packet.getPort();
				
		Player player = new Player(message, 0, ip, port);
		System.out.println("Name: " + player.getName() + ", IP: " + ip + ", PORT: " + port);
		
		sendData(player, "You have joined the game! Please wait until all players are ready...\n");
		
		return player;
	}
	
	public Player receiveBuzz() throws Exception {
		Player firstBuzz = null;
		
		DatagramPacket packet = receiveData();
		firstBuzz = game.getPlayerByAddress(packet.getAddress());
		
		return firstBuzz;
	}
	
	public String receiveFrom(Player player) throws Exception {
		String response = null;
		boolean fromPlayer = false;
		DatagramPacket packet = null;
		
		System.out.println("Getting response from: " + player.getName());
		
		while(!fromPlayer) {
			packet = receiveData();
			packet.getAddress();
			if(packet.getAddress().equals(player.getIp())) {
				response = new String(packet.getData()).trim();
				fromPlayer = true;
			}
		}
		
		return response;
	}
	
	public void broadcast(String message) throws Exception {
		for(int i=0; i<game.getPlayers().size(); i++) {
			sendData(game.getPlayer(i), message);
		}
	}
	
	public void broadcastList(ArrayList<String> list) {
		// NOT DONE
	}
	
	public void broadcastPlayers() throws Exception {
		// NOT DONE
	}
	
	public void sendList(Player player, ArrayList<String> list) {
		// NOT DONE
	}
	
	public void sendData(Player player, String message) throws Exception {
		sendData = new byte[BUFFER_SIZE];
		
		sendData = message.getBytes();
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, player.getIp(), player.getPort());
		socket.send(packet);
		System.out.println("Packet sent... ");
	}
	
	public DatagramPacket receiveData() throws Exception {
		receiveData = new byte[BUFFER_SIZE];
		
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		socket.receive(packet);
		System.out.println("Packet received... ");
		
		return packet;
	}
	
}
