package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code displays the Signup UI for users
 ******************************************************************************/


public class SignUPActivity extends Activity {
    EditText editTextUserEmail;
    Button btnCreateAccount;

    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Get Refferences of Views
        editTextUserEmail=(EditText)findViewById(R.id.editTextUserEmail);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String userEmail=editTextUserEmail.getText().toString();

                // check if any of the fields are vaccant
                if(userEmail.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                    return;
                }
                // check if both password matches
                String emailPattern = "[a-zA-Z0-9._-]+@gmail+\\.+com+";

                // onClick of button perform this simplest code.
                if (userEmail.matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }
                    // Save the Data in Database
                loginDataBaseAdapter.insertEntry(userEmail);
                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}