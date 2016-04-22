package com.epicodus.socialite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.makePlansButton) Button mMakePlansButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMakePlansButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mMakePlansButton) {
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
        }
    }
}