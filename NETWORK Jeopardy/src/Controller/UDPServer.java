package Controller;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Model.Jeopardy;
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
		
		initGame();
		
		DatagramSocket serverSocket = new DatagramSocket(9876);
		
		// wait for players
		while(game.getPlayers().size() < 3) {
			receiveData(serverSocket);
		}
		
		
		
		System.out.println(game.getWinner());
	}
	
	public void sendData() {
		
		
		
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
	
	public void initGame() {
		game = new Jeopardy();
		questions = new ArrayList<Question>();
		
		questions.add(new Question("A1", "Math", "Bringing two or more numbers together to make a new total.", "addition", 100, false));
		questions.add(new Question("A2", "Math", "Splitting into equal parts or groups.", "division", 100, false)); 
		questions.add(new Question("A3", "Math", "Taking one number away from another.", "subtraction", 100, false)); 
		questions.add(new Question("A4", "Math", "The sum of all given values, divided by the amount of values given.", "average", 200, false)); 
		questions.add(new Question("A5", "Math", "A factor that occurs in all the numbers being compared.", "common factor", 200, false)); 
		questions.add(new Question("A6", "Math", "A multiple that occurs in all the numbers being compared.", "common multiple", 200, false)); 
		questions.add(new Question("A7", "Math", "The bottom number of a fraction.", "denominator", 300, false)); 
		questions.add(new Question("A8", "Math", "A fraction with a numerator that is larger than the denominator.", "improper fraction", 300, false)); 
		questions.add(new Question("A9", "Math", "The result of multiplication.", "product", 300, false));
		questions.add(new Question("B1", "Science", "A test performed to verify a hypothesis.", "experiment", 100, false));
		questions.add(new Question("B2", "Science", "A guess about the result of an experiment.", "hypothesis", 100, false));
		questions.add(new Question("B3", "Science", "The factor in an experiment that does not change.", "control", 100, false));
		questions.add(new Question("B4", "Science", "The green pigment found in various plants.", "chlorophyll", 200, false));
		questions.add(new Question("B5", "Science", "The process by which species change to adapt to their environment.", "evolution", 200, false));
		questions.add(new Question("B6", "Science", "The organelle often referred to as the brain of the cell.", "nucleus", 200, false));
		questions.add(new Question("B7", "Science", "DNA molecules that contain the set of instructions required to build a cell.", "chromosomes", 300, false));
		questions.add(new Question("B8", "Science", "The observable characteristics of an individual.", "phenotype", 300, false));
		questions.add(new Question("B9", "Science", "The number of protons in an atom.", "atomic number", 300, false));
		questions.add(new Question("C1", "English", "The name of a person, place, or thing.", "noun", 100, false));
		questions.add(new Question("C2", "English", "A word used to refer to a noun, usually to avoid repetition.", "pronoun", 100, false));
		questions.add(new Question("C3", "English", "A word used to describe a noun or pronoun.", "adjective", 100, false));
		questions.add(new Question("C4", "English", "A joining word.", "conjunction", 200, false));
		questions.add(new Question("C5", "English", "A verb form ending in -ing and used as a noun.", "gerund", 200, false));
		questions.add(new Question("C6", "English", "A word derived from a verb and having the qualities of both a verb and adjective; never used as a noun.", "participle", 200, false));
		questions.add(new Question("C7", "English", "A group of words containing a subject and its verb.", "clause", 300, false));
		questions.add(new Question("C8", "English", "An exclamation inserted into an utterance without grammatical connection.", "interjection", 300, false));
		questions.add(new Question("C9", "English", "A group of words not containing a subject and its verb", "phrase", 300, false));
		
		game.setQuestions(questions);
		
		
	}
	
}
