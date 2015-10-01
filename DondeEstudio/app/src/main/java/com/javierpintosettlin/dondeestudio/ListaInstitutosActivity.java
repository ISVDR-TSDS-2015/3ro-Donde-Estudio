package com.javierpintosettlin.dondeestudio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ListaInstitutosActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_institutos);

        // Obtengo la ListView
        final ListView listView = (ListView) findViewById(R.id.listInstitutos);

        // Defino un array para mostrar en la ListView
        final String[] values = new String[] { "Instituto Superior Villa del Rosario, Villa del Rosario, Córdoba",
                "Instituto Inmaculado Corazon de Maria Adoratrices, Villa del Rosario, Córdoba",
                "Universidad Nacional de Villa Maria, Facultad de Veterinaria, Villa del Rosario, Córdoba",
                "Universidad Tecnológica Nacional Facultad Regional Córdoba, Córdoba Capital"
        };

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Asigno el adapter a la ListView
        listView.setAdapter(adapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_institutos, menu);
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
}
