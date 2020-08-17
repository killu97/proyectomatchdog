package com.example.ntpf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.example.ntpf.adapter.mascotasAdapter;
import com.example.ntpf.entidades.mascotas;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Login_Register extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    TextView tv_registrar;
    EditText edt_c, edt_p;
    Button btn_i;
    String user, passw;
    public static String nombreUsuario = "";

    RequestQueue request;

    JsonObjectRequest jsonObjectRequest;

    public static  synchronized  String getInstance(){
       return  nombreUsuario;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__register);

        tv_registrar = (TextView) findViewById(R.id.click_registrar);
        edt_c=(EditText) findViewById(R.id.edtcorreo);
        edt_p=(EditText) findViewById(R.id.edtcontrasena);
        btn_i=(Button) findViewById(R.id.btn_iniciar);


        recuperarpreferencias();

        tv_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(getApplicationContext(), registro_usuario.class);
                startActivity(intentReg);
            }
        });

        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=edt_c.getText().toString();
                passw=edt_p.getText().toString();
                if (!user.isEmpty()&&!passw.isEmpty()) {
                    validarusuario("http://192.168.0.113:80/webservicesdeproyecto/login.php");

                    /*SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                    String user=preferences.getString("usuario", null);
                    String pass=preferences.getString("contrasena", null);*/

                    userLog("http://192.168.0.113/webservicesdeproyecto/obtener_datos_usuario_log.php");
                    //datosloguser();
                }else{
                    Toast.makeText(Login_Register.this, "Llene los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void userLog(String URL){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // AQUI ES IMPORTANTE QUE SI USAS POST O GET EN EL SERVIDOR DEBES RECIBIR EL CORRESPONDIENTE $_POST O $_GET O
        // TE DARA PROBLEMAS
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // RECIBE LOS DATOS DEL SERVIDOR EN FORMATO DE STRING EN RESPONSE

                        //Toast.makeText(registro_usuario.this, response, Toast.LENGTH_SHORT).show();

                        try {
                            // CREAMOS UNA VARIABLE LOCAL VACIA
                            String user_id = "";
                            String nomcan = "";

                            // LA RESPUESTA DE STRING LA CONVERTIMOS EN ARREGLO
                            // YA QUE TU RESPUESTA JSON EMPIEZA CON []
                            // SI EMPEZARA CON {} SERIA UN OBJETO
                            JSONArray jsonArray = new JSONArray(response);

                            // YA QUE SE TIENE EL ARRAY LO RECORREMOS PARA LEER LOS DATOS
                            for (int i = 0; i < jsonArray.length(); i++) {
                                // DENTRO DE LOS [] TIENES {} POR TAL MOTIVO ES NECESARIO CREAR UN OBJETO CON ESE INDEX DEL ARRAY
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                // GUARDAMOS EL VALOR DEL CAMPO user_id EN UNA VARIABLE LOCAL PARA PODERLA USAR DESPUES
                                user_id = jsonObject1.getString("user_id");
                                nomcan = jsonObject1.getString("can_nom");
                            }

                            // MOSTRAMOS EL DATO EN EL CONTROL CORRESPONDIENTE
                            Toast.makeText(getApplicationContext(), "Nombre= "+nomcan+" y "+"Code= "+user_id, Toast.LENGTH_LONG).show();

                            Intent intent2= new Intent(Login_Register.this, MainActivity.class);
                            intent2.putExtra("userlog",nomcan);
                            intent2.putExtra("codelog",user_id);
                            startActivity(intent2);
                            finish();




                                SharedPreferences preferences=getSharedPreferences("userlog", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("nomcan",nomcan);
                                editor.putString("user_id",user_id);
                                editor.commit();
                                nombreUsuario = nomcan;


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                // Datos enviados para revisar que exista el usuarion en la base de datos
                Map<String, String> params = new Hashtable<String, String>();
                params.put("correo", user);
                params.put("contrasena", passw);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void validarusuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guardarpreferencias();
                    /*Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();*/
                }else{
                    Toast.makeText(Login_Register.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login_Register.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", user);
                parametros.put("contrasena", passw);
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void guardarpreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();

        editor.putString("usuario",edt_c.getText().toString());
        editor.putString("contrasena",edt_p.getText().toString());
        editor.putBoolean("sesion",true);
        editor.commit();
    }
    private void recuperarpreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        edt_c.setText(preferences.getString("usuario", null));
        edt_p.setText(preferences.getString("contrasena", null));
    }








    private void datosloguser(){

        //este url permite hacer la busqueda en el edit text
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        String myuser=preferences.getString("usuario", null);
        String mypass=preferences.getString("contrasena", null);
        String url2="http://192.168.0.113/webservicesdeproyecto/obtener_datos_usuario_log.php?correo="+myuser+"&contrasena="+mypass;

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url2,null,this,this);
        request.add(jsonObjectRequest);
    }



    @Override
    public void onResponse(JSONObject response) {
        //Toast.makeText(getApplicationContext(), "r="+response, Toast.LENGTH_SHORT).show();

        mascotas Mascotas=null;

        JSONArray json=response.optJSONArray("userlog");
        try {

            String user_id2 = "";
            String nomcan2 = "";

            for (int i=0;i<json.length();i++){
                Mascotas=new mascotas();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);

                nomcan2=jsonObject.getString("can_nom");
                user_id2=jsonObject.getString("user_id");


            }


            Toast.makeText(getApplicationContext(), "Nombre= "+nomcan2+" y "+"Code= "+user_id2, Toast.LENGTH_LONG).show();

            /*Intent intent2= new Intent(Login_Register.this, MainActivity.class);
            intent2.putExtra("userlog",nomcan2);
            intent2.putExtra("codelog",user_id2);
            startActivity(intent2);
            finish();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "No se puede conectar "+error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("ERROR: ", error.toString());
    }
}
