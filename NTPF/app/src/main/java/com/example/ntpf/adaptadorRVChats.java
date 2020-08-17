package com.example.ntpf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adaptadorRVChats extends RecyclerView.Adapter<adaptadorRVChats.ChatNolder> {
    private List<Chat> listaChats;

    public adaptadorRVChats(List<Chat> listaChats) {
        this.listaChats = listaChats;
    }


    @NonNull
    @Override
    public ChatNolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_layout,parent, false);
        return new ChatNolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatNolder holder, int position) {
        holder.tvNombre.setText(this.listaChats.get(position).getNombre());
        holder.tvMensajeFinal.setText(this.listaChats.get(position).getMensaje());
        holder.setDestino(this.listaChats.get(position).getOrigen());
        holder.setOrigen(this.listaChats.get(position).getDestino());
    }

    @Override
    public int getItemCount() {
        return this.listaChats.size();
    }

    class ChatNolder extends RecyclerView.ViewHolder {
         String origen;
         String destino;
        private TextView tvNombre;
        private TextView tvMensajeFinal;
        private LinearLayout llvChat;


        public ChatNolder(@NonNull final View itemView) {
            super(itemView);
            this.tvNombre = itemView.findViewById(R.id.tvNombre);
            this.tvMensajeFinal = itemView.findViewById(R.id.tvMensajeFinal);
            this.llvChat = itemView.findViewById(R.id.llvChat);
            this.llvChat.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {


                    SharedPreferences preferences = itemView.getContext().getSharedPreferences("userlog",  Context.MODE_PRIVATE);
                    String re_nomcan=preferences.getString("nomcan", null);
                    Intent intent = new Intent(view.getContext(), mensajesA.class);
                    intent.putExtra("origen", getOrigen());
                    intent.putExtra("destino", getDestino());
                    intent.putExtra("nombre", tvNombre.getText().toString());
                    intent.putExtra("nombreUser", re_nomcan);
                    view.getContext().startActivity(intent);
                    System.out.println("Click en: "+ destino);
                }
            });
        }

        public String getOrigen() {
            return origen;
        }

        public void setOrigen(String origen) {
            this.origen = origen;
        }

        public String getDestino() {
            return destino;
        }

        public void setDestino(String destino) {
            this.destino = destino;
        }

    }
}
