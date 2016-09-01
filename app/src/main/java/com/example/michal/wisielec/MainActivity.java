package com.example.michal.wisielec;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void WyborKat(View view) {



        switch (view.getId()) {
            case R.id.sport:
                Intent intent = new Intent(this, Area_game.class);
                intent.putExtra(Area_game.EXTRA_MESSAGE, "SPORT"); //Optional parameters
                startActivity(intent);


                break;
            case R.id.zwierzeta:
                Intent intentDwa = new Intent(this, Area_game.class);
                intentDwa.putExtra(Area_game.EXTRA_MESSAGE,"ZWIERZĘTA"); //Optional parameters
                startActivity(intentDwa);



                break;
            case R.id.kraj:
                Intent intentTrzy = new Intent(this, Area_game.class);
                intentTrzy.putExtra(Area_game.EXTRA_MESSAGE,"KRAJ"); //Optional parameters
                startActivity(intentTrzy);


                break;
            case R.id.gang:
                Intent intentCztery = new Intent(this, Area_game.class);
                intentCztery.putExtra(Area_game.EXTRA_MESSAGE,"GANG ALBANI"); //Optional parameters
                startActivity(intentCztery);


                break;
            case R.id.dodaj:
                Intent intentDodaj = new Intent(this,MainActivity2Activity.class);
                startActivity(intentDodaj);


                break;
        }

    }
}
