package com.example.qa_answer.view.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.qa_answer.R;
import com.example.qa_answer.databinding.ActivitySignUpBinding;
import com.example.qa_answer.view.Dialog.LoadingDialog;
import com.example.qa_answer.view_model.SignUpViewModel;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    ActivityResultLauncher pickImageLauncher;
    private SignUpViewModel viewModel;
    LoadingDialog dialog;
    boolean checkFirstTime=false; //Kiểm tra xem phải lần đâu vào không để không hiển thị toast

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel=new ViewModelProvider(SignUpActivity.this).get(SignUpViewModel.class);
        binding.setSignUpViewModel(viewModel);
        dialog=new LoadingDialog(this);
        //init launcher
        initLauncher();
        //Pick image
        pickImage();
        //Set sự kiện quan sát
        viewModel.getCheck().observe(SignUpActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                    if (aBoolean==true) {
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, viewModel.getToastMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, viewModel.getToastMessage(), Toast.LENGTH_SHORT).show();
                    }
            }
        });
        //Set click
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                viewModel.SignUp();
            }
        });
        //
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initLauncher() {
        pickImageLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode()==RESULT_OK) {
                Intent intent=result.getData();
                if (intent!=null) {
                    Glide.with(SignUpActivity.this)
                            .load(intent.getData())
                            .placeholder(R.mipmap.default1)
                            .into(binding.imgUser);
                    viewModel.setmImgUrl(intent.getData().toString());
                }
            }
        });
    }

    private void pickImage() {
        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(SignUpActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                pickImageLauncher.launch(intent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(SignUpActivity.this, "Hãy cấp quyền để thực hiện chức năng này", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

}