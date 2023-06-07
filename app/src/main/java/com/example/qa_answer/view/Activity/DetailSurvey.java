package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.qa_answer.databinding.ActivityDetailSurveyBinding;

public class DetailSurvey extends AppCompatActivity {
    ActivityDetailSurveyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailSurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);


    }
}