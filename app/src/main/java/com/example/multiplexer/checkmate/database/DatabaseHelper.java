package com.example.multiplexer.checkmate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "checkmate.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDBTables(db);
    }

    private void createDBTables(SQLiteDatabase db) {
        CreateQueries createObj = new CreateQueries();
        String createNote = createObj.createNoteTable();
        try {
            db.execSQL(createNote);
            Log.i("Note: ", "Hoise");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Notes");
        onCreate(db);
    }

    public void insertNote(String s, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues noteContent = new ContentValues();
        noteContent.put("note", s);
        if(date.equals("")){
            date = "No Reminder";
        }
        noteContent.put("datetimenote", date);
        try {
            db.insert("Notes", null, noteContent);
            Log.i("Dhukse", noteContent.toString());
        } catch (SQLException e) {
            Log.i("Error", e.toString());
        } finally {
            db.close();
        }
    }

    public ArrayList<String> getAllNotes() {
        ArrayList<String> notes = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM Notes";
        Cursor cur = db.rawQuery(query, null);

        cur.moveToFirst();
        while(!cur.isAfterLast()){
            notes.add(cur.getString(cur.getColumnIndex("note"))+"\n("+cur.getString(cur.getColumnIndex("datetimenote"))+")");
            cur.moveToNext();
        }
        return notes;
    }

    public void deleteNotes(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getWritableDatabase();
        String query = "SELECT * FROM Notes";
        Cursor cur = db.rawQuery(query, null);
        cur.moveToPosition(position);
        int id = cur.getInt(cur.getColumnIndex("id"));
        try{
            db2.delete("Notes", "id = '"+id+"'", null);
        }catch (SQLException e){
            Log.i("Error", e.toString());
        }
    }
}

