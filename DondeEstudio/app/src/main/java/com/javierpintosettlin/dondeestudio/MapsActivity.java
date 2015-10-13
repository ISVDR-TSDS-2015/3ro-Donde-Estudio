package com.javierpintosettlin.dondeestudio;

import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private long idCategoria = -1;
    private long idCarrera = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        idCategoria = getIntent().getLongExtra("idCategoria", -1);
        idCarrera = getIntent().getLongExtra("idCarrera", -1);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        ManejadorBase db = new ManejadorBase(this);
        List<Institucion> listInstituciones = db.getInstituciones(idCategoria, idCarrera);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i<listInstituciones.size()-1; i++)
        {
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(listInstituciones.get(i).getGeoLatitud()),
                    Double.parseDouble(listInstituciones.get(i).getGeoLongitud())))
                    .title(listInstituciones.get(i).getNombreInstitucion()));

            builder.include(new LatLng(Double.parseDouble(listInstituciones.get(i).getGeoLatitud()),
                    Double.parseDouble(listInstituciones.get(i).getGeoLongitud())));
        }

//        mMap.addMarker(new MarkerOptions().position(new LatLng(-31.553984, -63.534776)).title("Instituto Superior Villa del Rosario"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(-31.553649, -63.540560)).title("UNVM - Veterinaria"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(-31.551544, -63.538895)).title("ICMA - Magisterio"));
//        builder.include(new LatLng(-31.553984, -63.534776));
//        builder.include(new LatLng(-31.553649, -63.540560));
//        builder.include(new LatLng(-31.551544, -63.538895));

        LatLngBounds bounds = builder.build();
        int padding = 30; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300, 400, padding);
        mMap.moveCamera(cu);
    }
}
