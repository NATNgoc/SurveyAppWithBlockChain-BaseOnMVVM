package com.example.qa_answer.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BlockChain {
    int proofOfWork;
    private static BlockChain instance=null;

    private BlockChain() {
        FirebaseDatabase.getInstance().getReference("BlockChain").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                proofOfWork=snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static BlockChain getInstance() {
        if (instance==null) {
            instance=new BlockChain();
         }
        return instance;
    }
}
