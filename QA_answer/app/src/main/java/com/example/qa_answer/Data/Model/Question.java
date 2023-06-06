package com.example.qa_answer.Data.Model;

public class Question {
    String idQuestion;
    String question;
    String choiceNumber1;
    String choiceNumber2;
    String choiceNumber3;
    String idSurvey;

    public Question(String idQuestion, String question, String choiceNumber1, String choiceNumber2, String choiceNumber3, String idSurvey) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.choiceNumber1 = choiceNumber1;
        this.choiceNumber2 = choiceNumber2;
        this.choiceNumber3 = choiceNumber3;
        this.idSurvey = idSurvey;
    }

    public Question() {
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoiceNumber1() {
        return choiceNumber1;
    }

    public void setChoiceNumber1(String choiceNumber1) {
        this.choiceNumber1 = choiceNumber1;
    }

    public String getChoiceNumber2() {
        return choiceNumber2;
    }

    public void setChoiceNumber2(String choiceNumber2) {
        this.choiceNumber2 = choiceNumber2;
    }

    public String getChoiceNumber3() {
        return choiceNumber3;
    }

    public void setChoiceNumber3(String choiceNumber3) {
        this.choiceNumber3 = choiceNumber3;
    }

    public String getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(String idSurvey) {
        this.idSurvey = idSurvey;
    }
}
