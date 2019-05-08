package com.example.admin.smartball;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.os.*;
import android.text.method.*;
import android.view.*;
import android.widget.*;

import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.*;

public class Training extends Activity {

    //widgets
    Button start, stop, resultPage;
    TextView trainingView, PlayerName, PlayerSurname, PlayerAge, PlayerFoot, PlayerPosition;
    ImageView PlayerImage;

    String Item = null;
    String Item2 = null;
    int idBul = 0;
    int Hit = 0;

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    boolean stopThread;
    private InputStream inputStream;
    public static String EXTRA_Item33 = "";
    public static String EXTRA_Item2 = "";


    //date variables
    Date Now = new Date();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    final Calendar takvim = Calendar.getInstance();
    int hour = takvim.get(Calendar.HOUR_OF_DAY);
    int minute = takvim.get(Calendar.MINUTE);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training);

        Intent newint = getIntent();
        address = newint.getStringExtra(BtControl.EXTRA_ADDRESS); //receive the address of the bluetooth device


        Item = TrainingPlayerList.EXTRA_Item;

        start = (Button)findViewById(R.id.Start);
        stop = (Button)findViewById(R.id.Stop);
        resultPage = (Button)findViewById(R.id.ResultPage);
        trainingView = (TextView)findViewById(R.id.TrainingView);
        PlayerName = (TextView)findViewById(R.id.playerName);
        PlayerSurname = (TextView)findViewById(R.id.PlayerSurname);
        PlayerAge = (TextView)findViewById(R.id.playerAge);
        PlayerFoot = (TextView)findViewById(R.id.playerFoot);
        PlayerPosition = (TextView)findViewById(R.id.playerPosition);
        PlayerImage = (ImageView) findViewById(R.id.PlayerImage);
        PlayerImage.setImageResource(R.drawable.user);

        new ConnectBT().execute(); //Call the class to connect

        final String[] itemBol = Item.split(" - ");
        // id'mizi alıyoruz
        idBul = Integer.valueOf(itemBol[0].toString());
        // Diğer verilerimizi set ediyoruz.
        PlayerName.setText(itemBol[1].toString());
        PlayerSurname.setText(itemBol[2].toString());
        PlayerAge.setText(itemBol[3].toString());
        PlayerFoot.setText(itemBol[4].toString());
        PlayerPosition.setText(itemBol[5].toString());


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("Antreman başlatıldı.");
                DataRead();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(stopThread == false) {
                    stopThread = true;
                    msg("Antreman durduruldu.");
                }else{
                    msg("Başlamamış antremanı durduramam.");
                }
            }
        });

        resultPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msg("Sonuçlar kaydedildi.");
                Disconnect();

                int TotalShots = 10;
                int percentShots = (Hit*100)/10;
                String date = hour + ":" + minute  + "        " + df.format(Now);

                Item2 = idBul + " - " + date + " - " + TotalShots + " - " + Hit + " - " + percentShots;
                Intent i = new Intent(Training.this, ResultPage.class);
                i.putExtra(EXTRA_Item2, Item2);

                String array = itemBol[0] + " - " + itemBol[1] + " - " +  itemBol[2] + " - " +  itemBol[3] +
                        " - " + itemBol[4] + " - " +  itemBol[5];
                EXTRA_Item33 = array;
                startActivity(i);
            }
        });
    }


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private void DataRead()
    {
        trainingView.setMovementMethod(new ScrollingMovementMethod());
        trainingView.setMaxLines(Integer.MAX_VALUE);
        final Handler handler = new Handler();
        stopThread = false;
        final Thread thread  = new Thread(new Runnable()
        {
            public void run()
            {
                while(!Thread.currentThread().isInterrupted() && !stopThread)
                {
                    try
                    {
                        inputStream= btSocket.getInputStream();
                        int byteCount = inputStream.available();
                        if(byteCount > 0) {
                            byte[] rawBytes = new byte[byteCount];
                            inputStream.read(rawBytes);
                            final String string = new String(rawBytes, "UTF-8");
                                handler.post(new Runnable() {
                                    public void run() {

                                            Hit = Hit + 1;
                                            trainingView.append("\n" + "Hareket algılandı." + "      İsabet sayısı : " + Hit);

                                    }
                                });
                            }
                    }
                    catch (IOException ex)
                    {
                        stopThread = true;
                    }
                }
            }
        });
        thread.start();
    }



    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        //finish(); //return to the first layout

    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Training.this, "Bağlantı Kuruluyor...",
                    "Lütfen Bekleyiniz!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown            , the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();                                //get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);                //connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);          //create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)                                                      //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Bağlantı başarısız oldu. Lütfen tekrar deneyiniz.");
                finish();
            }
            else
            {
                msg("Bağlatı başarılı bir şekilde kuruldu.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}
