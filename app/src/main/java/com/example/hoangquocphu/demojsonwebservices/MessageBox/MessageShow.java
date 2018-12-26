package com.example.hoangquocphu.demojsonwebservices.MessageBox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.example.hoangquocphu.demojsonwebservices.R;

public class MessageShow {
    public static void showAlertDialog(Context context) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle("Do you married me ?");
        alertDialog.setMessage("I will kill you if you say No.");

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        alertDialog.setPositiveButton("Yes", clickListener);
        alertDialog.setNegativeButton("No", clickListener);
        alertDialog.setIcon(R.drawable.avt2);
        alertDialog.show();
    }
}
