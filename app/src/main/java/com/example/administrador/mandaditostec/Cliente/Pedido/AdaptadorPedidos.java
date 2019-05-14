package com.example.administrador.mandaditostec.Cliente.Pedido;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrador.mandaditostec.R;

import java.util.ArrayList;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.HolderPedidos>{

    private ArrayList<ModeloPedidos> listaPedidos;
    private Context context;

    public AdaptadorPedidos(ArrayList<ModeloPedidos> listaPedidos, Context context) {
        this.listaPedidos = listaPedidos;
        this.context = context;
    }

    @Override
    public HolderPedidos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        return new HolderPedidos(view);
    }

    @Override
    public void onBindViewHolder(HolderPedidos holder, int position) {
        ModeloPedidos modeloPedidos = listaPedidos.get(position);

        holder.txtDireccion.setText(modeloPedidos.getDireccion());
        holder.txtPedido.setText(modeloPedidos.getPedido());
        holder.txtHora.setText(modeloPedidos.getHora());
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class HolderPedidos extends RecyclerView.ViewHolder {

        private TextView txtDireccion, txtPedido, txtHora;
        private ImageView imgPedido;

        public HolderPedidos(View itemView) {
            super(itemView);

            txtDireccion = itemView.findViewById(R.id.txtDireccionPedido);
            txtPedido = itemView.findViewById(R.id.txtDescripcionPedido);
            txtHora = itemView.findViewById(R.id.txtHoraPedido);

        }
    }
}
