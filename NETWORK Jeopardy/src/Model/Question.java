package Model;

public class Question {
	
	private String category;
	private String question;
	private String answer;
	private int points;
	private boolean isAnswered;
	
	public Question() {
		
	}
	
	public Question(int points, String category, String question, String answer, boolean isAnswered) {
		this.points = points;
		this.category = category;
		this.question = question;
		this.answer = answer;
		this.isAnswered = isAnswered;
	
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
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

	public boolean isAnswered() {
		return isAnswered;
	}
	
	public void setIsAnswered(boolean isAnswered) {
		this.isAnswered = isAnswered;
	}
	
}
