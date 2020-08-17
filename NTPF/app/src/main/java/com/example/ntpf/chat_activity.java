package com.example.ntpf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class chat_activity extends AppCompatActivity {
    List<Chat> listaChats;
    private RecyclerView rvChats;
    private adaptadorRVChats adaptadorChats;
    private String codigoOrigen;
    private long backpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);
        SharedPreferences preferences=getSharedPreferences("userlog", Context.MODE_PRIVATE);
        String re_code=preferences.getString("user_id",null);
        this.codigoOrigen = re_code;//reemplazar
        this.setComponents();
    }

    private void setComponents(){
        this.listaChats = new LinkedList<>();
        this.rvChats = findViewById(R.id.rvChats);
        this.adaptadorChats = new adaptadorRVChats(this.listaChats);
        this.rvChats.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rvChats.setAdapter(this.adaptadorChats);
        this.rvChats.setHasFixedSize(true);

        FirebaseFirestore.getInstance().collection("chat").whereEqualTo("destino", this.codigoOrigen).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<Chat> listatmp = new LinkedList<>();
                for (DocumentChange mDocumentChange: queryDocumentSnapshots.getDocumentChanges()){
                    if(mDocumentChange.getType() == DocumentChange.Type.ADDED){
                        listatmp.add(mDocumentChange.getDocument().toObject(Chat.class));
                    }
                }
                for(int i =0; i < listatmp.size(); i++){

                    if(listatmp.get(i).getOrigen() != codigoOrigen && !listaChats.contains(listatmp.get(i))){
                        listaChats.add(listatmp.get(i));
                    }
                }
                adaptadorChats.notifyDataSetChanged();
                rvChats.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        backpress=System.currentTimeMillis();
        if (backpress +2000>System.currentTimeMillis()){
            Intent intent = new Intent(chat_activity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }
}
