package com.example.ntpf.ui.tools;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ntpf.Cargando;
import com.example.ntpf.Login_Register;
import com.example.ntpf.MainActivity;
import com.example.ntpf.R;
import com.example.ntpf.chat_activity;
import com.example.ntpf.registro_usuario;
import com.example.ntpf.ui.gallery.GalleryViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

public class ToolsFragment extends Fragment {

    ProgressBar p;
    TextView tv;
    int i=0;
    Handler h=new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View root = inflater.inflate(R.layout.activity_main, container, false);
        View root = inflater.inflate(R.layout.activity_cargando, container, false);

        p=(ProgressBar)root.findViewById(R.id.progressBar);
        tv=(TextView)root.findViewById(R.id.textView2);

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
                        Thread.sleep(7);
                    } catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    if(i==100){
                        SharedPreferences preferences=getActivity().getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                        boolean sesion=preferences.getBoolean("sesion",false);
                        if (sesion){
                            Intent intent=new Intent(getContext(), registro_usuario.class);
                            startActivity(intent);
                            getActivity().finish();
                        }else {
                            Intent intent = new Intent(getContext(), Login_Register.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }
                    i++;
                }
            }
        });
        hilo.start();
        return root;
    }

    }