package com.example.pedroantonio.videoclubandroid;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap googleMap;
    MarkerOptions torrent;
    MarkerOptions video10;
    MarkerOptions bigOrange;
    MarkerOptions luceros;
    MarkerOptions chaplin;
    private Circle geoFenceLimits;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                setUpMap();
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void setUpMap() {
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMyLocationEnabled(true);

        torrent = new MarkerOptions().position(new LatLng(38.39452453256175, -0.5147534608840942))
                .title("VideoClub Torrent").snippet("Videoclub-Cafetería");
        googleMap.addMarker(torrent);
        bigOrange = new MarkerOptions().position(new LatLng(38.395043778319234, -0.5198845267295837))
                .title("Videoclub bigOrange").snippet("Juegos y películas");
        googleMap.addMarker(bigOrange);
        video10 = new MarkerOptions().position(new LatLng(38.397124794224766, -0.5233392119407654))
                .title("VideoClub10").snippet("Conocido videoclub");
        googleMap.addMarker(video10);
        chaplin = new MarkerOptions().position(new LatLng(38.40523423890488, -0.5283495783805847))
                .title("VideoClub Chaplin").snippet("Antíguo videoclub");
        googleMap.addMarker(chaplin);
        luceros = new MarkerOptions().position(new LatLng(38.34609912392657, -0.4906189441680908))
                .title("Videoclub luceros").snippet("El mejor de Alicante");
        googleMap.addMarker(luceros);

        // Dibuja un círculo que simboliza el radio de la geofence en cada marca del mapa.
        if (geoFenceLimits != null)
            geoFenceLimits.remove();
        CircleOptions circleTorrent = new CircleOptions()
                .center(torrent.getPosition())
                .strokeColor(Color.argb(40, 255, 0, 0))
                .fillColor(Color.argb(90, 255, 0, 0))
                .radius(100);
        geoFenceLimits = googleMap.addCircle(circleTorrent);

        CircleOptions circleChaplin = new CircleOptions()
                .center(chaplin.getPosition())
                .strokeColor(Color.argb(40, 255, 0, 0))
                .fillColor(Color.argb(90, 255, 0, 0))
                .radius(100);
        geoFenceLimits = googleMap.addCircle(circleChaplin);

        CircleOptions circleVideo10 = new CircleOptions()
                .center(video10.getPosition())
                .strokeColor(Color.argb(40, 255, 0, 0))
                .fillColor(Color.argb(90, 255, 0, 0))
                .radius(100);
        geoFenceLimits = googleMap.addCircle(circleVideo10);

        CircleOptions circleBO = new CircleOptions()
                .center(bigOrange.getPosition())
                .strokeColor(Color.argb(40, 255, 0, 0))
                .fillColor(Color.argb(90, 255, 0, 0))
                .radius(100);
        geoFenceLimits = googleMap.addCircle(circleBO);

        CircleOptions circleLuceros = new CircleOptions()
                .center(luceros.getPosition())
                .strokeColor(Color.argb(40, 255, 0, 0))
                .fillColor(Color.argb(90, 255, 0, 0))
                .radius(100);
        geoFenceLimits = googleMap.addCircle(circleLuceros);

        LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location myLocation = locationManager.getLastKnownLocation(provider);
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));;
    }
}