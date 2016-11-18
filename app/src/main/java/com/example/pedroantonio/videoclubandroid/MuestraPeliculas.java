package com.example.pedroantonio.videoclubandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.core.deps.guava.reflect.TypeToken;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.R.id.input;

public class MuestraPeliculas extends AppCompatActivity implements View.OnClickListener{

    Button btBuscar;
    TextView tvInformacion, tvPoster, tvGenero, tvFechaSalida, tvTitulo,
            tvAnyo, tvDuracion, tvGuion, tvActores, tvIMDBRating;
    EditText etNombrePelicula;
    WebView portada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_peliculas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        portada = (WebView)findViewById(R.id.webView);
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btPeliculas:

                // Con éste if podemos lanzar el hilo sin necesidad de crear una asynctask.
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }

                // Hilo de la conexión al servicio web y su manipulación y asignación de datos en los componentes de la activity.
                runOnUiThread(new Runnable() {
                    public void run(){
                        try {
                            URL url = new URL("http://www.omdbapi.com/?t="  +
                                    URLEncoder.encode(etNombrePelicula.getText().toString(), "UTF-8"));

                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                                    " (Linux; Android 1.5; es-ES) Ejemplo HTTP");

                            int respuesta = connection.getResponseCode();

                            if (respuesta == HttpURLConnection.HTTP_OK) {

                                InputStream input = url.openStream();

                                // Manipulo el json usando gson y lo transformo en un hashmap para buscar por clave-valor.
                                Map<String, String> map = new Gson().fromJson(new InputStreamReader(
                                        input, "UTF-8"), new TypeToken<Map<String, String>>() {
                                }.getType());

                                // Comprobación de si la película está disponible en el servicio web.
                                if (map.get("Response").toString().equals("False")) {
                                    tvTitulo.setText("No hay datos acerca de la película: '" + etNombrePelicula.getText().toString() + "'");
                                    portada.setVisibility(View.INVISIBLE);
                                } else { // En caso de estar disponible, fijo los datos a los correspondientes textviews.
                                    tvTitulo.setText("Titulo: ");
                                    tvAnyo.setText("Año: ");
                                    tvFechaSalida.setText("Fecha de salida: ");
                                    tvDuracion.setText("Duración: ");
                                    tvPoster.setText("Imagen de la película: ");
                                    tvGenero.setText("Género: ");
                                    tvActores.setText("Actores: ");
                                    tvGuion.setText("Guión: ");
                                    tvIMDBRating.setText("Puntuación IMDB: ");
                                    tvTitulo.append(map.get("Title").toString());
                                    tvAnyo.append(map.get("Year").toString());
                                    tvFechaSalida.append(map.get("Released").toString());
                                    tvDuracion.append(map.get("Runtime").toString());
                                    tvGenero.append(map.get("Genre").toString());
                                    tvActores.append(map.get("Actors").toString());
                                    tvGuion.append(map.get("Plot").toString());
                                    tvIMDBRating.append(map.get("imdbRating").toString());

                                    // Compruebo que la película tenga una portada disponible en la base de datos
                                    if (map.get("Poster").toString().equals("N/A")) {
                                        tvPoster.append("No hay portada disponible");
                                        portada.setVisibility(View.INVISIBLE);
                                    } else {
                                        portada.getSettings().setJavaScriptEnabled(true);
                                        portada.setVisibility(View.VISIBLE);
                                        portada.loadUrl(map.get("Poster").toString());
                                    }
                                }
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
}