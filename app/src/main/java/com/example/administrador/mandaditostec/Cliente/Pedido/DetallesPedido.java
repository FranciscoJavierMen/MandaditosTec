package com.example.administrador.mandaditostec.Cliente.Pedido;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrador.mandaditostec.R;

import static com.example.administrador.mandaditostec.Cliente.Pedido.FormDialog.TAG;

public class DetallesPedido extends DialogFragment {

    public static final String TAG = "Detalles del pedido";
    private Toolbar toolbar;

    public static DetallesPedido display(FragmentManager fragmentManager) {
        DetallesPedido detallesPedido = new DetallesPedido();
        detallesPedido.show(fragmentManager, TAG);
        return detallesPedido;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(FormDialog.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalles_pedido, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
