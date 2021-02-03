package com.teachonlineahmedoalamin.trackingperiod.Utiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.teachonlineahmedoalamin.trackingperiod.Model.TrackedInfoLocal;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "trackedInfoLocal";
    private static final String PERIOD_TABLE = "period";
    private static final String ID = "id";
    private static final String DAYS_OF_CYCLE = "daysCycle";
    private static final String PERIOD_DAYS = "periodDays";
    private static final String LAST_DATE = "lastDate";
    private static final String LOCAL_NAME = "localName";

    private static final String CREATE_PERIOD_TABLE = "CREATE TABLE " + PERIOD_TABLE + "(" + ID + " INTEGER PRIMARY KEY , "
            + DAYS_OF_CYCLE + " INTEGER, "+ PERIOD_DAYS + " INTEGER, "
            + LAST_DATE + " TEXT," + LOCAL_NAME + " TEXT)";

    private SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, NAME, null, VERSION);
    }


    public  void deleteAll(){
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PERIOD_TABLE);
        // Create tables again
        onCreate(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PERIOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PERIOD_TABLE);
        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void insertLocalperiodData(TrackedInfoLocal data) {
        ContentValues cv = new ContentValues();
        cv.put(DAYS_OF_CYCLE, data.getDaysOfCycle());
        cv.put(PERIOD_DAYS,data.getPeriodDays());
        cv.put(LAST_DATE, data.getLastDateLocal());
        cv.put(LOCAL_NAME, data.getLocalName());
        db.insert(PERIOD_TABLE, null, cv);
    }

    public TrackedInfoLocal getAllData() {
        TrackedInfoLocal info = new TrackedInfoLocal();
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db.query(PERIOD_TABLE, null, null, null, null, null, null, null);
            if (cur != null) {
                if (cur.moveToFirst()) {
                    do {
                        info.setId(cur.getInt(cur.getColumnIndex(ID)));
                        info.setDaysOfCycle(cur.getInt(cur.getColumnIndex(DAYS_OF_CYCLE)));
                        info.setPeriodDays(cur.getInt(cur.getColumnIndex(PERIOD_DAYS)));
                        info.setLocalName(cur.getString(cur.getColumnIndex(LOCAL_NAME)));
                        info.setLastDateLocal(cur.getString(cur.getColumnIndex(LAST_DATE)));

                    }
                    while (cur.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return info;
    }

    public void updateDaysOfCycle(int id, int days){
        ContentValues cv = new ContentValues();
        cv.put(DAYS_OF_CYCLE, days);
        db.update(PERIOD_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updatePeriodDays(int id, int days){
        ContentValues cv = new ContentValues();
        cv.put(PERIOD_DAYS, days);
        db.update(PERIOD_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateLastDateLocal(int id, String lastDate) {
        ContentValues cv = new ContentValues();
        cv.put(LAST_DATE, lastDate);
        db.update(PERIOD_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void updateLocalName(int id, String localName) {
        ContentValues cv = new ContentValues();
        cv.put(LOCAL_NAME, localName);
        db.update(PERIOD_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteTask(int id){
        db.delete(PERIOD_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }

    public void deleteAllFromData(){
        db.execSQL("delete from "+ PERIOD_TABLE);
    }
}

