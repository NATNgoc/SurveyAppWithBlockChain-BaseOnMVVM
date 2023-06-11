package com.example.qa_answer.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.data.model.User;
import com.example.qa_answer.R;
import com.example.qa_answer.data.repository.UserRepository;
import com.example.qa_answer.databinding.ActivityHomeBinding;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view.Fragment.HomeFragment;
import com.example.qa_answer.view.Fragment.SurveyFragment;
import com.example.qa_answer.view.ItemClickInterface;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import me.ibrahimsn.lib.OnItemSelectedListener;


public class HomeActivity extends AppCompatActivity implements ItemClickInterface {
    ActivityHomeBinding binding;
    private static User currentUser;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutBackground,new HomeFragment()).commit();
        dialog=new LoadingDialog(HomeActivity.this);
        dialog.show();
        //
        initUser();
        //Set sự kiện cho navigation
        setEventForBottomNavigation();

    }

    private void initUser() {
        UserRepository.getInstance().getmFirebaseDatabase().getReference("User")
                .child(Objects.requireNonNull(UserRepository.getInstance().getmFirebaseAuth().getCurrentUser()).getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentUser=snapshot.getValue(User.class);
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        dialog.dismiss();
                    }
                });
    }

    private void setEventForBottomNavigation() {
        binding.bottomBar.setOnItemSelectedListener((OnItemSelectedListener) i -> {
            Fragment tmp=new HomeFragment();
            switch (i) {
                case 0:
                    tmp=new HomeFragment();
                    break;
                case 1:
                    tmp=new SurveyFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutBackground,tmp).commit();
            return true;
        });

    }

    @Override
    public void OnClick(Survey survey) {
        Intent intent=new Intent(HomeActivity.this, DetailSurveyActivity.class);
        intent.putExtra("Item",survey);
        startActivity(intent);

    }



    public static User getCurrentUser() {
        return currentUser;
    }

}