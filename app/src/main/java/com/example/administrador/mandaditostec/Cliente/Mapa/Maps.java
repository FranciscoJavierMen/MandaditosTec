package com.example.administrador.mandaditostec.Cliente.Mapa;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.mandaditostec.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.content.ContentValues.TAG;

public class Maps extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float ZOOM = 14f;

    private TextView txtDireccion;

    private FloatingActionButton fabUbicacion, fabSeleccionar;
    private RelativeLayout relativeLayout, info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        txtDireccion = findViewById(R.id.txtDireccionDetalle);
        relativeLayout = findViewById(R.id.relLayout2);
        info = findViewById(R.id.relLayout1);

        getLocationPermission();

        fabUbicacion = findViewById(R.id.fabUbicacion);
        fabSeleccionar = findViewById(R.id.fabSeleccionarUbicacion);

        fabUbicacion.setOnClickListener(this);
    }

    private void detallesUbicacion(LatLng point){
        try{
            Intent regresarUbicacion = new Intent();
            regresarUbicacion.putExtra("punto_seleccionado", point);
            setResult(Activity.RESULT_OK, regresarUbicacion);
            finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Obtener la localización exacta del dispositivo
    private void obtenerUbicacion(){
        Log.d(TAG, "Obtiener localización del dispositivo");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        try{
            if (mLocationPermissionGranted){//verfifica los permisos
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Ubicación actual seleccionada", Toast.LENGTH_SHORT).show();
                            Location currentLocation = (Location)task.getResult();

                            moverCamara(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    ZOOM,
                                    "Mi ubicación");

                        } else {
                            Toast.makeText(getApplicationContext(), "Ubicación nula", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException se){
            Log.e(TAG, "Obtener posición del dispositivo Esception: "+ se.getMessage());

        }
    }

    //Seleccionar la ubicación actual del dispositivo
    private void seleccionarUbicacion(){
        Log.d(TAG, "Obtiener localización del dispositivo");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        try{
            if (mLocationPermissionGranted){//verfifica los permisos
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Ubicación actual seleccionada", Toast.LENGTH_SHORT).show();
                            Location currentLocation = (Location)task.getResult();

                            moverCamara(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    ZOOM,
                                    "Mi ubicación");

                            final LatLng point = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            final MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(point);
                            markerOptions.title("Usar esta ubicación");
                            markerOptions.snippet("Tu ubicación actual");
                            markerOptions.draggable(true);

                            mMap.addMarker(markerOptions);

                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    info.setVisibility(View.GONE);
                                    fabUbicacion.setVisibility(View.GONE);
                                    txtDireccion.setText("Longitud: "+point.longitude+"\nLatitud: "+point.longitude);
                                    fabSeleccionar.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            detallesUbicacion(point);
                                        }
                                    });
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "Ubicación nula", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException se){
            Log.e(TAG, "Obtener posición del dispositivo Esception: "+ se.getMessage());

        }
    }

    //Mueve la cámara al punto ubicado
    private void moverCamara(LatLng latLng, float zoom, String title ){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (!title.equals("Mi ubicación")){
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(markerOptions);
        }
    }

    //Método para comprobar permisos de localizaicón
    private void getLocationPermission(){
        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }
        }else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0){

                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    onMapReady(mMap);

                }
            }
        }
    }


    //Método para selccionar un lugar en el mapa
    private void miUbicacion(){

        final MarkerOptions markerOptions = new MarkerOptions();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng point) {

                markerOptions.position(point);
                markerOptions.title("Usar esta ubicación");
                markerOptions.draggable(true);

                mMap.addMarker(markerOptions);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        info.setVisibility(View.GONE);
                        fabUbicacion.setVisibility(View.GONE);
                        txtDireccion.setText("Longitud: "+point.longitude+"\nLatitud: "+point.longitude);
                        fabSeleccionar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                detallesUbicacion(point);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Toast.makeText(getApplicationContext(), "El mapa estpa listo", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {
            obtenerUbicacion();
            miUbicacion();
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        try{
            switch (view.getId()){
                case R.id.fabUbicacion:
                    seleccionarUbicacion();
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
