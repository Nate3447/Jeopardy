package Model;

import java.net.InetAddress;
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
//				- If answer correct: Points awarded to player, player becomes "picker", else: move on.
//			- Any Time:
//				- Players ring buzzer
//				- Players get all player details (name, scores)
//		- End Phase (Player has 1000+ points):
//			- Scores shown
//			- Winner is chosen

public class Jeopardy {
	
	private ArrayList<Player> players;
	private ArrayList<Question> questions;
	private int currentPicker;
	private boolean hasWinner;
	
	public Jeopardy() {
		players = new ArrayList<Player>();
		questions = new ArrayList<Question>();
		currentPicker = 0;
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
		categories.add("Math");
		categories.add("Science");
		categories.add("English");
		
		return categories;
		
	}
	
	public void addPointsToPlayer(int index, int points) {
		players.get(index).addPoints(points);
	}
	
	public ArrayList<Question> getQuestionsByCategory(String category) {
	
		ArrayList<Question> questionSet = new ArrayList<Question>();
		
		// Get questions from given category
		for(int i=0; i<questions.size(); i++) {
			if(questions.get(i).getCategory().equals(category)) {
				questionSet.add(questions.get(i));
			}
		}
		
		return questionSet;
		
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
	
	public Question getQuestionByPoints(ArrayList<Question> questions, int points) {
		Question question = questions.get(0);
		
		for(int i=0; i<questions.size(); i++) {
			if(questions.get(i).getPoints() == points) {
				question = questions.get(i);
			}
		}
		
		return question;
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
		hasWinner = false;
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getPoints() >= 1000) {
				hasWinner = true;
			}
		}
		return hasWinner;
	}
	
	public Player getPlayerByAddress(InetAddress address) {
		Player player = null;
		
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getIp().equals(address)) {
				player = players.get(i);
			}
		}
		
		return player;
	}
	
	public int getPlayerIndexByName(String name) {
		int index = 0;
		
		for(int i=0; i<players.size(); i++) {
			if(players.get(i).getName().equals(name)) {
				index = i;
			}
		}
		
		return index;
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
		
		questions.add(new Question(100, "Math", "Bringing two or more numbers together to make a new total.", "addition", false));
		questions.add(new Question(200, "Math", "Splitting into equal parts or groups.", "division", false)); 
		questions.add(new Question(300, "Math", "Taking one number away from another.", "subtraction", false)); 
		questions.add(new Question(400, "Math", "The sum of all given values, divided by the amount of values given.", "average", false)); 
		questions.add(new Question(500, "Math", "A factor that occurs in all the numbers being compared.", "common factor", false)); 
		questions.add(new Question(600, "Math", "A multiple that occurs in all the numbers being compared.", "common multiple", false)); 
		questions.add(new Question(700, "Math", "The bottom number of a fraction.", "denominator", false)); 
		questions.add(new Question(800, "Math", "A fraction with a numerator that is larger than the denominator.", "improper fraction", false)); 
		questions.add(new Question(900, "Math", "The result of multiplication.", "product", false));
		questions.add(new Question(100, "Science", "A test performed to verify a hypothesis.", "experiment", false));
		questions.add(new Question(200, "Science", "A guess about the result of an experiment.", "hypothesis", false));
		questions.add(new Question(300, "Science", "The factor in an experiment that does not change.", "control", false));
		questions.add(new Question(400, "Science", "The green pigment found in various plants.", "chlorophyll", false));
		questions.add(new Question(500, "Science", "The process by which species change to adapt to their environment.", "evolution", false));
		questions.add(new Question(600, "Science", "The organelle often referred to as the brain of the cell.", "nucleus", false));
		questions.add(new Question(700, "Science", "DNA molecules that contain the set of instructions required to build a cell.", "chromosomes", false));
		questions.add(new Question(800, "Science", "The observable characteristics of an individual.", "phenotype", false));
		questions.add(new Question(900, "Science", "The number of protons in an atom.", "atomic number", false));
		questions.add(new Question(100, "English", "The name of a person, place, or thing.", "noun", false));
		questions.add(new Question(200, "English", "A word used to refer to a noun, usually to avoid repetition.", "pronoun", false));
		questions.add(new Question(300, "English", "A word used to describe a noun or pronoun.", "adjective", false));
		questions.add(new Question(400, "English", "A joining word.", "conjunction", false));
		questions.add(new Question(500, "English", "A verb form ending in -ing and used as a noun.", "gerund", false));
		questions.add(new Question(600, "English", "A word derived from a verb and having the qualities of both a verb and adjective; never used as a noun.", "participle", false));
		questions.add(new Question(700, "English", "A group of words containing a subject and its verb.", "clause", false));
		questions.add(new Question(800, "English", "An exclamation inserted into an utterance without grammatical connection.", "interjection", false));
		questions.add(new Question(900, "English", "A group of words not containing a subject and its verb", "phrase", false));
		
	}
}
