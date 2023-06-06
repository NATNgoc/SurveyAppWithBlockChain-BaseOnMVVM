package com.example.qa_answer.Data.Model;

public class DetailSurvey {
    String idSurvey;
    String idUser;
    Double dateSurvey;
    int tokenBeRewarded;

    public DetailSurvey(String idSurvey, String idUser, Double dateSurvey, int tokenBeRewarded) {
        this.idSurvey = idSurvey;
        this.idUser = idUser;
        this.dateSurvey = dateSurvey;
        this.tokenBeRewarded = tokenBeRewarded;
    }

    public DetailSurvey() {
    }

    public String getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(String idSurvey) {
        this.idSurvey = idSurvey;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public Double getDateSurvey() {
        return dateSurvey;
    }

    public void setDateSurvey(Double dateSurvey) {
        this.dateSurvey = dateSurvey;
    }

    public int getTokenBeRewarded() {
        return tokenBeRewarded;
    }

    public void setTokenBeRewarded(int tokenBeRewarded) {
        this.tokenBeRewarded = tokenBeRewarded;
    }
}
