package com.example.qa_answer.Data.Repository;

import androidx.annotation.NonNull;

import com.example.qa_answer.Data.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRepository {
    private static UserRepository instance=null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private User current;

    private UserRepository() {
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
    }

    public static UserRepository getInstance() {
        if (instance==null) {
            instance=new UserRepository();
        }
        return instance;
    }

    public FirebaseDatabase getmFirebaseDatabase() {
        return mFirebaseDatabase;
    }

    public FirebaseAuth getmFirebaseAuth() {
        return mFirebaseAuth;
    }


}
