package com.lythonliu.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import com.lythonliu.dialog.R;

/**
 * Created by lythonliu on 2015/5/9.
 * 加载中对话框
 */
public class LoadingDialog {
    private AlertDialog alertDialog;
    private Context context;

    /*构造方法*/
    public LoadingDialog(Context context) {
        this.context = context;
        alertDialog = new AlertDialog.Builder(context).create();
    }

    public void showOnListener(final OnDismissListener dismissListener, final OnCancelListener cancelListener) {
        if (alertDialog != null)
            try {
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                View view_loading = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_loading, null);
                alertDialog.getWindow().setContentView(view_loading);
                alertDialog.setOnDismissListener(dismissListener);
                alertDialog.setOnCancelListener(cancelListener);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void setDismissListener(final OnDismissListener listener) {
        if (alertDialog != null && listener != null) {
            alertDialog.setOnDismissListener(listener);
        }
    }

    public void setCancelListener(final OnCancelListener listener) {
        if (alertDialog != null && listener != null) {
            alertDialog.setOnCancelListener(listener);
        }
    }

    public void show() {
        if (alertDialog != null)
            try {
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                View view_loading = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_loading, null);
                alertDialog.getWindow().setContentView(view_loading);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void show(String loadingMsg) {

        if (alertDialog != null)
            try {
                alertDialog.show();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                View view_loading = ((Activity) context).getLayoutInflater().inflate(R.layout.dialog_loading, null);
                TextView loadingTv = (TextView) view_loading.findViewById(R.id.loadingTv);
                loadingTv.setText(loadingMsg);
                alertDialog.getWindow().setContentView(view_loading);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public boolean isShowing() {
        return alertDialog.isShowing();
    }


    public void dismiss() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
