package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;

/*******************************************************************************
 * Created by VasukiSairam on 6/15/2015.
 * This code displays the fragment with Custom Measure UI
 ******************************************************************************/

public class CustomFragment extends Fragment {

    Button btnSave,btnPattern,btnDelete;
    AutoCompleteTextView txtName;
    TextView txtInfo;
    EditText edtBust,edtHip ,edtWaist,edtOutseamLength ,edtShoulderWidth,edtSleeveLength ,
    edtBackWidth ,edtUpperArmCirc, edtBackWaistLength;
    View view;
    String cus_name;
    Measure cus_measure;
    int apparel_res;
    ArrayList<Measure> measure_list;
    ArrayList<String> names;
    ArrayAdapter<String> name_adapter;
    OnMeasurePass mPass;

    public CustomFragment()
    {

    }

    public static CustomFragment newInstance() {
        CustomFragment cf = new CustomFragment();
        return cf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cus_measure=new Measure();
        measure_list=new ArrayList<>();
        names = new ArrayList<String>();
        view = inflater.inflate(R.layout.fragment_custom, container, false);
        btnSave=(Button)view.findViewById(R.id.btnSave);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        btnPattern = (Button) view.findViewById(R.id.btnPattern);
        if(this.getActivity().getLocalClassName().equals("Measure_Admin"))
        {
            btnDelete.setVisibility(View.VISIBLE);
            btnPattern.setVisibility(View.GONE);
        }
        else {
            btnPattern.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }
        txtName=(AutoCompleteTextView)view.findViewById(R.id.autotv);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        edtBust = (EditText)view.findViewById(R.id.edtBust);
        edtHip = (EditText)view.findViewById(R.id.edtHip);
        edtWaist = (EditText)view.findViewById(R.id.edtWaist);
        edtOutseamLength = (EditText)view.findViewById(R.id.edtOutseamLength);
        edtShoulderWidth = (EditText)view.findViewById(R.id.edtShoulderWidth);
        edtSleeveLength = (EditText)view.findViewById(R.id.edtSleeveLength);
        edtBackWidth = (EditText)view.findViewById(R.id.edtBackWidth);
        edtUpperArmCirc = (EditText)view.findViewById(R.id.edtUpperArmCirc);
        edtBackWaistLength = (EditText)view.findViewById(R.id.edtBackWaistLength);
        name_adapter = new ArrayAdapter<String>(
                view.getContext(),android.R.layout.select_dialog_item, names);
        txtName.setThreshold(2);
        name_adapter.notifyDataSetChanged();
        txtName.setAdapter(name_adapter);

        new GetMeasure().execute(view.getContext());

        return view;

    }
    private class GetMeasure extends AsyncTask<Object,Void,Void> {

        private Context con;

        @Override
        protected Void doInBackground(Object... params) {
            MeasureDB db=new MeasureDB();
            con=(Context)params[0];
            Measure m;
                    try {

                        Cursor c = db.MeasureListRead(con);
                        c.moveToFirst();
                        do{
                            m = new Measure(
                                    c.getString(c.getColumnIndexOrThrow(MeasureDB.MeasureTable.COLUMN_NAME_CUST_NAME)),
                                    c.getString(c.getColumnIndexOrThrow(MeasureDB.MeasureTable.COLUMN_NAME_APPAREL_MEASURE)));
                            Log.d("in custom name", m.getCust_name());
                            Log.d("in custom measure", m.getMeasure());
                            measure_list.add(m);
                        } while (c.moveToNext());
                        c.close();

                    } catch(Exception e)
                    {
                        e.printStackTrace();

                    }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            for (Measure m : measure_list) {
                names.add(m.cust_name);
            }
            name_adapter.notifyDataSetChanged();
            txtName.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View v, int position,
                                        long arg3) {
                    int selectedPos = names.indexOf((((TextView) v).getText()).toString());
                    cus_measure = measure_list.get(selectedPos);
                    addListener(view);

                }

            });

            txtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    // Your validation code goes here
                    if (!hasFocus && txtName.getText().length() == 0) {
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                txtName.setError("Please enter a value");
                                edtBust.clearFocus();
                                txtName.requestFocus();

                            }
                        }, 100);
                    } else {
                        if (!hasFocus) {
                            cus_measure.setCust_name(txtName.getText().toString());
                            addListener(view);
                        }
                    }

                }
            });

        }

        @Override
        protected void onPreExecute() {

        }


    }

    public void addListener(View v) {

        edtBust.setEnabled(true);
        edtHip.setEnabled(true);
        edtWaist.setEnabled(true);
        edtOutseamLength.setEnabled(true);
        edtShoulderWidth.setEnabled(true);
        edtSleeveLength.setEnabled(true);
        edtBackWidth.setEnabled(true);
        edtUpperArmCirc.setEnabled(true);
        edtBackWaistLength.setEnabled(true);
        txtInfo.setText("Measure in inches");

        edtBust.setText(Float.toString(cus_measure.getBust()));

        edtBust.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    cus_measure.bust = Float.parseFloat(edtBust.getText().toString());

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    txtInfo.setText("Measure in inches around Bust");
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    txtInfo.setText("Measure in inches around Bust");
                }
            });

        edtHip.setText(Float.toString(cus_measure.getHip()));

        edtHip.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.hip=Float.parseFloat(edtHip.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches around Hip");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches around Hip");
            }
        });

        edtWaist.setText(Float.toString(cus_measure.getWaist()));

        edtWaist.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.waist=Float.parseFloat(edtWaist.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches around Waist");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches around Waist");
            }
        });
        edtOutseamLength.setText(Float.toString(cus_measure.getOutseamLength()));

        edtOutseamLength.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.outseam_length=Float.parseFloat(edtOutseamLength.getText().toString());
                          }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches the length from waist to Toe");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches the length from waist to Toe");
            }
        });

        edtShoulderWidth.setText(Float.toString(cus_measure.getShoulderWidth()));

        edtShoulderWidth.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.shoulder_width=Float.parseFloat(edtShoulderWidth.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches one side of shoulder");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches one side of shoulder");
            }
        });

        edtSleeveLength.setText(Float.toString(cus_measure.getSleeveLength()));

        edtSleeveLength.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.sleeve_length=Float.parseFloat(edtSleeveLength.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches Sleeve length");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches Sleeve length");
            }
        });

        edtBackWidth.setText(Float.toString(cus_measure.getBackWidth()));

        edtBackWidth.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.back_width=Float.parseFloat(edtBackWidth.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches full shoulder back ");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches full shoulder back ");
            }
        });

        edtUpperArmCirc.setText(Float.toString(cus_measure.getUpperArmCirc()));

        edtUpperArmCirc.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.upper_arm_circ=Float.parseFloat(edtUpperArmCirc.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches around upper arm");
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches around upper arm");
            }
        });

        edtBackWaistLength.setText(Float.toString(cus_measure.getBackWaistLength()));

        edtBackWaistLength.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                cus_measure.back_waist_length=Float.parseFloat(edtBackWaistLength.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtInfo.setText("Measure in inches from shoulder to waist");
            }


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtInfo.setText("Measure in inches from shoulder to waist");
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.getInstance().setMeasure(cus_measure);
                SaveMeasure(v.getContext());

            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteMeasure(v.getContext());

            }

        });

        btnPattern.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Controller.getInstance().setMeasure(cus_measure);
                    passData(true);

            }

            });


        }
    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        mPass = (OnMeasurePass) a;
    }

    public void passData(boolean value) {
        mPass.onMeasurePass(value);
    }

    public void SaveMeasure(Context context)
    {
        MeasureDB measuredb=new MeasureDB();
        measuredb.MeasureWrite(context, cus_measure);
    }

    public void DeleteMeasure(Context context)
    {
        MeasureDB measuredb=new MeasureDB();
        measuredb.MeasureDelete(context, cus_measure);
    }


    }


