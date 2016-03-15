package com.vacationplanner.an.vacationguru;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ChooseType extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        ImageView iv1=(ImageView) findViewById(R.id.imageView2);
        ImageView iv2=(ImageView) findViewById(R.id.imageView3);
        ImageView iv3=(ImageView) findViewById(R.id.imageView4);
        ImageView iv4=(ImageView) findViewById(R.id.imageView5);
        ImageView iv5=(ImageView) findViewById(R.id.imageView6);
        ImageView iv6=(ImageView) findViewById(R.id.imageView7);
        TextView tv1=(TextView) findViewById(R.id.textViewc1);
        TextView tv2=(TextView) findViewById(R.id.textViewc2);
        TextView tv3=(TextView) findViewById(R.id.textViewc3);
        TextView tv4=(TextView) findViewById(R.id.textViewc4);
        TextView tv5=(TextView) findViewById(R.id.textViewc5);
        TextView tv6=(TextView) findViewById(R.id.textViewc6);
        TextView tv7=(TextView) findViewById(R.id.textViewc7);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        iv4.setOnClickListener(this);
        iv5.setOnClickListener(this);
        iv6.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_type, menu);
        return true;
    }
    @Override
    public void onClick(View view)
    {
        Intent intent=new Intent(this,PredefinedSearch.class);
        String extra="";
        switch(view.getId())
        {
            case R.id.imageView2:
                extra="Restaurant";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.imageView3:
                extra="Coffee";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.imageView4:
                extra="Park";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.imageView5:
                extra="Store";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.imageView6:
                extra="Cinema";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.imageView7:
                extra="Hotel";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc1:
                extra="Restaurant";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc2:
                extra="Coffee";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc3:
                extra="Park";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc4:
                extra="Store";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc5:
                extra="Cinema";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc6:
                extra="Hotel";
                intent.putExtra("Query",extra);
                startActivity(intent);
                break;
            case R.id.textViewc7:
                Intent intent2 =new Intent(this,SearchActivity.class);
                startActivity(intent2);
                break;
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
