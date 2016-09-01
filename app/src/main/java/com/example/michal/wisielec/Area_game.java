package com.example.michal.wisielec;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class Area_game extends Activity {
    public static final String EXTRA_MESSAGE = "";
    String str ="";
    int [] foto = {R.drawable.foto,R.drawable.fotodwa,R.drawable.fototrzy,R.drawable.fotocztery,
            R.drawable.fotopiec,R.drawable.fotoszesc,R.drawable.fotosiedem,R.drawable.fotoosiem,R.drawable.fotodziewiec};
    String haslotxt;
    int indexfoto = 0;
    int los2;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edytor;
    int countWin, countOver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_game);

        sharedPreferences = getSharedPreferences("com.example.michal.wisielec", Context.MODE_PRIVATE);
        edytor = sharedPreferences.edit();
        TextView textVievWynik = (TextView)findViewById(R.id.wynik);
        String youWin = String.valueOf(sharedPreferences.getInt("winn",0));
        String gameOwer = String.valueOf(sharedPreferences.getInt("ower",0));
        textVievWynik.setText(" WYNIK : "+" "+" WIN : "+youWin +" / "+ " OWER : " + gameOwer);


        Intent intent = getIntent();
        String kategoriaHasel = intent.getStringExtra(EXTRA_MESSAGE);
        //Toast.makeText(getApplicationContext(), kategoriaHasel, Toast.LENGTH_SHORT).show();


        try{
            SQLiteOpenHelper hh = new DBHasla(this);
            SQLiteDatabase db = hh.getReadableDatabase();
            Cursor cur = db.query("TS", new String[]{"TXT", "KAT"}, "KAT = ?", new String[]{kategoriaHasel}, null, null, null);
            getHaslazDB(cur);


            cur.close();
            db.close();


        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "Błąd bazy dabych", Toast.LENGTH_SHORT).show();

        }

        zamianaZnakow();
    }

    public void getHaslazDB(Cursor cur){
        int ktoreHaslo=0;
        cur.moveToFirst();
        String[] names = new String[cur.getCount()];
        while(!cur.isAfterLast()) {
            names[ktoreHaslo] = cur.getString(cur.getColumnIndex("TXT"));
            cur.moveToNext();
            ktoreHaslo++;
        }
        losowanieHasla(names);

    }

    public void losowanieHasla(String []names){
        Random random = new Random();
        los2 = random.nextInt(names.length);
        haslotxt = names[los2];
        Toast.makeText(getApplicationContext(), haslotxt, Toast.LENGTH_SHORT).show();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_area_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void zamianaZnakow(){
        TextView text = (TextView)findViewById(R.id.tvHaslo);
        for(int i=0; i<haslotxt.length();i++){
            if(Character.toString(haslotxt.charAt(i)).equals(" ")){
                str = str + " ";

            }else {str = str+ '-' ;}
        }
        text.setText(str);
    }



    public void odkryjZnak(String znakUzytkownika, int idznaku) {
        boolean czyTrafiona = false;
        Button zmianabuttonu = (Button)findViewById(idznaku);
        for (int i = 0; i < haslotxt.length(); i++) {
            if (Character.toString(haslotxt.charAt(i)).equals(znakUzytkownika)) {
                czyTrafiona = true;
                odkry(i, znakUzytkownika);
            }

        }

        if(czyTrafiona == true){
            zmianabuttonu.setClickable(false);
            zmianabuttonu.setBackgroundColor(Color.GREEN);
            indexfoto--;
            zmianaFotkiWynik();
        }else{
            zmianabuttonu.setClickable(false);
            zmianabuttonu.setBackgroundColor(Color.RED);
            indexfoto++;
            zmianaFotkiWynik();

        }

    }

    public void odkry(int indexDoOdkrycia, String znakDoOdkrycia){
        if(indexDoOdkrycia > str.length()){
            Toast bag = Toast.makeText(getApplicationContext(),"przepełnienie",Toast.LENGTH_LONG);
            bag.show();
        }else{
            str = str.substring(0,indexDoOdkrycia) + znakDoOdkrycia + str.substring(indexDoOdkrycia+1);
            //koniecGry(str);
            if(str.equals(haslotxt)) {
                winAdnOver(true,"YOU WIN !");
            }
        }
        TextView text = (TextView) findViewById(R.id.tvHaslo);
        text.setText(str);

    }


    void zmianaFotkiWynik(){
        if(indexfoto<0) {
            indexfoto = 0;

        }else if(indexfoto>=9) {
            indexfoto = 8;

        }else if(indexfoto<9) {
            int w = foto[indexfoto];
            ImageView imgV = (ImageView) findViewById(R.id.imObraz);
            imgV.setImageResource(w);
            if(indexfoto == 8){
                winAdnOver(false,"GAME OVER !");
            }
        }


    }


    public void winAdnOver(boolean wynik,String txt){
        if(wynik == true){
            //Toast.makeText(getApplicationContext(),txt ,Toast.LENGTH_LONG).show();
            oknoDialogowe(txt);
            tablicaWynikow(true);
        }else{
            //Toast.makeText(getApplicationContext(),txt ,Toast.LENGTH_LONG).show();
            oknoDialogowe(txt);
            tablicaWynikow(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);

            }
        }, 3000);

    }

    public void tablicaWynikow(boolean winAdnOwer){
        int winOrOwer;

        if(winAdnOwer == true){
            winOrOwer = sharedPreferences.getInt("winn",0);
            winOrOwer ++;
            edytor.putInt("winn",winOrOwer);

        }else{
            winOrOwer = sharedPreferences.getInt("ower",0);
            winOrOwer ++;
            edytor.putInt("ower",winOrOwer);
        }
        edytor.commit();

    }


    public void oknoDialogowe(String rezultat){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(rezultat);
        if(rezultat == "YOU WIN !"){
            String youWin = String.valueOf((sharedPreferences.getInt("winn",0))+1);
            builder.setMessage("To jest Twoje " + youWin +" wygrana");
        }else if(rezultat == "GAME OVER !"){
            String over = String.valueOf((sharedPreferences.getInt("ower",0))+1);
            builder.setMessage("To jest Twoje " + over +" przegrana");

        }
       // builder.setMessage("GRAJ DALEJ ;-)");
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }



    public void onClickAbc(View view) {
        int idButtonuDoFunkcji = view.getId();


        switch (view.getId()) {
            case R.id.A:
                odkryjZnak("A", idButtonuDoFunkcji);

                break;

            case R.id.Ą:
                odkryjZnak("Ą", idButtonuDoFunkcji);

                break;

            case R.id.B:
                odkryjZnak("B", idButtonuDoFunkcji);

                break;
            case R.id.C:
                odkryjZnak("C", idButtonuDoFunkcji);

                break;
            case R.id.Ć:
                odkryjZnak("Ć", idButtonuDoFunkcji);

                break;
            case R.id.D:
                odkryjZnak("D", idButtonuDoFunkcji);

                break;
            case R.id.E:
                odkryjZnak("E", idButtonuDoFunkcji);

                break;
            case R.id.Ę:
                odkryjZnak("Ę", idButtonuDoFunkcji);

                break;
            case R.id.F:
                odkryjZnak("F", idButtonuDoFunkcji);

                break;
            case R.id.G:
                odkryjZnak("G", idButtonuDoFunkcji);

                break;
            case R.id.H:
                odkryjZnak("H", idButtonuDoFunkcji);

                break;
            case R.id.I:
                odkryjZnak("I", idButtonuDoFunkcji);

                break;
            case R.id.J:
                odkryjZnak("J", idButtonuDoFunkcji);
                break;
            case R.id.K:
                odkryjZnak("K", idButtonuDoFunkcji);

                break;
            case R.id.L:
                odkryjZnak("L", idButtonuDoFunkcji);

                break;
            case R.id.Ł:
                odkryjZnak("Ł", idButtonuDoFunkcji);

                break;
            case R.id.M:
                odkryjZnak("M", idButtonuDoFunkcji);

                break;
            case R.id.N:
                odkryjZnak("N", idButtonuDoFunkcji);

                break;
            case R.id.Ń:
                odkryjZnak("Ń", idButtonuDoFunkcji);

                break;
            case R.id.O:
                odkryjZnak("O", idButtonuDoFunkcji);

                break;
            case R.id.Ó:
                odkryjZnak("Ó", idButtonuDoFunkcji);

                break;
            case R.id.P:
                odkryjZnak("P", idButtonuDoFunkcji);

                break;
            case R.id.Q:
                odkryjZnak("Q", idButtonuDoFunkcji);

                break;
            case R.id.R:
                odkryjZnak("R", idButtonuDoFunkcji);

                break;
            case R.id.S:
                odkryjZnak("S", idButtonuDoFunkcji);

                break;
            case R.id.Ś:
                odkryjZnak("Ś", idButtonuDoFunkcji);

                break;
            case R.id.T:
                odkryjZnak("T", idButtonuDoFunkcji);

                break;
            case R.id.U:
                odkryjZnak("U", idButtonuDoFunkcji);

                break;
            case R.id.V:
                odkryjZnak("V", idButtonuDoFunkcji);

                break;
            case R.id.W:
                odkryjZnak("W", idButtonuDoFunkcji);

                break;
            case R.id.X:
                odkryjZnak("X", idButtonuDoFunkcji);

                break;
            case R.id.Y:
                odkryjZnak("Y", idButtonuDoFunkcji);

                break;
            case R.id.Z:
                odkryjZnak("Z", idButtonuDoFunkcji);

                break;
            case R.id.Ż:
                odkryjZnak("Ż", idButtonuDoFunkcji);

                break;
            case R.id.Ź:
                odkryjZnak("Ź", idButtonuDoFunkcji);

                break;

        }
    }

}
