package com.example.pedroantonio.videoclubandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class BandasSonorasActivity extends AppCompatActivity {

    EditText etBuscar;
    TextView tvSonidoEncontrado;
    CheckBox cbSonidoOnOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandas_sonoras);

        etBuscar = (EditText)findViewById(R.id.etBuscaPelicula);
        tvSonidoEncontrado = (TextView) findViewById(R.id.tvSonidoEncontrado);
        cbSonidoOnOff = (CheckBox)findViewById(R.id.cbSonidoOnOff);

        URL seatURL = null;
        try {
            seatURL = new URL("http://freemusicarchive.org/api/get/genres.json?api_key=60BLHNQCAOUFPIBZ&limit=2");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //Return the JSON Response from the API
        BufferedReader br = null;
        try {
            br = new BufferedReader(new
                    InputStreamReader(seatURL.openStream(),
            Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String readAPIResponse = " ";
        StringBuilder jsonString = new StringBuilder();
        try {
            while((readAPIResponse = br.readLine()) != null){
                jsonString.append(readAPIResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObj = new JSONObject(jsonString.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
