package com.MET.salman.MoeslemExpenseTracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class StartActivity extends AppCompatActivity
{
    //DatabaseHandler
    DatabaseHandler db;
    SharedPreferences sharedPreferences;
    ImageButton StudentBtn,EmployeeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/DENSE-REGULAR.OTF");


        sharedPreferences = getSharedPreferences("launch",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getBoolean("start", false);
        boolean first = sharedPreferences.getBoolean("start", false);


        if(!first)
        {
            editor.putBoolean("start", false);
            editor.commit();
        }
        else
        {
            Intent i = new Intent(StartActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        //DatabaseHandler

        db = new DatabaseHandler(this);

        StudentBtn = (ImageButton) findViewById(R.id.StudentBtn);
        StudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent e = new Intent(StartActivity.this,StudentForm.class);
                startActivity(e);
                finish();

            }
        });

        EmployeeBtn = (ImageButton) findViewById(R.id.EmployeeBtn);
        EmployeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent e = new Intent(StartActivity.this,EmployeeForm.class);
                startActivity(e);
                finish();

            }
        });


    }
}
