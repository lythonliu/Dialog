package com.lythonliu.dialogdemo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lythonliu.dialog.CommonDialog;
import com.lythonliu.dialog.LoadingDialog;

public class MainActivity extends com.lythonliu.TouchBaseActivity {

    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
            }
        },1000);
    }

    @Override
    public void onBackPressed() {
        CommonDialog.makeBackDialog(this, new CommonDialog.OnEditContinueListener() {
            @Override
            public void onEditContinue() {
                finish();
            }
        }).show();
    }
}
