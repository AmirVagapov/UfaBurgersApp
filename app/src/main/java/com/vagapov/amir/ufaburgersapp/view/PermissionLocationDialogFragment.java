package com.vagapov.amir.ufaburgersapp.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.vagapov.amir.ufaburgersapp.R;

public class PermissionLocationDialogFragment extends DialogFragment {

    @NonNull
    public static PermissionLocationDialogFragment newInstance() {
       return new PermissionLocationDialogFragment();
    }

    public PermissionLocationDialogFragment() {
    }

    interface RequestInterface{
        void requestPermissionsFromActivity();
    }

    RequestInterface mRequestInterface;

    public void setRequestInterface(RequestInterface requestInterface) {
        mRequestInterface = requestInterface;
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                .setMessage(R.string.location_description)
                .setNeutralButton("OK", (dialogInterface, i) -> onCancel(dialogInterface));
        return dialog.create();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mRequestInterface.requestPermissionsFromActivity();
    }
}
