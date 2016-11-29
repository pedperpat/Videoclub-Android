package com.example.pedroantonio.videoclubandroid;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class BandasSonorasActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etBuscar;
    TextView tvSonidoEncontrado;
    MediaPlayer mediaPlayer;
    Button btPlay, btStop;

    // Para usarlo en conseguir la API key
    // https://api.soundcloud.com/tracks/"TRACK ID"/stream?client_id="YOUR CLIENT ID"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bandas_sonoras);

        btPlay = (Button)findViewById(R.id.btPlay);
        btStop = (Button)findViewById(R.id.btStop);
        etBuscar = (EditText)findViewById(R.id.etBuscaPelicula);
        tvSonidoEncontrado = (TextView) findViewById(R.id.tvSonidoEncontrado);

        mediaPlayer = new MediaPlayer();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        btPlay.setOnClickListener(this);
        btStop.setOnClickListener(this);

    }

    private void play_mp() {
       try
        {
            mediaPlayer.setDataSource("http://sound.vmuzice.com/download/VfA821jKQ9YVgraN_KztAO6vsv4oP0YdjxaIN-tgim6X29K5D3Ds7Q63X3UPVJMTczo4--Mnbk55BvcqISMd7o6uo6PUsqNj0Iy4QUK9iwpb5nrJcQ1xbl94NdbNGGS0m5YOLWixBoPdUB6qu22KwsCbtEPm_H6qjfGnJZdlNGM/celine_dion_my_heart_will_go_on_ost_titanic_(vmusice.net).mp3");
            mediaPlayer.prepareAsync();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
        catch (IllegalStateException e)
        {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {

            @Override
            public void onPrepared(MediaPlayer mp)
            {
                if (mp == mediaPlayer) {
                    mp.start();
                }
            }
        });

     /*   Thread playThread = new Thread() {
            public void run()
            {
                mediaPlayer = MediaPlayer.create(BandasSonorasActivity.this, R.raw.titanic);
                mediaPlayer.start();
            }
        };
        playThread.start();  */
    }

    private void stop_mp() {
        mediaPlayer.stop();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btPlay:
                play_mp();
                break;
            case R.id.btStop:
                stop_mp();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
