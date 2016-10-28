package com.example.multiplexer.checkmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Admin on 22-Oct-16.
 */
public class Third_Step_One extends AppCompatActivity {

    Button makeAppointment, remindMe;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ImageView play;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_three_one);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        remindMe = (Button) findViewById(R.id.take_note2);
        makeAppointment = (Button) findViewById(R.id.take_note);
        play = (ImageView) findViewById(R.id.play);
        RelativeLayout textHolderRL = (RelativeLayout) findViewById(R.id.text_holder);
        textHolderRL.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom));
        remindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remindMe = new Intent(Third_Step_One.this, RemindMe.class);
                startActivity(remindMe);
            }
        });

        makeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://doctorola.com/m/search/Oncology-Cancer-doctors/Dhaka/all");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();
        setBanglaTexts();
        if(!pref.contains("language")){
            editor.putString("language","bangla");
            editor.commit();
        }

        if (mediaPlayer.isPlaying()) {
            play.setImageResource(android.R.drawable.ic_media_pause);
        }
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    play.setImageResource(android.R.drawable.ic_media_play);
                    mediaPlayer.pause();
                }else{
                    play.setImageResource(android.R.drawable.ic_media_pause);
                    mediaPlayer.start();
                }
            }
        });


        final RadioButton bangla = (RadioButton) findViewById(R.id.bangla);

        final RadioButton english = (RadioButton) findViewById(R.id.english);

        if(pref.getString("language","").equals("bangla")){
            bangla.setChecked(true);
            english.setChecked(false);
        }
        else {
            bangla.setChecked(false);
            english.setChecked(true);
        }
        bangla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    if(english.isChecked()){
                        english.setChecked(false);
                        editor.putString("language","bangla");
                        editor.commit();
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                    }
                }
            }
        });
        english.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(bangla.isChecked()){
                        bangla.setChecked(false);
                        editor.putString("language","english");
                        editor.commit();
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                    }
                }
            }
        });



        Button navCheckNow = (Button) findViewById(R.id.buttonCheckNowNav);
        navCheckNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        Button navRemindMe= (Button) findViewById(R.id.buttonRemindMeNav);
        navRemindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_step = new Intent(getApplicationContext(), RemindMe.class);
                first_step.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Third_Step_One.this.finish();
                startActivity(first_step);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        Button navLearnMore = (Button) findViewById(R.id.buttonLearnMoreNav);
        navLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_step = new Intent(getApplicationContext(), LearnMore.class);
                first_step.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Third_Step_One.this.finish();
                startActivity(first_step);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        Button navNotes = (Button) findViewById(R.id.buttonNotes);
        navNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_step = new Intent(getApplicationContext(), Notes.class);
                first_step.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Third_Step_One.this.finish();
                startActivity(first_step);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });
        if (pref.getString("language", "bangla").equals("bangla")) {
            AssetManager am = getApplicationContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(am,
                    String.format(Locale.US, "font/%s", "sutonny.ttf"));
            TextView settings = (TextView) findViewById(R.id.settings);
            navCheckNow.setTypeface(typeface);
            navRemindMe.setTypeface(typeface);
            navLearnMore.setTypeface(typeface);
            settings.setTypeface(typeface);
            navCheckNow.setText("চেক করুন এখনই");
            navRemindMe.setText("রিমাইন্ডার সেট করুন");
            navLearnMore.setText("আরো জানুন");
            settings.setText("সেটিংস্‌");
            navNotes.setText("নোটসমূহ");
        }



    }

    private void setBanglaTexts() {
        if (pref.getString("language", "bangla").equals("bangla")) {
            AssetManager am = getApplicationContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(am,
                    String.format(Locale.US, "font/%s", "sutonny.ttf"));
            TextView description = (TextView) findViewById(R.id.description);
            TextView title = (TextView) findViewById(R.id.title_step);
            title.setText("3q avc");
            title.setTypeface(typeface);
            TextView suspicious = (TextView) findViewById(R.id.textView);
            suspicious.setTypeface(typeface);
            suspicious.setText("m‡›`n jvM‡Q!");
            Button take_note = (Button) findViewById(R.id.take_note);
            Button take_note2 = (Button) findViewById(R.id.take_note2);
            TextView note2= (TextView) findViewById(R.id.textView2);
            take_note.setTypeface(typeface);
            take_note.setText("A¨vc‡q›U‡g›U wVK Kiæb");
            take_note2.setTypeface(typeface);
            take_note2.setText("wigvBÛvi †mU Kiæb");
            description.setTypeface(typeface);
            note2.setTypeface(typeface);
            note2.setText("cieZx© †PKAv‡ci Rb¨\nwigvBÛvi w`b");
            description.setText("†Kvb A¯^vfvweKZv †Pv‡L\n" +
                    "co‡j Aek¨B `ªæZ Wv³v‡ii\n" +
                    "kiYvcbœ nb|\n" +
                    "\n" +
                    "cÖwZgv‡m wcwiqW e‡Üi GKw`b\n" +
                    "ci wb‡RB †PK Kiæb| \n" +
                    "hv‡`i wcwiqW eÜ n‡q †M‡Q Zviv\n" +
                    "gv‡mi †h‡Kv‡bv wbw`©ó w`b\n" +
                    "†mjd †PwKs Gi Rb¨\n" +
                    "wba©viY Kiæb|\n");
            int resID = getResources().getIdentifier("bangla6", "raw", getPackageName());
            mediaPlayer = MediaPlayer.create(this, resID);
            mediaPlayer.start();

        } else {
            int resID = getResources().getIdentifier("step6", "raw", getPackageName());
            mediaPlayer = MediaPlayer.create(this, resID);
            mediaPlayer.start();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        mediaPlayer.pause();
        play.setImageResource(android.R.drawable.ic_media_play);
    }
}