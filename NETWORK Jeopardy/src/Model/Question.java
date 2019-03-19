package Model;

public class Question {
	
	private String category;
	private String question;
	private String answer;
	private int points;
	private boolean isAnswered;
	
	public Question() {
		
	}
	
	public Question(String category, String question, String answer, int points, boolean isAnswered) {
	
		this.category = category;
		this.question = question;
		this.answer = answer;
		this.points = points;
		this.isAnswered = isAnswered;
	
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isAnswered() {
		return isAnswered;
	}
	
	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	
}
