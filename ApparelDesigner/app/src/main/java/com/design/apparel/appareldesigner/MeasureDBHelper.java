package com.design.apparel.appareldesigner;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*******************************************************************************
 * Created by VasukiSairam on 6/16/2015.
 * This code is the DB helper class for MeasureDB
 ******************************************************************************/

public class MeasureDBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MeasureDB.MeasureTable.TABLE_NAME + "(" +
                    MeasureDB.MeasureTable.COLUMN_NAME_CUST_NAME + TEXT_TYPE + COMMA_SEP +
                    MeasureDB.MeasureTable.COLUMN_NAME_CUST_ID + TEXT_TYPE + COMMA_SEP +
                    MeasureDB.MeasureTable.COLUMN_NAME_TIMESTAMP + TEXT_TYPE + COMMA_SEP +
                    MeasureDB.MeasureTable.COLUMN_NAME_APPAREL_MEASURE + TEXT_TYPE + ")";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MeasureDB.MeasureTable.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Measure.db";

    public MeasureDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG", "DB helper sql" + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and home over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}

