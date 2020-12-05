package com.example.latihanretrofit.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import com.example.latihanretrofit.R;

public class CustomLoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public CustomLoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_loading, null));

        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void dismissLoading(){
        dialog.dismiss();
    }
}
