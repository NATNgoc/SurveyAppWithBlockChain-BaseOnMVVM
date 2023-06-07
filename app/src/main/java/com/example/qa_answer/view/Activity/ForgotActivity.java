package com.example.qa_answer.view.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.qa_answer.view.Dialog.ForgotpassDialog;
import com.example.qa_answer.databinding.ActivityForgotBinding;
import com.example.qa_answer.view_model.ForgotViewModel;

public class ForgotActivity extends AppCompatActivity {

    ActivityForgotBinding binding;
    private ForgotViewModel viewModel;
    private ForgotpassDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotBinding.inflate(getLayoutInflater());
        viewModel=new ViewModelProvider(this).get(ForgotViewModel.class);
        binding.setForgotViewModel(viewModel);
        setContentView(binding.getRoot());
        dialog=new ForgotpassDialog(this);
        //Set event request
        setEventForRequest();
        //Set event for button
        setEventButton();
    }
    private void setEventButton() {
        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                viewModel.sendRequestReset();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setEventForRequest() {
        viewModel.getCheck().observe(ForgotActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    if (aBoolean==true) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Toast.makeText(ForgotActivity.this, viewModel.getmToastMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }, 3000);

                    } else {
                        dialog.dismiss();
                        Toast.makeText(ForgotActivity.this, viewModel.getmToastMessage(), Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
}
