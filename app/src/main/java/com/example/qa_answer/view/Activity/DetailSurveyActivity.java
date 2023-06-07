package com.example.qa_answer.view.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.DetailSurvey;
import com.example.qa_answer.data.model.Question;
import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.data.repository.UserRepository;
import com.example.qa_answer.databinding.ActivityDetailSurveyBinding;
import com.example.qa_answer.view.Adapter.DetailSurveyAdapter;
import com.example.qa_answer.view_model.QuestionViewModel;

import java.util.ArrayList;
import java.util.Date;

public class DetailSurveyActivity extends AppCompatActivity {
    ActivityDetailSurveyBinding binding;
    private QuestionViewModel viewModel;
    ArrayList<Question> dsQuestion;
    Survey currentSurvey;
    private DetailSurveyAdapter detailSurveyAdapter;
    ArrayList<Answer> dsAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailSurveyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.setLifecycleOwner(this);
        viewModel=new ViewModelProvider(this).get(QuestionViewModel.class);
        binding.setQuestionViewModel(viewModel);
        dsAnswer =new ArrayList<>();
        currentSurvey= (Survey) getIntent().getSerializableExtra("Item");
        dsQuestion=viewModel.dsDetailSurvey.getValue();
        detailSurveyAdapter=new DetailSurveyAdapter(DetailSurveyActivity.this,dsQuestion, dsAnswer);
        //
        observeList();
        viewModel.getQuestionById2(currentSurvey.getIdSurvey());
        //
        binding.ryc.setHasFixedSize(true);
        binding.ryc.setLayoutManager(new LinearLayoutManager(DetailSurveyActivity.this, RecyclerView.VERTICAL,false));
        binding.ryc.setAdapter(detailSurveyAdapter);
        //
        setEvent();
    }

    private void setEvent() {
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()==false) {
                    Toast.makeText(DetailSurveyActivity.this,"Bạn chưa điền hết đáp án",Toast.LENGTH_SHORT).show();
                } else {
                    
                }
            }
        });

    }

    private void observeList() {
        viewModel.dsDetailSurvey.observe(this, new Observer<ArrayList<Question>>() {
            @Override
            public void onChanged(ArrayList<Question> questions) {
                dsQuestion.clear();
                dsQuestion.addAll(questions);
                initListDetailSurvey(dsQuestion.size());
                detailSurveyAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initListDetailSurvey(int size) {
        for (int i=0;i<size;i++) {
            Answer tmp=new Answer();
            tmp.setIdUser(UserRepository.getInstance().getmFirebaseAuth().getCurrentUser().getUid());
            tmp.setSelectedChoice(-1);
            dsAnswer.add(tmp);
        }
    }
    private boolean check() {
        for (int i=0;i<=dsAnswer.size();i++) {
            Answer tmp=dsAnswer.get(i);
            if (tmp.getSelectedChoice()==-1) return false;
        }
        return true;
    }
}