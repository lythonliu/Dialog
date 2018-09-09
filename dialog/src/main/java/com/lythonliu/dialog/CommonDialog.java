package com.lythonliu.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by lythonliu on 2015/5/9.
 */
public class CommonDialog extends Dialog implements android.view.View.OnClickListener {

    private Context mContext;
    private LinearLayout mBody_ll;
    private LinearLayout mContent_ll;
    private TextView mTitleTv;
    private TextView mMessageTv;
    private LinearLayout mBottom_ll;
    private Button mBtnButton1;
    private Button mBtnButton2;

    private static CommonDialog mCommonDialog;
    private OnClickListener mOnClickListener1;
    private OnClickListener mOnClickListener2;
    private EditText editText;

    public CommonDialog(Context context) {
        this(context, R.layout.common_dialog);
    }

    /*
       构造方法
    * 1.supper
    * 2.member
    * 3.contentView
    * 4.findview
    * 5.events
    * 6.extralset
    * */
    public CommonDialog(Context context, int layoutResID) {
        super(context, R.style.Theme_Dialog);
        mContext = context;
        setContentView(layoutResID);
        findViews();
        initEvents();
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }



    private void findViews() {
        mBody_ll = (LinearLayout) findViewById(R.id.common_dialog_body_ll);
        mContent_ll = (LinearLayout) findViewById(R.id.common_dialog_content_ll);
        mTitleTv = (TextView) findViewById(R.id.common_dialog_title_tv);
        mMessageTv = (TextView) findViewById(R.id.common_dialog_message_tv);
        if (mMessageTv==null) mMessageTv  = (TextView) findViewById(R.id.common_dialog_editable_message);
        mBottom_ll = (LinearLayout) findViewById(R.id.common_dialog_bottom_ll);
        mBtnButton1 = (Button) findViewById(R.id.common_dialog_button_left);
        mBtnButton2 = (Button) findViewById(R.id.common_dialog_button_right);
        mBody_ll.setVisibility(View.VISIBLE);

        DisplayMetrics metric = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
        mBody_ll.setMinimumWidth(metric.widthPixels - 50);

    }

    private void initEvents() {
        mBtnButton1.setOnClickListener(this);
        mBtnButton2.setOnClickListener(this);
    }


    public void setDialogContentView(View view) {
        if (view == null)
            return;
        if (mContent_ll.getChildCount() > 0) {
            mContent_ll.removeAllViews();
        }
        mContent_ll.addView(view);
    }

    public void setDialogContentView(int resource, OnDialogCustomViewListener listener) {
        View v = LayoutInflater.from(mContext).inflate(resource, null);
        if (mContent_ll.getChildCount() > 0) {
            mContent_ll.removeAllViews();
        }
        mContent_ll.addView(v);
        if (listener != null) {
            listener.onUpdateCustomView(v);
        }
    }


    public TextView getText() {
        return mMessageTv;
    }


    public void setEnable(boolean isEnable) {
        mCommonDialog.setCancelable(isEnable);
        mCommonDialog.setCanceledOnTouchOutside(isEnable);
    }

    public static CommonDialog makeDialog(Context context, CharSequence message, CharSequence button1,DialogInterface.OnClickListener listener1, CharSequence button2, DialogInterface
            .OnClickListener listener2) {
        return makeDialog(context,null,message,button1,listener1,button2,listener2);
    }

    public static CommonDialog makeDialog(Context context, CharSequence title, CharSequence message, CharSequence button1, DialogInterface.OnClickListener listener1) {
        return makeDialog(context,title,message,button1,listener1,null,null);
    }

    public static CommonDialog makeDialog(Context context, CharSequence message, CharSequence button1,DialogInterface.OnClickListener listener1) {
        return makeDialog(context,null,message,button1,listener1);
    }

    public static CommonDialog makeEditDialog(Context context, String msgHint, CharSequence button1,DialogInterface.OnClickListener listener1, CharSequence button2, DialogInterface.OnClickListener listener2) {
        return makeDialog(context,null,msgHint,button1,listener1,button2,listener2,R.layout.common_dialog_editable);
    }

    public static CommonDialog makeBackDialog(final Context context, CharSequence message, final boolean listener1Finish, CharSequence button1, OnClickListener listener1, CharSequence button2,
                                              OnClickListener listener2) {
        if (listener1 == null) listener1 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener1Finish && context instanceof Activity) {
                    dialog.dismiss();
                    ((Activity) context).finish();
                } else {
                    dialog.cancel();
                }
            }
        };
        if (listener2 == null) listener2 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!listener1Finish && context instanceof Activity) {
                    dialog.dismiss();
                    ((Activity) context).finish();
                } else {
                    dialog.cancel();
                }
            }
        };
        if (TextUtils.isEmpty(button1)) button1 = context.getResources().getString(R.string.common_dialog_sureback);
        if (TextUtils.isEmpty(button2)) button2 = context.getResources().getString(R.string.common_dialog_cancle);

        return makeDialog(context,null,message,button1,listener1,button2,listener2);
    }

    /*
    * 返回放弃编辑提示对话框
    * */
    public static CommonDialog makeBackDialog(final Context context, final OnEditContinueListener intercface) {
        CommonDialog mBackDialog = CommonDialog.makeDialog(context, context.getString(R.string.common_dialog_msg_forgive),
                context.getString(R.string.common_dialog_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        intercface.onEditContinue();
                    }
                }, context.getString(R.string.common_dialog_continue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        dialog.cancel();
                    }
                });
        return mBackDialog;
    }

    public static CommonDialog makeDialog(Context context, CharSequence title, CharSequence message, CharSequence button1str,
                                          DialogInterface.OnClickListener listener1, CharSequence button2str, DialogInterface.OnClickListener listener2,
                                          int layoutResID) {
        if (layoutResID == -1) {
            mCommonDialog = new CommonDialog(context);
        } else {
            mCommonDialog = new CommonDialog(context, layoutResID);
        }
        if (!TextUtils.isEmpty(title)){
            mCommonDialog.setTitle(title);
        }
        mCommonDialog.setMessage(message);

        if (mCommonDialog.buttonIsExist(button1str, listener1, button2str, listener2)) {
            mCommonDialog.setButton1(button1str, listener1);
            if (TextUtils.isEmpty(button2str)){
                mCommonDialog.mBtnButton2.setVisibility(View.GONE);
                mCommonDialog.setButton1Background(R.drawable.common_dialog_bg_button_bottom);
            }else{
                mCommonDialog.setButton2(button2str, listener2);
            }
        }

        return mCommonDialog;
    }

    public static CommonDialog makeDialog(Context context, CharSequence title, CharSequence message, CharSequence button1str,
                                          DialogInterface.OnClickListener listener1, CharSequence button2str, DialogInterface.OnClickListener listener2) {
        if (listener1 == null) listener1 = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        if (listener2 == null) listener2 = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        return makeDialog(context, title, message, button1str, listener1, button2str, listener2, -1);
    }

    public static CommonDialog makeDialog(Context context, String message, String button1, OnClickListener listener1, String button2, OnClickListener listener2) {
        if (listener1 == null) listener1 = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        if (listener2 == null) listener2 = new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        };
        return CommonDialog.makeDialog(context, null,message, button1, listener1, button2, listener2);
    }

    public void setTitle(CharSequence text) {
        if (!TextUtils.isEmpty(text)) {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(text);
        } else {
            mTitleTv.setVisibility(View.GONE);
        }
    }

    public void setMessage(CharSequence text) {
        if (text != null) {
            mMessageTv.setVisibility(View.VISIBLE);
            if (mMessageTv instanceof EditText){
                mMessageTv.setHint(text);
            }else{
                mMessageTv.setText(text);
            }
        } else {
            mMessageTv.setVisibility(View.GONE);
        }
    }

    public void setMessageColor(int color) {
        if (mMessageTv != null) {
            mMessageTv.setTextColor(color);
        }
    }

    public boolean buttonIsExist(CharSequence button1, DialogInterface.OnClickListener listener1, CharSequence button2,
                                 DialogInterface.OnClickListener listener2) {
        if (!TextUtils.isEmpty(button1)/* && listener1 != null) */|| !TextUtils.isEmpty(button2) /*&& listener2 != null)*/) {
            mBottom_ll.setVisibility(View.VISIBLE);
            return true;
        } else {
            mBottom_ll.setVisibility(View.GONE);
            return false;
        }
    }

    public void setButton1(CharSequence text, DialogInterface.OnClickListener listener) {
        if (!TextUtils.isEmpty(text) && listener != null) {
            mBottom_ll.setVisibility(View.VISIBLE);
            mBtnButton1.setVisibility(View.VISIBLE);
            mBtnButton1.setText(text);
            mOnClickListener1 = listener;
        } else {
            mBtnButton1.setVisibility(View.GONE);
        }
    }

    public void setButton2(CharSequence text, DialogInterface.OnClickListener listener) {
        if (!TextUtils.isEmpty(text) && listener != null) {
            mBottom_ll.setVisibility(View.VISIBLE);
            mBtnButton2.setVisibility(View.VISIBLE);
            mBtnButton2.setText(text);
            mOnClickListener2 = listener;
        } else {
            mBtnButton2.setVisibility(View.GONE);
        }
    }

    public void setButton1Background(int id) {
        mBtnButton1.setBackgroundResource(id);
    }

    public void setButton2Background(int id) {
        mBtnButton2.setBackgroundResource(id);
    }

    public void setButton2TextColor(int color) {
        mBtnButton2.setTextColor(color);
    }

    public void setButton2Enable(boolean enabled) {
        mBtnButton2.setEnabled(enabled);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.common_dialog_button_left) {
            if (mOnClickListener1 != null) {
                mOnClickListener1.onClick(this, 0);
            }

        } else if (i == R.id.common_dialog_button_right) {
            if (mOnClickListener2 != null) {
                mOnClickListener2.onClick(this, 1);
            }

        }
    }




    public interface OnDialogCustomViewListener {
        void onUpdateCustomView(View view);
    }

    public interface OnEditContinueListener {
        void onEditContinue();
    }



    public static void release() {
        mCommonDialog = null;
    }
}
