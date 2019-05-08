package com.example.admin.smartball;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ResultPage extends Activity {

    Button HomePage;
    TextView playerName, PlayerSurname, playerAge, playerFoot, playerPosition;
    TextView trainingDate, totalShots, percentShots, succesfulShots;
    ImageView PlayerImage;

    String Item = null;
    String Item2 = null;
    int idBul = 0;
    int idBul2 = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        HomePage = (Button)findViewById(R.id.HomePage);
        playerName = (TextView)findViewById(R.id.playerName);
        PlayerSurname = (TextView)findViewById(R.id.playerSurname);
        playerAge = (TextView)findViewById(R.id.playerAge);
        playerFoot = (TextView)findViewById(R.id.playerFoot);
        playerPosition = (TextView)findViewById(R.id.playerPosition);
        PlayerImage = (ImageView)findViewById(R.id.PlayerImage);
        PlayerImage.setImageResource(R.drawable.user);


        //Intent newint = getIntent();
        //Item = newint.getStringExtra(Training.EXTRA_Item33);

        final String[] itemBol = Training.EXTRA_Item33.split(" - ");
        // id'mizi alıyoruz
        idBul = Integer.valueOf(itemBol[0].toString());
        // Diğer verilerimizi set ediyoruz.
        playerName.setText(itemBol[1].toString());
        PlayerSurname.setText(itemBol[2].toString());
        playerAge.setText(itemBol[3].toString());
        playerFoot.setText(itemBol[4].toString());
        playerPosition.setText(itemBol[5].toString());


        trainingDate = (TextView)findViewById(R.id.TrainingDate);
        totalShots = (TextView)findViewById(R.id.TotalShots);
        succesfulShots = (TextView)findViewById(R.id.SuccesfulShots);
        percentShots = (TextView) findViewById(R.id.PercentShots);

        Intent newint2 = getIntent();
        Item2 = newint2.getStringExtra(Training.EXTRA_Item2);

        String[] itemBol2 = Item2.split(" - ");
        // id'mizi alıyoruz
        idBul2 = Integer.valueOf(itemBol2[0].toString());        //id
        // Diğer verilerimizi set ediyoruz.
        trainingDate.setText(itemBol2[1].toString());            //date
        totalShots.setText(itemBol2[2].toString());              //tottal shots
        succesfulShots.setText(itemBol2[3].toString());          //Hit
        percentShots.setText(itemBol2[4].toString());            //percent shots

        DataBase_SQLite dbSQL = new DataBase_SQLite(ResultPage.this);
        dbSQL.AddData_TableTrainingData(itemBol2[0], itemBol2[1], itemBol2[2], itemBol2[3], "10");


        HomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ResultPage.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
