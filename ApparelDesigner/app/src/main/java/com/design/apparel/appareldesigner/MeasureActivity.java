package com.design.apparel.appareldesigner;

import java.util.Locale;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

/*******************************************************************************
 * Created by VasukiSairam on 6/17/2015.
 * This code is the main activity that displays tabs for Standard and Custom measure
 ******************************************************************************/

public class MeasureActivity extends FragmentActivity implements OnMeasurePass{

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    PagerTabStrip ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measure);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        ps = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        ps.setBackgroundColor(getResources().getColor(R.color.green));
        ps.getChildAt(1).setPadding(40, 20, 40, 20);
        ps.getChildAt(1).setBackgroundResource(R.drawable.tab_bg);
        ps.setDrawFullUnderline(false);
    }

    public void onMeasurePass(boolean value) {
        if (value) {
            Intent apparelView = new Intent(this, Apparel_View.class);
            startActivity(apparelView);
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return StandardFragment.newInstance();

                case 1:
                    return CustomFragment.newInstance();

                }
            return null;

        }

        @Override
        public int getCount() {
              return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                }
            return null;
        }
    }


}
