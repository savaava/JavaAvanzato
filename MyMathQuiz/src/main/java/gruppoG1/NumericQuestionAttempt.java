package gruppoG1;

public class NumericQuestionAttempt {
    private final NumericQuestion question;
    private Integer givenAnswer;
    private String result;

    public NumericQuestionAttempt(NumericQuestion question, Integer givenAnswer) {
        this.question = question;
        this.givenAnswer = givenAnswer;
    }

    public NumericQuestion getQuestion() {
        return question;
    }

    public Integer getGivenAnswer() {
        return givenAnswer;
    }
    public void setGivenAnswer(Integer givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public boolean isCorrect() {
        if(givenAnswer == null) return false;
        return question.getResult() == givenAnswer;
    }

    public String getResult() {
        return isCorrect() ? "Corretto" : "Sbagliato";
    }

    @Override
    public String toString() {
        return question.toString() + givenAnswer;
    }
}
