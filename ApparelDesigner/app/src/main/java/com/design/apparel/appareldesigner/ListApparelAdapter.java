package com.design.apparel.appareldesigner;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sairam on 4/9/2015.
 */
public class ListApparelAdapter extends BaseAdapter {

    Context context;
    protected List<Apparel> listApparels;
    LayoutInflater inflater;
    PatternMaker pt_maker;


    public ListApparelAdapter(Context context, List<Apparel> listApparels) {
        this.listApparels = listApparels;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        pt_maker=new PatternMaker();
    }

    public int getCount() {
        return listApparels.size();
    }

    public Apparel getItem(int position) {
        return listApparels.get(position);
    }

   public long getItemId(int position) {
        return listApparels.get(position).getApparel_name().hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;


        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.apparel_item, parent, false);

            holder.txtApparel = (TextView) convertView.findViewById(R.id.txtApparel);
            holder.imgApparel = (ImageView) convertView.findViewById(R.id.imgApparel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Apparel apparel = listApparels.get(position);
        Controller.getInstance().setApparel(apparel);
        holder.txtApparel.setText(apparel.getApparel_name());
        holder.imgApparel.setImageBitmap(pt_maker.drawApparelBitmapFromBlob(context));

        return convertView;
    }

    private class ViewHolder {
        TextView txtApparel;
        ImageView imgApparel;
    }


}
