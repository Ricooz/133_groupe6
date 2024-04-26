package ch.emf.youquiz.beans;

import java.util.List;

public class QuizSubmission {
    private Integer pkQuiz;
    private List<Question> questions;

    public Integer getPkQuiz() {
        return pkQuiz;
    }

    public void setPkQuiz(Integer pkQuiz) {
        this.pkQuiz = pkQuiz;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}