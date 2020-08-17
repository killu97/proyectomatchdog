package com.example.ntpf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class registro_usuario extends AppCompatActivity {
    EditText nom, edad, email, password, numcel, cannom, canedad, raza, color, user_c, password_c;
    TextView sex, txt_registro, txtv7, txt_userid, txtv6;
    Spinner s_sexo;
    Button registrar, botonCargar, b_actualizar,btn_code;
    //camara
    Bitmap bitmap;
    ImageView imagen;
    String path;
    private final String CARPETA_RAIZ="misImagenesPrueba/";
    private final String RUTA_IMAGEN=CARPETA_RAIZ+"misFotos";
    private long backpress;

    final int COD_SELECCIONA=10;
    final int COD_FOTO=20;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //info personal
        nom= (EditText) findViewById(R.id.edt_nom);
        edad= (EditText) findViewById(R.id.edt_edad);
        email= (EditText) findViewById(R.id.edt_email);
        password= (EditText) findViewById(R.id.edt_password);
        numcel= (EditText) findViewById(R.id.edt_numcel);
        user_c=(EditText) findViewById(R.id.edt_emailc);
        password_c=(EditText) findViewById(R.id.edt_passwordc);
        btn_code=(Button) findViewById(R.id.btncode);
        txt_userid=(TextView) findViewById(R.id.txtuserid);

        //info mascota
        cannom= (EditText) findViewById(R.id.edt_cannom);
        canedad= (EditText) findViewById(R.id.edt_canedad);
        raza= (EditText) findViewById(R.id.edt_raza);
        color= (EditText) findViewById(R.id.edt_color);
        s_sexo= (Spinner) findViewById(R.id.sp_sexo);
        sex=(TextView) findViewById(R.id.txt_sexo);
        imagen=(ImageView)findViewById(R.id.imagenId);
        botonCargar=(Button)findViewById(R.id.btn_escoger_imagen);
        b_actualizar=findViewById(R.id.btn_actualizar);
        registrar=(Button) findViewById(R.id.btn_registrar);
        txt_registro=(TextView) findViewById(R.id.textView8);
        txtv7=(TextView) findViewById(R.id.textView7);
        txtv6=(TextView) findViewById(R.id.textView6);

        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscar_userid("http://192.168.0.113:80/webservicesdeproyecto/buscar_id.php");
            }
        });

        b_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarservicio("http://192.168.0.113:80/webservicesdeproyecto/actualizar_datos.php");
                Toast.makeText(getApplicationContext(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent=new Intent(getApplicationContext(), Login_Register.class);
                startActivity(intent);
                finish();

            }
        });

        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        if(preferences.getString("usuario", null) == null){
            b_actualizar.setVisibility(View.INVISIBLE);
            registrar.setVisibility(View.VISIBLE);
            txt_registro.setVisibility(View.VISIBLE);
            txtv7.setVisibility(View.INVISIBLE);
            txtv6.setVisibility(View.INVISIBLE);
            user_c.setVisibility(View.INVISIBLE);
            password_c.setVisibility(View.INVISIBLE);
            txt_userid.setVisibility(View.INVISIBLE);
            btn_code.setVisibility(View.INVISIBLE);
        }else if(preferences.getString("usuario", null)!= null){
            b_actualizar.setVisibility(View.VISIBLE);
            txtv7.setVisibility(View.VISIBLE);
            txtv6.setVisibility(View.VISIBLE);
            user_c.setVisibility(View.VISIBLE);
            password_c.setVisibility(View.VISIBLE);
            registrar.setVisibility(View.INVISIBLE);
            txt_registro.setVisibility(View.INVISIBLE);
            txt_userid.setVisibility(View.VISIBLE);
            btn_code.setVisibility(View.VISIBLE);

        }


        /*b_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent=new Intent(getApplicationContext(), Login_Register.class);
                startActivity(intent);
                finish();
            }
        });*/

        if(validaPermisos()){
            botonCargar.setEnabled(true);
        }else {
            botonCargar.setEnabled(false);
        }

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.sexo_can, android.R.layout.simple_spinner_item);
        s_sexo.setAdapter(adapter);
        s_sexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Drawable imagen_a=imagen.getDrawable();
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((nom.length()==0)||(edad.length()==0)||(email.length()==0)||(password.length()==0)||(numcel.length()==0)
                        ||(cannom.length()==0)||(canedad.length()==0)||(raza.length()==0)||(color.length()==0)||(sex.length()==0)||(imagen.getDrawable()==imagen_a))

                {
                    Toast.makeText(getApplicationContext(), "llene todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    ejecutarservicio("http://192.168.0.113:80/webservicesdeproyecto/pruebaderegistroimagen.php");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        backpress=System.currentTimeMillis();
        if (backpress +2000>System.currentTimeMillis()){
            Intent intent = new Intent(registro_usuario.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            super.onBackPressed();
        }
    }


    public boolean validaPermisos(){

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }
        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)&&
                (checkSelfPermission(WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA))||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacioon();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&
                    grantResults[1]==PackageManager.PERMISSION_GRANTED){
                botonCargar.setEnabled(true);
            }else {
                solicitarpermisosmanual();
            }
        }
    }

    private void solicitarpermisosmanual() {
        final CharSequence [] opciones={"Sí", "No"};
        final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(registro_usuario.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Sí")){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package", getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private void cargarDialogoRecomendacioon() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(registro_usuario.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la aplicación");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    public void oneClick(View view) {
        cargarimagen();
    }

    private void cargarimagen() {

        final CharSequence [] opciones={"Tomar foto", "Cargar imagen", "Cancelar"};
        final AlertDialog.Builder alertOpciones= new AlertDialog.Builder(registro_usuario.this);
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Tomar foto")){
                    tomarfoto();
                }else{
                    if(opciones[i].equals("Cargar imagen")){
                        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplcación"),COD_SELECCIONA);
                    }else{
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();
    }

    private void tomarfoto(){
        File fileImagen=new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada=fileImagen.exists();
        String nombreimagen="";
        if(isCreada==false){
            isCreada=fileImagen.mkdirs();
        }
        if(isCreada==true){
            nombreimagen = (System.currentTimeMillis()/1000)+".jpg";
        }

        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreimagen;

        File imagen=new File (path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            String authorities=getApplicationContext().getPackageName()+".provider";
            Uri imageUri= FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }else{
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode) {
                case COD_SELECCIONA:
                    Uri mipath = data.getData();
                    imagen.setImageURI(mipath);
                    try {
                        bitmap=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),mipath);
                        imagen.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String s, Uri uri) {
                            Log.i("Ruta de almacenamiento","path: "+path);
                        }
                    });

                    bitmap= BitmapFactory.decodeFile(path);
                    imagen.setImageBitmap(bitmap);
                    break;
            }
            bitmap=redimensonartamano(bitmap,600,800);
        }
    }

    private Bitmap redimensonartamano(Bitmap bitmap, float anchonuevo, float altonuevo) {
        int ancho=bitmap.getWidth();
        int alto=bitmap.getHeight();

        if(ancho>anchonuevo||alto>altonuevo){
            float escalaancho=anchonuevo/ancho;
            float escalaalto=altonuevo/alto;

            Matrix matrix=new Matrix();
            //matrix.setRotate(90);
            matrix.postScale(escalaancho,escalaalto);
            return Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);
        }else {
            return bitmap;
        }
    }

    /*private Bitmap rotarimagen(Bitmap bitmap){
        ExifInterface exifInterface = null;
        try {
            ExifInterface exifInterface1 = new ExifInterface(RUTA_IMAGEN);
        }catch (IOException e){
            e.printStackTrace();
        }
        int orientacion= exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        Matrix matrix = new Matrix();
        switch (orientacion){
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
                default:
        }
        Bitmap rotarBitmap= Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        //imagen.setImageBitmap(rotarBitmap);
        return rotarimagen(bitmap);
    }*/


    public void ejecutarservicio(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    /*Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(registro_usuario.this, Login_Register.class);
                    startActivity(intent);*/
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
                String imagenc = convertirImgString(bitmap);

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
                parametros.put("sexo", sex.getText().toString());
                parametros.put("foto", imagenc);
                parametros.put("user_id", txt_userid.getText().toString());

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void buscar_userid(String URL){
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
                                String nombre = "";
                                String edadh = "";
                                String correoh = "";
                                String contrasenah = "";
                                String num_celh = "";

                                String nomcan = "";
                                String edadcan = "";
                                String razacan = "";
                                String colorcan = "";
                                String sexcan = "";
                                String fotocan = "";

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

                                    nombre = jsonObject1.getString("nombre");
                                    edadh = jsonObject1.getString("edad");
                                    correoh = jsonObject1.getString("correo");
                                    contrasenah = jsonObject1.getString("contrasena");
                                    num_celh = jsonObject1.getString("num_celular");

                                    nomcan = jsonObject1.getString("can_nom");
                                    edadcan = jsonObject1.getString("can_edad");
                                    razacan = jsonObject1.getString("raza");
                                    colorcan = jsonObject1.getString("color");
                                    sexcan = jsonObject1.getString("sexo");
                                    fotocan = jsonObject1.getString("foto");
                                }

                                // MOSTRAMOS EL DATO EN EL CONTROL CORRESPONDIENTE
                                txt_userid.setText(user_id);

                                nom.setText(nombre);
                                edad.setText(edadh);
                                email.setText(correoh);
                                password.setText(contrasenah);
                                numcel.setText(num_celh);

                                cannom.setText(nomcan);
                                canedad.setText(edadcan);
                                raza.setText(razacan);
                                color.setText(colorcan);
                                sex.setText(sexcan);
                                //imagen.setImageBitmap();

                                //Toast.makeText(registro_usuario.this, fotocan, Toast.LENGTH_SHORT).show();
                                /*Bitmap bm = convertirStringImg(fotocan);
                                imagen.setImageBitmap(bm);*/

                                //String cleanImage = fotocan.replace("data:image/png;base64,", "").replace("data:image/jpeg;base64,","");
                                /*byte[] imagenByte = Base64.decode(fotocan.getBytes(), Base64.DEFAULT);
                                Bitmap decodedImage = BitmapFactory.decodeByteArray(imagenByte, 0, imagenByte.length);
                                imagen.setImageBitmap(decodedImage);*/


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
                params.put("correo", user_c.getText().toString().trim());
                params.put("contrasena", password_c.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private String convertirImgString(Bitmap bitmap) {

        ByteArrayOutputStream array=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imagenByte=array.toByteArray();
        String imagenString= Base64.encodeToString(imagenByte,Base64.DEFAULT);

        return imagenString;
    }

   private Bitmap convertirStringImg(String encodedString){
        try {
            byte[] imagenByte = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imagenByte, 0, imagenByte.length);
            return decodedImage;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }


       /* byte[] imagenByte = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imagenByte, 0, imagenByte.length);
        imagen.setImageBitmap(decodedImage);

        return decodedImage;*/

    }
}
