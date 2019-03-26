package Model;

import java.util.ArrayList;

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
	
	public void joinPlayer(String name) {
		
		
		
	}
	
	public void leavePlayer(String name) {
		
		
		
	}
	
	public ArrayList<String> getCategories() {
	
		ArrayList<String> categories = new ArrayList<String>();
		
		return categories;
		
	}
	
	public ArrayList<Question> getQuestionsByCategory(String category) {
	
		ArrayList<Question> questions = new ArrayList<Question>();
		
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
		return true;
	}
	
	public String getWinner() {
		return "Winner";
	}
}
