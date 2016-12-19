package com.example.pedroantonio.videoclubandroid;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Historial extends AppCompatActivity {
    List<PeliculasBuscadas> peliculas = new ArrayList<>();
    PeliculasBuscadas peliculaGuardada;
    MuestraPeliculas muestraPeliculas = new MuestraPeliculas();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    openFileInput("historial.txt")));
            String texto = fin.readLine();

            while (texto != null) {
                String[] elementos = texto.split("\\|");
                peliculaGuardada = new PeliculasBuscadas(elementos[3], elementos[1], elementos[2],
                        elementos[4], elementos[0], elementos[5]);
                if(!peliculas.contains(peliculaGuardada)) {
                    peliculas.add(peliculaGuardada);
                }
                texto = fin.readLine();
            }
            fin.close();
        } catch (Exception ex) {
        }
        final LinearLayout lm = (LinearLayout) findViewById(R.id.linear);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        for (int i = 0; i < peliculas.size(); i++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView tvSeparador1 = new TextView(this);
            tvSeparador1.setText("");
            layout.addView(tvSeparador1);
            /////////////////////////////////////////// Imagen con la portada de la película
            ImageView image = new ImageView(getBaseContext());
            image.setImageBitmap(muestraPeliculas.getBitmapFromURL(peliculas.get(i).getPortada()));
            layout.addView(image);
            /////////////////////////////////////////// Boton de título
            Button btTituloDinamico = new Button(this);
            btTituloDinamico.setText(peliculas.get(i).getTitulo());
            btTituloDinamico.setEnabled(false);
            layout.addView(btTituloDinamico);
            /////////////////////////////////////////// Botón de año
            Button btAnyo = new Button(this);
            btAnyo.setText(peliculas.get(i).getAnyo());
            btAnyo.setEnabled(false);
            layout.addView(btAnyo);
            /////////////////////////////////////////// Textview con la información
            TextView tvInfo = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvInfo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            tvInfo.setText("Información acerca de la película: \n" + "Fecha de salida: "
                    + peliculas.get(i).getFechaSalida() + "\n" + " Duración: "
                    + peliculas.get(i).getDuración() + "\n" + " Género: "
                    + peliculas.get(i).getGenero());
            layout.addView(tvInfo);
            TextView tvSeparador2 = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvSeparador2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            tvSeparador2.setText("------------------------------------------------------------");
            layout.addView(tvSeparador2);
            lm.addView(layout);
        }
    }
}