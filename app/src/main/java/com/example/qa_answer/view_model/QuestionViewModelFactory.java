package com.example.qa_answer.view_model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class QuestionViewModelFactory implements ViewModelProvider.Factory{

    String id;

    public QuestionViewModelFactory(String id) {
        this.id=id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QuestionViewModel.class)) {
            return (T) new QuestionViewModel(this.id);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
