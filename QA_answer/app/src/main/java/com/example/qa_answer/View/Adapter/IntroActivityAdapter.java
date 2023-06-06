package com.example.qa_answer.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qa_answer.R;
import com.example.qa_answer.databinding.ItemAdapterIntroactivityBinding;
import java.util.ArrayList;
import java.util.Arrays;

public class IntroActivityAdapter extends RecyclerView.Adapter {
    Context context;
    private final ArrayList<Integer> dsImage=new ArrayList<>(Arrays.asList(R.mipmap.intro_image1, R.mipmap.intro_image2,R.mipmap.intro_image3));

    public IntroActivityAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemAdapterIntroactivityBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder=(ViewHolder) holder;
        viewHolder.binding.imageView.setImageResource(dsImage.get(position));
    }

    @Override
    public int getItemCount() {
        return dsImage.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ItemAdapterIntroactivityBinding binding;
        public ViewHolder(@NonNull ItemAdapterIntroactivityBinding tmp) {
            super(tmp.getRoot());
            binding=tmp;
        }
    }
}
