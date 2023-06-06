package com.example.qa_answer.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.qa_answer.Data.Model.User;
import com.example.qa_answer.R;
import com.example.qa_answer.View.Dialog.LoadingDialog;
import com.example.qa_answer.databinding.ActivityHomeBinding;
import com.example.qa_answer.view_model.HomeViewModel;


public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    public static User currentUser;
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        viewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHomeViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());
        dialog=new LoadingDialog(this);
        //Observe check in viewmodel
        observeCheck();
        //Init user
        initUser();

    }

    private void observeCheck() {
        viewModel.check.observe(HomeActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean==true) {
                    currentUser=viewModel.user.getValue();
                    Toast.makeText(HomeActivity.this, currentUser.toString(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void initUser() {
        dialog.show();
        viewModel.getCurrentUser();
    }
}