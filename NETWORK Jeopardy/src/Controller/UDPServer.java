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
	private static final boolean NO_REPLY = false;
	private static final boolean HAS_REPLY = true;
	private static final String END_CODE = "11203447";
	
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
			waitForPlayers();
		}
		broadcast("\nGame started!\n");
		// Give player data to other players
		broadcastPlayers();
		// First player that joined is first picker
		Player picker = game.getPlayer(0);

		broadcast(picker.getName() + " is now choosing a category... \n");
		// Send picker categories
		sendData(picker, "Choose a category! \n", NO_REPLY);
		ArrayList<String> categories = new ArrayList<String>();
		for(int i=0; i<game.getCategories().size(); i++) {
			categories.add((i+1) + " - " + game.getCategories().get(i));
		}
		sendList(picker, categories);
		sendData(picker, "Enter number of chosen category: ", HAS_REPLY);
		
		boolean valid = false;
		ArrayList<Question> questions = new ArrayList<Question>();
		// Picker chooses category for game
		int choice = 0;
		while(!valid) {
			String response = receiveAnswer();
			choice = Integer.parseInt(response);
			if(choice > 0 && choice <= categories.size()) {
				valid = true;
				questions = game.getQuestionsByCategory(game.getCategories().get(choice-1));
			} else {
				sendData(picker, "Invalid input. Choose again. ", HAS_REPLY);
			}
		}
		
		
		// Tell all players what category is being used
		broadcast(picker.getName() + " has chosen the category: " + game.getCategories().get(choice-1));

		System.out.println("QUESTIONS: " + questions);
		
		ArrayList<String> questionOptions = new ArrayList<String>();
		questionOptions = getQuestionOptions(questions);
		System.out.println("VALUES OPTIONS: " + questionOptions);

		while(!game.hasWinner()) {
			// use ArrayList questions to track question stats
			
			broadcast("The following point values are available for this category: ");
			broadcast(picker.getName() + " will now pick a question. When you receive the question, you may buzz by sending any input. Get ready! \n");
			broadcastList(questionOptions);
			// Tell picker to choose question
			
			sendData(picker, "\nWhat value would you like to try to answer? ", HAS_REPLY);
			
			// Listen for chosen value
			String chosenValue = receiveAnswer();
			// Tell all players chosen question
			broadcast(picker.getName() + " chose to answer the " + chosenValue + " point question. ");
			
			//Question currentQuestion = game.getQuestionByPoints(questions, Integer.getInteger(chosenValue));
			int current = getQuestionIndexByPoints(questions, Integer.parseInt(chosenValue));
			broadcast("The question is: " + questions.get(current).getQuestion() + "\n");
			buzzMode();
			
			// Wait for buzz
			// Get response from all players. First person to send message answers
			Player buzzPlayer = receiveBuzz();
			
			// Tell first buzzer to answer
			broadcast(buzzPlayer.getName() + " was the first to hit the buzzer! Waiting for answer... ");
			sendData(buzzPlayer, "Your answer is: ", HAS_REPLY);
			
			// Get answer
			String answer = receiveAnswer();

			// If correct, change picker, add points
			if(questions.get(current).getAnswer().equals(answer.toLowerCase())) {
				picker = game.getPlayer(game.getPlayerIndexByName(buzzPlayer.getName()));
				picker.addPoints(questions.get(current).getPoints());
				broadcast(picker.getName() + " answered the question correctly!");
			} else {
				broadcast(picker.getName() + " gave a wrong answer!");
			}
			questions.remove(current);
			questionOptions.remove(current);
			broadcast("Current scores: \n");
			broadcastPlayers();
			broadcast("\nMoving on to the next round... ");
		}
		
		broadcast("A player has reached 1000+ points!\n");
		
		broadcast("The winner is " + game.getWinner() + "!\n");
		endGame();
		
	}
	
	public ArrayList<String> getQuestionOptions(ArrayList<Question> questions) {
		ArrayList<String> options = new ArrayList<String>();
		
		for(int i=0; i<questions.size(); i++) {
			if(!questions.get(i).isAnswered()) {
				options.add(Integer.toString(questions.get(i).getPoints()));
			}
		}
		
		return options;
	}
	
	public int getQuestionIndexByPoints(ArrayList<Question> questions, int value) {
		int index = 0;
		for(int i=0; i<questions.size(); i++) {
			if(questions.get(i).getPoints() == value) {
				index = i;
			}
		}
		return index;
	}
	
	public void waitForPlayers() throws Exception {
		System.out.println("Players joined: 0");
		game.joinPlayer(playerJoins());
		System.out.println(game.getPlayer(game.getPlayers().size()-1).getName() + " has joined.\n");
		// if player joins, broadcast to all players
		broadcast(game.getPlayers().size() + " player(s) ready...  \n\n");
	}
	
	public Player playerJoins() throws Exception {
		
		DatagramPacket packet = receiveData();
		
		String message = new String(packet.getData()).trim();
		InetAddress ip = packet.getAddress();
		int port = packet.getPort();
				
		Player player = new Player(message, 0, ip, port);
		System.out.println("Name: " + player.getName() + ", IP: " + ip + ", PORT: " + port);
		
		sendData(player, "You have joined the game! Please wait until all players are ready...\n", NO_REPLY);
		
		return player;
	}
	
	public Player receiveBuzz() throws Exception {
		Player firstBuzz = null;
		ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();
		// WAIT FOR EACH PLAYER'S BUZZ
		for(int i=0; i<game.getPlayers().size(); i++) {
			packets.add(receiveData());
			if(i==0) {
				firstBuzz = game.getPlayerByAddress(packets.get(0).getAddress());
			}
			System.out.println("Buzz received... ");
		}
		
		return firstBuzz;
	}
	
	public void broadcast(String message) throws Exception {
		for(int i=0; i<game.getPlayers().size(); i++) {
			sendData(game.getPlayer(i), message, NO_REPLY);
		}
	}

	public void broadcastPlayers() throws Exception {
		Player player;
		broadcast("Players: \n");
		for(int i=0; i<game.getPlayers().size(); i++) {
			player = game.getPlayer(i);
			broadcast("\nPlayer " + (i+1) + ": " + player.getName() + "\nPoints: " + player.getPoints() + "\n");
		}
	}
	
	public void buzzMode() throws Exception {
		for(int i=0; i<game.getPlayers().size(); i++) {
			sendData(game.getPlayer(i), "Hit your buzzers!", HAS_REPLY);
		}
	}
	
	public void endGame() throws Exception {
		for(int i=0; i<game.getPlayers().size(); i++) {
			sendData(game.getPlayer(i), END_CODE, NO_REPLY);
		}
	}
	
	public void broadcastList(ArrayList<String> list) throws Exception {
		for(int i=0; i<list.size(); i++) {
			broadcast(list.get(i));
		}
	}
	
	public void sendList(Player player, ArrayList<String> list) throws Exception {
		for(int i=0; i<list.size(); i++) {
			sendData(player, list.get(i), false);
		}
	}
	
	public String receiveAnswer() throws Exception {
		String message = new String(receiveData().getData()).trim();
		return message;
	}
	
	public void sendData(Player player, String message, boolean getResponse) throws Exception {
		String newMessage;
		if(getResponse) {
			newMessage = "1" + message;
		} else {
			newMessage = "0" + message;
		}
		
		sendData = new byte[BUFFER_SIZE];
		receiveData = new byte[BUFFER_SIZE];
		
		sendData = newMessage.getBytes();
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, player.getIp(), player.getPort());
		socket.send(packet);
		System.out.println("Packet sent: " + new String(packet.getData()).trim());

	}
	
	public DatagramPacket receiveData() throws Exception {
		receiveData = new byte[BUFFER_SIZE];
		sendData = new byte[BUFFER_SIZE];
		DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
		socket.receive(packet);
		// STOP AND WAIT
		System.out.println("Packet received: " + new String(packet.getData()).trim());
		DatagramPacket ack = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
		socket.send(ack);
		
		return packet;
	}
	
}
