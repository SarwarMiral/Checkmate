package com.example.multiplexer.checkmate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multiplexer.checkmate.database.DatabaseHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Admin on 23-Oct-16.
 */
public class RemindMe extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    Button timeBtn, dateBtn, saveBtn;
    TextView timeText, dateText;
    EditText setNote;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
     DrawerLayout drawer;
    Calendar now = Calendar.getInstance();
    int main_year , main_monthOfYear,  main_dayOfMonth,  main_hourOfDay,  main_minute,  main_second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_remind_me);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        timeBtn = (Button) findViewById(R.id.pickTime);
        dateBtn = (Button) findViewById(R.id.pickDate);
        saveBtn = (Button) findViewById(R.id.saveButton);
        timeText = (TextView) findViewById(R.id.pickTimeText);
        dateText = (TextView) findViewById(R.id.pickDateText);
        setNote = (EditText) findViewById(R.id.editTextNote);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                RemindMe.this.finish();
                startActivity(home);
            }
        });
        setBanglaTexts();
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        RemindMe.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setThemeDark(true);
                tpd.vibrate(true);
                tpd.setAccentColor(Color.parseColor("#9C27B0"));
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Pick a Time");
                tpd.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        dateText.setText("You've set a reminder on "+main_dayOfMonth+"-"+main_monthOfYear+"-"+main_year+ " At "+hourOfDay+":"+minute);
                       if(pref.getString("language", "bangla").equals("bangla")){
                           AssetManager am = getApplicationContext().getAssets();
                           Typeface typeface = Typeface.createFromAsset(am,
                                   String.format(Locale.US, "font/%s", "sutonny.ttf"));
                           dateText.setTypeface(typeface);
                           dateText.setText("Avcwb GKwU wigvBÛvi †mU K‡i‡Qb "+main_dayOfMonth+"-"+main_monthOfYear+"-"+main_year+"  "+hourOfDay+":"+minute);
                       }
                        main_hourOfDay=hourOfDay;
                        main_minute = minute;
                        main_second = second;
                        Log.i("dates",main_dayOfMonth+" "+main_monthOfYear+" "+main_year);
                        Log.i("times",hourOfDay+" "+minute);
                    }
                });

            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RemindMe.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setThemeDark(true);
                dpd.vibrate(true);
                dpd.setAccentColor(Color.parseColor("#9C27B0"));

                Calendar[] dates2 = new Calendar[13];
                for (int i = -6; i <= 6; i++) {
                    Calendar date = Calendar.getInstance();
                    date.add(Calendar.WEEK_OF_YEAR, i);
                    dates2[i + 6] = date;
                }
                dpd.setHighlightedDays(dates2);

                dpd.show(getFragmentManager(), "Pick a Date");
                dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        main_year = year;
                        main_monthOfYear= monthOfYear;
                        main_dayOfMonth = dayOfMonth;
                        timeBtn.performClick();
                    }
                });
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateText.getText().toString().equals("")){
                    if(!setNote.getText().toString().equals("")){
                        DatabaseHelper db = new DatabaseHelper(RemindMe.this);
                        Log.i("Dhukbe", setNote.getText().toString());
                        db.insertNote(setNote.getText().toString(), "");
                        Toast.makeText(RemindMe.this, "Your note is saved.", Toast.LENGTH_LONG).show();
                        //setAlarm(main_dayOfMonth,main_monthOfYear,main_year,main_hourOfDay,main_minute,main_second);
                        editor.putString("alarm_note",setNote.getText().toString());
                        editor.commit();
                        setNote.setText("");
                        dateText.setText("");
                    }else {
                        Toast.makeText(RemindMe.this, "Please chose a date.", Toast.LENGTH_LONG).show();
                    }
                    //setAlarm(now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.MONTH),now.get(Calendar.YEAR));
                }else if(setNote.getText().toString().equals("")){
                  setNote.setError("Please set note for the reminder");
                } else {
                    DatabaseHelper db = new DatabaseHelper(RemindMe.this);
                    Log.i("Dhukbe", setNote.getText().toString());
                    db.insertNote(setNote.getText().toString(), dateText.getText().toString());
                    Toast.makeText(RemindMe.this, "Your reminder is saved.", Toast.LENGTH_LONG).show();
                    setAlarm(main_dayOfMonth,main_monthOfYear,main_year,main_hourOfDay,main_minute,main_second);
                    editor.putString("alarm_note",setNote.getText().toString());
                    editor.commit();
                    setNote.setText("");
                    dateText.setText("");
                }
            }
        });
       navAction();
    }

    private void setBanglaTexts() {
        if (pref.getString("language", "bangla").equals("bangla")) {
            AssetManager am = getApplicationContext().getAssets();
            Typeface typeface = Typeface.createFromAsset(am,
                    String.format(Locale.US, "font/%s", "sutonny.ttf"));
            dateBtn.setText("তারিখ নির্বাচন করুন");
            TextView title = (TextView) findViewById(R.id.title_reminder);
            title.setText("মনে রাখুন");
            saveBtn.setText("রিমাইন্ডার সেভ করুন");
            setNote.setTypeface(typeface);
            setNote.setHint("†bvU ivLyb");
        }
    }


    public void setAlarm(int day,int month,int y,int hour,int min , int second){

        int xx= (int) System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();

        int x= (int) System.currentTimeMillis();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE,min );
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.YEAR, y);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.DATE, day);


        Intent intent = new Intent(RemindMe.this, AlarmReciver.class);
        PendingIntent examIntent = PendingIntent.getBroadcast(
                RemindMe.this, xx, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(android.content.Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), examIntent);
        Log.i("Time in milli",cal.getTimeInMillis()+"");
       // alarmManager.set(AlarmManager.RTC_WAKEUP, time, examIntent);

    }

    public void navAction(){
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
                Intent first_step = new Intent(RemindMe.this, First_Step_One.class);
                startActivity(first_step);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
            }
        });

        Button navRemindMe= (Button) findViewById(R.id.buttonRemindMeNav);
        navRemindMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                RemindMe.this.finish();
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
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "You picked this time: " + hourString + "h " + minuteString + "m";
        timeText.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "You picked this date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
        dateText.setText(date);
    }

    @Override
    public void onResume() {
        super.onResume();
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("Pick a Time");
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Pick a Date");

        if (tpd != null) tpd.setOnTimeSetListener(this);
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

}

