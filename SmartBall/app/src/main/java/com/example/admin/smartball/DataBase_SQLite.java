package com.example.admin.smartball;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

import java.util.*;

public class DataBase_SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PlayerData";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PERSONS = "persons";
    private static final String ROW_ID = "id";
    private static final String ROW_NAME = "name";
    private static final String ROW_SURNAME = "surname";
    private static final String ROW_AGE = "age";
    private static final String ROW_FOOT = "foot";
    private static final String ROW_POSITION = "position";

    private static final String TABLE_TRAINING_DATA = "trainingdata";
    private static final String ROW_TRAINING_ID = " training_id";
    private static final String ROW_PERSONS_ID = "person_id";
    private static final String ROW_TRAINING_DATE = "training_date";
    private static final String ROW_TOTAL_SHOTS = "total_shots";
    private static final String ROW_SUCCESSFUL_SHOTS = "successful_shots";
    private static final String ROW_PERCENT_SHOTS = "percent_shots";


    public DataBase_SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_PERSONS + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROW_NAME + " TEXT NOT NULL, "
                + ROW_SURNAME + " TEXT NOT NULL, "
                + ROW_AGE + " TEXT NOT NULL, "
                + ROW_FOOT + " TEXT NOT NULL, "
                + ROW_POSITION + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_TRAINING_DATA + "("
                + ROW_TRAINING_ID + " INTEGER PRİMARY KEY AUTOINCREMENT, "
                + ROW_PERSONS_ID + " TEXT NOT NULL, "
                + ROW_TRAINING_DATE + " TEXT NOT NULL, "
                + ROW_TOTAL_SHOTS +  " TEXT NOT NULL, "
                + ROW_SUCCESSFUL_SHOTS + " TEXT NOT NULL, "
                + ROW_PERCENT_SHOTS + " TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAINING_DATA);

        onCreate(db);
    }

    // ----------------------------------------------- Table Person ------------------------------------------------------


    public void AddData_TablePerson(String Name, String Surname, String Age, String Foot, String Position){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_NAME, Name);
            cv.put(ROW_SURNAME, Surname);
            cv.put(ROW_AGE, Age);
            cv.put(ROW_FOOT, Foot);
            cv.put(ROW_POSITION, Position);
            db.insert(TABLE_PERSONS,  null,cv);
        }catch (Exception e){
        }
        db.close();
    }

    public List<String> DataList_TablePerson(){
        List<String> Datas = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] columns = {ROW_ID,ROW_NAME,ROW_SURNAME,ROW_AGE, ROW_FOOT, ROW_POSITION};
            Cursor cursor = db.query(TABLE_PERSONS, columns,null,null,null,null,null);
            while (cursor.moveToNext()){
                Datas.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)
                        + " - "
                        + cursor.getString(3)
                        + " - "
                        + cursor.getString(4)
                        + " - "
                        + cursor.getString(5));
            }
        }catch (Exception e){
        }
        db.close();
        return Datas;
    }

    public void DeletePlayer_TablePerson(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_ID + " = " + id ;
            db.delete(TABLE_PERSONS,where,null);
        }catch (Exception e){
        }
        db.close();
    }

    public void EditPlayer_TablePerson(int id, String Name, String Surname, String Age, String Foot, String Position){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(ROW_NAME, Name);
            cv.put(ROW_SURNAME, Surname);
            cv.put(ROW_AGE, Age);
            cv.put(ROW_FOOT, Foot);
            cv.put(ROW_POSITION, Position);
            String where = ROW_ID +" = '"+ id + "'";
            db.update(TABLE_PERSONS,cv,where,null);
        }catch (Exception e){
        }
        db.close();
    }

    // ----------------------------------------------- Table Training Data --------------------------------------------------



    public void AddData_TableTrainingData(String personsID, String trainingDate, String totalShots, String succesfulShots, String percentShots){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues kv = new ContentValues();
            kv.put(ROW_PERSONS_ID, personsID);
            kv.put(ROW_TRAINING_DATE, trainingDate);
            kv.put(ROW_TOTAL_SHOTS, totalShots);
            kv.put(ROW_SUCCESSFUL_SHOTS, succesfulShots);
            kv.put(ROW_PERCENT_SHOTS, percentShots);
            db.insert(TABLE_TRAINING_DATA,  null,kv);
        }catch (Exception e){
        }
        db.close();
    }

    public List<String> DataList_TableTrainingData(){
        List<String> Datas = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] columns = {ROW_TRAINING_ID,ROW_PERSONS_ID,ROW_TRAINING_DATE,ROW_TOTAL_SHOTS, ROW_SUCCESSFUL_SHOTS, ROW_PERCENT_SHOTS};
            Cursor cursor = db.query(TABLE_TRAINING_DATA, columns,null,null,null,null,null);
            while (cursor.moveToNext()){
                Datas.add(cursor.getInt(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)
                        + " - "
                        + cursor.getString(3)
                        + " - "
                        + cursor.getString(4)
                        + " - "
                        + cursor.getString(5));
            }
        }catch (Exception e){
        }
        db.close();
        return Datas;
    }

    public void DeletePlayer_TabletrainingData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            // id ye göre verimizi siliyoruz
            String where = ROW_TRAINING_ID + " = " + id ;
            db.delete(TABLE_TRAINING_DATA,where,null);
        }catch (Exception e){
        }
        db.close();
    }
}