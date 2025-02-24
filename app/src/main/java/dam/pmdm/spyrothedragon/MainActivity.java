package dam.pmdm.spyrothedragon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// codigo para que siempre salga la guia
//
//        SharedPreferences preferences = getSharedPreferences("configuracion", 0);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("guiaVista", false);
//        editor.apply();
//
// acaba el codigo

        // Configurar navegación con NavController
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);
            NavigationUI.setupWithNavController(binding.navView, navController);
            NavigationUI.setupActionBarWithNavController(this, navController);
        }

        // Configurar menú de navegación inferior
        binding.navView.setOnItemSelectedListener(this::selectedBottomMenu);

        // Verificar si la guía debe mostrarse
       SharedPreferences preferences = getSharedPreferences("configuracion", MODE_PRIVATE);
       boolean guiaVista = preferences.getBoolean("guiaVista", false);

        if (!guiaVista) {
            mostrarGuia();
        }else{
            findViewById(R.id.includeGuia).setVisibility(View.GONE);
        }
    }

    // Método para mostrar la guía interactiva
    private void mostrarGuia() {
        View guia = findViewById(R.id.includeGuia);

        if (guia != null) {
            guia.setVisibility(View.VISIBLE);

            // Inflar el fragmento de la guía
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.includeGuia, new GuiaFragment())
                    .commit();
        } else {
            guia.setVisibility(View.GONE);


        }
    }

    // Método para manejar la selección del menú inferior
    private boolean selectedBottomMenu(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_characters)
            navController.navigate(R.id.navigation_characters);
        else if (menuItem.getItemId() == R.id.nav_worlds)
            navController.navigate(R.id.navigation_worlds);
        else
            navController.navigate(R.id.navigation_collectibles);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú de opciones
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Maneja el clic en el ícono de información
        if (item.getItemId() == R.id.action_info) {
            showInfoDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        // Crear un cuadro de diálogo con información
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_about)
                .setMessage(R.string.text_about)
                .setPositiveButton(R.string.accept, null)
                .show();
    }

    // MEtodo para cambiar de pestaña desde la guia

    public void cambiarPestana(int itemId) {
        BottomNavigationView navView = findViewById(R.id.navView);
        if (navView != null) {
            navView.setSelectedItemId(itemId);
        }
    }
}
