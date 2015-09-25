package com.design.apparel.appareldesigner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * Created by VasukiSairam on 6/30/2015.
 * This code contains the functions to display the patterns after mathematical
 * expression evaluation using JEXL library
 ******************************************************************************/

public class PatternMaker {

    PatternMaker()
    {
        super();
    }

    public Bitmap drawApparelBitmapFromBlob(Context c) {
        Apparel apparel;
        Bitmap imageBitmap=null;
        apparel = Controller.getInstance().getApparel();
        imageBitmap =decodeSampledBitmapFromByteArray(apparel.getBlob_img_apparel(), 200, 200);
        return imageBitmap;
    }

    public Bitmap drawAllPiecesBitmapFromBlob(Context c) {
        Apparel apparel;
        Bitmap imageBitmap=null;
        apparel = Controller.getInstance().getApparel();
        Log.d("Apparel", apparel.getApparel_name());
        imageBitmap =decodeSampledBitmapFromByteArray(apparel.getBlob_img_all_pieces(), 200, 200);
        return imageBitmap;
    }

    public Bitmap drawPieceBitmap(Context c,int _i,int t,String type,int xh,int yh,int xw,int yw) {
        Apparel apparel;
        Bitmap imageBitmap=null;

        apparel = Controller.getInstance().getApparel();
        Log.d("shape=",apparel.getPieces().get(_i).getPiece_shape());

        if(type=="shape") {
            imageBitmap = decodeSampledBitmapFromResource(c.getResources(),
                    c.getResources().getIdentifier(apparel.getPieces().get(_i).getPiece_shape(), "drawable", c.getPackageName()),
                    200, 200);
        }
        else
        {
            imageBitmap = decodeSampledBitmapFromResource(c.getResources(),
                    c.getResources().getIdentifier(apparel.getPieces().get(_i).getPiece_fold(), "drawable", c.getPackageName()),
                    200, 200);

        }
        Canvas canvas = new Canvas(imageBitmap);

        Paint p = new Paint();
        p.setTextSize(40f);
        p.setColor(Color.BLUE);
        String h,w;
        Piece pa=apparel.getPieces().get(_i);

        if(type=="fold")
        {
            h = Evaluate(pa.getPiece_height(),t);

            switch(pa.getPiece_fold())
            {
                case "one_fold":w = Evaluate(pa.getPiece_width()+"/2",t); break;
                case "two_fold":w = Evaluate(pa.getPiece_width()+"/4",t); break;
                case "four_fold":w= Evaluate(pa.getPiece_width()+"/2",t);
                                 h= Evaluate(pa.getPiece_width()+"/2",t);break;
                default:w= Evaluate(pa.getPiece_width(),t);break;
            }
        }
        else
        {
            h = Evaluate(pa.getPiece_height(),2);
            w = Evaluate(pa.getPiece_width(),2);
        }


        canvas.drawText(h+"\"", xh,yh, p);
        canvas.drawText(w + "\"", xw, yw, p);

        return imageBitmap;

    }


    public byte[] drawApparelBitmap(Context c) {
        Apparel apparel;
        Bitmap imageBitmap=null;

        apparel = Controller.getInstance().getApparel();
        if (c.getResources().getIdentifier(apparel.getDrawable_id(), "drawable", c.getPackageName()) != 0) {

            imageBitmap = decodeSampledBitmapFromResource(c.getResources(),
                    c.getResources().getIdentifier(apparel.getDrawable_id(), "drawable", c.getPackageName()),
                    200, 200);
        }
        else
        {
            imageBitmap=decodeSampledBitmapFromFile(apparel.getDrawable_id(),200, 200);
        }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inMutable = true;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        return bArray;

    }

    public byte[] drawAllPiecesBitmap(Context c) {
        Apparel apparel;
        Bitmap imageBitmap=null;

        apparel = Controller.getInstance().getApparel();
        if (c.getResources().getIdentifier(apparel.getPieces_Drawable_id(), "drawable", c.getPackageName()) != 0) {

            imageBitmap = decodeSampledBitmapFromResource(c.getResources(),
                    c.getResources().getIdentifier(apparel.getPieces_Drawable_id(), "drawable", c.getPackageName()),
                    200, 200);
        }
        else
        {
            imageBitmap=decodeSampledBitmapFromFile(apparel.getPieces_Drawable_id(),200, 200);
        }
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inScaled = true;
        opt.inMutable = true;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        return bArray;
    }

    public Bitmap drawPatternFromID(Context c,int _i) {
        Apparel apparel;
        Bitmap imageBitmap=null;

        apparel = Controller.getInstance().getApparel();

        Log.d("LOG-type1 ", "from res id");
        if (c.getResources().getIdentifier(apparel.getPieces().get(_i).getPiece_image_id(), "drawable", c.getPackageName()) != 0) {
                imageBitmap = decodeSampledBitmapFromResource(c.getResources(),
                        c.getResources().getIdentifier(apparel.getPieces().get(_i).getPiece_image_id(), "drawable", c.getPackageName()),
                        200, 200);
        }
        else
        {
               imageBitmap = decodeSampledBitmapFromFile(apparel.getPieces().get(_i).getPiece_image_id(),200, 200);
        }

        return imageBitmap;

    }

    public Bitmap drawPatternFromBlob(Context c,int _i,int type) {
        Apparel apparel;
        Bitmap imageBitmap=null;
        Canvas canvas;

        apparel = Controller.getInstance().getApparel();

        imageBitmap= decodeSampledBitmapFromByteArray(apparel.getPieces().get(_i).getPiece_blob_img(), 600, 600);

        if(type==2) {
            Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);//&lt;--true makes copy mutable
            canvas = new Canvas(mutableBitmap);

            Paint p = new Paint();
            p.setTextSize(20f);
            p.setColor(Color.BLUE);

            String r, angle, disp_str;
            Piece pa = apparel.getPieces().get(_i);

            for (int i = 0; i < pa.getPatternTexts().size(); i++) {
                r = Evaluate(pa.getPatternTexts().get(i).getR(), 1);
                angle = Evaluate(pa.getPatternTexts().get(i).getAngle(),1);
                disp_str = "(" + r + " , " + angle + ")";
                int px = pa.getPatternTexts().get(i).getX() / 6;
                int py = pa.getPatternTexts().get(i).getY() / 6;
                canvas.drawText( disp_str, px * 7.52f, py * 7.52f, p);
                Log.d("LOG-canvas width ", String.valueOf(canvas.getWidth()));
                Log.d("LOG-canvas height ", String.valueOf(canvas.getHeight()));
            }
            return mutableBitmap;
        }
        return imageBitmap;

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inMutable=true;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromByteArray(byte[] barray,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inMutable=true;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(barray,0,barray.length,options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(barray, 0, barray.length, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String filename,
                                                         int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = true;
        options.inMutable=true;
        options.inJustDecodeBounds = true;

        String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath();

        try {
            if (isSdReadable() == true) {
              BitmapFactory.decodeFile(fullPath + "/" + filename,options);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(fullPath + "/" + filename, options);
    }



    public static int calculateInSampleSize(
        BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static boolean isSdReadable() {

        boolean mExternalStorageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = true;
            Log.i("isSdReadable", "External storage card is readable.");
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.i("isSdReadable", "External storage card is readable.");
            mExternalStorageAvailable = true;
        } else {
            mExternalStorageAvailable = false;
        }

        return mExternalStorageAvailable;
    }

  /*  public void testJexl()
    {
        String r,angle,disp_str;
        r=Evaluate("math:cos(pi)",1,2);
        Log.d("result= ", r);
        r=Evaluate("M_H + M_W",1,2);
        Log.d("result= ", r);
        r=Evaluate("math:sqrt(M_BW)",1,2);
        Log.d("result= ", r);
        r=Evaluate(" math:sqrt(math:pow((10) - (M_B / 12), 2) + math:pow(((M_B) / 4), 2))",1,2);
        Log.d("result= ", r);

    }*/



    public String Evaluate(String expression,int type) {

       Measure m;

       if(type==2) {

            Controller.getInstance().setMeasure(new Measure("Test", "12:14:12:11:1:2:5:5:3"));

        }

        Apparel a = Controller.getInstance().getApparel();
        m=a.getMeasure();
        Log.d("num pieces= ",String.valueOf(a.getNum_pieces()));
        String H[]=new String[a.getNum_pieces()];
        String W[]=new String[a.getNum_pieces()];
        JexlContext context = new MapContext();
        Map<String, Object> funcs = new HashMap<String, Object>();
        funcs.put("math", new MyMath());
        JexlEngine jexl = new JexlEngine();
        jexl.setFunctions(funcs);
        Expression e = jexl.createExpression(expression);

        context.set("pi", Math.PI);
        context.set("M_B", m.getBust());
        context.set("M_H", m.getHip());
        context.set("M_W", m.getWaist());
        context.set("M_OL", m.getOutseamLength());
        context.set("M_SW", m.getShoulderWidth());
        context.set("M_SL", m.getSleeveLength());
        context.set("M_BW", m.getBackWidth());
        context.set("M_UAC", m.getUpperArmCirc());
        context.set("M_BWL", m.getBackWaistLength());
        for(int n=0;n<a.getNum_pieces();n++)
        {
            H[n]= "H"+n;
            W[n]="W"+n;
            if((a.getPieces().get(n).getPiece_height()!=null) && (a.getPieces().get(n).getPiece_width()!=null) )
            {
                context.set(H[n], a.H[n]);
                context.set(W[n], a.W[n]);
            }

        }

        Object obj;
        try {
            obj = e.evaluate(context);
            if (obj.getClass() == Integer.class) {
                return String.format("%d", obj);
            }
            if (obj.getClass() == Float.class) {
                return String.format("%.2f", obj);
            }
        }catch(Exception ex)
        {
            return "error";
        }

        return obj.toString();
    }

}
