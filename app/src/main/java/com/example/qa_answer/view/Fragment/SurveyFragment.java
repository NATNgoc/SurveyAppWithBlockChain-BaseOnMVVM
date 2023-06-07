package com.example.qa_answer.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qa_answer.DateFormater;
import com.example.qa_answer.data.model.Survey;
import com.example.qa_answer.databinding.FragmentSurveyBinding;
import com.example.qa_answer.view.Adapter.SurveyAdapter;
import com.example.qa_answer.view_model.SurveyViewModel;

import java.util.ArrayList;
import java.util.Date;


public class SurveyFragment extends Fragment {

    FragmentSurveyBinding binding;
    private SurveyViewModel viewModel;
    ArrayList<Survey> dsSurvey;
    private SurveyAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSurveyBinding.inflate(inflater,container,false);
        viewModel=new ViewModelProvider(this).get(SurveyViewModel.class);
        binding.setSurveyViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        //Observe when list changed
        observeList();
        //
        dsSurvey=viewModel.listSurvey.getValue();
        //Set recycleView
        adapter=new SurveyAdapter(getContext(),dsSurvey);
        binding.recycleView.setHasFixedSize(true);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        binding.recycleView.setAdapter(adapter);
        return binding.getRoot();
    }

    private void observeList() {
        viewModel.listSurvey.observe(getViewLifecycleOwner(), new Observer<ArrayList<Survey>>() {
            @Override
            public void onChanged(ArrayList<Survey> surveys) {
                dsSurvey.clear();
                dsSurvey.addAll(surveys);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @BindingAdapter("app:timeStamp")
    public static void formatDate(TextView view, long timeStamp) {
        Date date=new Date(timeStamp);
        view.setText(DateFormater.dateFormater.format(date));
    }
    @BindingAdapter("app:imgUrl")
    public static void loadUrlToImage(ImageView view, String url) {
        Glide.with(view.getContext()).load(url).into(view);
    }


}