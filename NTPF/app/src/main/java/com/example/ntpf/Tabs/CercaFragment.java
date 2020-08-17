package com.example.ntpf.Tabs;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.ntpf.Login_Register;
import com.example.ntpf.MainActivity;
import com.example.ntpf.R;
import com.example.ntpf.adapter.mascotasAdapter;
import com.example.ntpf.entidades.mascotas;
import com.example.ntpf.mensajesA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CercaFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerMascotas;
    EditText Busacador;
    TextView UserId;
    ArrayList<mascotas> listademascotas;
    Button btn_Buscador, AddChat;
    ProgressDialog progress;

    RequestQueue request;

    JsonObjectRequest jsonObjectRequest;

    public CercaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_cerca, container, false);

        listademascotas=new ArrayList<>();

        UserId=(TextView)vista.findViewById(R.id.txtuserid);
        Busacador=(EditText)vista.findViewById(R.id.buscador);
        btn_Buscador=(Button)vista.findViewById(R.id.btnbuscador);
        recyclerMascotas=(RecyclerView)vista.findViewById(R.id.recycler1);
        recyclerMascotas.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerMascotas.setHasFixedSize(true);



        btn_Buscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Busacador.length()!=0){
                    cargarWebservice();
                }
                if (recyclerMascotas!=null){
                    listademascotas.clear();
                }
                if (Busacador.length()==0){
                    listademascotas.clear();
                    Toast.makeText(getContext(), "Escribe algo en el buscador", Toast.LENGTH_SHORT).show();
                }
            }
        });


        request= Volley.newRequestQueue(getContext());

        return vista;
    }

   private void cargarWebservice(){
        progress=new ProgressDialog(getContext());
        progress.setMessage("Cargando...");
        progress.show();

        //este url permite hacer la busqueda en el edit text
       String url="http://192.168.0.113/webservicesdeproyecto/buscar_razas.php?raza="+Busacador.getText().toString()
               +"&sexo="+Busacador.getText().toString()+"&can_nom="+Busacador.getText().toString();

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(final JSONObject response) {

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
                Mascotas.setUser_id(jsonObject.getString("user_id"));
                listademascotas.add(Mascotas);

            }
            progress.hide();
            mascotasAdapter adapter=new mascotasAdapter(listademascotas);

            //parte del onclickListener
            adapter.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    /*Toast.makeText(getContext(), listademascotas.get(recyclerMascotas.getChildAdapterPosition(view)).getUser_id()+" "+
                            listademascotas.get(recyclerMascotas.getChildAdapterPosition(view)).getCanNom(),
                            Toast.LENGTH_SHORT).show();*/

                    SharedPreferences preferences=view.getContext().getSharedPreferences("userlog", Context.MODE_PRIVATE);
                    String re_code=preferences.getString("user_id",null);
                    String re_nomcan=preferences.getString("nomcan",null);

                    //Toast.makeText(getContext(), Mascotas.getUser_id(), Toast.LENGTH_SHORT).show();
                    String nombre=listademascotas.get(recyclerMascotas.getChildAdapterPosition(view)).getCanNom();
                    String destino=listademascotas.get(recyclerMascotas.getChildAdapterPosition(view)).getUser_id();
                    Intent intent = new Intent(view.getContext(), mensajesA.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("destino", destino);
                    intent.putExtra("origen", re_code);
                    intent.putExtra("nombreUser", re_nomcan);
                    view.getContext().startActivity(intent);


                }
            });

            recyclerMascotas.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


           /* JSONArray json2=response.optJSONArray("userlogin");
            JSONObject jsonObject2=null;
        try {

                jsonObject2=json2.getJSONObject(0);
                Mascotas.setUser_id(jsonObject2.optString("user_id"));
                Mascotas.setCanNom(jsonObject2.optString("can_nom"));
                Mascotas.setDato(jsonObject2.getString("foto"));




            Toast.makeText(getContext(), Mascotas.getUser_id(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), Mascotas.getCanNom(), Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }*/



    }
    @Override
    public void onErrorResponse(VolleyError error) {
        /*Toast.makeText(getContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());*/
        progress.hide();
    }


    public void filtrar(String texto){
        ArrayList<mascotas> filtrarLista = new ArrayList<>();

        for (mascotas Mascotas : listademascotas){
            if(Mascotas.getRaza().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(Mascotas);
            }
        }
        mascotasAdapter adapter=new mascotasAdapter(listademascotas);
        adapter.filtrar(filtrarLista);
    }
}
