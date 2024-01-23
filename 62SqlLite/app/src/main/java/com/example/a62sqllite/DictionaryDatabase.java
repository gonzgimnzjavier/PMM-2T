package com.example.a62sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DictionaryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final String TABLE_DICTIONARY = "dictionary";
    private static final String FIELD_WORD = "word";
    private static final String FIELD_DEFINITION = "definition";
    private static final int DATABASE_VERSION = 1;

    DictionaryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DICTIONARY +
                "(_id INTEGER PRIMARY KEY," +
                FIELD_WORD + " TEXT, " +
                FIELD_DEFINITION + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Handle database upgrade as needed
    }

    public void saveRecord(String word, String definition) {
        long id = findWordID(word);
        if (id > 0) {
            updateRecord(id, word, definition);
        } else {
            addRecord(word, definition);
        }
    }

    public long addRecord(String word, String definition) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);
        return db.insert(TABLE_DICTIONARY, null, values);
    }

    // You need to implement these methods
    public int updateRecord(long id, String word, String definition) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_id", id);
        values.put(FIELD_WORD, word);
        values.put(FIELD_DEFINITION, definition);
        return db.update(TABLE_DICTIONARY, values, "_id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteRecord(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_DICTIONARY, "_id = ?", new String[]{String.valueOf(id)});
    }

    public long findWordID(String word) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id FROM " + TABLE_DICTIONARY +
                " WHERE " + FIELD_WORD + " = '" + word + "'";
        return db.rawQuery(query, null).getCount();
    }
    public String getDefinition(String word) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT definition FROM " + TABLE_DICTIONARY +
                " WHERE " + FIELD_WORD + " = '" + word + "'";
        return db.rawQuery(query, null).toString();
    }
}