package com.example.listview_m7_ppb_c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KontakDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "kontak.db";

    public KontakDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE IF NOT EXISTS " + KontakContract.KontakList.TABLE_NAME + " (" +
        KontakContract.KontakList._ID + " INTEGER PRIMARY KEY, " +
        KontakContract.KontakList.COLUMN_NAMA + " VARCHAR(20), " +
        KontakContract.KontakList.COLUMN_NO_HP + " VARCHAR(10))";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + KontakContract.KontakList.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
