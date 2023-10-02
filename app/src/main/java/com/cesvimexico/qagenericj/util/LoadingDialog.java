package com.cesvimexico.qagenericj.util;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.cesvimexico.qagenericj.R;

public class LoadingDialog {


    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(Activity myactivity) {
        activity = myactivity;
    }

    public void startLoadingDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();
    }


    public void dismisDialog() {
        alertDialog.dismiss();
    }

}
