package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.qa_answer.data.model.User;
import com.example.qa_answer.R;
import com.example.qa_answer.databinding.ActivityHomeBinding;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view.Fragment.HomeFragment;
import com.example.qa_answer.view.Fragment.SurveyFragment;
import com.example.qa_answer.view_model.HomeViewModel;

import me.ibrahimsn.lib.OnItemSelectedListener;


public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private HomeViewModel viewModel;
    public static User currentUser;
    LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutBackground,new HomeFragment()).commit();
        //Set sự kiện cho navigation
        setEventForBottomNavigation();

    }

    private void setEventForBottomNavigation() {
        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                Fragment tmp;
                switch (i) {
                    case 0:
                        tmp=new HomeFragment();
                        break;
                    case 1:
                        tmp=new SurveyFragment();
                        break;
                    default:
                        tmp=new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.layoutBackground,tmp).commit();
                return true;
            }
        });

    }


}