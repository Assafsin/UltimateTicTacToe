package com.example.ultimatetictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "memory_db.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "players";
    public static final String UID = "_id";                 // primary Key, automatic ID
    public static final String KEY_NAME = "name";           // name of the player
    public static final String SCORE = "score";               // number of wins


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NAME + " TEXT, " + SCORE + " INTEGER );";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * @param name
     */
    public void addData(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(SCORE, 0);
        boolean inserted =  db.insert(TABLE_NAME, null, cv)>0;


    }

    public void addData(String name, int score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(SCORE, score);
        boolean inserted =  db.insert(TABLE_NAME, null, cv)>0;

    }

    /**
     * @param name
     * @return
     */
    public boolean exist( String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME +"= ? " , new String[]{name});

        if(c.moveToFirst()) {// if moveToFirst() returns false - c is empty
            c.close();
            db.close();
            return true;
        }
        c.close();
        return false;
    }

    /**
     * @param name
     * @return
     */
    public boolean addToPlayerList( String name,int cards)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_NAME +"= ? " , new String[]{name});

        /*  if the player exist in the database */
        if(c!=null && c.moveToFirst()) {// if moveToFirst() returns false - c is empty
            Log.d("in addToPlayerList", "exist");
            String d = c.getString(1);
            int score = c.getInt(2);
            score+=cards;
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME, name);
            cv.put(SCORE, score);
            db.update(TABLE_NAME, cv, KEY_NAME+"= ? " , new String[]{name});
            c.close();
            db.close();
            return true;
        }
        /* adding a new player to the database*/
        else {
            this.addData(name,cards);
        }
        c.close();
        return false;
    }


    /**
     * Getting all names
     * returns arrayList of players
     * */
    public ArrayList<String> getAllNames(){
        ArrayList<String> players = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while (!cursor.isAfterLast())  {
            players.add(cursor.getString(1));
            cursor.moveToNext();
        }
        // closing connection
        cursor.close();
        return players;
    }

    /**
     * @return
     */
    public ArrayList<Integer> getAllWins(){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while (!cursor.isAfterLast())  {
            arrayList.add(cursor.getInt(2));
            cursor.moveToNext();
        }
        // closing connection
        cursor.close();
        return arrayList;
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> arrayList = new ArrayList<Player>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Player p;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        cursor.moveToFirst();
        while (!cursor.isAfterLast())  {
            p = new Player(cursor.getString(1));
            p.setScore(cursor.getInt(2));
            arrayList.add(p);
            cursor.moveToNext();
        }
        // closing connection
        cursor.close();
        db.close();

        return arrayList;
    }

    public void remove( String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + "= ? " , new String[]{name});
    }
}


