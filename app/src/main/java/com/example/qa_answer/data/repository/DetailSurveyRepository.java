package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.DetailSurvey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailSurveyRepository {
    private FirebaseDatabase mFirebaseDatabase;
    private MutableLiveData<ArrayList<DetailSurvey>> _dsDetailSurvey;
    private LiveData<ArrayList<DetailSurvey>> dsDetailSurvey;
    private MutableLiveData<Boolean> isAddSuccessful;

    public DetailSurveyRepository() {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        _dsDetailSurvey=new MutableLiveData<>();
        dsDetailSurvey=_dsDetailSurvey;
        isAddSuccessful=new MutableLiveData<>();
    }

    public void getListDetailSurvey() {
        mFirebaseDatabase.getReference("DetailSurvey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DetailSurvey> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    DetailSurvey tmp=item.getValue(DetailSurvey.class);
                    ds.add(tmp);
                }
                _dsDetailSurvey.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addDetailSurvey(DetailSurvey detailSurvey) {
        FirebaseDatabase.getInstance().getReference("DetailSurvey").child(detailSurvey.getIdSurvey()).setValue(detailSurvey).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    isAddSuccessful.postValue(true);
                } else {
                    isAddSuccessful.postValue(false);
                }
            }
        });
    }

    public MutableLiveData<Boolean> getIsAddSuccessful() {
        return this.isAddSuccessful;
    }

    public LiveData<ArrayList<DetailSurvey>> getDsDetailSurvey() {
        return this.dsDetailSurvey;
    }
}
