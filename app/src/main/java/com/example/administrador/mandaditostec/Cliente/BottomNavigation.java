package com.example.administrador.mandaditostec.Cliente;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrador.mandaditostec.R;

public class BottomNavigation extends AppCompatActivity implements
        FragmentPedidos.OnFragmentInteractionListener,
        FragmentMandaderos.OnFragmentInteractionListener,
        FragmentPerfil.OnFragmentInteractionListener{
    //Declaración del menú de navegación
    private BottomNavigationView navigation;
    //Declaración de los fragmentos
    private FragmentPedidos fragmentPedidos;
    private FragmentMandaderos fragmentMandaderos;
    private FragmentPerfil fragmentPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

    }

    //Listener para el menu de navegación
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_pedidos:
                    setFragment(fragmentPedidos);
                    Toast.makeText(BottomNavigation.this, "Texto", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_mandaderos:
                    setFragment(fragmentMandaderos);
                    return true;
                case R.id.navigation_perfil:
                    setFragment(fragmentPerfil);
                    return true;
                    default:
                        return false;
            }
        }
    };

    //Método para establecer el fragment seleccionado dentro del FrameLayout
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
