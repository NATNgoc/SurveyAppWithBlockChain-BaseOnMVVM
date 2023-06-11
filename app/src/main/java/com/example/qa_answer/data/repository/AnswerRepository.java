package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.Block;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnswerRepository {
    private FirebaseDatabase mFirebaseDatabase;
    private MutableLiveData<ArrayList<Answer>> _dsAnswer;
    private LiveData<ArrayList<Answer>> dsAnswer;
    private MutableLiveData<Boolean> isAddSuccessful;

    public AnswerRepository() {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        _dsAnswer=new MutableLiveData<>();
        dsAnswer=_dsAnswer;
        isAddSuccessful=new MutableLiveData<>();
    }

    public void getDsBlock() {
        mFirebaseDatabase.getReference("Block").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Answer> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Answer tmp=item.getValue(Answer.class);
                    ds.add(tmp);
                }
                _dsAnswer.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void addDsAnswer(ArrayList<Answer> ds, int position) {
        if (!ds.isEmpty()) {
            if (position<ds.size()) {
                Answer answer=ds.get(position);
                FirebaseDatabase.getInstance().getReference("Answer").child(answer.getIdQuestion())
                        .child(answer.getIdUser())
                        .setValue(answer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    if (position+1<ds.size()) {
                                        addDsAnswer(ds,position+1);
                                    } else if (position==ds.size()-1) {
                                        isAddSuccessful.postValue(true);
                                    }
                                } else {
                                    isAddSuccessful.postValue(false);
                                }
                            }
                        });
            }
        }
    }

    public MutableLiveData<Boolean> getIsAddSuccessful() {
        return this.isAddSuccessful;
    }

}
