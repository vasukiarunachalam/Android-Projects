package com.design.apparel.appareldesigner;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/****************************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code handles all database access functionalities like read and write of Apparels
 ***************************************************************************************/

public class ApparelDB {

    ContentValues values;
    Context context;
    int action;
    public ApparelDB() {}


    public static abstract class ApparelTable implements BaseColumns {

        public static final String TABLE_NAME = "ApparelTable";
        public static final String COLUMN_NAME_APPAREL_NAME = "apparel_name";
        public static final String COLUMN_NAME_APPAREL_TYPE = "apparel_type";
      //  public static final String COLUMN_NAME_DRAWABLE_ID = "drawable_id";
        public static final String COLUMN_NAME_APPAREL_IMG = "apparel_img";
        public static final String COLUMN_NAME_ALL_PIECES_IMG = "all_pieces_img";
        public static final String COLUMN_NAME_BOLT_WIDTH = "bolt_width";
        public static final String COLUMN_NAME_LENGTH = "length";
        public static final String COLUMN_NAME_NUM_PIECES = "num_pieces";
        public static final String COLUMN_NAME_PIECES = "pieces";

    }

    public void ApparelWrite(Context context,String name,String type,String id) {
        ApparelDBHelper mDbHelper = new ApparelDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME, name);
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE, type);
       // values.put(ApparelDB.ApparelTable.COLUMN_NAME_DRAWABLE_ID, id);


        Log.d("LOG", "In ApparelWrite");
        long newRowId;
        newRowId = db.insert(
                ApparelDB.ApparelTable.TABLE_NAME,
                null,
                values);

    }

    public void ApparelData(Context con,int act) {
        context=con;
        action=act;
        Apparel a= Controller.getInstance().getApparel();
        Log.d("in apparel data",a.getApparel_name());
        values = new ContentValues();
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME, a.getApparel_name());
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE, a.getApparel_type());
        //values.put(ApparelDB.ApparelTable.COLUMN_NAME_DRAWABLE_ID, a.getDrawable_id());
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inMutable = true;

        /*if(a.getBlob_img_apparel()== null) {

            Bitmap imageBitmap1= BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier(a.getDrawable_id(), "drawable", context.getPackageName()), opt);
            ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
            imageBitmap1.compress(Bitmap.CompressFormat.PNG, 100, bos1);
            byte[] bArray1 = bos1.toByteArray();
            values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG, bArray1);
        }
        else
        {
            values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG,a.getBlob_img_apparel());
        }

        if(a.getBlob_img_all_pieces()== null) {

            Bitmap imageBitmap2 = BitmapFactory.decodeResource(context.getResources(),
                    context.getResources().getIdentifier(a.getPieces_Drawable_id(), "drawable", context.getPackageName()), opt);
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            imageBitmap2.compress(Bitmap.CompressFormat.PNG, 100, bos2);
            byte[] bArray2 = bos2.toByteArray();
            values.put(ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG, bArray2);
        }
        else
        {
            values.put(ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG,a.getBlob_img_all_pieces());
        }*/
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG,a.getBlob_img_apparel());
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG,a.getBlob_img_all_pieces());
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_BOLT_WIDTH, a.getBolt_Width());
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_LENGTH, a.getLength());
        values.put(ApparelDB.ApparelTable.COLUMN_NAME_NUM_PIECES, a.getNum_pieces());

        Log.d("in apparel data",String.valueOf(a.getNum_pieces()));
        JSONArray jsonPatternTexts;
        JSONArray jsonPieces=new JSONArray();
        JSONObject jsonPIObject,jsonPTObject;

        for (int i=0;i< a.getNum_pieces();i++) {
            Log.d("in apparel loop", String.valueOf(i));
            Piece piece=a.getPieces().get(i);
            try {

                jsonPIObject = new JSONObject();
                jsonPIObject.put("piece_shape", piece.getPiece_shape());
                jsonPIObject.put("piece_fold", piece.getPiece_fold());
                jsonPIObject.put("piece_height",piece.getPiece_height());
                jsonPIObject.put("piece_width",piece.getPiece_width());
                jsonPIObject.put("piece_info",piece.getPiece_info());
                jsonPIObject.put("piece_image_id",piece.getPiece_image_id());

                Bitmap image = BitmapFactory.decodeResource(context.getResources(),
                        context.getResources().getIdentifier(a.getPieces().get(i).getPiece_image_id(), "drawable", context.getPackageName()), opt);
                Log.d("apparel db image id", a.pieces.get(i).getPiece_image_id());
                ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, bos1);
                byte[] bArray = bos1.toByteArray();

                String jsonString=Base64.encodeToString(bArray,Base64.DEFAULT);
                jsonPIObject.put("piece_image_blob",jsonString);

                Log.d("LOG", " json str" + jsonString.toString());
                jsonPatternTexts = new JSONArray();
                for (int k = 0; k < piece.getPatternTexts().size(); k++) {
                        PatternText pt=piece.getPatternTexts().get(k);
                        jsonPTObject = new JSONObject();

                        jsonPTObject.put("r", pt.getR());
                        jsonPTObject.put("angle", pt.getAngle());
                        jsonPTObject.put("x", pt.getX());
                        jsonPTObject.put("y", pt.getY());

                        jsonPatternTexts.put(jsonPTObject);
                        Log.d("jsonPTObject", jsonPTObject.toString());
                        Log.d("jsonPatterntexts array", jsonPatternTexts.toString());

                    }

                    jsonPIObject.put("pattern_texts", jsonPatternTexts);
                    jsonPieces.put(jsonPIObject);
                    Log.d("jsonPIObject", jsonPIObject.toString());
                    Log.d("json Pieces", jsonPieces.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        values.put(ApparelDB.ApparelTable.COLUMN_NAME_PIECES, jsonPieces.toString());

        ConfirmDialog(context,printContentValues(values));
     }


    public Apparel ApparelListRead(Context context,String aname,String tname) {

        ApparelDBHelper mDbHelper = new ApparelDBHelper(context);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Log.d("Apparel name",aname);


        String[] projection = {
                ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME,
                ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE,
                ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG,
                ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG,
                ApparelDB.ApparelTable.COLUMN_NAME_BOLT_WIDTH,
                ApparelDB.ApparelTable.COLUMN_NAME_LENGTH,
                ApparelDB.ApparelTable.COLUMN_NAME_NUM_PIECES,
                ApparelDB.ApparelTable.COLUMN_NAME_PIECES

        };
        String selection = ApparelTable.COLUMN_NAME_APPAREL_NAME + " = " + "'" + aname+"'"+" AND " + ApparelTable.COLUMN_NAME_APPAREL_TYPE + " = " + "'"+ tname +"'";
        String[] selectionArgs = { };

        Cursor c = db.query(
                ApparelDB.ApparelTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                   // The sort order


        );
        c.moveToFirst();
        Apparel a=new Apparel();
        Log.d("cursor count",String.valueOf(c.getCount()));
        if(c.getCount()<=0)
        {
            a.setApparel_type(tname);
            a.setApparel_name(aname);
            return a;
        }

        return CursorToApparel(c);
    }

    public Apparel CursorToApparel(Cursor c) {

        Apparel a=new Apparel();
        String _r,_angle;
        int _x,_y;
        a.setApparel_name(c.getString(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME)));
        a.setApparel_type(c.getString(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE)));
        a.setDrawable_id("apparel_" + a.getApparel_name().trim().replaceAll(" ", "_").toLowerCase());
        a.setPieces_Drawable_id("all_pieces_" + a.getApparel_name().trim().replaceAll(" ", "_").toLowerCase());
        a.setBlob_img_apparel(c.getBlob(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG)));
        a.setBlob_img_all_pieces(c.getBlob(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG)));
        a.setBolt_Width(c.getString(c.getColumnIndexOrThrow(ApparelTable.COLUMN_NAME_BOLT_WIDTH)));
        a.setLength(c.getString(c.getColumnIndexOrThrow(ApparelTable.COLUMN_NAME_LENGTH)));
        a.setNum_pieces(c.getInt(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_NUM_PIECES)));

        JSONArray jsonPieces, jsonPatterns, jsonPatternTexts;

        ArrayList<Piece> pieces = new ArrayList<Piece>();

        String Pieces=c.getString(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_PIECES));
        try {
            jsonPieces =new JSONArray(Pieces);
            Log.d("json read",jsonPieces.toString());


            for (int i = 0; i < jsonPieces.length(); i++) {

                JSONObject jsonPIObject=jsonPieces.getJSONObject(i);
                Piece piece=new Piece();
                piece.setPiece_shape(jsonPIObject.getString("piece_shape"));
                piece.setPiece_fold(jsonPIObject.getString("piece_fold"));
                piece.setPiece_height(jsonPIObject.getString("piece_height"));
                piece.setPiece_width(jsonPIObject.getString("piece_width"));
                piece.setPiece_info(jsonPIObject.getString("piece_info"));
                piece.setPiece_image_id(jsonPIObject.getString("piece_image_id"));

                String strimage=jsonPIObject.getString("piece_image_blob");
                byte[] bArray1 = Base64.decode(strimage,Base64.DEFAULT);
                piece.setPiece_blob_img(bArray1);

                jsonPatternTexts = jsonPIObject.getJSONArray("pattern_texts");
                ArrayList<PatternText> patterntexts = new ArrayList<PatternText>();

                for (int k = 0; k < jsonPatternTexts.length(); k++) {

                        JSONObject jsonPTObject = jsonPatternTexts.getJSONObject(k);

                        patterntexts.add(k, new PatternText(jsonPTObject.getString("r"),
                                                             jsonPTObject.getString("angle"),
                                                             jsonPTObject.getInt("x"),
                                                             jsonPTObject.getInt("y")));

                    }

                    piece.setPatternTexts(patterntexts);
                    pieces.add(piece);
                }
            a.setPieces(pieces);

        }catch (JSONException e) {
            e.printStackTrace();
        }
        c.close();
        return a;
    }

    public Cursor ApparelListRead(Context context,String tname) {

        ApparelDBHelper mDbHelper = new ApparelDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                ApparelTable.COLUMN_NAME_APPAREL_NAME,
                ApparelTable.COLUMN_NAME_APPAREL_TYPE,
                ApparelTable.COLUMN_NAME_APPAREL_IMG
        };
        String selection =  ApparelTable.COLUMN_NAME_APPAREL_TYPE + " = " + "'"+ tname +"'";
        String[] selectionArgs = { };

        Cursor c = db.query(
                ApparelTable.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                   // The sort order


        );


        return c;

    }


    public Object toObject (byte[] bytes)
    {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        }
        catch (IOException ex) {
            //TODO: Handle the exception
        }
        catch (ClassNotFoundException ex) {
            //TODO: Handle the exception
        }
        return obj;
    }
    public byte[] toByteArray (Object obj)
    {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray ();
        }
        catch (IOException ex) {
            //TODO: Handle the exception
        }
        return bytes;
    }

    public void ConfirmDialog(Context context,String message)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Apparel Details");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        setConfirmation(id);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        setConfirmation(id);
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void setConfirmation(int result) {
        if (result == DialogInterface.BUTTON_POSITIVE) {
            ApparelDBHelper mDbHelper = new ApparelDBHelper(context);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            String selection = ApparelTable.COLUMN_NAME_APPAREL_NAME + " = " + "'" +
                    values.get(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME)+"'"+" AND " +
                    ApparelTable.COLUMN_NAME_APPAREL_TYPE + " = " + "'"+
                    values.get(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_TYPE) +"'";
            String[] selectionArgs = { };
            long RowId=0;

            switch(action)
            {

                case 1:
                    Log.d("LOG", "In ApparelUpdate");

                    RowId = db.update(
                            ApparelDB.ApparelTable.TABLE_NAME,
                            values,
                            selection,
                            null
                    );

                    if (RowId==0)
                    {
                        Log.d("LOG", "In ApparelWrite");
                        Log.d("value =", values.toString());

                        RowId = db.insert(
                                ApparelDB.ApparelTable.TABLE_NAME,
                                null,
                                values);
                        Log.d("LOG write id", String.valueOf(RowId));
                    }
                    break;
                case 2:
                    Log.d("LOG", "In ApparelDelete");

                    RowId = db.delete(
                            ApparelDB.ApparelTable.TABLE_NAME,
                            selection,
                            null
                    );
                    break;

            }

//            db.close();
          //  Toast.makeText(context, "Apparel Data Added-Success"+String.valueOf(RowId), Toast.LENGTH_LONG).show();

        }


    }


    public String printContentValues(ContentValues vals)
    {
        String str_message = new String();
        Set<Map.Entry<String, Object>> s=vals.valueSet();
        Iterator itr = s.iterator();

        Log.d("DatabaseSync", "ContentValue Length :: " +vals.size());

        while(itr.hasNext())
        {
            Map.Entry me = (Map.Entry)itr.next();
            String key = me.getKey().toString();
            Object value =  me.getValue();
           if(key!= ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG || (key!= ApparelDB.ApparelTable.COLUMN_NAME_ALL_PIECES_IMG)) {
               str_message += key + " : " + (String) (value == null ? null : value.toString())+"\r\n";
           }
        }
        return str_message;
    }


    public Long deleteApparel(Context context,String aname,String tname) {
        Log.d("LOG", "In ApparelWrite");
        long DRowId;
        ApparelDBHelper mDbHelper = new ApparelDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = ApparelTable.COLUMN_NAME_APPAREL_NAME + " = " + "'" +
                aname+"'"+" AND " +
                ApparelTable.COLUMN_NAME_APPAREL_TYPE + " = " + "'"+
                tname +"'";
        String[] selectionArgs = { };
        DRowId = db.delete(
                ApparelDB.ApparelTable.TABLE_NAME,
                selection,
                null
        );
        return DRowId;
    }
}

