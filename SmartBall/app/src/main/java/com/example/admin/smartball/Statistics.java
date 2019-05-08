package com.example.admin.smartball;


import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.*;

public class Statistics extends Activity {

    Button HomePage;
    ListView personList;

    public static String EXTRA_Item = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        HomePage = (Button) findViewById(R.id.ResultPage);
        personList = (ListView)findViewById(R.id.PersonsList);

        List();

        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
    }

    public void List(){
        DataBase_SQLite vt = new DataBase_SQLite(Statistics.this);
        List<String> list = vt.DataList_TablePerson();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Statistics.this,
                android.R.layout.simple_list_item_1,android.R.id.text1,list);
        personList.setAdapter(adapter);
        personList.setOnItemClickListener(myListClickListener);
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int position, long id)
        {

            // Tıklanan verimizi alıyoruz
            String item = personList.getItemAtPosition(position).toString();

            // Make an intent to start next activity.
            Intent i = new Intent(Statistics.this, TrainingStatistic.class);
            i.putExtra(EXTRA_Item, item);
            startActivity(i);
        }
    };

}
