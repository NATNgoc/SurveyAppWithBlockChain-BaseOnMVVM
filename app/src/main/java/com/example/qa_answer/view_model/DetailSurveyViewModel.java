package com.example.qa_answer.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.DetailSurvey;
import com.example.qa_answer.data.repository.AnswerRepository;
import com.example.qa_answer.data.repository.BlockRepository;
import com.example.qa_answer.data.repository.DetailSurveyRepository;
import com.example.qa_answer.data.repository.UserRepository;

import java.util.ArrayList;

public class DetailSurveyViewModel extends ViewModel {

    UserRepository userRepository;
    BlockRepository blockRepository;
    AnswerRepository answerRepository;

    public LiveData<Boolean> isAddBlockSuccessfully;
    public LiveData<Boolean> isAddDetailSuccessfully;
    public LiveData<Boolean> isAddListAnswerSuccessfully;

    public LiveData<Block> lastBlock;

    public
    DetailSurveyRepository detailSurveyRepository;

    public DetailSurveyViewModel() {
        userRepository=UserRepository.getInstance();
        blockRepository=BlockRepository.getInstance();
        answerRepository=new AnswerRepository();
        detailSurveyRepository=new DetailSurveyRepository();
        isAddBlockSuccessfully= blockRepository.getIsAddSuccessful();
        isAddDetailSuccessfully=detailSurveyRepository.getIsAddSuccessful();
        isAddListAnswerSuccessfully=answerRepository.getIsAddSuccessful();
        lastBlock=blockRepository.lastBlock;
    }

    public void addBlock(Block tmp) {
        blockRepository.addBlock(tmp);
    }



    public void addDetailSurvey(DetailSurvey detailSurvey) {
        detailSurveyRepository.addDetailSurvey(detailSurvey);
    }

    public void addListAnswer(ArrayList<Answer> ds,int position) {
        answerRepository.addDsAnswer(ds,position);
    }

}
