package com.design.apparel.appareldesigner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This program displays the admin Menu to administer Apparels and Measures
 ******************************************************************************/
public class AdminMenu extends Activity {
    Button btnApparels,btnMeasurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getBackground().setDither(true);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.main_menu);
        Initializer();
    }

    void Initializer() {

        btnApparels = (Button) findViewById(R.id.buttonApparels);
        btnMeasurements = (Button) findViewById(R.id.buttonMeasurements);

        btnApparels.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intentAdmin = new Intent(getApplicationContext(), Apparel_Admin.class);
                                              startActivity(intentAdmin);

                                          }
                                      }
        );

        btnMeasurements.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intentMeasure = new Intent(getApplicationContext(),Measure_Admin.class);
                                             startActivity(intentMeasure);

                                         }
                                     }
        );

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
