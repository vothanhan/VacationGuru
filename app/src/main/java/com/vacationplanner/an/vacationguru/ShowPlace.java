package com.vacationplanner.an.vacationguru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowPlace extends AppCompatActivity {
    double longtitude;
    double latitude;
    private String name="";
    private String address="";
    private String phone="";
    private String type="";
    private String price="";
    private String rating="";
    private String web="";
    Bitmap bitmap;
    private static final String APIKEY="AIzaSyCrICjxNFWVNSbb3clujuGXr26DG0lw7Ms";
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place);
        Intent intent=getIntent();
        String PlaceUrl=HttpPlaceDetail();
        ReadTask readTask=new ReadTask();
        readTask.execute(PlaceUrl);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_place, menu);
        return true;
    }
    private String HttpPlaceDetail()
    {
        Intent intent=getIntent();
        String id="";
        id=intent.getStringExtra("ID");
        String Begin="https://maps.googleapis.com/maps/api/place/details/json?";
        String Url="";
        Url=Begin+"placeid="+id+"&"+"key="+APIKEY;
        return Url;
    }
    private class ReadImageUrl extends  AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(ShowPlace.this);
            pDialog.setMessage("Loading image...");
            pDialog.show();
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
                ImageView img=(ImageView) findViewById(R.id.imageView);
                img.setImageBitmap(image);
            }
            pDialog.dismiss();
        }

    }
    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                HttpConnection http = new HttpConnection();
                data = http.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            TextView tv1 = (TextView) findViewById(R.id.textView);
            TextView tv2 = (TextView) findViewById(R.id.textView2);
            TextView tv3 = (TextView) findViewById(R.id.textView3);
            TextView tv4 = (TextView) findViewById(R.id.textView4);
            TextView tv5 = (TextView) findViewById(R.id.textView5);
            TextView tv6 = (TextView) findViewById(R.id.textView6);
            TextView tv7 = (TextView) findViewById(R.id.textView7);
            String processedtype="";
            try {
                JSONObject places = new JSONObject(result);
                JSONObject jsonObject=places.getJSONObject("result");
                name = jsonObject.optString("name");
                address = jsonObject.optString("formatted_address");
                phone = jsonObject.optString("formatted_phone_number");
                JSONObject geometry = (JSONObject) jsonObject.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                longtitude=location.getDouble("lng");
                latitude=location.getDouble("lat");
                Integer prices=jsonObject.optInt("price_level");
                if(prices==0)
                {
                    price="Unknown";
                }
                else if(prices==1)
                {
                    price="Inexpensive";
                }
                else if(prices==2)
                {
                    price="Moderate";
                }
                else if(prices==3)
                {
                    price="Expensive";
                }
                else if(prices==4)
                {
                    price="Very Expensive";
                }
                else
                {
                    price="Unknown";
                }
                rating=jsonObject.optString("rating");
                web=jsonObject.optString("website");
                JSONArray photos=jsonObject.optJSONArray("photos");
                type=jsonObject.optString("types");
                processedtype=type.replaceAll("\\[", "").replaceAll("\\]", "").replace("\"","").replace("_"," ").replace(",",", ");
                if(photos!=null)
                {
                    JSONObject photo= (JSONObject) photos.get(0);
                    String photoref=photo.optString("photo_reference");
                    String begin="https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&";
                    String params="photo_reference="+photoref+"&key="+APIKEY;
                    String url=begin+params;
                    ReadImageUrl readImageUrl=new ReadImageUrl();
                    readImageUrl.execute(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent=getIntent();
            tv1.setText("Name: " + name);
            tv2.setText("Address: " + address);
            tv3.setText("Phone: " + phone);
            tv4.setText("Type: " + processedtype);
            tv5.setText("Price: " + price);
            tv6.setText("Rating: " + rating);
            tv7.setText("Website: " + web);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.VISIBLE);
            tv3.setVisibility(View.VISIBLE);
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
            Button button=(Button) findViewById(R.id.button4);
            button.setVisibility(View.VISIBLE);
            TextView tv8=(TextView) findViewById(R.id.textView8);
            tv8.setVisibility(View.INVISIBLE);
        }
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

    public void showonMap(View view) {
        Intent intent1=getIntent();
        Intent intent2=new Intent(this,MapsActivity.class);
        intent2.putExtra("Name",intent1.getStringExtra("Name"));
        intent2.putExtra("Long",longtitude);
        intent2.putExtra("Lat",latitude);
        startActivity(intent2);
    }
}
