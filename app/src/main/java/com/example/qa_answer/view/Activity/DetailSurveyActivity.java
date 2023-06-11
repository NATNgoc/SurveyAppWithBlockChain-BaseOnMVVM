package com.example.qa_answer.view.Activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.widget.Toast;

import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.Block;
import com.example.qa_answer.data.model.BlockChain;
import com.example.qa_answer.data.model.DetailSurvey;
import com.example.qa_answer.data.model.Question;
import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.data.model.User;
import com.example.qa_answer.data.repository.UserRepository;
import com.example.qa_answer.databinding.ActivityDetailSurveyBinding;
import com.example.qa_answer.view.Adapter.DetailSurveyAdapter;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view_model.DetailSurveyViewModel;
import com.example.qa_answer.view_model.QuestionViewModel;
import com.example.qa_answer.view_model.QuestionViewModelFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DetailSurveyActivity extends AppCompatActivity {
    ActivityDetailSurveyBinding binding;
    private QuestionViewModel viewModel;
    ArrayList<Question> dsQuestion;
    LoadingDialog dialog;
    Survey currentSurvey;
    private DetailSurveyAdapter detailSurveyAdapter;
    ArrayList<Answer> dsAnswer;
    private DetailSurveyViewModel detailSurveyViewModel;

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
        detailSurveyViewModel=new ViewModelProvider(this).get(DetailSurveyViewModel.class);
        binding.setDetailSurveyViewModel(detailSurveyViewModel);
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
        binding.imgBack.setOnClickListener(view -> finish());

        binding.imgSend.setOnClickListener(view -> {
            dialog=new LoadingDialog(DetailSurveyActivity.this);
            dialog.show();
            if (!check()) {
                dialog.dismiss();
                Toast.makeText(DetailSurveyActivity.this,"Bạn chưa điền hết đáp án",Toast.LENGTH_SHORT).show();
            } else {
                Block lastBlock=detailSurveyViewModel.lastBlock.getValue();
                Block newBlock;
                if (lastBlock!=null) {
                    newBlock=new Block(lastBlock.getIndex()+1,new Date().getTime(),lastBlock.getHash(),UserRepository.getInstance().getmFirebaseAuth().getUid(),currentSurvey.getReward());
                    newBlock.mineBlock(BlockChain.getInstance().getProofOfWork());
                    Toast.makeText(DetailSurveyActivity.this,lastBlock.toString(),Toast.LENGTH_LONG).show();
                } else {
                    newBlock=new Block();
                    newBlock.createGenesisBlock();
                }
               detailSurveyViewModel.isAddBlockSuccessfully.observe(DetailSurveyActivity.this, aBoolean -> {
                    if (aBoolean) {
                        detailSurveyViewModel.isAddListAnswerSuccessfully.observe(DetailSurveyActivity.this, aBoolean1 -> {
                            if (aBoolean1) {
                                Toast.makeText(DetailSurveyActivity.this, "Điểm thưởng sẽ được cộng sau vài giây", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                finish();
                            } else {
                                Toast.makeText(DetailSurveyActivity.this, "Thất bại", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });
                        detailSurveyViewModel.addListAnswer(dsAnswer,0);

                    } else {
                        Toast.makeText(DetailSurveyActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                detailSurveyViewModel.addBlock(newBlock);
                User currentUser=HomeActivity.getCurrentUser();
                currentUser.setToken(currentUser.getToken()+currentSurvey.getReward());
                UserRepository.getInstance().getmFirebaseDatabase().getReference("User").child(currentUser.getIdUser()).setValue(currentUser).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DetailSurvey detailSurvey=new DetailSurvey(currentSurvey.getIdSurvey(),currentUser.getIdUser(),new Date().getTime(),currentSurvey.getReward());
                        detailSurveyViewModel.addDetailSurvey(detailSurvey);
                    }
                });
            }
        });

    }

    private void observeList() {
        viewModel.dsQuestion.observe(this, new Observer<ArrayList<Question>>() {
            @SuppressLint("NotifyDataSetChanged")
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
            tmp.setIdUser(Objects.requireNonNull(UserRepository.getInstance().getmFirebaseAuth().getCurrentUser()).getUid());
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