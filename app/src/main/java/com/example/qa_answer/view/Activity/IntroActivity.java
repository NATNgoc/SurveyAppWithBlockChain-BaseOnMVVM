package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.BlockChain;
import com.example.qa_answer.data.repository.BlockRepository;
import com.example.qa_answer.databinding.ActivityIntroBinding;
import com.example.qa_answer.view.Adapter.IntroActivityAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class IntroActivity extends AppCompatActivity {

    ActivityIntroBinding binding;
    private IntroActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter=new IntroActivityAdapter(IntroActivity.this);
        binding.viewPaper.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.viewPaper.setAdapter(adapter);
        binding.dotsIndicator.attachTo(binding.viewPaper);
        //
        BlockChain tmp=BlockChain.getInstance();
        BlockRepository blockRepository=BlockRepository.getInstance();
        //
        binding.dotsIndicator.setFitsSystemWindows(true);
        binding.viewPaper.setFitsSystemWindows(true);
        //Attach viewPaper to TabLayout
        new TabLayoutMediator(binding.tabLayout,binding.viewPaper, (tab, position)-> {
        }).attach();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==2) {
                    binding.button2.setVisibility(View.VISIBLE);
                } else {
                    binding.button2.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Set event for button Next
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IntroActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}