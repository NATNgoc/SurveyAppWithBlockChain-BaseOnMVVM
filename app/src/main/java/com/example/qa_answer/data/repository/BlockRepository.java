package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.Survey;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlockRepository {
    FirebaseDatabase mFirebaseDatabase;
    MutableLiveData<ArrayList<Block>> dsBlock;
    MutableLiveData<Boolean> isAddSuccessful;
    MutableLiveData<Block> lastBlock;

    public BlockRepository() {
        lastBlock=new MutableLiveData<>();
        dsBlock=new MutableLiveData<>();
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

    public MutableLiveData<Boolean> addBlock(Block block) {
        isAddSuccessful=new MutableLiveData<>();
            mFirebaseDatabase.getReference("Block")
                    .child(block.getIndex()+"").push().
                    setValue(block).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        isAddSuccessful.postValue(true);
                    else
                        isAddSuccessful.postValue(false);
                }
            });
            return null;
    }

    public MutableLiveData<Block> getLastestBlock() {
        mFirebaseDatabase.getReference("Block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    DataSnapshot lastChild = null;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        lastChild = child;
                    }
                    // Lấy item cuối cùng trong snapshot
                    Block lastItem = lastChild.getValue(Block.class);
                    lastBlock.postValue(lastItem);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return lastBlock;
    }

    public MutableLiveData<Block> getLastBlock() {
        return lastBlock;
    }

    public void setLastBlock(MutableLiveData<Block> lastBlock) {
        this.lastBlock = lastBlock;
    }
}
