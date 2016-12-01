package com.example.pedroantonio.videoclubandroid;

import android.app.Fragment;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap googleMap;

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
                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                LatLng luceros = new LatLng(38.34609912392657, -0.4906189441680908);
                LatLng torrent = new LatLng(38.39452453256175, -0.5147534608840942);
                LatLng chaplin = new LatLng(38.40523423890488, -0.5283495783805847);
                LatLng video10 = new LatLng(38.397124794224766, -0.5233392119407654);
                LatLng bigOrange = new LatLng(38.395043778319234, -0.5198845267295837);
                LatLng posicionActual = new LatLng(VideoCercanos.latitude,VideoCercanos.longitude);

                googleMap.addMarker(new MarkerOptions().position(luceros).title("VideoClub Luceros")
                        .snippet("Es el videoclub más famoso de Alicante"));

                googleMap.addMarker(new MarkerOptions().position(video10).title("VideoClub10")
                        .snippet("Un conocido videoclub de SanVicente"));

                googleMap.addMarker(new MarkerOptions().position(bigOrange).title("VideoClub BigOrange")
                        .snippet("Juegos y películas"));

                googleMap.addMarker(new MarkerOptions().position(chaplin).title("VideoClub Chaplin")
                        .snippet("Antíguo videoclub"));

                googleMap.addMarker(new MarkerOptions().position(torrent).title("VideoClub Torrent")
                        .snippet("Videoclub-Cafetería"));

                googleMap.addMarker(new MarkerOptions().position(luceros).title("VideoClub Luceros")
                        .snippet("Es el videoclub más famoso de Alicante"));
                
                CameraPosition cameraPosition = new CameraPosition.Builder().target(posicionActual).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
}