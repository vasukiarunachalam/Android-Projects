package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/30/2015.
 * This code displays the piece information
 ******************************************************************************/

public class  PieceViewFragment  extends Fragment implements OnFinishDialog {


    View v;
    Button btnPattern;
    TextView txtheight,txtwidth,txtinfo,txtshape,txtfold;
    ImageView img_shape,img_fold;
    Apparel apparel;
    Fragment thisfragment;
    int _i;
    Bitmap imageBitmap;
    OnApparelPass ApparelPasser;
    PatternMaker pt_maker;


    static PieceViewFragment newInstance(int i) {
        PieceViewFragment piece1 = new PieceViewFragment();
        Bundle args = new Bundle();
        args.putInt("i", i);
        piece1.setArguments(args);
        return piece1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _i = getArguments().getInt("i");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.piece_view, container,
                false);
        thisfragment=this;
        pt_maker=new PatternMaker();
        apparel= Controller.getInstance().getApparel();

        txtheight=(TextView)v.findViewById(R.id.txtPiece_Height);
        txtwidth=(TextView)v.findViewById(R.id.txtPiece_Width);
        txtinfo=(TextView)v.findViewById(R.id.txtPiece_Info);
        txtshape=(TextView)v.findViewById(R.id.txtPiece_Shape);
        txtfold=(TextView)v.findViewById(R.id.txtPiece_Fold);
        img_shape=(ImageView)v.findViewById(R.id.img_shape);
        img_fold=(ImageView)v.findViewById(R.id.img_fold);

        btnPattern = (Button)v.findViewById(R.id.btnPattern);
        addListener();
        return v;
    }


    public void addListener()
    {
        txtshape.setText(apparel.getPieces().get(_i).getPiece_shape());
        txtfold.setText(apparel.getPieces().get(_i).getPiece_fold());
        txtheight.setText(pt_maker.Evaluate(apparel.getPieces().get(_i).getPiece_height(),1));
        txtwidth.setText(pt_maker.Evaluate(apparel.getPieces().get(_i).getPiece_width(),1));
        txtinfo.setText(apparel.getPieces().get(_i).getPiece_info());

        img_shape.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i,1,"shape", 100, 350, 300, 700));
        img_fold.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i,1,"fold",100, 350, 300, 700));

        btnPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Controller.getInstance().setApparel(apparel);
                    DialogFragment newFragment = PatternView.newInstance(_i);
                    newFragment.setTargetFragment(thisfragment, -1);
                    newFragment.show(getFragmentManager(), "pattern1");
                }

        });

    }


    @Override
    public void OnFinishDialog (Boolean value){
        Log.d("LOG-Onfinishdialog ", "value=" + String.valueOf(value));
        if (value) {
            apparel = Controller.getInstance().getApparel();
            passData(apparel);
        }
    }

    @Override
    public void onAttach (Activity a){
        super.onAttach(a);
        ApparelPasser = (OnApparelPass) a;
    }

    public void passData (Object data){
        ApparelPasser.onApparelPass(data);
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        Toast.makeText(v.getContext(),
                "Bitmap Destroyed", Toast.LENGTH_LONG).show();
        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.layout_shape);
        unbindDrawables(layout1);

        System.gc();
        Runtime.getRuntime().gc();
    }


    private void unbindDrawables (View view)
    {
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




