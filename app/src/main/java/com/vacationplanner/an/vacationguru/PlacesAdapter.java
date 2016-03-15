package com.vacationplanner.an.vacationguru;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by An on 8/4/2015.
 */
public class PlacesAdapter extends ArrayAdapter<MyPlace> {
    Context context;
    Holder cur;
    ProgressDialog progressDialog;
    Bitmap bitmap;
    public PlacesAdapter(Context context, int resource, ArrayList<MyPlace> places) {
        super(context, resource, places);
        this.context = context;
    }

    @Override
    public View getView(int position, View cell, ViewGroup parent) {
        Holder holder;
        if(cell==null)
        {
            holder=new Holder();
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            cell=inflater.inflate(R.layout.list_view,null);
            holder.imageView=(ImageView)cell.findViewById(R.id.icon1);
            holder.name=(TextView)cell.findViewById(R.id.name);
            holder.address=(TextView)cell.findViewById(R.id.address);
            holder.rating=(TextView)cell.findViewById(R.id.rating);
            cell.setTag(holder);}
        else
        {
            holder=(Holder)cell.getTag();
        }
        MyPlace place=getItem(position);
        holder.name.setText(place.getName());
        holder.rating.setText(place.getRating());
        holder.address.setText(place.getAddress());
        ReadImageUrl readImageUrl=new ReadImageUrl();
        holder.imageView.setImageResource(R.drawable.process);
        cur=holder;
        readImageUrl.execute(place.getImageUrl());
        return cell;
    }
    private class ReadImageUrl extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Bitmap doInBackground (String... url)
        {
            try {
                bitmap= BitmapFactory.decodeStream((InputStream) new URL(url[0]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap image){
            if(image!=null)
            {
                cur.imageView.setImageBitmap(image);
            }
        }
    }

    private class Holder{
        ImageView imageView;
        TextView name;
        TextView address;
        TextView rating;
    }
}

