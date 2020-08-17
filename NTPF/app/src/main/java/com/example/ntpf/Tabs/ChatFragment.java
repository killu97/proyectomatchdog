package com.example.ntpf.Tabs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ntpf.Chat;
import com.example.ntpf.R;
import com.example.ntpf.adapter.mascotasAdapter;
import com.example.ntpf.chat_activity;
import com.example.ntpf.entidades.mascotas;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    List<Chat> listaChats;
    ArrayList<mascotas> listademascotas;
    RecyclerView recyclerchats;
    mascotas Mascotas, txtmascotaid;
    RequestQueue request;
    Bundle datos;

    JsonObjectRequest jsonObjectRequest;

    public ChatFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.listaChats = new LinkedList<>();
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerchats = (RecyclerView) vista.findViewById(R.id.recyclerchats);
        recyclerchats.setLayoutManager(new LinearLayoutManager(this.getContext()));

        /*Intent intent = new Intent(vista.getContext(), chat_activity.class);
        vista.getContext().startActivity(intent);*/

        return vista;
    }

    /*private void addChat(){
        //String url="http://192.168.0.112/webservicesdeproyecto/buscar_razas.php?raza="+Busacador.getText().toString();
        String url="http://192.168.0.112/webservicesdeproyecto/add_mascota_chat.php?user_id="+txtcode2.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }*/

    @Override
    public void onResponse(JSONObject response) {

        mascotas Mascotas=null;

        JSONArray json=response.optJSONArray("mascotas");
        try {
            for (int i=0;i<json.length();i++){
                Mascotas=new mascotas();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                Mascotas.setCanNom(jsonObject.getString("can_nom"));
                Mascotas.setRaza(jsonObject.getString("raza"));
                Mascotas.setSexo(jsonObject.getString("sexo"));
                Mascotas.setCanEdad(jsonObject.getString("can_edad"));
                Mascotas.setDato(jsonObject.getString("foto"));
                //Mascotas.setUser_id(jsonObject.getString("user_id"));
                listademascotas.add(Mascotas);

            }
            mascotasAdapter adapter=new mascotasAdapter(listademascotas);
            recyclerchats.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
    }
}
