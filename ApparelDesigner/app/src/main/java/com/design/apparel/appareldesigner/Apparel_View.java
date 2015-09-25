package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code displays the Apparel details.
 ******************************************************************************/


public class Apparel_View extends Activity {

    Button btnPiece;
    TextView txtNumPieces,txtName,txtType,txtBoltWidth,txtLength;
    ImageView image;
    Apparel apparel;
    PatternMaker pt_maker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getBackground().setDither(true);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.apparel_view);

        Initialiser();

    }


    public void Initialiser() {

        apparel= Controller.getInstance().getApparel();
        txtName = (TextView) findViewById(R.id.txtName);
        txtType = (TextView) findViewById(R.id.txttype);
        txtNumPieces = (TextView) findViewById(R.id.txtNum);
        txtBoltWidth = (TextView) findViewById(R.id.txtBoltWidth);
        txtLength = (TextView) findViewById(R.id.txtLength);
        image = (ImageView) findViewById(R.id.imageView);
        btnPiece = (Button) findViewById(R.id.btnPiece);
        pt_maker=new PatternMaker();
        addListener();
    }



    public void addListener() {


        txtName.setText(apparel.getApparel_name());
        txtType.setText(apparel.getApparel_type());
        txtNumPieces.setText(String.valueOf(apparel.getNum_pieces()));
        txtBoltWidth.setText(pt_maker.Evaluate(apparel.getBolt_Width(),1));
        txtLength.setText(pt_maker.Evaluate(apparel.getLength(),1));

        if (apparel.getBlob_img_all_pieces() != null) {
           image.setImageBitmap(pt_maker.drawAllPiecesBitmapFromBlob(this));
        }

        btnPiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent piece_view = new Intent(v.getContext(),Piece_View.class);
                startActivityForResult(piece_view,1);

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {

                apparel= Controller.getInstance().getApparel();
                addListener();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        apparel=new Apparel();
        Controller.getInstance().setApparel(apparel);
    }
}

