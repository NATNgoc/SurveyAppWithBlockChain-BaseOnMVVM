package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestionReposipitory {
    FirebaseDatabase mFirebaseDatabase;
    MutableLiveData<ArrayList<Question>> dsQuestion;

    public QuestionReposipitory() {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        dsQuestion=new MutableLiveData<>(new ArrayList<Question>());
    }

    public MutableLiveData<ArrayList<Question>> getListQuestionById(String id) {
        mFirebaseDatabase.getReference("Question").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Question> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Question tmp=item.getValue(Question.class);
                    ds.add(tmp);
                }
                dsQuestion.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dsQuestion;
    }

}
