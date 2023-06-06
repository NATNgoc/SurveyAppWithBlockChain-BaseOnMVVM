package com.example.qa_answer.View.Dialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import com.airbnb.lottie.LottieAnimationView;
import com.example.qa_answer.R;

public class LoadingDialog {
    private Dialog dialog;

    public LoadingDialog(Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            LottieAnimationView animationView = dialog.findViewById(R.id.animationView);
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setContentView(int animation) {
        dialog.setContentView(animation);
    }
}
