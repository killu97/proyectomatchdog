package com.example.ntpf.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ntpf.MainActivity;
import com.example.ntpf.R;
import com.example.ntpf.Tabs.CercaFragment;
import com.example.ntpf.Tabs.ChatFragment;
import com.example.ntpf.entidades.mascotas;

import java.util.ArrayList;
import java.util.List;

public class mascotasAdapter extends RecyclerView.Adapter<mascotasAdapter.mascotasHolder> implements View.OnClickListener{

    List<mascotas> listaMascotas;


    //parte del onclickListener
    private View.OnClickListener listener;

    public mascotasAdapter(List<mascotas> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    @NonNull
    @Override
    public mascotasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.mascotas_lista, parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        //parte del onclickListener
        vista.setOnClickListener(this);



        return new mascotasHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull mascotasHolder holder, int position) {

        holder.txtcannom.setText(listaMascotas.get(position).getCanNom());
        holder.txtraza.setText(listaMascotas.get(position).getRaza());
        holder.txtsexo.setText(listaMascotas.get(position).getSexo());
        holder.txtcanedad.setText(listaMascotas.get(position).getCanEdad()+" años");

        if (listaMascotas.get(position).getImagencan()!=null){
            holder.foto_can.setImageBitmap(listaMascotas.get(position).getImagencan());
        }
        else {
            holder.foto_can.setImageResource(R.drawable.fotocan);
        }
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    //parte del onclickListener
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    //parte del onclickListener
    @Override
    public void onClick(View view) {
        TextView txtNombre = view.findViewById(R.id.can_nomr);
        if (listener!=null){
            listener.onClick(view);


            Intent intent = new Intent();
            intent.putExtra("nombre", txtNombre.getText().toString());
            intent.putExtra("destino", "");
            intent.putExtra("origen", "2");
            intent.putExtra("nombreUser", "");

            //listener.onClick(view);
        }
    }

    public class mascotasHolder extends RecyclerView.ViewHolder{

        TextView txtcannom, txtraza, txtsexo, txtcanedad, txtuserid;
        ImageView foto_can;

        public mascotasHolder(View itemView){
            super(itemView);
            txtcannom=(TextView)itemView.findViewById(R.id.can_nomr);
            txtraza=(TextView)itemView.findViewById(R.id.razar);
            txtsexo=(TextView)itemView.findViewById(R.id.sexor);
            txtcanedad=(TextView)itemView.findViewById(R.id.can_edadr);
            foto_can=(ImageView)itemView.findViewById(R.id.fotocan);
            //txtuserid=(TextView)itemView.findViewById(R.id.user_idr);
        }
    }
    public void filtrar(ArrayList<mascotas> filtroMascotas){
        this.listaMascotas=filtroMascotas;
        notifyDataSetChanged();
    }
}
