package com.epicodus.socialite.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.socialite.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.makePlansButton) Button mMakePlansButton;
    @Bind(R.id.textView) TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMakePlansButton.setOnClickListener(this);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/bario.ttf");
        mTextView.setTypeface(myCustomFont);
    }

    @Override
    public void onClick(View v) {
        if(v == mMakePlansButton) {
            Intent intent = new Intent(MainActivity.this, PlanActivity.class);
            startActivity(intent);
        }
    }
}