package com.example.ntpf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ntpf.ui.gallery.GalleryFragment;

public class Cargando extends Activity {

    ProgressBar p;
    TextView tv;
    int i=0;
    Handler h=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargando);

        p=(ProgressBar)findViewById(R.id.progressBar);
        tv=(TextView)findViewById(R.id.textView2);

        Thread hilo=new Thread(new Runnable() {
            @Override
            public void run() {
                while(i<=100){
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(i+" %");
                            p.setProgress(i);
                        }
                    });
                    try {
                        Thread.sleep(15);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    if(i==100){
                        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                        boolean sesion=preferences.getBoolean("sesion",false);
                        if (sesion){
                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Intent intent = new Intent(Cargando.this, Login_Register.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    i++;
                }
            }
        });
        hilo.start();
    }
}
