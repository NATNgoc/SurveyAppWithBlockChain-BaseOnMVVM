package com.example.qa_answer.view_model;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class ForgotViewModel extends ViewModel {
    UserRepository userRepository=UserRepository.getInstance();
    private MutableLiveData<Boolean> _check;
    LiveData<Boolean> check;
    public String mEmail;
    private String mToastMessage;

    public ForgotViewModel() {
        _check=new MutableLiveData<>();
        check=_check;
        mEmail=mToastMessage="";
    }

    public LiveData<Boolean> getCheck() {
        return check;
    }

    public String getmToastMessage() {
        return mToastMessage;
    }

    private boolean check() {
        if (mEmail.isEmpty()) {
            mToastMessage="Không được để trống";
            return false;
        } else
        if (!mEmail.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            mToastMessage="Email không đúng định dạng";
            return false;
        }
        return true;
    }

    public void sendRequestReset() {
        if (check()==true) {
            userRepository.getmFirebaseAuth().sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mToastMessage="Hãy vào hòm thư email để reset";
                        _check.postValue(true);
                    } else {
                        mToastMessage="Kiếm tra lại thông tin";
                        _check.postValue(false);
                    }
                }
            });
        } else {
            _check.postValue(false);
        }
    }

}
