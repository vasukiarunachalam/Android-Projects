package com.design.apparel.appareldesigner;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
/*******************************************************************************
 * Created by VasukiSairam on 7/03/2015.
 * This code displays the apparels from database as a list
 ******************************************************************************/

public class ApparelListActivity extends ListActivity {

    ArrayList<Apparel> apparel_list;
    ListApparelAdapter ladapter;
    String apparel_type;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apparel_list=new ArrayList<>();
        apparel_type=this.getIntent().getStringExtra("Apparel_Type");
        ListView lv=getListView();
        lv.setBackgroundResource(R.drawable.fabric_bg);
        lv.setDividerHeight(20);
        lv.addHeaderView(new View(this));
        lv.addFooterView(new View(this));
        new GetApparel().execute(this,1,apparel_type);

    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        new GetApparel().execute(this,2,apparel_type,apparel_list.get(position-1).getApparel_name());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dress_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   private class GetApparel extends AsyncTask<Object,Void,Void> {

        private Context con;
        Apparel apparel;
        int read_type;

        @Override
        protected Void doInBackground(Object... params) {
            ApparelDB db=new ApparelDB();
            con=(Context)params[0];
            read_type=(int)params[1];
            String app_type=(String)params[2];

            switch(read_type) {
                case 1:
                    try {
                        Cursor c = db.ApparelListRead(con, app_type);
                        Log.d("apparel count",String.valueOf(c.getCount()));
                        c.moveToFirst();
                        do {
                            apparel = new Apparel(
                                    c.getString(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_NAME)),
                                    c.getBlob(c.getColumnIndexOrThrow(ApparelDB.ApparelTable.COLUMN_NAME_APPAREL_IMG)));
                            apparel_list.add(apparel);
                        }while(c.moveToNext());
                        c.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    String app_name = (String) params[3];
                    apparel = db.ApparelListRead(con, app_name, app_type);
                    break;
            }
          return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            switch(read_type) {
                case 1:
                    ladapter = new ListApparelAdapter(con, apparel_list);
                    ladapter.notifyDataSetChanged();
                    setListAdapter(ladapter);
                    break;
                case 2:
                    Controller.getInstance().setApparel(apparel);
                    Intent MeasureActivityOpener = new Intent(con, MeasureActivity.class);
                    con.startActivity(MeasureActivityOpener);
                    break;
            }
        }

        @Override
        protected void onPreExecute() {

        }


    }
}
