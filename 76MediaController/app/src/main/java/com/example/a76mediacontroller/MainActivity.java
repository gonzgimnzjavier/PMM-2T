package com.example.a76mediacontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;

public class MainActivity extends AppCompatActivity implements MediaController.MediaPlayerControl{

    private MediaController mediaController;
    private  MediaPlayer mediaPlayer;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtener instancias
        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.constraintLayout));
        handler = new Handler();

        //eventos
        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.metodo_para_escapar));
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //muestra los controles y arranca
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //SE MUESTRA EL CONTROLADOR DE MEDIOS
                        //TRAS 20 SEGUNDOS DE INACTIVIDAD SE OCULTA
                        mediaController.show(20000);
                       mediaPlayer.start();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        //pasar el evento al controlador de medios
        mediaController.show();
        return false;
    }
    //CICLO DE VIDA DE MEDIA PLAYER
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }
}