package com.javierpintosettlin.dondeestudio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

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

    public void IrMapa(View view) {
        Intent mapIntent = new Intent(this, MapsActivity.class);
        startActivity(mapIntent);
    }

    public void IrLista(View view) {
        Intent mapIntent = new Intent(this, ListaInstitutosActivity.class);
        startActivity(mapIntent);
    }

    public void mapaInstituciones(View view) {
        // Mapa basado en una Dirección
        // Uri location = Uri.parse("geo:0,0?q=Hipólito+Irigoyen+890,+Villa+del+Rosario,+Córdoba,+Argentina");
        // O un mapa tomado desde las coordenadas GPS
        Uri location = Uri.parse("geo:-31.553936,-63.534822?z=18"); // z es el Zoom
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }
}
