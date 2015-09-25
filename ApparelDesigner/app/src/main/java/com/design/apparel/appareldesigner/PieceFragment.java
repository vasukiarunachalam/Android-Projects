package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/21/2015.
 * This code displays the piece by piece fragment
 ******************************************************************************/

public class  PieceFragment extends Fragment implements OnFinishDialog {


    View v;
    Button btnPattern;
    EditText edtheight, edtwidth, edtinfo;
    ImageView img_shape, img_fold;
    String[] shapes, folds;
    ArrayAdapter<String> shape_adapter, fold_adapter;
    Apparel apparel;
    Spinner spnshape, spnfold;
    Fragment thisfragment;
    int _i;
    OnApparelPass ApparelPasser;
    PatternMaker pt_maker;
    Apparel_Keyboard mCustomKeyboard;


    static PieceFragment newInstance(int i) {
        PieceFragment piece1 = new PieceFragment();
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
        v = inflater.inflate(R.layout.piece, container,
                false);
        thisfragment = this;
        pt_maker = new PatternMaker();
        apparel = Controller.getInstance().getApparel();

        edtheight = (EditText) v.findViewById(R.id.edt_piece_height);
        edtwidth = (EditText) v.findViewById(R.id.edt_piece_width);
        edtinfo = (EditText) v.findViewById(R.id.edt_piece_info);
        spnshape = (Spinner) v.findViewById(R.id.spn_piece_shape);
        spnfold = (Spinner) v.findViewById(R.id.spn_piece_fold);
        img_shape = (ImageView) v.findViewById(R.id.img_shape);
        img_fold = (ImageView) v.findViewById(R.id.img_fold);
        shapes = new String[]{"rectangle1", "rectangle2", "square"};
        folds = new String[]{"one_fold", "two_fold", "three_fold"};


        shape_adapter = new ArrayAdapter<String>(
                v.getContext(), android.R.layout.simple_dropdown_item_1line, shapes);
        fold_adapter = new ArrayAdapter<String>(
                v.getContext(), android.R.layout.simple_dropdown_item_1line, folds);
        spnshape.setAdapter(shape_adapter);
        spnfold.setAdapter(fold_adapter);
        spnshape.requestFocus();

        btnPattern = (Button) v.findViewById(R.id.btnPattern);
        addListener();
        return v;
    }


    public void addListener() {
        mCustomKeyboard= new Apparel_Keyboard(v,R.id.k_piece);
        mCustomKeyboard.registerEditText(R.id.edt_piece_height);
        mCustomKeyboard.registerEditText(R.id.edt_piece_width);
        edtheight.setText(null);
        edtwidth.setText(null);

        if (apparel.getPieces().get(_i).getPiece_shape() != null) {
            spnshape.setSelection(shape_adapter.getPosition(apparel.getPieces().get(_i).getPiece_shape()));
        }

        if (apparel.getPieces().get(_i).getPiece_fold() != null) {
            spnfold.setSelection(fold_adapter.getPosition(apparel.getPieces().get(_i).getPiece_fold()));
        }

        if (apparel.getPieces().get(_i).getPiece_height() != null) {
            edtheight.setText(apparel.pieces.get(_i).getPiece_height());
        }


        if (apparel.getPieces().get(_i).getPiece_width() != null) {
            edtwidth.setText(apparel.pieces.get(_i).getPiece_width());
        }
        if (apparel.getPieces().get(_i).getPiece_info() != null) {
            edtinfo.setText(apparel.pieces.get(_i).getPiece_info());
        }

        if ((apparel.getPieces().get(_i).getPiece_height() != null) && (apparel.getPieces().get(_i).getPiece_width() != null)) {
            img_shape.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i, 2, "shape", 100, 350, 300, 700));
            img_fold.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i, 2, "fold", 100, 350, 300, 700));
        }

        spnshape.requestFocus();
        spnshape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if (getResources().getIdentifier(shape_adapter.getItem(position), "drawable", v.getContext().getPackageName()) != 0) {
                    apparel.getPieces().get(_i).setPiece_shape(shape_adapter.getItem(position));
                    spnfold.requestFocus();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spnfold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {
                if (getResources().getIdentifier(fold_adapter.getItem(position), "drawable", v.getContext().getPackageName()) != 0) {
                    apparel.getPieces().get(_i).setPiece_fold(fold_adapter.getItem(position));
                   // edtheight.requestFocus();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        edtheight.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER))
                    if (edtheight.getText().length() == 0) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                edtheight.setError(null);
                                edtwidth.clearFocus();
                                edtheight.requestFocus();

                            }
                        }, 100);
                    } else {

                        if (pt_maker.Evaluate(edtheight.getText().toString(), 2) == "error") {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    edtheight.setError("Please enter valid Formula for height");
                                    edtwidth.clearFocus();
                                    edtheight.requestFocus();

                                }
                            }, 100);
                        } else {

                            apparel.getPieces().get(_i).setPiece_height(edtheight.getText().toString());
                            Log.d("piece height ", edtheight.getText().toString());
                        }

                    }
                return false;
            }

      });
        edtwidth.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (edtwidth.getText().length() == 0) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                edtwidth.setError("Please enter a value");
                                edtinfo.clearFocus();
                                edtwidth.requestFocus();

                            }
                        }, 100);
                    } else {
                        if (pt_maker.Evaluate(edtwidth.getText().toString(), 2) == "error") {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    edtwidth.setError("Please enter valid Formula for width");
                                    edtinfo.clearFocus();
                                    edtwidth.requestFocus();

                                }
                            }, 100);
                        } else {
                            apparel.getPieces().get(_i).setPiece_width(edtwidth.getText().toString());
                            Log.d("piece width ", edtwidth.getText().toString());
                        }
                    }
                }
                return false;
            }
        });

        edtinfo.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                apparel.getPieces().get(_i).setPiece_info(edtinfo.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        edtinfo.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d("on key down ", "len h=" + String.valueOf(edtheight.getText().length()));

                    if (edtheight.getText().length() == 0) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                edtheight.setError("Enter a value");
                                edtwidth.clearFocus();
                                edtheight.requestFocus();

                            }
                        }, 100);
                    } else {
                        if (edtwidth.getText().length() == 0) {
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {
                                    edtwidth.setError("Enter a value");
                                    btnPattern.clearFocus();
                                    edtwidth.requestFocus();

                                }

                            }, 100);
                        } else {

                            apparel.getPieces().get(_i).setPiece_height(edtheight.getText().toString());
                            apparel.getPieces().get(_i).setPiece_width(edtwidth.getText().toString());
                            apparel.getPieces().get(_i).setPiece_info(edtinfo.getText().toString());
                            img_fold.setVisibility(View.VISIBLE);
                            img_shape.setVisibility(View.VISIBLE);
                            if ((apparel.getPieces().get(_i).getPiece_height() != null) && (apparel.getPieces().get(_i).getPiece_width() != null)) {
                                img_shape.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i, 2, "shape", 100, 350, 300, 700));
                                img_fold.setImageBitmap(pt_maker.drawPieceBitmap(v.getContext(), _i, 2, "fold", 100, 350, 300, 700));
                            }
                        }

                    }
                }
                return false;
            }
        });


        btnPattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtheight.getText().toString() == null) {
                    edtheight.setError("Please enter a value");
                    edtheight.requestFocus();
                } else {
                    if (edtwidth.getText().toString() == null) {
                        edtwidth.setError("Please enter a value");
                        edtwidth.requestFocus();
                    }
                }
                if (edtheight.getText().toString() != null && edtwidth.getText().toString() != null) {
                    Controller.getInstance().setApparel(apparel);

                 /*   DialogFragment newFragment = PatternDialog.newInstance(_i);
                    Fragment newFragment = PatternAdmin.newInstance(_i);
                    newFragment.setTargetFragment(thisfragment, -1);

                    newFragment.show(getFragmentManager(), "pattern1");*/
                    Intent intentPattern=new Intent(v.getContext(),PatternAdmin.class);
                    intentPattern.putExtra("i","_i");
                    startActivity(intentPattern);
                }
            }

        });


    }

    @Override
    public void OnFinishDialog(Boolean value) {

        if (value) {
            apparel = Controller.getInstance().getApparel();
            passData(apparel);
        }
    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        ApparelPasser = (OnApparelPass) a;
    }

    public void passData(Object data) {
        ApparelPasser.onApparelPass(data);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LinearLayout layout1 = (LinearLayout) v.findViewById(R.id.layout_shape);
        unbindDrawables(layout1);
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

