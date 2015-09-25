package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.net.Uri;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
/*******************************************************************************
 * Created by VasukiSairam on 6/16/2015.
 * This code is the fragment to display standard details
 ******************************************************************************/



public class  StandardFragment extends Fragment {

    String stdTypeSize;
    OnMeasurePass mPass;

    public StandardFragment() {
    }

    public static StandardFragment newInstance() {
        StandardFragment sf = new StandardFragment();
        return sf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_standard, container, false);
        addListener(view);
        return view;
    }


    public void addListener(View v) {

        RadioGroup rg= (RadioGroup)v.findViewById(R.id.standard);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup gr, int checkedId){
                View V=gr.getRootView();
                stdTypeSize=null;
                Spinner wspinner = (Spinner)V.findViewById(R.id.women_spinner);
                ArrayAdapter<CharSequence> wadapter;

                Spinner pspinner = (Spinner)V.findViewById(R.id.petite_spinner);
                ArrayAdapter<CharSequence> padapter;

                Spinner gspinner = (Spinner)V.findViewById(R.id.girls_spinner);
                 ArrayAdapter<CharSequence> gadapter;

                switch(checkedId) {
                    case R.id.radioWomen:
                        wadapter = ArrayAdapter.createFromResource(V.getContext(),
                                R.array.womensize, android.R.layout.simple_spinner_item);
                        wadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        wspinner.setAdapter(wadapter);
                        wspinner.setVisibility(View.VISIBLE);
                        pspinner.setVisibility(View.GONE);
                        gspinner.setVisibility(View.GONE);
                        Toast.makeText(V.getContext(), "Women spinner", Toast.LENGTH_LONG).show();
                        stdTypeSize="Women"+":";

                        wspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                                stdTypeSize.concat(parent.getItemAtPosition(position).toString());
                            }

                            public void onNothingSelected(AdapterView<?> parent) {

                            }

                        });

                        break;

                    case R.id.radioPetite:
                        padapter = ArrayAdapter.createFromResource(V.getContext(),
                                R.array.womensize, android.R.layout.simple_spinner_item);
                        padapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        pspinner.setAdapter(padapter);
                        pspinner.setVisibility(View.VISIBLE);
                        wspinner.setVisibility(View.GONE);
                        gspinner.setVisibility(View.GONE);
                        Toast.makeText(V.getContext(), "Petite spinner", Toast.LENGTH_LONG).show();
                        stdTypeSize="Petite"+":";
                        pspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                                stdTypeSize.concat(parent.getItemAtPosition(position).toString());

                            }

                            public void onNothingSelected(AdapterView<?> parent) {

                            }

                        });
                        break;

                    case R.id.radioGirls:
                        gadapter = ArrayAdapter.createFromResource(V.getContext(),
                                R.array.womensize, android.R.layout.simple_spinner_item);
                        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        gspinner.setAdapter(gadapter);
                        gspinner.setVisibility(View.VISIBLE);
                        wspinner.setVisibility(View.GONE);
                        pspinner.setVisibility(View.GONE);
                        Toast.makeText(V.getContext(), "Girls spinner", Toast.LENGTH_LONG).show();
                        stdTypeSize="Girls"+":";
                        gspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                                stdTypeSize = parent.getItemAtPosition(position).toString();
                            }

                            public void onNothingSelected(AdapterView<?> parent) {

                            }

                        });
                        break;

                }
            }

        });



        Button btnSubmit = (Button) v.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               MeasureDB db=new MeasureDB();
               Measure m=db.MeasureRead(v.getContext(),stdTypeSize);
               Controller.getInstance().setMeasure(m);
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
}
