package com.example.qa_answer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.Survey;

import java.util.ArrayList;

public class SurveyViewModel {
    public LiveData<ArrayList<Survey>> listSurvey;
    private MutableLiveData<ArrayList<Survey>> _listSurvey;



}
