package com.example.multiplexer.checkmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by USER on 10/23/2016.
 */
public class LearnMore extends AppCompatActivity {

    Button buttonSymtomps, buttonAppointment, buttonLearning;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_learnmore);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        buttonSymtomps = (Button) findViewById(R.id.buttonSymtomps);
        buttonAppointment = (Button) findViewById(R.id.buttonAppointment);
        buttonLearning = (Button) findViewById(R.id.buttonLearning);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                LearnMore.this.finish();
                startActivity(home);
            }
        });
        buttonSymtomps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent symptoms = new Intent(LearnMore.this, Symptoms.class);
                startActivity(symptoms);
            }
        });
        buttonAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://doctorola.com/m/search/Oncology-Cancer-doctors/Dhaka/all");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        buttonLearning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent symptoms = new Intent(LearnMore.this, KeepLearning.class);
                startActivity(symptoms);
            }
        });
        setBanglaTexts();
        navAction();}

    private void setBanglaTexts() {
        if (pref.getString("language", "bangla").equals("bangla")) {
            AssetManager am = getApplicationContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(am,
                    String.format(Locale.US, "font/%s", "sutonny.ttf"));
            buttonSymtomps.setTypeface(typeface);
            buttonAppointment.setTypeface(typeface);
            buttonLearning.setTypeface(typeface);
            buttonSymtomps.setText("†PK Kivi jÿY ¸‡jv");
            buttonAppointment.setText("A¨vc‡q›U‡g›U wVK Kiæb");
            buttonLearning.setText("Rvb‡Z _vKyb");
        }
    }

    private void navAction() {
        if(!pref.contains("language")){
            editor.putString("language","bangla");
            editor.commit();
        }
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

                Intent checkNow = new Intent(LearnMore.this, First_Step_One.class);
                startActivity(checkNow);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        Button navRemindMe= (Button) findViewById(R.id.buttonRemindMeNav);
        navRemindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent first_step = new Intent(LearnMore.this, RemindMe.class);
                first_step.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                LearnMore.this.finish();
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
