package com.example.qa_answer.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.BlockChain;
import com.example.qa_answer.data.model.DetailSurvey;
import com.example.qa_answer.data.model.Question;
import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.data.repository.BlockRepository;
import com.example.qa_answer.data.repository.UserRepository;
import com.example.qa_answer.databinding.ActivityDetailSurveyBinding;
import com.example.qa_answer.view.Adapter.DetailSurveyAdapter;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view_model.QuestionViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class DetailSurveyActivity extends AppCompatActivity {
    ActivityDetailSurveyBinding binding;
    private QuestionViewModel viewModel;
    ArrayList<Question> dsQuestion;
    LoadingDialog dialog;
    Survey currentSurvey;
    private DetailSurveyAdapter detailSurveyAdapter;
    ArrayList<Answer> dsAnswer;
    Block lastBlock;

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
                    dialog=new LoadingDialog(DetailSurveyActivity.this);
                    dialog.show();
                    FirebaseDatabase.getInstance().getReference("Block").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChildren()) {
                                DataSnapshot lastChild = null;
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    lastChild = child;
                                }
                                // Lấy item cuối cùng trong snapshot
                                lastBlock = lastChild.getValue(Block.class);
                                Block newBlock=new Block(lastBlock.getIndex()+1,new Date().getTime(),lastBlock.getHash(),UserRepository.getInstance().getmFirebaseAuth().getUid()
                                        ,currentSurvey.getReward());
                                newBlock.mineBlock(4);
                                FirebaseDatabase.getInstance().getReference("Block").child(newBlock.getIndex()+"")
                                        .setValue(newBlock).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                DetailSurvey detailSurvey=new DetailSurvey(currentSurvey.getIdSurvey(), FirebaseAuth.getInstance().getCurrentUser().getUid(),new Date().getTime(),currentSurvey.getReward());
                                                FirebaseDatabase.getInstance().getReference("DetailSurvey").child(currentSurvey.getIdSurvey()).child(detailSurvey.getIdUser()).setValue(detailSurvey).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        finish();
                                                    }
                                                });
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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
        for (int i=0;i<dsAnswer.size();i++) {
            Answer tmp=dsAnswer.get(i);
            if (tmp.getSelectedChoice()==-1) return false;
        }
        return true;
    }
}