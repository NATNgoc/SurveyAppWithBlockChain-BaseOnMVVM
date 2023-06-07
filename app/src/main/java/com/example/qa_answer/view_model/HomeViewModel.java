package com.example.qa_answer.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.model.User;
import com.example.qa_answer.data.repository.UserRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {


   private MutableLiveData<User> _user;
    public MutableLiveData<Boolean> _check;
   public LiveData<User> user;
    public LiveData<Boolean> check;
   private UserRepository mRepository;
   public LiveData<String> uid;


   public HomeViewModel() {
       mRepository=UserRepository.getInstance();
       _user=new MutableLiveData<>();
       _check=new MutableLiveData<>();
       user=_user;
       check=_check;

   }

   public void getCurrentUser() {
           String uid=mRepository.getmFirebaseAuth().getUid();
           if (uid!=null) {
               mRepository.getmFirebaseDatabase().getReference("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       User tmp=snapshot.getValue(User.class);
                       _user.postValue(tmp);
                       _check.postValue(true);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       _check.postValue(false);
                   }
               });
           }
   }


}
