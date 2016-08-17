package br.com.projetomapateste.activity;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap googleMap;

    private LocationManager locationManager;

    //private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1001);
            }

        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
        }

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap gm) {

        googleMap = gm;

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        LatLng novaIguacu = new LatLng(-22.7598, -43.4516);
        MarkerOptions m = new MarkerOptions();
        googleMap.addMarker(new MarkerOptions().position(novaIguacu).title("Nova Iguaçu"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(novaIguacu));
    }


    @Override
    public void onLocationChanged(Location location) {

        googleMap.clear();
        LatLng posicao = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(posicao).title("EU"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1001) {

            if (resultCode == Activity.RESULT_CANCELED) {

                Toast.makeText(this, "Impossível Utilizar a Funcionalidade", Toast.LENGTH_SHORT).show();
            } else {

                if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Impossível Utilizar a Funcionalidade pois você não deu permissão", Toast.LENGTH_SHORT).show();
                } else {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
                }

            }
        }
    }
}
