package com.example.administrador.mandaditostec.Cliente.Pedido;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.mandaditostec.R;

import java.util.ArrayList;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.HolderPedidos>{

    private ArrayList<ModeloPedidos> listaPedidos;

    public AdaptadorPedidos(ArrayList<ModeloPedidos> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public HolderPedidos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new HolderPedidos(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(HolderPedidos holder, int position) {
        ModeloPedidos modeloPedidos = listaPedidos.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        holder.txtDireccionDestino.setText(modeloPedidos.getDireccionDestino());
        holder.txtPedido.setText(modeloPedidos.getPedido());
        holder.txtHora.setText(modeloPedidos.getHora());
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hacer algo
            }
        });
    }

    public void clear(){
        listaPedidos.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public void removeItem(int position){
        listaPedidos.remove(position);
        notifyItemRemoved(position);
    }

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemListener){
        mListener = itemListener;
    }

    public class HolderPedidos extends RecyclerView.ViewHolder {

        private TextView txtDireccionDestino, txtPedido, txtHora;

        public HolderPedidos(View itemView, final OnItemClickListener listener) {
            super(itemView);

            txtDireccionDestino = itemView.findViewById(R.id.txtDireccionPedido);
            txtPedido = itemView.findViewById(R.id.txtDescripcionPedido);
            txtHora = itemView.findViewById(R.id.txtHoraPedido);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
