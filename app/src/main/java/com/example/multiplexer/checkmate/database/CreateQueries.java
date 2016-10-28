package com.example.multiplexer.checkmate.database;

/**
 * Created by ASA on 5/11/2016.
 */
public class CreateQueries {
    public String createNoteTable() {
        String userCreateStr = "CREATE TABLE IF NOT EXISTS Notes (id integer primary key AUTOINCREMENT NOT NULL, note text, datetimenote text)";
        return userCreateStr;
    }
}
