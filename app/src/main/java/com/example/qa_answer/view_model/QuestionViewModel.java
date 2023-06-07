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

    private MutableLiveData<ArrayList<Question>> _dsDetailSurvey;
    public LiveData<ArrayList<Question>> dsDetailSurvey;
    private QuestionReposipitory mReposipitory;
    private ArrayList<Question> dsQuestion;

    public QuestionViewModel() {
        mReposipitory=new QuestionReposipitory();
        _dsDetailSurvey=new MutableLiveData<>(new ArrayList<Question>());
        dsDetailSurvey=_dsDetailSurvey;
    }

    public void getQuestionById(String id) {
        _dsDetailSurvey=mReposipitory.getListQuestionById(id);
    }

    public void getQuestionById2(String id) {
            FirebaseDatabase.getInstance().getReference("Question").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Question> ds=new ArrayList<>();
                    for (DataSnapshot item: snapshot.getChildren()) {
                        Question tmp=item.getValue(Question.class);
                        ds.add(tmp);
                    }
                    _dsDetailSurvey.postValue(ds);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
}

