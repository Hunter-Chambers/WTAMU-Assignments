package com.example.phonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EntryProvider extends ContentProvider {
    private static final String AUTHORITY = "com.example.phonebook.EntryProvider";
    private static final String DATABASE_NAME = "entryDB.db";
    private static final String TABLE_ENTRY = "entry";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final int DATABASE_VERSION = 1;
    public static final int ENTRIES = 1;
    public static final int ENTRIES_ID = 2;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_ENTRY);
    private SQLiteDatabase sqlDB;
    private UriMatcher uriMatcher;

    @Override
    public boolean onCreate() {
        //three lines to start a ContentProvider class
        uriMatcher = new UriMatcher(uriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_ENTRY, ENTRIES);
        uriMatcher.addURI(AUTHORITY, TABLE_ENTRY + "/#", ENTRIES_ID);
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        sqlDB = dbHelper.getWritableDatabase();
        if (sqlDB != null)
            return true;
        else
            return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        // this will help build SQL queries to be sent to an SQLiteDatabase
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_ENTRY);
        int uriType = uriMatcher.match(uri);
        if(uriType != ENTRIES)
            throw new UnsupportedOperationException( "Unknown URI: " + uri +
                    " is not supported");
        Cursor cursor = queryBuilder.query(sqlDB, strings,s ,strings1 ,null ,null,s1 );
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
        long id = 0;
        if(uriType == ENTRIES)
            id = sqlDB.insert(TABLE_ENTRY, null, contentValues);
        else
            throw new UnsupportedOperationException("Unknown URI: " + uri + " is a not supported");
        getContext().getContentResolver().notifyChange(uri, null);
        return uri.parse(TABLE_ENTRY + "/" + id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int uriType = uriMatcher.match(uri);
        int rowUpdated = 0;
        if(uriType == ENTRIES)
            rowUpdated = sqlDB.update(TABLE_ENTRY, contentValues, s, strings);
        else
            throw new UnsupportedOperationException("Unknown URI: " + uri + " is not supported");
        return rowUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int uriType = uriMatcher.match(uri);
        int rowsDeleted = 0;
        if(uriType == ENTRIES)
            rowsDeleted = sqlDB.delete(TABLE_ENTRY, s, strings);
        else
            throw new UnsupportedOperationException("Unknown URI: " + uri + " is not supported");
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            String create_entry_table = " CREATE TABLE " + TABLE_ENTRY + "(" +
                    COLUMN_FIRST_NAME + " TEXT PRIMARY KEY, " +
                    COLUMN_LAST_NAME + " TEXT, " + COLUMN_PHONE_NUMBER + " TEXT )";
            sqLiteDatabase.execSQL(create_entry_table);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
            onCreate(sqLiteDatabase);
        }
    }

}