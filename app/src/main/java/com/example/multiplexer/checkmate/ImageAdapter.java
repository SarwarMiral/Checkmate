package com.example.multiplexer.checkmate;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 10/23/2016.
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public ImageAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.symptoms, null);
            TextView textView = (TextView) gridView
                    .findViewById(R.id.grid_item_label);
            textView.setText(mobileValues[position]);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            if (position==0) {
                imageView.setImageResource(R.drawable.check1);
            } else if (position==1) {
                imageView.setImageResource(R.drawable.check2);
            } else if (position==2) {
                imageView.setImageResource(R.drawable.check3);
            } else if(position==3){
                imageView.setImageResource(R.drawable.check4);
            } else {
                imageView.setImageResource(R.drawable.check5);
            }
            SharedPreferences pref;
            SharedPreferences.Editor editor;
            Context context = parent.getContext();
            pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = pref.edit();
            if (pref.getString("language", "bangla").equals("bangla")) {
                AssetManager am = context.getAssets();
                Typeface typeface = Typeface.createFromAsset(am,
                        String.format(Locale.US, "font/%s", "shonar.ttf"));
                textView.setTypeface(typeface);
                if (position==0) {
                    textView.setText("লাম্প বা চাকা\n" +
                            "না দেখা গেলেও স্পর্শ\n" +
                            "করে বোঝা যেতে পারে");
                } else if (position==1) {
                    textView.setText("স্তন ত্বকের রঙ\n" +
                            "উদাহরণসরূপ স্তন" +
                            "ত্বকের\n রঙ লালচে" +
                            "হতে পারে");
                } else if (position==2) {
                    textView.setText("স্তনবৃন্তের স্থান " +
                            "পরিবর্তন");
                } else if(position==3){
                    textView.setText("র্যা শ বা জমাট\n" +
                            "বাঁধা অংশ");
                } else {
                    textView.setText("স্তনবৃন্তের থেকে\n" +
                            "স্রাব অথবা ক্ষরণ");
                }
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}