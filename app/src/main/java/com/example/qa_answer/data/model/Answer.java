package com.example.qa_answer.data.model;

public class Answer {
    String idQuestion;
    String idUser;
    int selectedChoice;

    public Answer(String idQuestion, String idUser, int selectedChoice) {
        this.idQuestion = idQuestion;
        this.idUser = idUser;
        this.selectedChoice = selectedChoice;
    }

    public Answer() {
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getSelectedChoice() {
        return selectedChoice;
    }

    public void setSelectedChoice(int selectedChoice) {
        this.selectedChoice = selectedChoice;
    }
}
