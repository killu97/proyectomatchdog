package com.example.ntpf;

/*import androidx.appcompat.app.AlertDialog;*/
import androidx.appcompat.app.AppCompatActivity;

/*import android.content.Intent;*/
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/*import org.json.JSONException;*/
/*import org.json.JSONObject;*/

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText nom, edad, email, password, numcel, cannom, canedad, raza, color;
    Button registrar;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //info personal
        nom= (EditText) findViewById(R.id.edt_nom);
        edad= (EditText) findViewById(R.id.edt_edad);
        email= (EditText) findViewById(R.id.edt_email);
        password= (EditText) findViewById(R.id.edt_password);
        numcel= (EditText) findViewById(R.id.edt_numcel);

        //info mascota
        cannom= (EditText) findViewById(R.id.edt_cannom);
        canedad= (EditText) findViewById(R.id.edt_canedad);
        raza= (EditText) findViewById(R.id.edt_raza);
        color= (EditText) findViewById(R.id.edt_color);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejecutarservicio("http://192.168.0.102:80/webservicesdeproyecto/conexion.php");
            }
        });
    }

    private void ejecutarservicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                //info gente
                parametros.put("nombre", nom.getText().toString());
                parametros.put("edad", edad.getText().toString());
                parametros.put("correo", email.getText().toString());
                parametros.put("contrasena", password.getText().toString());
                parametros.put("num_celular", numcel.getText().toString());
                //info can
                parametros.put("can_nom", cannom.getText().toString());
                parametros.put("can_edad", canedad.getText().toString());
                parametros.put("raza", raza.getText().toString());
                parametros.put("color", color.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
