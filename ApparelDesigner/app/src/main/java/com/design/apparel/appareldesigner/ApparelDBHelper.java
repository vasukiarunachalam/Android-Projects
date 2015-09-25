package com.design.apparel.appareldesigner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*******************************************************************************
 * Created by VasukiSairam on 6/30/2015.
 * This code is the ApparelDB helper class
 ******************************************************************************/
public class ApparelDBHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String BLOB_TYPE = " BLOB";
    private static final String INT_TYPE = " INTEGER";
    private static final String P_KEY = " PRIMARY KEY";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ApparelDB.ApparelTable.TABLE_NAME + "(" +
                    ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME + TEXT_TYPE + P_KEY+COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE + TEXT_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG + BLOB_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG + BLOB_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_BOLT_WIDTH + TEXT_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_LENGTH + TEXT_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_NUM_PIECES + INT_TYPE + COMMA_SEP +
                    ApparelDB.ApparelTable.COLUMN_NAME_PIECES + TEXT_TYPE + ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ApparelDB.ApparelTable.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ApparelDB.db";

    public ApparelDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d("LOG", "DB helper sql" + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and home over
        Log.d("LOG", "DB helper sql" + SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}


