package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.qa_answer.data.model.Survey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SurveyRepository {

    private FirebaseDatabase mFirebaseDatabase;
    MutableLiveData<ArrayList<Survey>> mListSurvey;

    SurveyRepository() {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mListSurvey=new MutableLiveData<>(new ArrayList<Survey>());
    }

    public MutableLiveData<ArrayList<Survey>> getListSurvey() {
        mFirebaseDatabase.getReference("Survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Survey> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Survey tmp=item.getValue(Survey.class);
                    if (tmp!=null) ds.add(tmp);
                }
                mListSurvey.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mListSurvey;
    }
}
