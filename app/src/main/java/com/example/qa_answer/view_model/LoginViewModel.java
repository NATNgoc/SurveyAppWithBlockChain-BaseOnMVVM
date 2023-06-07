package com.example.qa_answer.view_model;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.data.repository.UserRepository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class LoginViewModel extends ViewModel {

        public String mEmail="";
        public String mPass="";
        private int INVALID_EMAIL=1001;
        private int INVALID_INPUT=1002;
        UserRepository userRepository=UserRepository.getInstance();

        private MutableLiveData<Boolean> checkLogin;

    public LoginViewModel() {
        checkLogin=new MutableLiveData<>();
    }

    private String Toast_Message="";

        public void login() {
            if (check()==-1) {
                userRepository.getmFirebaseAuth().signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkLogin.postValue(true);
                        } else {
                            Toast_Message="Kiểm tra lại email hoặc mật khẩu";
                            checkLogin.postValue(false);
                        }
                    }
                });
            } else {
                checkLogin.postValue(false);
            }
        }
        private int check() {
            if (mEmail.isEmpty()||mPass.isEmpty()) {
                Toast_Message="Không được để trống";
                return INVALID_INPUT;
            }
            if (!mEmail.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
                Toast_Message="Emmail không đúng định dạng";
                return INVALID_EMAIL;
            }
            return -1;
        }

    public String getToast_Message() {
        return Toast_Message;
    }

    public MutableLiveData<Boolean> getCheckLogin() {
        return checkLogin;
    }
}
