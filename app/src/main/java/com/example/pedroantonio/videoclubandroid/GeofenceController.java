package com.example.pedroantonio.videoclubandroid;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Antonio on 22/11/2016.
 */

public class GeofenceController {
    private final String TAG = GeofenceController.class.getName();

    private Context context;
    private GoogleApiClient googleApiClient;
    private Gson gson;
    private SharedPreferences prefs;

    private List<MostrarVideoclubsCercanos> namedGeofences;
    public List<MostrarVideoclubsCercanos> getNamedGeofences() {
       return namedGeofences;
    }

    private List<MostrarVideoclubsCercanos> namedGeofencesToRemove;

    private Geofence geofenceToAdd;
    private MostrarVideoclubsCercanos namedGeofenceToAdd;

    // Referencia estática para conseguir la instancia
    private static GeofenceController INSTANCE;
    public static GeofenceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeofenceController();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();

        gson = new Gson();
        namedGeofences = new ArrayList<>();
        namedGeofencesToRemove = new ArrayList<>();
        //prefs = this.context.getSharedPreferences(Constants.SharedPrefs.Geofences, Context.MODE_PRIVATE);
    }
}
