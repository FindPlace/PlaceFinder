package com.example.anupam.places.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.example.anupam.places.R;
import com.example.anupam.places.constant.Constant;

/**
 * Created by anupam on 06-11-2016.
 */
public class ShowAlertDialogue {
    public static void showDialogue(final Context context, final Class targetClass,String alertMsg)
    {    AlertDialog alertDialog =new AlertDialog.Builder(
            context).create();
        alertDialog.setTitle(Constant.ALERT_MSG);

        // Setting Dialog Message
        alertDialog.setMessage(alertMsg);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(context,targetClass);
                context.startActivity(intent);

            }
        });
        alertDialog.show();
    }
    public static void showSettingsAlert(final Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        //Setting Dialog Title
        alertDialog.setTitle(Constant.ALERT_MSG);

        //Setting Dialog Message
        alertDialog.setMessage(Constant.ERROR_GPS);

        //On Pressing Setting button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);

            }
        });
        alertDialog.show();

    }
}
