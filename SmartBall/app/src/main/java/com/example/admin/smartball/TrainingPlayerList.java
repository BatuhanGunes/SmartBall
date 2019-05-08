package com.example.admin.smartball;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;

public class TrainingPlayerList extends Activity {

    //widgets
    Button HomePage;
    ListView playerList;

    public static String EXTRA_Item = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_player_list);


        HomePage = (Button) findViewById(R.id.ResultPage);
        playerList = (ListView)findViewById(R.id.PlayerList);


        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        List();
    }


    public void List(){
        DataBase_SQLite vt = new DataBase_SQLite(TrainingPlayerList.this);
        List<String> list = vt.DataList_TablePerson();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TrainingPlayerList.this,
                android.R.layout.simple_list_item_1,android.R.id.text1,list);
        playerList.setAdapter(adapter);
        playerList.setOnItemClickListener(myListClickListener);
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int position, long id)
        {

            // Tıklanan verimizi alıyoruz
            String item = playerList.getItemAtPosition(position).toString();
            EXTRA_Item = item;

            // Make an intent to start next activity.
            Intent i = new Intent(TrainingPlayerList.this, BtControl.class);
            startActivity(i);
        }
    };
}

