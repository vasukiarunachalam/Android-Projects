package com.design.apparel.appareldesigner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*******************************************************************************
 *
 *  Created by VasukiSairam on 6/28/2015.
 * This code is the USer Interface to get Apparel Details from Desgigner.
 * Apparel details input are -
 * 1.Apparel name and type
 * 2.Number of pieces
 * 3. Height and Boltwidth of material required in mathematical expression.
 *
 ******************************************************************************/
public class Apparel_Admin extends FragmentActivity  {

    Button btnNext;
    EditText edtimage_id, edtnum_pieces,edtName;
    EditText edtlength,edtboltwidth;
    ImageView img_apparel,img_all_pieces;
    TableLayout t_title, t_img, t_num;
    ArrayList<String> names;
    Apparel apparel;
    Cursor c;
    Spinner spnTypes;
    public int _i, _j, _k, num;
    static final int COMPLETE = 1;
    String apparel_type;
    ArrayAdapter<String> types_adapter;
    PatternMaker pt_maker;
    Apparel_Keyboard mCustomKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getBackground().setDither(true);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.basic_data);
        pt_maker=new PatternMaker();
        Initialiser();

    }


    public void Initialiser() {
        names = new ArrayList<String>();
        apparel = new Apparel();
        Controller.getInstance().setApparel(apparel);
        _i = _j = _k = 0;

       // edtimage_id = (EditText) findViewById(R.id.edtImage);
        edtnum_pieces = (EditText) findViewById(R.id.edtNum);
        img_apparel = (ImageView) findViewById(R.id.img_apparel);
        img_all_pieces= (ImageView) findViewById(R.id.img_all_pieces);
        btnNext = (Button) findViewById(R.id.btnNext);

        edtboltwidth = (EditText)findViewById(R.id.edtBoltWidth);
        edtlength  = (EditText)findViewById(R.id.edtLength);

        mCustomKeyboard= new Apparel_Keyboard(this.findViewById(android.R.id.content),R.id.k_apparel);
        mCustomKeyboard.registerEditText(R.id.edtBoltWidth);
        mCustomKeyboard.registerEditText(R.id.edtLength);

        edtName = (EditText) findViewById(R.id.edtName);
        spnTypes = (Spinner) findViewById(R.id.spinner1);
        String[] types = new String[]{"Dress", "Skirts", "Tops"};
        types_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, types);
        spnTypes.setAdapter(types_adapter);
        spnTypes.requestFocus();

        spnTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                apparel.setApparel_type(types_adapter.getItem(position));
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                apparel.setApparel_type(apparel_type=types_adapter.getItem(0));
            }
        });

        edtName.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                     new GetApparel().execute(v.getContext(),(edtName.getText()).toString());

                }
                return false;
            }
        });



    }
    private class GetApparel extends AsyncTask<Object,Void,Void> {

        private Context con;

        @Override
        protected Void doInBackground(Object... params) {
            ApparelDB db=new ApparelDB();
            con=(Context)params[0];
            String app_name=(String)params[1];
            //db.deleteApparel(con,"Summer Dress","Dress");

               try{
                   if (app_name != null) {

                       Controller.getInstance().setApparel(db.ApparelListRead(con, app_name, apparel.getApparel_type()));
                   }
               } catch (Exception e) {
                        e.printStackTrace();
               }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setImageListener();

            }
    }

    public void setImageListener() {

        apparel = Controller.getInstance().getApparel();
        apparel.setDrawable_id("apparel_" + apparel.getApparel_name().trim().replaceAll(" ", "_").toLowerCase());
        apparel.setPieces_Drawable_id("all_pieces_" + apparel.getApparel_name().trim().replaceAll(" ", "_").toLowerCase());

        if (apparel.getBlob_img_apparel() != null) {

            img_apparel.setImageBitmap(pt_maker.drawApparelBitmapFromBlob(this));

        } else {
            byte[] barray1 = pt_maker.drawApparelBitmap(this);
            if (barray1 == null) {
                Toast.makeText(this, "Please upload image -" + apparel.getDrawable_id(), Toast.LENGTH_SHORT).show();
            } else {
                apparel.setBlob_img_apparel(barray1);
                Controller.getInstance().setApparel(apparel);
                img_apparel.setImageBitmap(pt_maker.drawApparelBitmapFromBlob(this));
            }
        }

        if (apparel.getBlob_img_all_pieces() != null) {

            img_all_pieces.setImageBitmap(pt_maker.drawAllPiecesBitmapFromBlob(this));
            setListener();

        } else {
           byte[] barray2 = pt_maker.drawAllPiecesBitmap(this);
            if (barray2 == null) {
                Toast.makeText(this, "Please upload image -" + apparel.getPieces_Drawable_id(), Toast.LENGTH_SHORT).show();
            } else {
                apparel.setBlob_img_all_pieces(barray2);
                Controller.getInstance().setApparel(apparel);
                img_all_pieces.setImageBitmap(pt_maker.drawAllPiecesBitmapFromBlob(this));
                setListener();
            }
        }
    }

    public void setListener() {

        if(apparel.getNum_pieces()!=0) {
            edtnum_pieces.setText(String.valueOf(apparel.getNum_pieces()));
        }
        edtnum_pieces.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                try {
                    apparel.setNum_pieces(Integer.parseInt(edtnum_pieces.getText().toString().trim()));
                                  } catch (NumberFormatException e) {
                    edtnum_pieces.setText(null);
                    edtnum_pieces.setError("Please enter a number");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        if(apparel.getBolt_Width()!=null) {
            edtboltwidth.setText(apparel.getBolt_Width());
        }

       /* edtboltwidth.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
                if (!hasFocus && edtboltwidth.getText().length() == 0) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            edtboltwidth.setError(null);
                            edtlength.clearFocus();
                            edtboltwidth.requestFocus();

                        }
                    }, 100);
                }
            }
        });*/

        if(apparel.getLength()!=null) {
            edtlength.setText(apparel.getLength());
        }

       /*edtlength.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
                if (!hasFocus && edtlength.getText().length() == 0) {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            edtlength.setError(null);
                            btnNext.clearFocus();
                            edtlength.requestFocus();

                        }
                    }, 100);
                }
            }
        });*/

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apparel.setBolt_Width(edtboltwidth.getText().toString());
                apparel.setLength(edtlength.getText().toString());
                Log.d("boltwidth=", edtboltwidth.getText().toString());
                Log.d("=length",  edtlength.getText().toString());
                Controller.getInstance().setApparel(apparel);
                Intent piece_admin = new Intent(v.getContext(), Piece_Admin.class);
                startActivityForResult(piece_admin, COMPLETE);

            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == COMPLETE) {
            if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {

                apparel= Controller.getInstance().getApparel();
                setImageListener();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        apparel=new Apparel();
        Controller.getInstance().setApparel(apparel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LinearLayout layout = (LinearLayout)findViewById(R.id.layoutimage);
        unbindDrawables(layout);
        System.gc();
        Runtime.getRuntime().gc();
    }


    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}







