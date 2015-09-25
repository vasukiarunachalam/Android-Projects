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
import android.widget.Toast;

/*******************************************************************************
 * Created by VasukiSairam on 6/22/2015.
 * This code displays the options to list Apparels and apparel admin
 ******************************************************************************/

public class ApparelMenu extends Activity {


    ImageView btnDresses,btnSkirts,btnTops;
    Button btnAdmin;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().getBackground().setDither(true);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.apparel_menu);

        Initializer();
    }

    void Initializer() {

        btnDresses = (ImageView) findViewById(R.id.btnDresses);
        btnSkirts = (ImageView) findViewById(R.id.btnSkirts);
        btnTops = (ImageView) findViewById(R.id.btnTops);
        btnAdmin = (Button) findViewById(R.id.buttonAdmin);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_dress_bg);
        RoundImage img = new RoundImage(bm);
        btnDresses.setImageDrawable(img);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_skirts_bg);
        img = new RoundImage(bm);
        btnSkirts.setImageDrawable(img);
        bm = BitmapFactory.decodeResource(getResources(), R.drawable.img_tops_bg);
        img = new RoundImage(bm);
        btnTops.setImageDrawable(img);

        btnDresses.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             displayApparelList("Dress");
                                         }
                                     }
        );

        btnSkirts.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                           displayApparelList("Skirts");
                                                    }
                                                }
        );
        btnTops.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                          displayApparelList("Tops");
                                         }
                                     }
        );
        btnAdmin.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           loginDataBaseAdapter=new LoginDataBaseAdapter(v.getContext());
                                           loginDataBaseAdapter=loginDataBaseAdapter.open();
                                           String userEmail=loginDataBaseAdapter.getSingleEntry();
                                           if(userEmail.equals("sairamvasuki@gmail.com")) {

                                               Intent intentAdmin = new Intent(ApparelMenu.this, AdminMenu.class);
                                               startActivity(intentAdmin);
                                           }
                                       }
                                   }
        );


    }




    public void displayApparelList(String apparel_type)
    {

        Intent ListActivityOpener = new Intent(ApparelMenu.this, ApparelListActivity.class);
        ListActivityOpener.putExtra("Apparel_Type",apparel_type);
        startActivity(ListActivityOpener);
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
