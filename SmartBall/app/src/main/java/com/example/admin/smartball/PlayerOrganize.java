package com.example.admin.smartball;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.*;

public class PlayerOrganize extends Activity {

    Button personsAdd;
    ListView playerList;
    public static String EXTRA_Item = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_organize);

        personsAdd = (Button)findViewById(R.id.PersonsAdd);
        playerList = (ListView)findViewById(R.id.PlayerList);

        personsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlayerOrganize.this, AddPlayer.class);
                startActivity(i);
            }
        });

        DataBase_SQLite vt = new DataBase_SQLite(PlayerOrganize.this);
        List<String> list = vt.DataList_TablePerson();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PlayerOrganize.this,
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

            // Make an intent to start next activity.
            Intent i = new Intent(PlayerOrganize.this, EditPlayer.class);
            i.putExtra(EXTRA_Item, item);
            startActivity(i);
        }
    };
}
