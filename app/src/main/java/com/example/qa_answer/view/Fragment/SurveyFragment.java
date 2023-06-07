package com.example.qa_answer.view.Fragment;

import android.os.Bundle;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qa_answer.DateFormater;
import com.example.qa_answer.R;

import java.util.Date;


public class SurveyFragment extends Fragment {

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