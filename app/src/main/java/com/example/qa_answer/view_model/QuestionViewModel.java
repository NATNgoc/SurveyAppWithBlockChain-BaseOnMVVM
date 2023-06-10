package com.example.qa_answer.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.model.Question;
import com.example.qa_answer.data.repository.QuestionReposipitory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Question>> _dsQuestion;
    public LiveData<ArrayList<Question>> dsQuestion;
    private QuestionReposipitory mReposipitory;

    public QuestionViewModel(String id) {
        mReposipitory=new QuestionReposipitory();
        _dsQuestion=mReposipitory.getListQuestionById(id);
        dsQuestion=_dsQuestion;
    }

}

