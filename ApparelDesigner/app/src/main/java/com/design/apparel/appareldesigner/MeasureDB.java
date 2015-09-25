package com.design.apparel.appareldesigner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code handles all MeasureDB functionalities
 ******************************************************************************/

public final class MeasureDB {

    static int Cust_id=0;
    public MeasureDB() {
           }

    public static abstract class MeasureTable implements BaseColumns {

        public static final String TABLE_NAME = "Measure";
        public static final String COLUMN_NAME_CUST_NAME = "customer_name";
        public static final String COLUMN_NAME_CUST_ID = "CUST_ID";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_APPAREL_MEASURE = "measurement";

    }

    public void MeasureWrite(Context context,Measure m) {
        MeasureDBHelper mDbHelper = new MeasureDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Measure measure= MeasureRead(context, m.getCust_name());
        if (measure.getCust_name() == null) {

            ContentValues values = new ContentValues();

            String cust_id = "M" + Cust_id + 1;
            values.put(MeasureDB.MeasureTable.COLUMN_NAME_CUST_NAME, m.cust_name);
            values.put(MeasureDB.MeasureTable.COLUMN_NAME_CUST_ID, cust_id);
            values.put(MeasureDB.MeasureTable.COLUMN_NAME_TIMESTAMP, Calendar.getInstance(TimeZone.getDefault()).toString());
            values.put(MeasureDB.MeasureTable.COLUMN_NAME_APPAREL_MEASURE, m.getMeasure());

            Log.d("LOG", "In MeasureWrite");
            long newRowId;
            newRowId = db.insert(
                    MeasureDB.MeasureTable.TABLE_NAME,
                    null,
                    values);

        }
        else
        {
            Toast.makeText(context, "Measure name already exists , Please enter another name", Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void MeasureDelete(Context context,Measure m) {
        MeasureDBHelper mDbHelper = new MeasureDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = MeasureTable.COLUMN_NAME_CUST_NAME + " = " + "'" +
                m.getCust_name();
        String[] selectionArgs = { };
        long DRowId = db.delete(
                MeasureTable.TABLE_NAME,
                selection,
                null
        );
        db.close();
    }

    public Measure MeasureRead(Context context,String cname) {

        MeasureDBHelper mDbHelper = new MeasureDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                //  MeasureTable._ID,
                MeasureTable.COLUMN_NAME_CUST_NAME,
                MeasureDB.MeasureTable.COLUMN_NAME_CUST_ID,
                MeasureDB.MeasureTable.COLUMN_NAME_TIMESTAMP,
                MeasureDB.MeasureTable.COLUMN_NAME_APPAREL_MEASURE,
        };
        String selection = MeasureTable.COLUMN_NAME_CUST_NAME + " = " + "'" + cname + "'";
        String[] selectionArgs = { };

        Cursor c = db.query(
                MeasureTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                   // The sort order


        );
        Measure measure;
        if(c.getCount()>0) {
            c.moveToFirst();
            measure=new Measure(c.getString(c.getColumnIndexOrThrow(MeasureTable.COLUMN_NAME_CUST_NAME)),c.getString(c.getColumnIndexOrThrow(MeasureTable.COLUMN_NAME_APPAREL_MEASURE)));
            c.close();
            return measure;
        }

        return new Measure();


    }

    public Cursor MeasureListRead(Context context) {

        MeasureDBHelper mDbHelper = new MeasureDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
      //  db.execSQL("DROP TABLE Measure");
        String[] projection = {
                //  MeasureTable._ID,
                MeasureDB.MeasureTable.COLUMN_NAME_CUST_NAME,
                MeasureDB.MeasureTable.COLUMN_NAME_CUST_ID,
                MeasureDB.MeasureTable.COLUMN_NAME_TIMESTAMP,
                MeasureDB.MeasureTable.COLUMN_NAME_APPAREL_MEASURE,
        };

        String[] selectionArgs =  { };

        Cursor c = db.query(
                MeasureTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                   // The sort order


        );


        return c;

    }



}


