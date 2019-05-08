package com.example.admin.smartball;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Settings extends Activity {


    Button HomePage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        HomePage = (Button) findViewById(R.id.ResultPage);

        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
