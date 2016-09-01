package com.example.michal.wisielec;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by michal on 21.08.2016.
 */
public class DBHasla extends SQLiteOpenHelper {

    private static final String DB_NAME = "haslaDoGry.db";
    private static final int BD_VERSION = 1;


    public DBHasla(Context context) {
        super(context, DB_NAME, null, BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        upData(db, 0, BD_VERSION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersionDB, int newVersionDB) {
        upData(db,oldVersionDB,newVersionDB);

    }

    private void upData(SQLiteDatabase db,int oldVersionDB, int newVersionDB){
        if(oldVersionDB < 1){
            db.execSQL("CREATE TABLE TS (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "TXT TEXT,"
                    + "KAT TEXT );");

 // ============================= KAT SPORT ==================
            insetsTest(db, "PIŁKA NOŻNA", "SPORT");
            insetsTest(db,"HOKEJ","SPORT");
            insetsTest(db,"PIŁKA RĘCZNA","SPORT");
            insetsTest(db,"SKOK W DAL","SPORT");
            insetsTest(db,"SKOK O TYCZCE","SPORT");
            insetsTest(db,"SPRINT","SPORT");
            insetsTest(db,"MARATON","SPORT");
            insetsTest(db,"BOKS","SPORT");
            insetsTest(db,"RAJDY","SPORT");

// ============================= KAT KRAJ ==================

            insetsTest(db,"POLSKA","KRAJ");
            insetsTest(db,"NIEMCY","KRAJ");
            insetsTest(db,"ROSIA","KRAJ");
            insetsTest(db,"USA","KRAJ");
            insetsTest(db,"ANGLIA","KRAJ");
            insetsTest(db,"SŁOWACJA","KRAJ");
            insetsTest(db,"UKRAINA","KRAJ");
            insetsTest(db,"HISZPANIA","KRAJ");
            insetsTest(db,"NORWEGIA","KRAJ");
            insetsTest(db,"PORTUGALIA","KRAJ");


 // ============================= KAT ZWIERZ�TA ==================

            insetsTest(db,"PIES","ZWIERZĘTA");
            insetsTest(db,"KOT","ZWIERZĘTA");
            insetsTest(db,"KOŃ","ZWIERZĘTA");
            insetsTest(db,"WIEWIÓRKA","ZWIERZĘTA");
            insetsTest(db,"BOCIAN","ZWIERZĘTA");
            insetsTest(db,"KACZKA","ZWIERZĘTA");
            insetsTest(db,"ORZEŁ","ZWIERZĘTA");
            insetsTest(db,"KROKODYL","ZWIERZĘTA");
            insetsTest(db,"LEW","ZWIERZĘTA");
            insetsTest(db,"MAŁPA","ZWIERZĘTA");

 //========================== KAT Gang albani =============================

            insetsTest(db,"POPEK","GANG ALBANI");
            insetsTest(db,"BORIXON","GANG ALBANI");
            insetsTest(db,"ALIBABA","GANG ALBANI");
            insetsTest(db,"RIKI TIKI","GANG ALBANI");
            insetsTest(db,"PRAWDZIWA DAMA","GANG ALBANI");
            insetsTest(db,"NAPAD NA BAK","GANG ALBANI");
            insetsTest(db,"CIAPATY PIZDOL","GANG ALBANI");
            insetsTest(db,"MIS ALBANIA","GANG ALBANI");
            insetsTest(db,"BRUDNA DARIANNA","GANG ALBANI");




        }
        if(oldVersionDB < 2){
            db.execSQL("ALTER TABLE TS ADD COLUMN F NUMERIC;");


        }


    }

    private static void insetsTest(SQLiteDatabase db,String txt,String ka){
        ContentValues cv = new ContentValues();
        cv.put("TXT", txt);
        cv.put("KAT", ka);
        db.insert("TS",null,cv);

    }



    public boolean insertNewHaslo(String txt,String ka){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("TXT", txt);
        c.put("KAT", ka);
        long result = db.insert("TS",null,c);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }





}
