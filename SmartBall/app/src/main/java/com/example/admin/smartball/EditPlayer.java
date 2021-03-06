package com.example.admin.smartball;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.widget.*;

import java.io.*;
import java.util.*;

public class EditPlayer extends Activity {

    Button lastPage, playerDelete, editPlayer;
    EditText Name, Surname, Age, Foot, Position;
    ImageView PlayerImage;
    String Item = null;
    int idBul = 0;

    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private Uri mImageCaptureUri2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_player);

        Intent newint = getIntent();
        Item = newint.getStringExtra(PlayerOrganize.EXTRA_Item);

        lastPage = (Button) findViewById(R.id.LastPage);
        playerDelete = (Button) findViewById(R.id.PlayerDelete);
        editPlayer = (Button) findViewById(R.id.EditPlayer);
        Name = (EditText) findViewById(R.id.playerName);
        Surname = (EditText) findViewById(R.id.playerSurname);
        Age = (EditText) findViewById(R.id.playerAge);
        Foot = (EditText) findViewById(R.id.playerFoot);
        Position = (EditText) findViewById(R.id.playerPosition);
        PlayerImage = (ImageView) findViewById(R.id.PlayerImage);
        PlayerImage.setImageResource(R.drawable.user);


        String[] itemBol = Item.split(" - ");
        // id'mizi alıyoruz
        idBul = Integer.valueOf(itemBol[0].toString());
        // Diğer verilerimizi set ediyoruz.
        Name.setText(itemBol[1].toString());
        Surname.setText(itemBol[2].toString());
        Age.setText(itemBol[3].toString());
        Foot.setText(itemBol[4].toString());
        Position.setText(itemBol[5].toString());


        lastPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditPlayer.this, MainActivity.class);
                startActivity(i);
            }
        });

        playerDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBase_SQLite vt = new DataBase_SQLite(EditPlayer.this);
                vt.DeletePlayer_TablePerson(idBul);
                Intent i = new Intent(EditPlayer.this, MainActivity.class);
                startActivity(i);
            }
        });

        editPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cmgName = Name.getText().toString();
                String cmgSurname = Surname.getText().toString();
                String cmgAge = Age.getText().toString();
                String cmgFoot = Foot.getText().toString();
                String cmgPosition = Position.getText().toString();

                if (cmgName.length() > 0 && cmgSurname.length() > 0 && cmgAge.length() > 0
                        && cmgFoot.length() > 0 && cmgPosition.length() > 0) {
          // Veritabanı bağlantımızı açalım ver ardından gerekli bilgileri VeriDuzenle() gönderelim
          DataBase_SQLite vt = new DataBase_SQLite(EditPlayer.this);
          vt.EditPlayer_TablePerson(idBul, cmgName, cmgSurname, cmgAge, cmgFoot, cmgPosition);

                    msg("Değişiklikler kaydedildi.");
                } else {
                    msg("Lütfen gerekli alanların tümünü doldurunuz.");
                }
            }
        });
        final String[] items = new String[]{"Camerayı Kullan", "Galeriden Seç"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this);

        builder.setTitle("Resim seç");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { //pick from camera
                if (item == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                    try {
                        //intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else { //pick from file
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    Intent mIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(mIntent, PICK_FROM_FILE);
                }
            }
        });

        final android.support.v7.app.AlertDialog dialog = builder.create();



        PlayerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                doCrop();

                break;

            case PICK_FROM_FILE:
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");

                    PlayerImage.setImageBitmap(photo);

                }
                if (data.getData() != null) {
                    Uri imageUri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        PlayerImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                File f = new File(mImageCaptureUri.getPath());

                if (f.exists()) f.delete();

                break;

        }
    }

    private void doCrop() {

        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            mImageCaptureUri2 = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                    "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri2);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();
                    co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);
                    co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getApplicationContext(), cropOptions);

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
