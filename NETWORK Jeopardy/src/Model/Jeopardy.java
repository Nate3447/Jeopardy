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
//		- End Phase (No more questions left):
//			- Scores shown
//			- Winner is chosen

public class Jeopardy {
	
	private ArrayList<Player> players;
	private ArrayList<Question> questions;
	private boolean noWinner;
	
	public Jeopardy() {
		players = new ArrayList<Player>();
		questions = new ArrayList<Question>();
		noWinner = false;
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
		// count remaining questions
		//	if 0, select winner
		
		return true;
	}
	
	public String getWinner() {
		// check player arraylist for highest score
		
		return "Winner";
	}
}
