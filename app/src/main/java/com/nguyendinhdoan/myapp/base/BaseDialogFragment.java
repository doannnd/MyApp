package com.nguyendinhdoan.myapp.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;

import dmax.dialog.SpotsDialog;

public abstract class BaseDialogFragment extends DialogFragment {

    protected Activity activity;
    private AlertDialog loadingDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            activity = (Activity) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvent();

    protected void setUpDialog(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int deviceWith = displayMetrics.widthPixels;

        if (getDialog() != null && getDialog().getWindow() != null) {
            Window window = getDialog().getWindow();
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            window.requestFeature(Window.FEATURE_NO_TITLE);
            view.setMinimumWidth(deviceWith - 20);
        }

    }

    protected void showLoading() {
        loadingDialog = new SpotsDialog.Builder()
                .setContext(activity)
                .setCancelable(false)
                .setMessage("Loading...")
                .build();
        loadingDialog.show();
    }

    protected void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
    /**
     * show message toast ( type: res id)
     *
     * @param stringResId string res id
     */
    protected void toast(@StringRes int stringResId) {
        Toast.makeText(activity, getString(stringResId),
                Toast.LENGTH_SHORT).show();
    }

    /**
     * show message toast (type: string)
     *
     * @param message message
     */
    protected void toast(String message) {
        Toast.makeText(activity, message,
                Toast.LENGTH_SHORT).show();
    }
}
