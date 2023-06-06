package com.example.qa_answer.view_model;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qa_answer.Data.Model.User;
import com.example.qa_answer.Data.Repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class SignUpViewModel extends ViewModel {
    UserRepository userRepository=UserRepository.getInstance();
    public String mFullName;
    public String mPhoneNumber;
    public String mEmail;
    public String mPassword;
    public String mImgUrl;

    String toastMessage;

    private MutableLiveData<Boolean> _check;
    LiveData<Boolean> check;
    public SignUpViewModel() {
        _check=new MutableLiveData<>();
        check=_check;
        mFullName=mPhoneNumber=mEmail=mPassword=mImgUrl=toastMessage="";
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public LiveData<Boolean> getCheck() {
        return check;
    }

    public String getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
    }

    public void SignUp() {
        if (check()==true) {
            userRepository.getmFirebaseAuth().createUserWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        User user=new User(task.getResult().getUser().getUid(),mFullName,mPhoneNumber,mEmail,0);
                        userRepository.getmFirebaseDatabase().getReference("User").child(user.getIdUser()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    toastMessage = "Đăng ký thành công";
                                    _check.postValue(true);
                                } else {
                                    toastMessage="Check lại thông tin";
                                    _check.postValue(false);
                                }
                            }
                        });
                    } else {
                        toastMessage="Email đã được sử dụng";
                        _check.postValue(false);
                    }
                }
            });
        } else {
            _check.postValue(false);
        }
    }

    private boolean check() {
        if (mFullName.isEmpty()||mImgUrl.isEmpty()||mEmail.isEmpty()||mPassword.isEmpty()||mPhoneNumber.isEmpty()) {
            toastMessage="Không được để thông tin trống";
            return false;
        } else if (!mEmail.matches(String.valueOf(Patterns.EMAIL_ADDRESS))) {
            toastMessage="Email không đúng định dạng";
            return false;
        } else if (!mPhoneNumber.matches("(03|05|07|08|09|01[2689])[0-9]{8}\\b")) {
            toastMessage="Số điện thoại không đúng định dạng";
            return false;
        }
        return true;
    }


}
