package com.javierpintosettlin.dondeestudio;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private static String SOAP_ACTION = "http://tempuri.org/GetAllCarreras";
    private static String NAMESPACE = "http://tempuri.org/";
    private static String METHOD_NAME = "GetAllCarrerasResponse";
    private static String URL = "http://192.168.2.13/DondeEstudio/DondeEstudioWS.asmx?op=GetAllCarreras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TareaAccesoSOAP tareaAccesoSOAP = new TareaAccesoSOAP();
        tareaAccesoSOAP.execute();
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

    public class TareaAccesoSOAP extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            //si quieren abrir un Dialog informando el progreso, va aqui
            TextView txtWebServices = (TextView) findViewById(R.id.txtError);
            txtWebServices.setText("Sincronizando...");
        }

        @Override
        protected String doInBackground(String... urls) {

            //Inicializo soap request
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //Declaro la version del SOAP request
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;

            String resultado_string = "";

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                //Se llama al webservice
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapObject resultado_object =(SoapObject)envelope.getResponse();

                resultado_string = "¡¡¡Sincronización Completa!!!";

                List<CarrerasWS> listCarreras = RecibirDeSOAP(resultado_object);

                ManejadorBase db = new ManejadorBase(getApplicationContext());
                for(int i=0; i < listCarreras.size(); i++)
                {
                    db.addCarrera(listCarreras.get(i));
                    Log.d("Carrera: ", listCarreras.get(i).getNombreInstitucion() + ": " + listCarreras.get(i).getNombreCarrera());
                }

                //mostrarListaEnListView(listKiosco);

                return resultado_string;

            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Spinner spinnerCategorias = (Spinner)findViewById(R.id.spinnerCategorias);

            ManejadorBase db = new ManejadorBase(getApplicationContext());

            //Creamos el cursor
            Cursor c = db.getCursorAllCategorias();
            //Creamos el adaptador
            SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(getApplicationContext(),
                    android.R.layout.simple_spinner_item,
                    c,
                    new String[] {"nombre_categoria"},
                    new int[] {android.R.id.text1});
            //Añadimos el layout para el menú
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            //Le indicamos al spinner el adaptador a usar
            spinnerCategorias.setAdapter(adapter2);


            TextView txtWebServices = (TextView) findViewById(R.id.txtError);
            txtWebServices.setText(result);
        }
    }

    public List<CarrerasWS> RecibirDeSOAP(SoapObject soap) {
        List<CarrerasWS> listCarrera = new ArrayList<CarrerasWS>();

        for (int i=0; i < soap.getPropertyCount(); i++)
        {
            SoapObject carreraSOAP = (SoapObject)soap.getProperty(i);
            CarrerasWS carreraWS = new CarrerasWS();

            carreraWS.setIdCarrera(Integer.parseInt(carreraSOAP.getProperty(0).toString()));
            carreraWS.setNombreCarrera(carreraSOAP.getProperty(1).toString());
            carreraWS.setIdCategoria(Integer.parseInt(carreraSOAP.getProperty(2).toString()));
            carreraWS.setNombreCategoria(carreraSOAP.getProperty(3).toString());
            carreraWS.setIdInstitucion(Integer.parseInt(carreraSOAP.getProperty(4).toString()));
            carreraWS.setNombreInstitucion(carreraSOAP.getProperty(5).toString());
            carreraWS.setGeolatitud(carreraSOAP.getProperty(6).toString());
            carreraWS.setGeolongitud(carreraSOAP.getProperty(7).toString());

/*
<CarrerasWS>
          <Id_Carrera>int</Id_Carrera>
          <Descripcion_Carrera>string</Descripcion_Carrera>
          <Id_Categoria>int</Id_Categoria>
          <Descripcion_Categoria>string</Descripcion_Categoria>
          <Id_Institucion>int</Id_Institucion>
          <Descripcion_Institucion>string</Descripcion_Institucion>
          <Geolatitud>string</Geolatitud>
          <Geolongitud>string</Geolongitud>
        </CarrerasWS>
  */

            listCarrera.add(carreraWS);
        }

        return listCarrera;
    }
}
