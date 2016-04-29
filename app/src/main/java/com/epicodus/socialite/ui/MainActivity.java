package com.epicodus.socialite.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.socialite.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.makePlansButton) Button mMakePlansButton;
    @Bind(R.id.textView) TextView mTextView;
    @Bind(R.id.viewEventsButton) Button mViewEventsButton;
    @Bind(R.id.toolbar) Toolbar topToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMakePlansButton.setOnClickListener(this);
        mViewEventsButton.setOnClickListener(this);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/bario.ttf");
        mTextView.setTypeface(myCustomFont);

        setTitle(null);
        setSupportActionBar(topToolBar);
        topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));
    }

    @Override
    public void onClick(View v) {
        if(v == mMakePlansButton) {
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
        }if (v == mViewEventsButton) {
            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_add){
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Add", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_view){
            Intent intent = new Intent(MainActivity.this, EventListActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "View", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}