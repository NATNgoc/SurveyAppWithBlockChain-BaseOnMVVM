package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.Survey;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlockRepository {
    FirebaseDatabase mFirebaseDatabase;
    MutableLiveData<ArrayList<Block>> dsBlock;
    MutableLiveData<Boolean> isAddSuccessful;


    public BlockRepository() {
        dsBlock=new MutableLiveData<>();
        isAddSuccessful=new MutableLiveData<>();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
    }

    public MutableLiveData<ArrayList<Block>> getDsBlock() {
        mFirebaseDatabase.getReference("Block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Block> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Block tmp=item.getValue(Block.class);
                    ds.add(tmp);
                }
                dsBlock.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dsBlock;
    }

    public MutableLiveData<Boolean> addBlock() {
            return null;
    }

}
