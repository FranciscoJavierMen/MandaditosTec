package com.example.administrador.mandaditostec.Cliente.Pedido;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.administrador.mandaditostec.Cliente.Mapa.Maps;
import com.example.administrador.mandaditostec.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class FormDialog extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "Nuevo pedido";
    private final static int MAP_POINT = 999;
    private final static int MAP_PLACE = 1;
    private Toolbar toolbar;
    
    private FloatingActionButton fabEnviarPedido;
    private AppCompatButton btnSelectMandadero, btnSelectDireccion;
    private TextView txtMandadero, txtDireccion;
    private EditText edtDescripcionPedido;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public static FormDialog display(FragmentManager fragmentManager) {
        FormDialog formDialog = new FormDialog();
        formDialog.show(fragmentManager, TAG);
        return formDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(FormDialog.STYLE_NORMAL, R.style.AppTheme_FullScreenDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_form_dialog, container, false);

        toolbar = view.findViewById(R.id.toolbarForm);
        inicializar(view);
        inicializarFirebase();

        fabEnviarPedido.setOnClickListener(this);
        btnSelectMandadero.setOnClickListener(this);
        btnSelectDireccion.setOnClickListener(this);
        return view;
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(getActivity().getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializar(View view) {
        fabEnviarPedido = view.findViewById(R.id.fabEnviarPedido);
        btnSelectMandadero = view.findViewById(R.id.botonSeleccionarMandadero);
        btnSelectDireccion = view.findViewById(R.id.botonSeleccionarDestino);
        txtMandadero = view.findViewById(R.id.txtMandadero);
        txtDireccion = view.findViewById(R.id.txtDireccion);
        edtDescripcionPedido = view.findViewById(R.id.edtDescripcionPedido);
    }

    private void seleccionarPunto(){
        Intent punto = new Intent(getActivity(), Maps.class);
        startActivityForResult(punto, MAP_POINT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAP_POINT){
            try{
                LatLng latLng = data.getParcelableExtra("punto_seleccionado");
                txtDireccion.setText("Dirección seleccionada");
                Toast.makeText(getActivity().getApplicationContext(), "Punto seleccionado: "+latLng.latitude+" - "+latLng.longitude, Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                Toast.makeText(getActivity().getApplicationContext(), "Pedido cancelado...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent i;
        try{
            switch (view.getId()){
                case R.id.fabEnviarPedido:
                    if (validarPedido()){
                        enviarPedido();
                        dismiss();
                    }
                    break;
                case R.id.botonSeleccionarMandadero:

                    Toast.makeText(getActivity().getApplicationContext(), "Seleccionar mandadero", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.botonSeleccionarDestino:
                    seleccionarPunto();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void enviarPedido() {

        if(validarPedido()){
            String pedido, mandadero, direccion, hora;
            Long ts = System.currentTimeMillis()/1000;
            hora = ts.toString();

            pedido = edtDescripcionPedido.getText().toString();
            mandadero = txtMandadero.getText().toString();
            direccion = txtDireccion.getText().toString();

            ModeloPedidos modelo = new ModeloPedidos();

            modelo.setId(UUID.randomUUID().toString());
            modelo.setMandadero(mandadero);
            modelo.setDireccion(direccion);
            modelo.setPedido(pedido);
            modelo.setHora(hora);
            databaseReference.child("Pedido").child(modelo.getId()).setValue(modelo);
        }


        Toast.makeText(getActivity().getApplicationContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
    }

    private boolean validarPedido() {
        boolean validar = true;
        String pedido, mandadero, direccion, hora;

        pedido = edtDescripcionPedido.getText().toString();
        mandadero = txtMandadero.getText().toString();
        direccion = txtDireccion.getText().toString();

        if (pedido.equals("") || direccion.equals("Dirección no seleccionada")){
            validar = false;
        }

        if (pedido.equals("")){
            edtDescripcionPedido.setError("Campo requerido");
        }

        if (mandadero.equals("Nombre del mandaderos")){
            txtMandadero.setError("Requerido");
            txtMandadero.setText("Debe seleccionar un mandadero");
            txtMandadero.setTextColor(getResources().getColor(R.color.rojo, null));
        } else {
            txtMandadero.setTextColor(getResources().getColor(R.color.negro, null));
        }

        if (direccion.equals("Dirección no seleccionada")){
            txtDireccion.setError("Requerido");
            txtDireccion.setText("Debe seleccionar dirección ");
            txtDireccion.setTextColor(getResources().getColor(R.color.rojo, null));
        } else {
            txtDireccion.setTextColor(getResources().getColor(R.color.negro, null));
        }

        return validar;
    }
}
