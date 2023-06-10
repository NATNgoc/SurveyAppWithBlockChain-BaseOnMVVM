package com.example.qa_answer.data.model;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BlockChain {
    private int proofOfWork;
    private static BlockChain instance = null;

    private BlockChain() {
        FirebaseDatabase.getInstance().getReference("BlockChain").child("proofOfWork").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               setProofOfWork(snapshot.getValue(Integer.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static BlockChain getInstance() {
        if (instance == null) {
            instance = new BlockChain();
        }
        return instance;
    }

    public int getProofOfWork() {
        return this.proofOfWork;
    }

    public void setProofOfWork(int proofOfWork) {
        this.proofOfWork = proofOfWork;
    }
}
