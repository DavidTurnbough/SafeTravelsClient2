//**************************
// Written by David Turnbough
//**************************

package com.example.safetravelsclient;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

//**********
// Creates a pop-up dialog box instructing user how to generate a route.
//**********
public class InfoDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("")
                .setMessage("Click the middle icon at the top of the screen to create a route." + "\n" + "\n"
                             + "Click the weather emblem or temperature to open the weather view.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
