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
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1014];
		
		while(!game.hasWinner()) {
			
			// RECEIVE DATA
			
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			
			String sentence = new String(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			
			int port = receivePacket.getPort();
			
			// DO STUFF TO DATA
			
			String capitalizedSentence = sentence.toUpperCase();
			
			// SEND DATA
			
			sendData = capitalizedSentence.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			
			serverSocket.send(sendPacket);
		}
		
		System.out.println(game.getWinner());
	}
	
	public void initGame() {
		game = new Jeopardy();
		questions = new ArrayList<Question>();
		
		questions.add(new Question("Math", "Bringing two or more numbers together to make a new total.", "addition", 100, false));
		questions.add(new Question("Math", "Splitting into equal parts or groups.", "division", 100, false)); 
		questions.add(new Question("Math", "Taking one number away from another.", "subtraction", 100, false)); 
		questions.add(new Question("Math", "The sum of all given values, divided by the amount of values given.", "average", 200, false)); 
		questions.add(new Question("Math", "A factor that occurs in all the numbers being compared.", "common factor", 200, false)); 
		questions.add(new Question("Math", "A multiple that occurs in all the numbers being compared.", "common multiple", 200, false)); 
		questions.add(new Question("Math", "The bottom number of a fraction.", "denominator", 300, false)); 
		questions.add(new Question("Math", "A fraction with a numerator that is larger than the denominator.", "improper fraction", 300, false)); 
		questions.add(new Question("Math", "The result of multiplication.", "product", 300, false));
		questions.add(new Question("Science", "A test performed to verify a hypothesis.", "experiment", 100, false));
		questions.add(new Question("Science", "A guess about the result of an experiment.", "hypothesis", 100, false));
		questions.add(new Question("Science", "The factor in an experiment that does not change.", "control", 100, false));
		questions.add(new Question("Science", "The green pigment found in various plants.", "chlorophyll", 200, false));
		questions.add(new Question("Science", "The process by which species change to adapt to their environment.", "evolution", 200, false));
		questions.add(new Question("Science", "The organelle often referred to as the brain of the cell.", "nucleus", 200, false));
		questions.add(new Question("Science", "DNA molecules that contain the set of instructions required to build a cell.", "chromosomes", 300, false));
		questions.add(new Question("Science", "The observable characteristics of an individual.", "phenotype", 300, false));
		questions.add(new Question("Science", "The number of protons in an atom.", "atomic number", 300, false));
		questions.add(new Question("English", "The name of a person, place, or thing.", "noun", 100, false));
		questions.add(new Question("English", "A word used to refer to a noun, usually to avoid repetition.", "pronoun", 100, false));
		questions.add(new Question("English", "A word used to describe a noun or pronoun.", "adjective", 100, false));
		questions.add(new Question("English", "A joining word.", "conjunction", 200, false));
		questions.add(new Question("English", "A verb form ending in -ing and used as a noun.", "gerund", 200, false));
		questions.add(new Question("English", "A word derived from a verb and having the qualities of both a verb and adjective; never used as a noun.", "participle", 200, false));
		questions.add(new Question("English", "A group of words containing a subject and its verb.", "clause", 300, false));
		questions.add(new Question("English", "An exclamation inserted into an utterance without grammatical connection.", "interjection", 300, false));
		questions.add(new Question("English", "A group of words not containing a subject and its verb", "phrase", 300, false));
		
		game.setQuestions(questions);
		
		
	}
	
}
