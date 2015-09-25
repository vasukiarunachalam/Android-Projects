package com.design.apparel.appareldesigner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Button;

/*******************************************************************************
 * Created by VasukiSairam on 6/14/2015.
 * This code displays the piece details
 ******************************************************************************/

public class Piece_View extends FragmentActivity implements OnApparelPass {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    PagerTabStrip ps;
    Apparel apparel;
    int piece_count;
    Button btnAdd, btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apparel = Controller.getInstance().getApparel();
        piece_count = apparel.getNum_pieces();
        Log.d("piece count= ", String.valueOf(piece_count));
        setContentView(R.layout.piece_view_pager);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.notifyDataSetChanged();
        mViewPager = (ViewPager) findViewById(R.id.patternpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        ps = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        ps.setBackgroundColor(getResources().getColor(R.color.green));
        ps.getChildAt(1).setPadding(40, 20, 40, 20);
        ps.getChildAt(1).setBackgroundResource(R.drawable.tab_bg);
        ps.setDrawFullUnderline(false);


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


            return PieceViewFragment.newInstance(position);


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



