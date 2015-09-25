package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code is the main activity that displays signIn and SignUP options
 ******************************************************************************/

public class MainActivity extends Activity {
    Button btnSignIn,btnSignUp;
    ImageView imgView;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        imgView = (ImageView) findViewById(R.id.imgApparel);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.apparel_designer);
        RoundImage img = new RoundImage(bm);
        imgView.setImageDrawable(img);

        btnSignIn=(Button)findViewById(R.id.buttonSignIN);
        btnSignUp=(Button)findViewById(R.id.buttonSignUP);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intentSignUP=new Intent(getApplicationContext(),SignUPActivity.class);
                startActivity(intentSignUP);
            }
        });

       btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

             signIn(v);
            }
        });
    }

   public void signIn(View V)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.login);
        dialog.setTitle("Login");

        final EditText editTextUserEmail=(EditText)dialog.findViewById(R.id.editTextUserEmailToLogin);
        loginDataBaseAdapter=new LoginDataBaseAdapter(MainActivity.this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        Button btnSignIn=(Button)dialog.findViewById(R.id.buttonSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userEmail=editTextUserEmail.getText().toString();
                // fetch the Password form database for respective user name
                Boolean exists=loginDataBaseAdapter.getSingleEntry(userEmail);

                // check if the Stored password matches with  Password entered by user
                if(exists) {
                    Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_SHORT).show();
                    Intent intentApparels = new Intent(getApplicationContext(), ApparelMenu.class);
                    startActivity(intentApparels);


                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account does not exist, Please SignUP", Toast.LENGTH_LONG).show();
                }

               // new checkLogin().execute(v.getContext(),editTextUserEmail.getText().toString());
            }

        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }
}

