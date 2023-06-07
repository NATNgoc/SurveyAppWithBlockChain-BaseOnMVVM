package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.qa_answer.R;
import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.BlockChain;
import com.example.qa_answer.data.repository.BlockRepository;

import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    BlockRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       
    }
}