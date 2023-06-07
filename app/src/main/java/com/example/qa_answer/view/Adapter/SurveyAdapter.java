package com.example.qa_answer.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.databinding.ItemSurveyBackgroundBinding;
import com.example.qa_answer.view.Activity.HomeActivity;

import java.util.ArrayList;

public class SurveyAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<Survey> ds;

    public SurveyAdapter(Context context, ArrayList<Survey> ds) {
        this.context = context;
        this.ds = ds;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemSurveyBackgroundBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.binding.setItem(ds.get(position));
        viewHolder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity activity=(HomeActivity) context;
                activity.OnClick(ds.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (!ds.isEmpty()) {
            return ds.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSurveyBackgroundBinding binding;
        public ViewHolder(@NonNull ItemSurveyBackgroundBinding tmp) {
            super(tmp.getRoot());
            binding=tmp;
        }
    }

}
