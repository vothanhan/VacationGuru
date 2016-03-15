package com.vacationplanner.an.vacationguru;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PredefinedSearch extends AppCompatActivity {

    private static final String APIKEY="AIzaSyCrICjxNFWVNSbb3clujuGXr26DG0lw7Ms";
    private ArrayList<MyPlace> placeslist;
    ListView listView;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predefined_search);
        placeslist= new ArrayList<MyPlace>();
        Intent intent=getIntent();
        EditText editText=(EditText) findViewById(R.id.editText);
        listView=(ListView) findViewById(R.id.listView2);
        PlacesAdapter adapter= (PlacesAdapter) listView.getAdapter();
        if(adapter!=null)
        {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        doMySearch(intent.getStringExtra("Query"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;

    }
    private void doMySearch(String query)
    {
        pDialog = new ProgressDialog(PredefinedSearch.this);
        pDialog.setMessage("Loading ...");
        pDialog.show();
        String url= HttpFindPlace(query);
        ReadTask readTask=new ReadTask();
        readTask.execute(url);
    }
    private String HttpFindPlace(String query)
    {
        GPSTracker mGps=new GPSTracker(this);
        double lat=-1;
        double lng=-1;
        if(mGps.canGetLocation())
        {
            lat=mGps.getLatitude();
            lng=mGps.getLongitude();
        }
        Intent intent=getIntent();
        String Begin="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
        String Url;
        String keyword="";
        if(query!="")
        {
            keyword="keyword="+query+"&";
        }

        Url=Begin+lat+","+lng+"&radius=10000&"+keyword+"key="+APIKEY;
        return Url;
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
        protected void onPostExecute(String result)
        {
            try {
                JSONObject jsonObject=new JSONObject(result);
                JSONArray places=jsonObject.getJSONArray("results");
                for(int i=0;i<places.length();++i)
                {
                    JSONObject place= (JSONObject) places.get(i);
                    String rating =String.valueOf(place.optDouble("rating"));
                    String name=place.optString("name");
                    String address=place.optString("vicinity");
                    String iconURL=place.optString("icon");
                    String id=place.optString("place_id");
                    MyPlace place1=new MyPlace();
                    place1.setName(name);
                    place1.setAddress(address);
                    place1.setRating(rating);
                    place1.setId(id);
                    place1.setImageUrl(iconURL);
                    placeslist.add(place1);
                    PlacesAdapter adapter= new PlacesAdapter(getApplicationContext(),R.layout.list_view,placeslist);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent =new Intent(PredefinedSearch.this,ShowPlace.class);
                            intent.putExtra("ID", placeslist.get(position).getId());
                            startActivity(intent);
                        }
                    });

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
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
}
