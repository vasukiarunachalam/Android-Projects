package com.design.apparel.appareldesigner;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/30/2015.
 * This code displays dialog to accept pattern details
 ******************************************************************************/

public class PatternDialog extends DialogFragment {
    int _i;
    EditText edtImageid,edtR, edtangle, edtx, edty;
    Button btnsave,btnOK,btncancel;
    int touchX,touchY,imageX,imageY;
    Apparel apparel;
    int textViewClicked;
    TextView tv;
    View v;
    PatternMaker pt_maker;
    int patternTextCount;
    RelativeLayout rlayout;
    Apparel_Keyboard mCustomKeyboard;

    public static PatternDialog newInstance(int num) {
        PatternDialog f = new PatternDialog();
        Bundle args = new Bundle();
        args.putInt("i", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       _i = getArguments().getInt("i");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        v = inflater.inflate(R.layout.pattern_admin, container,
                false);
        pt_maker=new PatternMaker();
        textViewClicked=-1;
        edtImageid = (EditText) v.findViewById(R.id.edtImageid);
        edtR = (EditText) v.findViewById(R.id.edtR);
        edtangle = (EditText) v.findViewById(R.id.edtAngle);
        edtx=(EditText) v.findViewById(R.id.edtX);
        edty=(EditText) v.findViewById(R.id.edtY);
        rlayout=(RelativeLayout) v.findViewById(R.id.layoutimage);
        btnOK = (Button) v.findViewById(R.id.btnOK);
        btncancel = (Button) v.findViewById(R.id.btncancel);
        btnsave = (Button) v.findViewById(R.id.btnSave);
        apparel = Controller.getInstance().getApparel();
        patternTextCount=0;

        addListener();

        btnOK.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                             Controller.getInstance().setApparel(apparel);
                                             unbindDrawables(rlayout);
                                             OnFinishDialog mcallBack = (OnFinishDialog)getTargetFragment();
                                             mcallBack.OnFinishDialog(true);
                                             dismiss();
                                         }
                                 });

        btncancel.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             apparel.getPieces().get(_i).setPatternTexts(new ArrayList<PatternText>());
                                             Controller.getInstance().setApparel(apparel);
                                             OnFinishDialog mcallBack = (OnFinishDialog)getTargetFragment();
                                             mcallBack.OnFinishDialog(false);
                                             dismiss();

                                         }
                                     });

        btnsave.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           detectTextView();
                                           edtR.setText(null);
                                           edtangle.setText(null);
                                           edtx.setText(null);
                                           edty.setText(null);
                                           }


                                   });
        return v;
    }

public void detectTextView() {

    if (textViewClicked != -1) {
        if (edtR.getText().length() == 0 && edtangle.getText().length() == 0) {
            apparel.getPieces().get(_i).updatePatternText(textViewClicked, new PatternText(null, null, 0, 0));
            Log.d(" textView deleted ", String.valueOf(textViewClicked));
            Controller.getInstance().setApparel(apparel);
        } else {
            apparel.getPieces().get(_i).updatePatternText(textViewClicked, new PatternText(
                    edtR.getText().toString(),
                    edtangle.getText().toString(),
                    Integer.parseInt(edtx.getText().toString()),
                    Integer.parseInt(edty.getText().toString())));
            Controller.getInstance().setApparel(apparel);
            Log.d(" textView updated ", String.valueOf(textViewClicked));

        }
        updateTextViews();

    } else {
        Log.d("add text ", String.valueOf(apparel.getPieces().get(_i).getPatternTexts().size()));
        if (edtR.getText().toString() != null && edtangle.getText().toString() != null) {
            apparel = Controller.getInstance().getApparel();
            apparel.getPieces().get(_i).addPatternText(new PatternText(
                    edtR.getText().toString(),
                    edtangle.getText().toString(),
                    Integer.parseInt(edtx.getText().toString()),
                    Integer.parseInt(edty.getText().toString())));
            Controller.getInstance().setApparel(apparel);
            patternTextCount++;
            displayTextView();
        } else {
            if (edtR.getText().toString().length() == 0) {
                edtR.setError("Please add valid R value");
                edtR.requestFocus();
            } else {
                if (edtangle.getText().toString().length() == 0) {

                        edtangle.setError("Please add valid Angle value");
                        edtangle.requestFocus();
                    }
            }
       }
    }

}

public void displayTextView() {

    String r,angle,disp_str;
    PatternText pa;

    pa=apparel.getPieces().get(_i).getPatternTexts().get(patternTextCount-1);

    Log.d("pa x y ",String.valueOf(pa.getX())+" "+String.valueOf(pa.getY()));

    r = pt_maker.Evaluate(pa.getR(),2);
    angle = pt_maker.Evaluate(pa.getAngle(),2);
    if(r!="error" && angle!="error") {
            tv = new TextView(v.getContext());
            tv.setTextColor(Color.BLUE);
            tv.setTextSize(10f);
            tv.setX(pa.getX());
            tv.setY(pa.getY());
            disp_str = "(" + r + " , " + angle + ")";
            tv.setText(disp_str);
            tv.setId(patternTextCount-1);
            Log.d("  setid=  ",String.valueOf(tv.getId()));
            rlayout.addView(tv,patternTextCount-1);
            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Log.d("when textview clicked ",String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getX())
                            +String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getY()));
                    textViewClicked=v.getId();
                    Log.d("getid=  ",String.valueOf(v.getId()));
                    edtR.setText(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getR());
                    edtangle.setText(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getAngle());
                    edtx.setText(String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getX()));
                    edty.setText(String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getY()));

                }
            });

        }
        else
        {
            if (r == "error") {
                edtR.setError("Please add valid R value");
                edtR.requestFocus();
            }
            else
            {
                edtangle.setError("Please add valid Angle value");
                edtangle.requestFocus();
            }
        }


    }
    public void updateTextViews() {

        String r,angle,disp_str;
        PatternText pa;
        apparel= Controller.getInstance().getApparel();

        for(int k=0;k<apparel.getPieces().get(_i).getPatternTexts().size();k++)
        {
            pa=apparel.getPieces().get(_i).getPatternTexts().get(k);
            Log.d("pa x y ",String.valueOf(pa.getX())+" "+String.valueOf(pa.getY()));
            r = pt_maker.Evaluate(pa.getR(),2);
            angle = pt_maker.Evaluate(pa.getAngle(),2);
            if(r!="error" && angle!="error") {
                 tv = (TextView) rlayout.getChildAt(k);
                 tv.setTextColor(Color.BLUE);
                 tv.setTextSize(10f);
                 tv.setX(pa.getX());
                 tv.setY(pa.getY());
                 disp_str = "(" + r + " , " + angle + ")";
                 tv.setText(disp_str);
                 tv.setId(k);
                 Log.d("  setid=  ", String.valueOf(tv.getId()));
            }
        }
        if(apparel.getPieces().get(_i).getPatternTexts().size() < rlayout.getChildCount()) {
            rlayout.removeViewAt(rlayout.getChildCount()-1);
        }
    }

    public void addTextViews() {

        String r,angle,disp_str;
        PatternText pa;
        apparel= Controller.getInstance().getApparel();
        patternTextCount=0;
        for(int k=0;k<apparel.getPieces().get(_i).getPatternTexts().size();k++)
        {
           patternTextCount++;
           displayTextView();

        }
    }

    public void addListener()
    {
        mCustomKeyboard= new Apparel_Keyboard(v,R.id.k_pattern);
        mCustomKeyboard.registerEditText(R.id.edtR);
        mCustomKeyboard.registerEditText(R.id.edtAngle);

        rlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX=(int) event.getX();
                    touchY=(int) event.getY();
                    Log.d(" layout click X Y ",String.valueOf(touchX)+String.valueOf(touchY));
                    textViewClicked=-1;
                    edtx.setText(String.valueOf(touchX));
                    edty.setText(String.valueOf(touchY));

                    edtR.setText(null);
                    edtangle.setText(null);
                    edtR.requestFocus();
                   }
                return true;
            }
        });



        edtImageid.setText("piece"+(_i+1)+"_"+apparel.getApparel_name().trim().replace(" ","_").toLowerCase());

        if(apparel.getPieces().get(_i).getPiece_image_id()!=null)
        {
            if(apparel.getPieces().get(_i).getPiece_blob_img()!=null) {

                unbindDrawables(rlayout);
                Drawable dr = new BitmapDrawable(getResources(),pt_maker.drawPatternFromBlob(v.getContext(),_i,1));
                rlayout.setBackground(dr);
                addTextViews();

            }
        }

        edtImageid.setOnKeyListener( new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if (getResources().getIdentifier(edtImageid.getText().toString(), "drawable", v.getContext().getPackageName()) != 0) {

                        apparel.getPieces().get(_i).setPiece_image_id(edtImageid.getText().toString());
                        apparel.getPieces().get(_i).setPiece_blob_img(null);
                        Controller.getInstance().setApparel(apparel);
                        Drawable dr = new BitmapDrawable(getResources(),pt_maker.drawPatternFromID(v.getContext(),_i));
                        rlayout.setBackground(dr);

                    } else {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                edtImageid.setError("Please enter valid image id");
                                edtR.clearFocus();
                                edtImageid.requestFocus();

                            }
                        }, 100);

                    }
                }

                return false;
            }
        });



    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        RelativeLayout imagelayout=(RelativeLayout) v.findViewById(R.id.layoutimage);
        unbindDrawables(imagelayout);

        System.gc();
        Runtime.getRuntime().gc();
    }


    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }


}

