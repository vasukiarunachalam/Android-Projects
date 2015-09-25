package com.design.apparel.appareldesigner;

/*******************************************************************************
 * Created by VasukiSairam on 6/12/2015.
 * This code handles Logindatabase adapter
 ******************************************************************************/

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;

    import java.util.Calendar;
    import java.util.TimeZone;

public class LoginDataBaseAdapter
    {
        static final String DATABASE_NAME = "login.db";
        static final int DATABASE_VERSION = 1;
        public static final int NAME_COLUMN = 1;
        static final String DATABASE_CREATE = "create table "+"LOGIN"+
                "( " +"ID"+" integer primary key autoincrement,"+ "USEREMAIL  text,TIMESTAMP text); ";
        public  SQLiteDatabase db;
        private final Context context;
        private DataBaseHelper dbHelper;
        public  LoginDataBaseAdapter(Context _context)
        {
            context = _context;
            dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public  LoginDataBaseAdapter open() throws SQLException
        {
            db = dbHelper.getWritableDatabase();
            return this;
        }
        public void close()
        {
            db.close();
        }

        public  SQLiteDatabase getDatabaseInstance()
        {
            return db;
        }

        public void insertEntry(String userEmail)
        {
            ContentValues newValues = new ContentValues();
            // Assign values for each row.
            newValues.put("USEREMAIL", userEmail);
            newValues.put("TIMESTAMP", Calendar.getInstance(TimeZone.getDefault()).toString());

            // Insert the row into your table
            db.insert("LOGIN", null, newValues);
            ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
        }
        public int deleteEntry(String UserEmail)
        {
            //String id=String.valueOf(ID);
            String where="USEREMAIL=?";
            int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserEmail}) ;
            // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
            return numberOFEntriesDeleted;
        }
        public Boolean getSingleEntry(String userEmail)
        {
            Cursor cursor=db.query("LOGIN", null, " USEREMAIL=?", new String[]{userEmail}, null, null, null);
            if(cursor.getCount()<1) // UserName Not Exist
            {
                cursor.close();
                return false;
            }
            return true;
        }
        public String getSingleEntry()
        {
            Cursor cursor = null;

            try{

                cursor = db.rawQuery("SELECT USEREMAIL FROM LOGIN",null);

                if(cursor.getCount() > 0) {

                    cursor.moveToFirst();
                    String useremail = cursor.getString(cursor.getColumnIndex("USEREMAIL"));
                    cursor.close();
                    return useremail;
                }
                else
                {
                    return null;
                }

            }finally {

                cursor.close();
            }

        }

        public void  updateEntry(String userEmail)
        {
            // Define the updated row content.
            ContentValues updatedValues = new ContentValues();
            // Assign values for each row.
            updatedValues.put("USEREMAIL", userEmail);
            updatedValues.put("TIMESTAMP", Calendar.getInstance(TimeZone.getDefault()).toString());

            String where="USEREMAIL = ?";
            db.update("LOGIN",updatedValues, where, new String[]{userEmail});
        }
    }

