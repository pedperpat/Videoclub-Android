package com.example.pedroantonio.videoclubandroid;

/**
 * Created by Pedro Antonio on 12/12/2016.
 */


import android.net.Uri;
import com.google.android.gms.location.Geofence;

/**
 * Constants used in companion app.
 */
public final class Constants {

    private Constants() {
    }

    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.NEVER_EXPIRE;

    // Chaplin
    public static final String CHAPLIN_ID = "1";
    public static final double CHAPLIN_LATITUDE = 38.40523423890488;
    public static final double CHAPLIN_LONGITUDE = -0.5283495783805847;
    public static final float CHAPLIN_RADIUS_METERS = 100.0f;

    // Big orange
    public static final String BIGORANGE_ID = "2";
    public static final double BIGORANGE_LATITUDE = 38.395043778319234;
    public static final double BIGORANGE_LONGITUDE = -0.5198845267295837;
    public static final float BIGORANGE_RADIUS_METERS = 100.0f;
}