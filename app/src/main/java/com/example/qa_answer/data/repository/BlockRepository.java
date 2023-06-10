package com.example.qa_answer.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
    private FirebaseDatabase mFirebaseDatabase;
    private MutableLiveData<ArrayList<Block>> _dsBlock;
    private LiveData<ArrayList<Block>> dsBlock;
    public MutableLiveData<Boolean> isAddSuccessful;
    private MutableLiveData<Block> _lastBlock;
    public LiveData<Block> lastBlock;


    private static BlockRepository instance=null;

    private BlockRepository() {
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        _lastBlock=new MutableLiveData<>();
        _dsBlock=new MutableLiveData<>();
        isAddSuccessful=new MutableLiveData<>();
        getLastestBlock();
        getDsBlock();
        dsBlock=_dsBlock;
        lastBlock=_lastBlock;
    }

    public void getDsBlock() {
        mFirebaseDatabase.getReference("Block").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Block> ds=new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Block tmp=item.getValue(Block.class);
                    ds.add(tmp);
                }
                _dsBlock.postValue(ds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public MutableLiveData<Boolean> addBlock(Block block) {

            mFirebaseDatabase.getReference("Block")
                    .child(block.getIndex()+"")
                    .setValue(block).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                isAddSuccessful.postValue(true);
                            } else {
                                isAddSuccessful.postValue(false);
                            }
                        }
                    });

            return isAddSuccessful;
    }

    public void getLastestBlock() {
        mFirebaseDatabase.getReference("Block").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    DataSnapshot lastChild = null;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        lastChild = child;
                    }
                    // Lấy item cuối cùng trong snapshot
                    Block lastItem = lastChild.getValue(Block.class);
                    _lastBlock.postValue(lastItem);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static BlockRepository getInstance() {
        if (instance==null) {
            instance=new BlockRepository();
        }
        return instance;
    }

    public MutableLiveData<Boolean> getIsAddSuccessful() {
        return isAddSuccessful;
    }

    public LiveData<Block> getLastBlock() {
        return lastBlock;
    }

    public void setLastBlock(LiveData<Block> lastBlock) {
        this.lastBlock = lastBlock;
    }
}
