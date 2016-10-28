package com.example.multiplexer.checkmate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 22-Oct-16.
 */

public class Remind_note extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder mAlertDlgBuilder;
    AlertDialog mAlertDialog;
    View mDialogView = null;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.reminder_note);

        // Vibrate the mobile phone
        Vibrator vibrator = (Vibrator) this
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        Uri at = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(this, at);
        r.play();

        LayoutInflater inflater = getLayoutInflater();

        // Build the dialog
        mAlertDlgBuilder = new AlertDialog.Builder(this);
        mDialogView = inflater.inflate(R.layout.dialog_remind_me, null);
        ImageView cancel = (ImageView) mDialogView.findViewById(R.id.dialog_dismiss);
        cancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    finishAffinity();
                }

            }
        });
        Button toTheApp = (Button) mDialogView.findViewById(R.id.to_the_app);
        toTheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    mAlertDialog.dismiss();
                    Intent i = new Intent(Remind_note.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });
        TextView note_txt = (TextView) mDialogView.findViewById(R.id.show_note);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        note_txt.setText(pref.getString("alarm_note", ""));
        mAlertDlgBuilder.setCancelable(false);
        mAlertDlgBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                finishAffinity();
            }
        });
        mAlertDlgBuilder.setInverseBackgroundForced(true);
        mAlertDlgBuilder.setView(mDialogView);
        mAlertDialog = mAlertDlgBuilder.create();
        mAlertDialog.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            finishAffinity();
        } else {
            finishAffinity();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        r.stop();

    }

}
