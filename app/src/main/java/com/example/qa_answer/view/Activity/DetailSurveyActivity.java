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
import com.example.qa_answer.data.model.User;
import com.example.qa_answer.data.repository.AnswerRepository;
import com.example.qa_answer.data.repository.BlockRepository;
import com.example.qa_answer.data.repository.DetailSurveyRepository;
import com.example.qa_answer.data.repository.UserRepository;
import com.example.qa_answer.databinding.ActivityDetailSurveyBinding;
import com.example.qa_answer.view.Adapter.DetailSurveyAdapter;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view_model.QuestionViewModel;
import com.example.qa_answer.view_model.QuestionViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
        //Lấy survey hiện tại
        currentSurvey= (Survey) getIntent().getSerializableExtra("Item");
        //Set UI
        setUI();
        //Set event
        setEvent();
    }

    private void setUI() {
        //Khởi tạo viewmodel
        QuestionViewModelFactory factory = new QuestionViewModelFactory(currentSurvey.getIdSurvey());
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, factory);
        viewModel = viewModelProvider.get(QuestionViewModel.class);
        binding.setQuestionViewModel(viewModel);
        //Tạo danh sách answer
        dsAnswer =new ArrayList<>();
        //Get danh sách câu hỏi từ viewModel
        dsQuestion=viewModel.dsQuestion.getValue();
        //Tạo adapter
        detailSurveyAdapter=new DetailSurveyAdapter(DetailSurveyActivity.this,dsQuestion, dsAnswer);
        //
        observeList();
        //Set recycleView
        binding.ryc.setHasFixedSize(true);
        binding.ryc.setLayoutManager(new LinearLayoutManager(DetailSurveyActivity.this, RecyclerView.VERTICAL,false));
        binding.ryc.setAdapter(detailSurveyAdapter);
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
                dialog=new LoadingDialog(DetailSurveyActivity.this);
                dialog.show();
//                if (check()==true) {
//                    dialog.dismiss();
//                    Toast.makeText(DetailSurveyActivity.this,"Bạn chưa điền hết đáp án",Toast.LENGTH_SHORT).show();
//                } else {
                    BlockRepository blockRepository=BlockRepository.getInstance();
                    Block lastBlock=BlockRepository.getInstance().getLastBlock().getValue();
                    Block newBlock=new Block(lastBlock.getIndex()+1,new Date().getTime(),lastBlock.getHash(),UserRepository.getInstance().getmFirebaseAuth().getUid(),currentSurvey.getReward());
                    newBlock.mineBlock(BlockChain.getInstance().getProofOfWork());
                    Toast.makeText(DetailSurveyActivity.this,lastBlock.toString(),Toast.LENGTH_LONG).show();

                    BlockRepository.getInstance().isAddSuccessful.observe(DetailSurveyActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean==true) {
                                AnswerRepository answerRepository=new AnswerRepository();
                                answerRepository.getIsAddSuccessful().observe(DetailSurveyActivity.this, new Observer<Boolean>() {
                                    @Override
                                    public void onChanged(Boolean aBoolean) {
                                        if (aBoolean==true) {
                                            Toast.makeText(DetailSurveyActivity.this, "Điểm thưởng sẽ được cộng sau vài giây", Toast.LENGTH_LONG).show();
                                            dialog.dismiss();
                                            finish();
                                        } else {
                                            Toast.makeText(DetailSurveyActivity.this, "Thất bại", Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                    }
                                });
                                answerRepository.addDsAnswer(dsAnswer,0);

                            } else {
                                Toast.makeText(DetailSurveyActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                    });
                    blockRepository.addBlock(newBlock);
                    User currentUser=HomeActivity.getCurrentUser();
                    currentUser.setToken(currentUser.getToken()+currentSurvey.getReward());
                    UserRepository.getInstance().getmFirebaseDatabase().getReference("User").child(currentUser.getIdUser()).setValue(currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                DetailSurvey detailSurvey=new DetailSurvey(currentSurvey.getIdSurvey(),currentUser.getIdUser(),new Date().getTime(),currentSurvey.getReward());
                                DetailSurveyRepository repository=new DetailSurveyRepository();
                                repository.addDetailSurvey(detailSurvey);
                            }
                        }
                    });
                }
//            }
        });

    }

    private void observeList() {
        viewModel.dsQuestion.observe(this, new Observer<ArrayList<Question>>() {
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