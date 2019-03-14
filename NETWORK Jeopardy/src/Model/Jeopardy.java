package Model;

import java.util.ArrayList;

public class Jeopardy {
	
	ArrayList<Player> players;
	ArrayList<Question> questions;
	
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
	
}
