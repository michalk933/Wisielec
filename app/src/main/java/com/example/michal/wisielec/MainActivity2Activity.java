package com.example.michal.wisielec;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity2Activity extends Activity{
    DBHasla myDB;
    String haslo ="";
    String kat ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);



        RadioGroup grupa = (RadioGroup)findViewById(R.id.grupaRadioKat);
        //RadioButton checkedRadioButton = (RadioButton)grupa.findViewById(grupa.getCheckedRadioButtonId());
       // int wyborKat = grupa.getCheckedRadioButtonId();


        grupa.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radioSport:
                        Toast.makeText(getApplicationContext(),"sport",Toast.LENGTH_SHORT).show();
                        kat = "SPORT";

                        break;
                    case R.id.radioKraj:
                        Toast.makeText(getApplicationContext(),"kraj",Toast.LENGTH_SHORT).show();
                        kat = "KRAJ";
                        break;
                    case R.id.radioZwierze:
                        Toast.makeText(getApplicationContext(),"animal",Toast.LENGTH_SHORT).show();
                        kat = "ZWIERZĘTA";

                        break;
                    case R.id.radioAlbania:
                        Toast.makeText(getApplicationContext(),"albania",Toast.LENGTH_SHORT).show();
                        kat = "GANG ALBANI";

                        break;

                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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

    public void nowaPozycja(View view) {
        EditText ed = (EditText)findViewById(R.id.editor);
        haslo = ed.getText().toString().toUpperCase();

        if(!haslo.equals("") && !kat.equals("") ){

            myDB = new DBHasla(this);
            boolean prawda = myDB.insertNewHaslo(haslo,kat);
            if(prawda == true){
                Toast.makeText(getApplicationContext(),"DOADNO : "+haslo +" / "+kat,Toast.LENGTH_SHORT).show();

            }else if(prawda == false){
                Toast.makeText(getApplicationContext(),"NIE DODANO",Toast.LENGTH_SHORT).show();


            }


        }else if(haslo.equals("") || kat.equals("")){
            Toast.makeText(getApplicationContext(),"Brakuje danych",Toast.LENGTH_SHORT).show();

        }


    }
}
