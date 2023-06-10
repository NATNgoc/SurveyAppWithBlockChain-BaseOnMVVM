package com.example.qa_answer.data.repository;

import com.example.qa_answer.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserRepository {
    private static UserRepository instance=null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;

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
