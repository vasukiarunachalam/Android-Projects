package com.design.apparel.appareldesigner;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code is the fragment that displays Pattern Details
 ******************************************************************************/

public class PatternView extends DialogFragment {
    int d_Num, _i, _j, _k;
    TableLayout t_layout;
    TextView txtImageid,tv,txtPattern;
    ArrayList<TextView> txtviews;
    Button btnOK;
    Apparel apparel;
    TouchImageView imgPattern;
    int patternTextCount;
    //TouchImageView imgPattern;
    View v;
    PatternMaker pt_maker;
    RelativeLayout rlayout;

    public static PatternView newInstance(int num) {
        PatternView f = new PatternView();
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
        v = inflater.inflate(R.layout.pattern_view, container,
                false);
        pt_maker = new PatternMaker();

        txtImageid = (TextView) v.findViewById(R.id.txt_image_id);
        txtPattern = (TextView) v.findViewById(R.id.txtPattern);
        rlayout = (RelativeLayout) v.findViewById(R.id.layoutimage);
        //imgPattern = (TouchImageView) v.findViewById(R.id.imageView);
        //imgPattern=(TouchImageView)v.findViewById(R.id.imageView);
        btnOK = (Button) v.findViewById(R.id.btnOK);
        apparel = Controller.getInstance().getApparel();

        addListener();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //   Controller.getInstance().setApparel(apparel);
                unbindDrawables(rlayout);
                OnFinishDialog mcallBack = (OnFinishDialog) getTargetFragment();
                mcallBack.OnFinishDialog(true);
                dismiss();
            }
        });

        return v;
    }

    public void addListener() {


        txtImageid.setText("piece" + (_i + 1) + "_" + apparel.getDrawable_id());

        if (apparel.getPieces().get(_i).getPiece_image_id() != null) {
            if (apparel.getPieces().get(_i).getPiece_blob_img() != null) {

             /*   ByteArrayInputStream inputStream = new ByteArrayInputStream(apparel.getPieces().get(_i).getPiece_blob_img());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPattern.setImageBitmap(bitmap);*/
                //unbindDrawables(imgPattern);
          //      imgPattern.setImageBitmap(pt_maker.drawPatternFromBlob(v.getContext(), _i,2));
                unbindDrawables(rlayout);
                Drawable dr = new BitmapDrawable(getResources(),pt_maker.drawPatternFromBlob(v.getContext(),_i,1));
                rlayout.setBackground(dr);
                addTextViews();

            }
        }

    }

    public void addTextViews()
    {
        apparel= Controller.getInstance().getApparel();
        patternTextCount=0;
        for(int k=0;k<apparel.getPieces().get(_i).getPatternTexts().size();k++)
        {
            patternTextCount++;
             displayTextView();

        }
    }

    public void displayTextView() {

        String r,angle,disp_str;
        PatternText pa;

        pa=apparel.getPieces().get(_i).getPatternTexts().get(patternTextCount-1);
        Log.d("pa x y ", String.valueOf(pa.getX()) + " " + String.valueOf(pa.getY()));

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
                    Log.d("getid=  ",String.valueOf(v.getId()));
                    ((TextView)v).setTextColor(Color.RED);
                    txtPattern.setText(((TextView)v).getText().toString());
                  /*  edtR.setText(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getR());
                    edtangle.setText(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getAngle());
                    edtx.setText(String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getX()));
                    edty.setText(String.valueOf(apparel.getPieces().get(_i).getPatternTexts().get(v.getId()).getY()));*/

                }
            });

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        LinearLayout imagelayout = (LinearLayout) v.findViewById(R.id.layoutimage);
        unbindDrawables(imagelayout);

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


