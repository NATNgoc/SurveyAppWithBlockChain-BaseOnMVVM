package com.example.qa_answer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.data.repository.SurveyRepository;

import java.util.ArrayList;

public class SurveyViewModel extends ViewModel {
    public LiveData<ArrayList<Survey>> listSurvey;
    private MutableLiveData<ArrayList<Survey>> _listSurvey;
    private SurveyRepository mSurveyRepository;

    public SurveyViewModel() {
        mSurveyRepository=new SurveyRepository();
        _listSurvey=mSurveyRepository.getListSurvey();
        listSurvey=_listSurvey;
    }

}
