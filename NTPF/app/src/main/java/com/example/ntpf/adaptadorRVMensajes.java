package com.example.ntpf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorRVMensajes extends RecyclerView.Adapter<adaptadorRVMensajes.MensajeHolder> {
    private List<mensajes> listaMensajes;

    public adaptadorRVMensajes(List<mensajes> listaMensajes) {
        this.listaMensajes = listaMensajes;
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mensaje_layout, parent, false);
        return new MensajeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int position) {
        holder.tvName.setText(listaMensajes.get(position).getNombre());
        holder.tvMensaje.setText(listaMensajes.get(position).getMensaje());
        //holder.bandera

    }

    @Override
    public int getItemCount() {
        return this.listaMensajes.size();
    }

    class MensajeHolder  extends  RecyclerView.ViewHolder{
        private TextView tvName;
        private TextView tvMensaje;
        private boolean bandera = false;

        public MensajeHolder(@NonNull View itemView) {
            super(itemView);
            this.tvMensaje = itemView.findViewById(R.id.tvMensaje);
            this.tvName = itemView.findViewById(R.id.tvName);
            /*if(this.bandera){
                this.tvMensaje.setVisibility(0);
            }
            else {
                this.tvName.setVisibility(0);
            }*/

        }
    }
}
