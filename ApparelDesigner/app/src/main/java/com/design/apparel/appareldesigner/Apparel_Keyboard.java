package com.design.apparel.appareldesigner;

/*******************************************************************************
 * Created by VasukiSairam on 6/29/2015.
 * This is the code for custom keyboard to input mathematical expressions from user
 * for points on Pattern image.
 ******************************************************************************/

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Apparel_Keyboard implements OnTouchListener, OnClickListener,
        OnFocusChangeListener {
    private EditText mEt;
    private View mHostActivity;
    private LinearLayout mKLayout;
    private ScrollView sv;
    private ArrayList<String> L ;
    private View kv;
    int _i;
    Button b;

    private Button mB[] = new Button[35];

    public Apparel_Keyboard(View host,int layoutid) {
        mHostActivity=host;
        mKLayout=(LinearLayout)mHostActivity.findViewById(layoutid);
        sv=(ScrollView)mHostActivity.findViewById(R.id.scroll_view);
        L=new ArrayList<String> ();
        _i=0;
    }

    @Override
    public void onFocusChange(View v,boolean b) {
       /* if (v == mEt) {
            hideDefaultKeyboard();
            enableKeyboard();

        }*/

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }

    private static Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
             return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }

    @Override
    public void onClick(View v) {

        Context cont=mHostActivity.getContext();

        mEt=(EditText)scanForActivity(cont).getCurrentFocus();

        if (v != mB[0] && v != mB[3] && v != mB[4]) {
            addText(v);
        } else if (v == mB[4]) {
            disableKeyboard();
        } else if (v == mB[3]) {
            isBack();
        } else if (v == mB[0]) {
            clearText();
        }


    }


    private void addText(View v) {

            String b = "";
            b =  ((Button)v).getText().toString();
            if (b != null) {
                if(v == mB[30] || v == mB[31] || v == mB[32] || v == mB[33] || v == mB[34])
                {
                    String pre="math:";
                    pre.concat(b);
                    b=pre;

                }
                mEt.append(b);
                L.add(b);
                _i++;
            }
    }

    private void isBack() {

        CharSequence cc = mEt.getText();
        if (cc != null && cc.length() > 0) {
            {
                mEt.setText("");
                L.remove(L.size()-1);
                String value = TextUtils.join("", L);
                mEt.setText(value);
             //   mEt.append(cc.subSequence(0, cc.length() - 1));
            }

        }
    }


    private void clearText() {

            mEt.setText("");
    }

    private void enableKeyboard() {
        LayoutInflater inflater = LayoutInflater.from(mHostActivity.getContext());
        kv=inflater.inflate(R.layout.apparel_keyboard,mKLayout,false);
        sv=(ScrollView)mHostActivity.findViewById(R.id.scroll_view);
        sv.setFillViewport(true);
        Log.d("View=  ", mKLayout.toString());
        if(mKLayout.getVisibility()==View.GONE) {
            mKLayout.setVisibility(View.VISIBLE);
            mKLayout.addView(kv);
            setKeys(kv);
            scroll();
        }

    }

    public void scroll() {

        sv.postDelayed(new Runnable() {
            @Override
            public void run() {
             //   sv.smoothScrollTo(0,sv.getBottom());
                Log.d("Resource id=  ",String.valueOf(mKLayout.getId()));
                Log.d("R.id  ", String.valueOf(R.id.k_pattern));

                if(mKLayout.getId()== R.id.k_apparel)
                {
                    Log.d("R.id in side if  ", String.valueOf(R.id.k_apparel));
                    sv.smoothScrollTo(0, sv.getBottom());
                }

                if(mKLayout.getId()== R.id.k_piece)
                {
                        sv.smoothScrollTo(0,200);
                }

                if(mKLayout.getId()== R.id.k_pattern)
                {
                    sv.smoothScrollTo(0,sv.getBottom());
                }

            }
        }, 1000);
    }

    private void disableKeyboard() {
      //  collapse(mKLayout);
        if(mKLayout.getVisibility()==View.VISIBLE) {
            mKLayout.removeView(kv);
            mKLayout.setVisibility(View.GONE);
            sv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    sv.smoothScrollTo(0, sv.getTop());
                }
            }, 1000);
        }
    }


    private void hideDefaultKeyboard() {
        ((Activity)mHostActivity.getContext()).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void setKeys(View v) {

        mB[0] = (Button) v.findViewById(R.id.btnclr);
        mB[1] =(Button) v.findViewById(R.id.btnlb);
        mB[2] =(Button) v.findViewById(R.id.btnrb);
        mB[3] =(Button) v.findViewById(R.id.btndl);
        mB[4] =(Button) v.findViewById(R.id.btndone);

        mB[5] =(Button) v.findViewById(R.id.btnMB);
        mB[6] =(Button) v.findViewById(R.id.btnMH);
        mB[7] =(Button) v.findViewById(R.id.btnMW);
        mB[8] =(Button) v.findViewById(R.id.btnH);
        mB[9] =(Button) v.findViewById(R.id.btnW);

        mB[10] =(Button) v.findViewById(R.id.btnMOL);
        mB[11] =(Button) v.findViewById(R.id.btnMSW);
        mB[12] =(Button) v.findViewById(R.id.btnMSL);
        mB[13] =(Button) v.findViewById(R.id.btnadd);
        mB[14] =(Button) v.findViewById(R.id.btnsub);

        mB[15] =(Button) v.findViewById(R.id.btnMBW);
        mB[16] =(Button) v.findViewById(R.id.btnMUAC);
        mB[17] =(Button) v.findViewById(R.id.btnMBWL);
        mB[18] =(Button) v.findViewById(R.id.btnmul);
        mB[19] =(Button) v.findViewById(R.id.btndiv);

        mB[20] =(Button) v.findViewById(R.id.btn1);
        mB[21] =(Button) v.findViewById(R.id.btn2);
        mB[22] =(Button) v.findViewById(R.id.btn3);
        mB[23] =(Button) v.findViewById(R.id.btn4);
        mB[24] =(Button) v.findViewById(R.id.btn5);

        mB[25] =(Button) v.findViewById(R.id.btn6);
        mB[26] =(Button) v.findViewById(R.id.btn7);
        mB[27] =(Button) v.findViewById(R.id.btn8);
        mB[28] =(Button) v.findViewById(R.id.btn9);
        mB[29] =(Button) v.findViewById(R.id.btn0);

        mB[30] =(Button) v.findViewById(R.id.btnpow);
        mB[31] =(Button) v.findViewById(R.id.btnsqrt);
        mB[32] =(Button) v.findViewById(R.id.btnsin);
        mB[33] =(Button) v.findViewById(R.id.btncos);
        mB[34] =(Button) v.findViewById(R.id.btntan);

        for (int i = 0; i < mB.length; i++)
          mB[i].setOnClickListener(this);
   }

    public void registerEditText(int resid) {
        // Find the EditText 'resid'
        Context c;

        mEt= (EditText) mHostActivity.findViewById(resid);
        Log.d("in register", mEt.toString());
        // Make the custom keyboard appear
        mEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("in set on focus ", "hello");
              //  if (hasFocus) enableKeyboard();
                if (!hasFocus)
                     disableKeyboard();
            }
        });

        mEt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                enableKeyboard();
            }
        });
        // Disable standard keyboard hard way
       mEt.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                Log.d("in set touch listener ", "hello");
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        mEt.setInputType(mEt.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
    }
}
