package Controller;

import java.util.ArrayList;

import Model.*;

public class Main {
	
	public static void main(String[] args) {

		UDPClient client;
		UDPServer server;
		Jeopardy game;
		ArrayList<Question> questions;
		
		boolean isServer = true;
		
		if (isServer) {
			server = new UDPServer();
			game = new Jeopardy();
			questions = new ArrayList<Question>();
			for(int i=0; i<27; i++) {
				switch(i) {
					case 0: questions.add(new Question("Math", "Bringing two or more numbers together to make a new total.", "addition", 100, false));
						break;
					case 1: questions.add(new Question("Math", "Splitting into equal parts or groups.", "division", 100, false)); 
						break;
					case 2: questions.add(new Question("Math", "Taking one number away from another.", "subtraction", 100, false)); 
						break;
					case 3: questions.add(new Question("Math", "The sum of all given values, divided by the amount of values given.", "average", 200, false)); 
						break;
					case 4: questions.add(new Question("Math", "A factor that occurs in all the numbers being compared.", "common factor", 200, false)); 
						break;
					case 5: questions.add(new Question("Math", "A multiple that occurs in all the numbers being compared.", "common multiple", 200, false)); 
						break;
					case 6: questions.add(new Question("Math", "The bottom number of a fraction.", "denominator", 300, false)); 
						break;
					case 7: questions.add(new Question("Math", "A fraction with a numerator that is larger than the denominator.", "improper fraction", 300, false)); 
						break;
					case 8: questions.add(new Question("Math", "The result of multiplication.", "product", 300, false));
						break;
					case 9: questions.add(new Question("Science", "A test performed to verify a hypothesis.", "experiment", 100, false));
						break;
					case 10: questions.add(new Question("Science", "A guess about the result of an experiment.", "hypothesis", 100, false));
						break;
					case 11: questions.add(new Question("Science", "The factor in an experiment that does not change.", "control", 100, false));
						break;
					case 12: questions.add(new Question("Science", "The green pigment found in various plants.", "chlorophyll", 200, false));
						break;
					case 13: questions.add(new Question("Science", "The process by which species change to adapt to their environment.", "evolution", 200, false));
						break;
					case 14: questions.add(new Question("Science", "The organelle often referred to as the brain of the cell.", "nucleus", 200, false));
						break;
					case 15: questions.add(new Question("Science", "DNA molecules that contain the set of instructions required to build a cell.", "chromosomes", 300, false));
						break;
					case 16: questions.add(new Question("Science", "The observable characteristics of an individual.", "phenotype", 300, false));
						break;
					case 17: questions.add(new Question("Science", "The number of protons in an atom.", "atomic number", 300, false));
						break;
					case 18: questions.add(new Question("English", "The name of a person, place, or thing.", "noun", 100, false));
						break;
					case 19: questions.add(new Question("English", "A word used to refer to a noun, usually to avoid repetition.", "pronoun", 100, false));
						break;
					case 20: questions.add(new Question("English", "A word used to describe a noun or pronoun.", "adjective", 100, false));
						break;
					case 21: questions.add(new Question("English", "A joining word.", "conjunction", 200, false));
						break;
					case 22: questions.add(new Question("English", "A verb form ending in -ing and used as a noun.", "gerund", 200, false));
						break;
					case 23: questions.add(new Question("English", "A word derived from a verb and having the qualities of both a verb and adjective; never used as a noun.", "participle", 200, false));
						break;
					case 24: questions.add(new Question("English", "A group of words containing a subject and its verb.", "clause", 300, false));
						break;
					case 25: questions.add(new Question("English", "An exclamation inserted into an utterance without grammatical connection.", "interjection", 300, false));
						break;
					case 26: questions.add(new Question("English", "A group of words not containing a subject and its verb", "phrase", 300, false));
						break;	
				}
			}
			game.setQuestions(questions);
			
			
		} else {
			client = new UDPClient();
			
		}
		
	}

}
