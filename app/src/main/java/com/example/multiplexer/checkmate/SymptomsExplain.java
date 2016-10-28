package com.example.multiplexer.checkmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Admin on 23-Oct-16.
 */
public class SymptomsExplain extends AppCompatActivity{

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_symptoms_explain);

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
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SymptomsExplain.this.finish();
                startActivity(home);
            }
        });
        setBanglaTexts();
        navAction();
        ImageView prev = (ImageView) findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SymptomsExplain.this.finish();
            }
        });
    }

    private void setBanglaTexts() {
        if (pref.getString("language", "bangla").equals("bangla")) {
            AssetManager am = getApplicationContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(am,
                    String.format(Locale.US, "font/%s", "sutonny.ttf"));
            TextView tv1 = (TextView) findViewById(R.id.text1);
            TextView tv2 = (TextView) findViewById(R.id.text2);
            TextView tv3 = (TextView) findViewById(R.id.text3);
            TextView tv4 = (TextView) findViewById(R.id.text4);
            TextView tv5 = (TextView) findViewById(R.id.text5);
            TextView tv6 = (TextView) findViewById(R.id.text6);
            TextView tv7 = (TextView) findViewById(R.id.text7);
            TextView tv8 = (TextView) findViewById(R.id.text8);

            tv1.setTypeface(typeface);
            tv2.setTypeface(typeface);
            tv3.setTypeface(typeface);
            tv4.setTypeface(typeface);
            tv5.setTypeface(typeface);
            tv6.setTypeface(typeface);
            tv7.setTypeface(typeface);
            tv8.setTypeface(typeface);

            tv1.setText("†PK Kivi j¶Y¸‡jv");
            tv2.setText("mvaviYZ, wcwiq‡Wi mgqKv‡j ¯Í‡bi Z¡K cwieZ©b A_ev e¨_v Abyfe n‡Z cv‡i| ZvB wbqwgZ †PK Kiyb hv‡Z gv‡mi wfbœ wfbœ mg‡q ¯Í‡bi †Kvb A¯^vfvweK cwieZ©b †PvL Gwo‡q bv hvq|");
            tv3.setText("aiyb");
            tv4.setText("# ¯Íb, ey‡Ki DcwifvM A_ev eM‡j †Kvb jv¤ú ev PvKv Abyfe Ki‡Z cv‡ib|\n" +
                    "# ¯Í‡bi †Kvb †dvjv Ask Av‡Q? A_ev Z¡‡Ki †gvUv Ask hv Ab¨vb¨ Ask †_‡K wfbœ?\n" +
                    "# ¯Í‡bi †Kvb Ask ev eM‡j †Kvb A¯^vfvweK e¨_v Abyfe Ki‡Z cv‡ib?\n");
            tv5.setText("‡`Lyb");
            tv6.setText("# ¯Í‡bi AvKvi Ges AvK…wZ‡Z †Kvb cwieZ©b j¶bxq wKbv? †hgb GKwU ¯Íb Ab¨wUi Zyjbvq wbPy †`Lvq wKbv|\n" +
                    "# Z¡‡K †Kvb cwieZ©b j¶Yxq wKbv? †hgb, ¯Í‡bi Z¡‡K †Kvb †dvuov wKsev †`‡e hvIqv Ask †`Lv hvq wKbv|\n" +
                    "# ¯Í‡b †Kvb is cwieZ©bkxj wKbv? †hgb, ¯Íb wKQyUv jvj‡P nq wKbv|\n" +
                    "# j¶ ivLyb ¯Íbe…šÍ ¯’vb cwieZ©b K‡i‡Q wKbv? †hgb ¯Íbe…šÍ †fZ‡ii w`‡K P‡j wM‡q‡Q wKbv|\n" +
                    "# ¯Íbe…šÍ †_‡K †Kvb ¶iY ev m«ve nq wKbv| GKwU A_ev `yBwU ¯Íbe…‡šÍ ¶iY n‡Z cv‡i|\n" +
                    "# ¯Íbe…šÍ A_ev Zvi Av‡kcv‡ki RvqMvq †Kvb i¨vk A_ev RgvU evuav Ask †`Lv hvq wKbv|\n");
            tv7.setText("‡PK Kiyb");
            tv8.setText("# †Kvb A¯^vfvweK wKQy †`Lv †M‡Q? Zvn‡j hZ `ªæZ m¤¢e Wv³v‡ii mv‡_ †hvMv‡hvM Kiæb|");
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

                Intent checkNow = new Intent(SymptomsExplain.this, First_Step_One.class);
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
                Intent first_step = new Intent(SymptomsExplain.this, RemindMe.class);
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
                SymptomsExplain.this.finish();
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
