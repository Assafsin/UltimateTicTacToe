package com.example.ultimatetictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "winners_db.db";
    private static final int DATABASE_VERSION = 1;

    // Table: users
    private static final String TABLE_NAME = "users";
    private static final String USER_ID = "userid";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    public static final String SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // USERS
        String createUsersTable = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "email TEXT, " +
                "score INTEGER, "+
                "password TEXT)";


        db.execSQL(createUsersTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // USER METHODS
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=? OR email=?", new String[]{username, email});

        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        }

        cursor.close();
        ContentValues values = new ContentValues();

        values.put(USERNAME, username);
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(SCORE ,0);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username=? AND password=?", new String[]{username, password});
        boolean result = cursor.moveToFirst();
        cursor.close();
        return result;
    }

    public void addData(String name, int score)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, name);
        cv.put(SCORE, score);
        boolean inserted =  db.insert(TABLE_NAME, null, cv)>0;

    }
    public boolean exist( String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME +"= ? " , new String[]{name});

        if(c.moveToFirst()) {// if moveToFirst() returns false - c is empty
            c.close();
            db.close();
            return true;
        }
        c.close();
        return false;
    }

    public boolean addToPlayerList(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = ?", new String[]{name});

        if (c != null && c.moveToFirst()) {
            // Player exists – update score
            int currentScore = c.getInt(c.getColumnIndexOrThrow(SCORE));
            currentScore++;

            ContentValues cv = new ContentValues();
            cv.put(SCORE, currentScore);

            db.update(TABLE_NAME, cv, USERNAME + " = ?", new String[]{name});
            c.close();
            db.close();
            return true;
        } else {
            // Player doesn't exist – add with score 1
            addData(name, 1);
            if (c != null) c.close();
            db.close();
            return false;
        }
    }


    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> arrayList = new ArrayList<>();

        // Order by score DESC to show highest scores at the top
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Player p = new Player(cursor.getString(1)); // username
                p.setScore(cursor.getInt(3)); // score
                arrayList.add(p);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return arrayList;
    }

    public void remove( String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, USERNAME + "= ? " , new String[]{name});
    }
}
