package com.example.ntpf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class mensajesA extends AppCompatActivity {

    private RecyclerView rvMensajes;
    private EditText etNombre;
    private EditText etMensaje;
    private ImageButton btnEnviar;
    private adaptadorRVMensajes adaptadorMensajes;
    private List<mensajes> listaMensajes;
    private String nombre;
    private String origen;
    private String destino;
    private String nombreUser;

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        this.nombre = getIntent().getStringExtra("nombre");
        this.origen = getIntent().getStringExtra("origen");
        this.destino = getIntent().getStringExtra("destino");
        this.nombreUser = getIntent().getStringExtra("nombreUser");
        System.out.println("datos de la pantalla de chat: " + this.nombre + " " + this.origen + " " + this.destino + " " + this.nombreUser);
        this.setComponents();
    }

    private void setComponents(){
        this.rvMensajes = findViewById(R.id.rvMensajes);
        this.etNombre = findViewById(R.id.etNombre);
        this.etMensaje = findViewById(R.id.etMensaje);
        this.btnEnviar = findViewById(R.id.btnEnviar);
        this.listaMensajes = new LinkedList<>();
        this.adaptadorMensajes = new adaptadorRVMensajes(this.listaMensajes);
        this.rvMensajes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMensajes.setAdapter(this.adaptadorMensajes);
        rvMensajes.setHasFixedSize(true);
        this.etNombre.setText(this.nombre);
        FirebaseFirestore.getInstance().collection("chat").
                addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange mDocumentChange: queryDocumentSnapshots.getDocumentChanges()){
                            if(mDocumentChange.getType() == DocumentChange.Type.ADDED){
                                mensajes mensaje = mDocumentChange.getDocument().toObject(mensajes.class);
                                System.out.println("El mensaje es: " + mensaje.toString());
                                System.out.println("codigos: " + getOrigen() + " d: " + getDestino());
                                if((mensaje.getOrigen().equals(getOrigen()) || mensaje.getDestino().equals( getOrigen())) &&
                                        (mensaje.getDestino().equals( getDestino()) || mensaje.getOrigen().equals( getDestino()))){
                                    listaMensajes.add(mensaje);
                                    adaptadorMensajes.notifyDataSetChanged();
                                    rvMensajes.smoothScrollToPosition(listaMensajes.size());
                                }
                            }
                        }
                    }
                });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etMensaje.length() == 0){
                    return;
                }
                mensajes mensaje = new mensajes(getNombreUser(),etMensaje.getText().toString());
                mensaje.setOrigen(getOrigen());
                mensaje.setDestino(getDestino());
                mensaje.setOrigenOr(getDestino());
                mensaje.setDestinoOr(getOrigen());
                FirebaseFirestore.getInstance().collection("chat").add(mensaje);
                etMensaje.setText("");
            }
        });
    }
}
