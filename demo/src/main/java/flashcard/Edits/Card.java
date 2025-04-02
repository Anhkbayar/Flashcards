package flashcard.Edits;

public class Card {

    private final String question;
    private final String answer;
    private int mistakeCounter;
    private int recentMistakeCounter;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
        mistakeCounter = 0;
        recentMistakeCounter = -1;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getMistakeCounter() {
        return mistakeCounter;
    }

    public int getRecentMistakeCounter() {
        return recentMistakeCounter;
    }

    public void mistakeAdder(int order) {
        mistakeCounter++;
        recentMistakeCounter = order;
    }

}
