package Model;

import java.util.ArrayList;
import java.util.Scanner;

// 	ACTIONS:
//		- Preparation Phase:
//			- Player sends ID
//			- Players ready to start
//		- Game Phase:
//			- Question:
//				- "Picker" (Selected randomly at start or person who answered last question correctly) picks a category and dollar value.
//				- Players can hit buzzer to try to answer.
//				- Ten seconds are given to answer for person who hit buzzer.
//				- If answer correct: Points awarded to player, player becomes "picker", else: move on.
//			- Any Time:
//				- Players ring buzzer
//				- Players get all player details (name, scores)
//				- Players view scores
//				- Players can chat
//		- End Phase (Player has 1000+ points):
//			- Scores shown
//			- Winner is chosen

public class Jeopardy {
	
	private ArrayList<Player> players;
	private ArrayList<Question> questions;
	private int currentPhase;
	private int currentPicker;
	private boolean hasWinner;
	
	public Jeopardy() {
		players = new ArrayList<Player>();
		questions = new ArrayList<Question>();
		currentPicker = 0;
		currentPhase = Phase.START;
		hasWinner = false;
	}
	
	public static void instruct() {
		System.out.println("Welcome to Jeopardy! \n\n\tIn this you are to compete with other players by accumulating the highest number of points.\n\tTo gain points, a category and question is picked by a designated \"picker\".\n\tThe first player to hit the buzzer gets to answer the chosen question.\n\tThe first person to reach 2000 points is declared the winner.\n\nPress any key to join a game. ");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
	}
	
	public ArrayList<Player> getPlayers() {
		
		return players;
		
	}

	public void setPlayers(ArrayList<Player> players) {
		
		this.players = players;
		
	}

	public ArrayList<Question> getQuestions() {
		
		return questions;
		
	}

	public void setQuestions(ArrayList<Question> questions) {
		
		this.questions = questions;
		
	}
	
	public int getCurrentPicker() {
		return currentPicker;
	}
	
	public void setCurrentPicker(int currentPicker) {
		this.currentPicker = currentPicker;
	}
	
	public int getCurrentPhase() {
		return currentPhase;
	}
	
	public void setCurrentPhase(int currentPhase) {
		this.currentPhase = currentPhase;
	}
	
	public void joinPlayer(Player player) {
		players.add(player);
	}
	
	public boolean playerExists(Player player) {
		boolean exists = false;
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getName().equals(player.getName())) {
				exists = true;
			}
		}
		return exists;
	}
	
	public void leavePlayer(String name) {
		
		boolean found = false;
		int playerIndex = 0;
		for(int i=0; i<players.size();i++) {
			if(players.get(i).getName().equals(name)) {
				playerIndex = i;
				found = true;
			}
		}
		if(found) {
			players.remove(playerIndex);
		}
		
	}
	
	public Player getPlayer(int playerNo) {
		return players.get(playerNo);
	}
	
	public ArrayList<String> getCategories() {
	
		ArrayList<String> categories = new ArrayList<String>();
		
		// Get categories given in question arraylist

		
		return categories;
		
	}
	
	public ArrayList<Question> getQuestionsByCategory(String category) {
	
		ArrayList<Question> questions = new ArrayList<Question>();
		
		// Get questions from given category
		
		return questions;
		
	}
	
	public boolean answerIsCorrect(Question question, String answer) {
		
		if(question.getAnswer().equals(answer)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean hasWinner() {
		// check if anyone has 1000 or more points
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getPoints() >= 1000) {
				hasWinner = true;
			}
		}
		return true;
	}
	
	public String getWinner() {
		String winner = "null";
		for(int i=0; i<players.size();i++) {
			if(players.get(i).getPoints() >= 1000) {
				winner = players.get(i).getName();
			}
		}
		return winner;
	}
	
	public void initGame() {
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
		
	}
}
