package com.example.qa_answer.view.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qa_answer.data.model.User;
import com.example.qa_answer.databinding.FragmentHomeBinding;
import com.example.qa_answer.view_model.HomeViewModel;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        viewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setHomeViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        //Observe check in viewmodel
        observeCheck();
        //Init user
        initUser();

        return binding.getRoot();
    }


    private void observeCheck() {
        viewModel.check.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean==true) {
                    currentUser=viewModel.user.getValue();
                } else {
                    Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUser() {
        viewModel.getCurrentUser();
    }
    @BindingAdapter("app:imgUrl")
    public static void loadUrlToImage(ImageView view, int url) {
        Glide.with(view.getContext()).load(url).into(view);
    }
}