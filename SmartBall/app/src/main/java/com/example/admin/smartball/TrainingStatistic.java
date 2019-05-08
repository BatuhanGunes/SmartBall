package com.example.admin.smartball;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;


public class TrainingStatistic extends Activity {

    Button HomePage;
    ListView TrainingList;

    String Item = null;
    int idbul = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_statistic);

        Intent newint = getIntent();
        Item = newint.getStringExtra(Statistics.EXTRA_Item);

        HomePage = (Button)findViewById(R.id.HomePage);
        TrainingList = (ListView) findViewById(R.id.TrainingList);


        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String[] itemBol = Item.split(" - ");
        // id'mizi alıyoruz
        idbul = Integer.valueOf(itemBol[0].toString());

        List();
    }

    public void List(){
        DataBase_SQLite vt = new DataBase_SQLite(TrainingStatistic.this);
        List<String> list = vt.DataList_TableTrainingData();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TrainingStatistic.this,
                android.R.layout.simple_list_item_1,android.R.id.text1,list);
                TrainingList.setAdapter(adapter);
    }
}
