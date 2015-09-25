package com.design.apparel.appareldesigner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/27/2015.
 * This code displays the fragment activity to display piece details
 ******************************************************************************/

public class Piece_Admin extends FragmentActivity implements OnApparelPass {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    PagerTabStrip ps;
    Apparel apparel;
    int piece_count;
    Button btnExit, btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apparel = Controller.getInstance().getApparel();
        piece_count = apparel.getNum_pieces();
        Log.d("piece count= ", String.valueOf(piece_count));
        setContentView(R.layout.piece_pager);
        if (apparel.getPieces().size() < apparel.getNum_pieces()) {

            Log.d("size() = ", String.valueOf(apparel.pieces.size()));
            Log.d("num pieces = ", String.valueOf(apparel.getNum_pieces()));
            apparel.createPieces(piece_count);
            Controller.getInstance().setApparel(apparel);
        }


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.notifyDataSetChanged();
        mViewPager = (ViewPager) findViewById(R.id.patternpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        ps = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        ps.setBackgroundColor(getResources().getColor(R.color.green));
        ps.getChildAt(1).setPadding(40, 20, 40, 20);
        ps.getChildAt(1).setBackgroundResource(R.drawable.tab_bg);
        ps.setDrawFullUnderline(false);


        btnExit = (Button) findViewById(R.id.btnExit);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

          /*  btnAdd.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              ApparelDB1 db1=new ApparelDB1();
                                              db1.ApparelData(v.getContext(),1);
                                          //    Toast.makeText(v.getContext(), "Apparel Data Added-Success", Toast.LENGTH_LONG).show();

                                          }
                                      }
            );*/

        btnUpdate.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             if (ValidateApparel()) {
                                                 ApparelDB db1 = new ApparelDB();
                                                 db1.ApparelData(v.getContext(), 1);

                                             }
                                         }
                                     }
        );

        btnDelete.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             ApparelDB db1 = new ApparelDB();
                                             db1.ApparelData(v.getContext(), 2);
                                             Toast.makeText(v.getContext(), "Apparel Data Deleted-Success", Toast.LENGTH_LONG).show();

                                         }
                                     }
        );

        btnExit.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                            onBackPressed();

                                         }
                                     }
        );


    }


public boolean ValidateApparel() {
    apparel = Controller.getInstance().getApparel();
    if (apparel.getApparel_name() == null ||
                apparel.getApparel_type() == null ||
                apparel.getDrawable_id() == null ||
                apparel.getNum_pieces() == 0)
    {
        Toast.makeText(this, "Apparel Data not complete", Toast.LENGTH_LONG).show();
        return false;
    }
    for(int i=0;i<apparel.getNum_pieces();i++)
    {
        Piece p=apparel.getPieces().get(i);
        if (p.getPiece_shape() == null ||
                p.getPiece_fold() == null ||
                p.getPiece_height() == null||
                p.getPiece_width() == null ||
                p.getPiece_image_id() == null ||
                p.getPatternTexts().size()== 0)
        {
            Toast.makeText(this, "Apparel Piece Data not complete", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    return true;
}


        public void onApparelPass(Object a)
        {
            apparel=(Apparel)a;
            Controller.getInstance().setApparel(apparel);

        }


        public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

            public SectionsPagerAdapter(FragmentManager fm) {
                super(fm);
            }

            @Override
            public Fragment getItem(int position) {


               return PieceFragment.newInstance(position);


            }

            @Override
            public int getCount() {
                return piece_count;
            }


            @Override
            public CharSequence getPageTitle(int position) {

                        return "Piece"+(position+1);


            }
        }
    @Override
    public void onBackPressed() {
        // Write your code here

        super.onBackPressed();
        Controller.getInstance().setApparel(apparel);
    }

    @Override
    public void onDestroy() {
        // Write your code here

        super.onDestroy();
        Controller.getInstance().setApparel(apparel);
    }


    }


