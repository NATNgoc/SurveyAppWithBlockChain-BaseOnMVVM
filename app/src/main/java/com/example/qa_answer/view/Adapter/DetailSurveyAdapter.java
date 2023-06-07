package com.example.qa_answer.view.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qa_answer.R;
import com.example.qa_answer.data.model.Answer;
import com.example.qa_answer.data.model.DetailSurvey;
import com.example.qa_answer.data.model.Question;
import com.example.qa_answer.databinding.ItemDetailSurveyBinding;
import com.example.qa_answer.view.Activity.DetailSurveyActivity;

import java.util.ArrayList;

public class DetailSurveyAdapter extends RecyclerView.Adapter{
    Context context;

    ArrayList<Question> dsQuestion;

    ArrayList<Answer> dsAnswer;
    public DetailSurveyAdapter(Context context, ArrayList<Question> dsQuestion, ArrayList<Answer> dsAnswer) {
        this.context = context;
        this.dsQuestion = dsQuestion;
        this.dsAnswer = dsAnswer;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemDetailSurveyBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Question currentQuestion=dsQuestion.get(position);
        ViewHolder viewHolder= (ViewHolder) holder;
        viewHolder.binding.setItem(currentQuestion);
        Answer currentAnswer=dsAnswer.get(position);
        UpdateColor(currentAnswer.getSelectedChoice(),viewHolder.binding.cardView,viewHolder.binding.cardView2,viewHolder.binding.cardView3);
        currentAnswer.setIdQuestion(currentQuestion.getIdQuestion());
        viewHolder.binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAnswer.setSelectedChoice(1);
                UpdateColor(currentAnswer.getSelectedChoice(),viewHolder.binding.cardView,viewHolder.binding.cardView2,viewHolder.binding.cardView3);
            }
        });
        viewHolder.binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAnswer.setSelectedChoice(2);
                UpdateColor(currentAnswer.getSelectedChoice(),viewHolder.binding.cardView,viewHolder.binding.cardView2,viewHolder.binding.cardView3);
            }
        });
        viewHolder.binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAnswer.setSelectedChoice(3);
                UpdateColor(currentAnswer.getSelectedChoice(),viewHolder.binding.cardView,viewHolder.binding.cardView2,viewHolder.binding.cardView3);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (!dsQuestion.isEmpty()) {
            return dsQuestion.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemDetailSurveyBinding binding;
        public ViewHolder(@NonNull ItemDetailSurveyBinding tmp) {
            super(tmp.getRoot());
            binding=tmp;
        }
    }
    private  void UpdateColor(int choice,CardView card1,CardView card2,CardView card3) {
        if (choice==1) {
            card1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_main));
            card2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            card3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else
        if (choice==2) {
            card2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_main));
            card1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            card3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else
        if (choice==3) {
            card3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray_main));
            card2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            card1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            card3.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            card2.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
            card1.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }
}
