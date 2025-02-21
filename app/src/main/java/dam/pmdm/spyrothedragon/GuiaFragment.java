package dam.pmdm.spyrothedragon;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaBinding;

public class GuiaFragment extends Fragment {

    private FragmentGuiaBinding binding;
    private int pantallaActual = 1;
    private static final int TOTAL_PANTALLAS = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuiaBinding.inflate(inflater, container, false);
        // Asegurarnos de que el contenedor de pantallas existe
        if (binding.contenedorPantallas == null) {
            Log.e("GuiaFragment", "Error: No se encontró contenedorPantallas");
        } else {
            Log.d("GuiaFragment", "ContenedorPantallas encontrado correctamente.");
        }
        // Cargar la primera pantalla
        mostrarPantalla(1);

        return binding.getRoot();
    }

    // Método para cambiar entre pantallas de la guía
    private void mostrarPantalla(int numeroPantalla) {
        int layoutResId = obtenerLayoutPantalla(numeroPantalla);
        if (layoutResId == 0) return;

        // Reemplazar la pantalla anterior con la nueva
        View nuevaPantalla = getLayoutInflater().inflate(layoutResId, binding.contenedorPantallas, false);
        binding.contenedorPantallas.removeAllViews();
        binding.contenedorPantallas.addView(nuevaPantalla);

        // Configurar botones de la pantalla
        configurarBotones(nuevaPantalla);
    }

    // Obtiene el layout correspondiente a cada pantalla
    private int obtenerLayoutPantalla(int numeroPantalla) {
        switch (numeroPantalla) {
            case 1: return R.layout.pantalla_1;
            case 2: return R.layout.pantalla_2;
            case 3: return R.layout.pantalla_3;
            case 4: return R.layout.pantalla_4;
            case 5: return R.layout.pantalla_5;
            case 6: return R.layout.pantalla_6;
            default: return 0;
        }
    }

    // Configurar los botones de cada pantalla
    private void configurarBotones(View pantalla) {
        View botonAvanzar = pantalla.findViewById(R.id.botonComenzar);
        View botonOmitir = pantalla.findViewById(R.id.botonOmitirGuia);

        // Botón para avanzar a la siguiente pantalla
        if (botonAvanzar != null) {
            botonAvanzar.setOnClickListener(v -> {
                if (pantallaActual < TOTAL_PANTALLAS) {
                    pantallaActual++;
                    mostrarPantalla(pantallaActual);
                } else {
                    cerrarGuia();
                }
            });
        }

        // Botón para omitir la guía
        if (botonOmitir != null) {
            botonOmitir.setOnClickListener(v -> cerrarGuia());
        }
    }

    // Método para cerrar la guía
    private void cerrarGuia() {
        // Guardamos en SharedPreferences que la guía ya se mostró
        SharedPreferences preferences = requireActivity().getSharedPreferences("configuracion", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("guiaVista", true);
        editor.apply();

        // Ocultar la guía en la actividad principal
        requireActivity().findViewById(R.id.includeGuia).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}