package com.vacationplanner.an.vacationguru;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class FindAttr extends AppCompatActivity {
    RadioGroup radioGroup;
    int PLACE_PICKER_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_attr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_attr, menu);
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

    public void findonmap(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            Context context = getApplicationContext();
            startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(data, this);
                Intent intent=new Intent(this,ShowPlace.class);
                String type="Other";
                for(int i=0;i<place.getPlaceTypes().size();++i)
                {
                    if(place.getPlaceTypes().get(i)== Place.TYPE_RESTAURANT)
                    {
                        type="Restaurant";
                        break;
                    }
                    else if(place.getPlaceTypes().get(i)==Place.TYPE_STORE)
                    {
                        type="Shop";
                        break;
                    }
                    else if(place.getPlaceTypes().get(i)==Place.TYPE_LODGING)
                    {
                        type="Hotel";
                        break;
                    }
                    else if(place.getPlaceTypes().get(i)==Place.TYPE_MUSEUM)
                    {
                        type="Museum";
                        break;
                    }
                    else if(place.getPlaceTypes().get(i)==Place.TYPE_PARK)
                    {
                        type="Park";
                        break;
                    }
                    else
                    {
                        type="Other";
                        break;
                    }
                }

                intent.putExtra("Name",place.getName());
                intent.putExtra("Address",place.getAddress());
                intent.putExtra("Type",type);
                intent.putExtra("ID",place.getId());
                intent.putExtra("Long",place.getLatLng().longitude);
                intent.putExtra("Lat",place.getLatLng().latitude);
                startActivity(intent);

            }
        }
    }

    public void findnearby(View view) {
        Intent intent=new Intent(this,ChooseType.class);
        startActivity(intent);
    }
}
