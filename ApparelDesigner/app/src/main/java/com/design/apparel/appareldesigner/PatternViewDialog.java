package com.design.apparel.appareldesigner;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code displays the dialog for patten details
 ******************************************************************************/

public class PatternViewDialog extends DialogFragment {
    int d_Num, _i, _j, _k;
    TableLayout t_layout;
    TextView txtImageid;
    Button btnOK;
    Apparel apparel;
    TouchImageView imgPattern;

    //TouchImageView imgPattern;
    View v;
    PatternMaker pt_maker;
    LinearLayout layout;

    public static PatternViewDialog newInstance(int num) {
        PatternViewDialog f = new PatternViewDialog();
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
        layout = (LinearLayout) v.findViewById(R.id.layoutimage);
        imgPattern = (TouchImageView) v.findViewById(R.id.imageView);
        //imgPattern=(TouchImageView)v.findViewById(R.id.imageView);
        btnOK = (Button) v.findViewById(R.id.btnOK);
        apparel = Controller.getInstance().getApparel();

        addListener();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Controller.getInstance().setApparel(apparel);
                unbindDrawables(imgPattern);
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
                imgPattern.setImageBitmap(pt_maker.drawPatternFromBlob(v.getContext(), _i,2));

            }
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


