package com.example.pedroantonio.videoclubandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import android.os.Vibrator;

public class MuestraPeliculas extends AppCompatActivity implements View.OnClickListener{
    Button btBuscar;
    TextView tvInformacion, tvPoster, tvGenero, tvFechaSalida, tvTitulo, tvAnyo, tvDuracion, tvGuion,
            tvActores, tvIMDBRating;
    EditText etNombrePelicula;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_peliculas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Busca una pelicula");
        setSupportActionBar(toolbar);

        imageView = (ImageView)findViewById(R.id.imageView);
        tvPoster = (TextView)findViewById(R.id.tvPoster);
        tvGenero = (TextView)findViewById(R.id.tvGenero);
        tvFechaSalida = (TextView) findViewById(R.id.tvFechaSalida);
        tvTitulo = (TextView)findViewById(R.id.tvTitulo);
        tvAnyo = (TextView)findViewById(R.id.tvAnyo);
        tvDuracion = (TextView)findViewById(R.id.tvDuracion);
        tvGuion = (TextView)findViewById(R.id.tvGuion);
        tvActores = (TextView)findViewById(R.id.tvActores);
        tvIMDBRating = (TextView)findViewById(R.id.tvIMDBRating);
        tvInformacion = (TextView)findViewById(R.id.tvTitulo);
        btBuscar = (Button)findViewById(R.id.btPeliculas);
        etNombrePelicula = (EditText)findViewById(R.id.etNameOfMovie);
        etNombrePelicula.setOnClickListener(this);
        btBuscar.setOnClickListener(this);

        imageView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPeliculas:
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                // Hilo de la conexión al servicio web y su manipulación y asignación de datos en los componentes de la activity.
                runOnUiThread(
                        new Runnable() {
                    public void run(){
                        try {
                            URL url = new URL("http://www.omdbapi.com/?t="  +
                                    URLEncoder.encode(etNombrePelicula.getText().toString(), "UTF-8"));

                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                            int respuesta = connection.getResponseCode();

                            if (respuesta == HttpURLConnection.HTTP_OK) {
                                final Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Limpio todos los datos que tenían los textviews.
                                tvTitulo.setText("");
                                tvAnyo.setText("");
                                tvFechaSalida.setText("");
                                tvDuracion.setText("");
                                tvPoster.setText("");
                                tvGenero.setText("");
                                tvActores.setText("");
                                tvGuion.setText("");
                                tvIMDBRating.setText("");

                                InputStream input = url.openStream();

                                // Manipulo el json usando gson y lo transformo en un hashmap para buscar por clave-valor.
                                Map<String, String> map = new Gson().fromJson(new InputStreamReader(
                                        input, "UTF-8"), new TypeToken<Map<String, String>>() {
                                }.getType());

                                // Comprobación de si la película está disponible en el servicio web.
                                // Si la película está disponible vibrará 1 vez, si no está disponible vibrará 2 veces.
                                if (map.get("Response").toString().equals("False")) {
                                    tvTitulo.setText("No hay datos acerca de la película: '" + etNombrePelicula.getText().toString() + "'");
                                    imageView.setVisibility(View.INVISIBLE);
                                    final long[] pattern = {300,300};
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            for(int i = 0; i < 2; i++){
                                                v.vibrate(pattern, -1);
                                                try {
                                                    Thread.sleep(600);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }.start();
                                } else { // En caso de estar disponible, fijo los datos a los correspondientes textviews.
                                    tvTitulo.setText("Titulo: " + map.get("Title").toString());
                                    tvAnyo.setText("Año: " + map.get("Year").toString());
                                    tvFechaSalida.setText("Fecha de salida: " + map.get("Released").toString());
                                    tvDuracion.setText("Duración: " + map.get("Runtime").toString());
                                    tvPoster.setText("Imagen de la película: ");
                                    tvGenero.setText("Género: " + map.get("Genre").toString());
                                    tvActores.setText("Actores: " + map.get("Actors").toString());
                                    tvGuion.setText("Guión: " + map.get("Plot").toString());
                                    tvIMDBRating.setText("Puntuación IMDB: " + map.get("imdbRating").toString());

                                    // Compruebo que la película tenga una portada disponible en la base de datos
                                    if (map.get("Poster").toString().equals("N/A")) {
                                        tvPoster.append("No hay portada disponible");
                                        imageView.setVisibility(View.INVISIBLE);
                                    } else {
                                        imageView.setVisibility(View.VISIBLE);
                                        imageView.setImageBitmap(getBitmapFromURL(map.get("Poster").toString()));
                                    }
                                    try
                                    {
                                        OutputStreamWriter fout=
                                                new OutputStreamWriter(
                                                        openFileOutput("historial.txt", Context.MODE_APPEND));
                                        fout.append(map.get("Title") + "|" + map.get("Year") + "|"
                                                + map.get("Released") + "|" + map.get("Runtime") + "|"
                                                + map.get("Genre") + "|" + map.get("Poster") + "|");
                                        fout.write("\n");
                                        fout.close();
                                    }
                                    catch (Exception ex)
                                    {
                                    }
                                }
                                v.vibrate(300);
                            }
                        } catch (JsonIOException | JsonSyntaxException | IOException e){
                        }
                   }
                });
                break;
            case R.id.etNameOfMovie:
                etNombrePelicula.setText("");
                break;
            default:
                break;
        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}