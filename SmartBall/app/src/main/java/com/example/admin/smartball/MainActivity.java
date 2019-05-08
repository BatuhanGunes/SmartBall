package com.example.admin.smartball;

import android.content.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    //widgets
    Button StartTrining;
    Button Statistics;
    Button playerOrganize;
    Button Settings;
    Button LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StartTrining = (Button) findViewById(R.id.StartTrining);
        Statistics = (Button) findViewById(R.id.statistics);
        playerOrganize = (Button) findViewById(R.id.PlayerOrganize);
        Settings = (Button) findViewById(R.id.settings);
        LogOut = (Button) findViewById(R.id.logOut);


        StartTrining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, TrainingPlayerList.class);
                startActivity(i);
            }
        });


        Statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.admin.smartball.Statistics.class);
                startActivity(i);
            }
        });


        playerOrganize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PlayerOrganize.class);
                startActivity(i);
            }
        });


        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, com.example.admin.smartball.Settings.class);
                startActivity(i);
            }
        });

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }
}