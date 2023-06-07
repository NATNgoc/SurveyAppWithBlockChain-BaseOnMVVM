package com.example.qa_answer.data.model;

import java.io.Serializable;

public class Survey implements Serializable {
    String idSurvey;
    String nameOfSurvey;
    String description;
    long dateStart;
    String imageOfSurvey;
    long dateEnd;
    int reward;

    public Survey(String idSurvey, String nameOfSurvey, String description, long dateStart, String imageOfSurvey, long dateEnd, int reward) {
        this.idSurvey = idSurvey;
        this.nameOfSurvey = nameOfSurvey;
        this.description = description;
        this.dateStart = dateStart;
        this.imageOfSurvey = imageOfSurvey;
        this.dateEnd = dateEnd;
        this.reward = reward;
    }

    public Survey() {
    }

    public String getImageOfSurvey() {
        return imageOfSurvey;
    }

    public void setImageOfSurvey(String imageOfSurvey) {
        this.imageOfSurvey = imageOfSurvey;
    }

    public String getIdSurvey() {
        return idSurvey;
    }

    public void setIdSurvey(String idSurvey) {
        this.idSurvey = idSurvey;
    }

    public String getNameOfSurvey() {
        return nameOfSurvey;
    }

    public void setNameOfSurvey(String nameOfSurvey) {
        this.nameOfSurvey = nameOfSurvey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
