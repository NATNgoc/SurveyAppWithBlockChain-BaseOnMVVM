package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qa_answer.databinding.ActivityLoginBinding;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view_model.LoginViewModel;


public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private LoadingDialog dialog;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        viewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setLoginViewModel(viewModel);
        setContentView(binding.getRoot());
        dialog=new LoadingDialog(this);
        //Set event thay đổi
        setEventLoginWhenChanged();
        //Set su kien
        setEventForButton();
    }

    private void setEventForButton() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                viewModel.login();
            }
        });
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        binding.txtForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setEventLoginWhenChanged() {
        viewModel.getCheckLogin().observe(LoginActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    if (aBoolean == true) {
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, viewModel.getToast_Message(), Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }
}