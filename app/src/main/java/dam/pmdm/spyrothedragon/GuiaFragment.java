package dam.pmdm.spyrothedragon;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.fragment.app.Fragment;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaBinding;

// Fragmento que gestiona la Guía Interactiva de la aplicación
public class GuiaFragment extends Fragment {

    private FragmentGuiaBinding binding;
    private int pantallaActual = 1; // Controla la pantalla en la que estamos
    private static final int TOTAL_PANTALLAS = 6; // Número total de pantallas de la guía
    private MediaPlayer mediaPlayer; // Reproductor de sonido para la música de fondo

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuiaBinding.inflate(inflater, container, false);

        // Iniciar la música de fondo
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.fondospyro);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // La música se repite en bucle
            mediaPlayer.setVolume(0.3f, 0.3f); // Volumen al 30%
            mediaPlayer.start();
        }

        // Cargar la primera pantalla de la guía
        mostrarPantalla(1);

        return binding.getRoot();
    }

    // Método para mostrar una pantalla específica de la guía
    private void mostrarPantalla(int numeroPantalla) {
        int layoutResId = obtenerLayoutPantalla(numeroPantalla);
        if (layoutResId == 0) return;

        View nuevaPantalla = getLayoutInflater().inflate(layoutResId, binding.contenedorPantallas, false);
        if (nuevaPantalla == null) return;

        // Animación de deslizamiento al cambiar de pantalla
        TranslateAnimation slideIn = new TranslateAnimation(300, 0, 0, 0);
        slideIn.setDuration(900);

        AnimationSet animacionFinal = new AnimationSet(true);
        animacionFinal.addAnimation(slideIn);

        nuevaPantalla.startAnimation(animacionFinal); // Aplica la animación

        // Reemplazar la pantalla anterior con la nueva
        binding.contenedorPantallas.removeAllViews();
        binding.contenedorPantallas.addView(nuevaPantalla);

        // Configurar botones y animaciones en la nueva pantalla
        configurarBotones(nuevaPantalla);
        configurarAnimacionesBocadillo(nuevaPantalla);

        // Cambiar de pestaña en la barra de navegación según la guía
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;

            switch (numeroPantalla) {
                case 3:
                    mainActivity.cambiarPestana(R.id.nav_worlds); // Cambiar a la pestaña de mundos
                    break;
                case 4:
                    mainActivity.cambiarPestana(R.id.nav_collectibles); // Cambiar a la pestaña de coleccionables
                    break;
                case 5:
                    mainActivity.cambiarPestana(R.id.nav_characters); // Cambiar a la pestaña de personajes
                    break;
                default:
                    break;
            }
        }
    }

    // Método para obtener el layout correspondiente a cada pantalla
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

    // Configurar botones en cada pantalla de la guía
    private void configurarBotones(View pantalla) {
        View botonAvanzar = pantalla.findViewById(R.id.botonComenzar);
        View botonOmitir = pantalla.findViewById(R.id.botonOmitirGuia);

        // Configurar el botón para avanzar a la siguiente pantalla
        if (botonAvanzar != null) {
            // Animación de movimiento para el botón avanzar
            TranslateAnimation animacion = new TranslateAnimation(0, -20, 0, 0);
            animacion.setDuration(700);
            animacion.setRepeatMode(TranslateAnimation.REVERSE);
            animacion.setRepeatCount(TranslateAnimation.INFINITE);
            botonAvanzar.startAnimation(animacion);

            botonAvanzar.setOnClickListener(v -> {
                if (pantallaActual < TOTAL_PANTALLAS) {
                    if (pantallaActual == 5) {
                        reproducirSonido(R.raw.fin_guia); // Sonido al finalizar la guía
                    } else if (pantallaActual > 1 && pantallaActual < 5) {
                        reproducirSonido(R.raw.boton_avanzar); // Sonido al avanzar
                    }
                    pantallaActual++;
                    mostrarPantalla(pantallaActual);
                } else {
                    cerrarGuia(); // Cierra la guía cuando llega al final
                }
            });
        }

        // Configurar el botón para omitir la guía
        if (botonOmitir != null) {
            botonOmitir.setOnClickListener(v -> cerrarGuia());
        }
    }

    // Método para cerrar la guía y guardar en SharedPreferences que ya se mostró
    private void cerrarGuia() {
        SharedPreferences preferences = requireActivity().getSharedPreferences("configuracion", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("guiaVista", true);
        editor.apply();

        // Detener la música de fondo al cerrar la guía
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Ocultar la guía en la actividad principal
        requireActivity().findViewById(R.id.includeGuia).setVisibility(View.GONE);
    }

    // Configurar las animaciones de los bocadillos en cada pantalla
    private void configurarAnimacionesBocadillo(View pantalla) {
        View bocadillo = pantalla.findViewById(R.id.bocadilloGuia);
        View anillo = pantalla.findViewById(R.id.anilloResaltado);

        if (bocadillo != null) {
            aplicarAnimacion(bocadillo);
        }
        if (anillo != null) {
            aplicarAnimacion(anillo);
        }
    }

    // Método para aplicar animaciones a los elementos
    private void aplicarAnimacion(View view) {
        // Animación de aparición (fade-in)
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(700);

        // Animación de rebote
        ScaleAnimation bounce = new ScaleAnimation(
                0.8f, 1f, // Escala en X
                0.8f, 1f, // Escala en Y
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        bounce.setDuration(800);
        bounce.setInterpolator(new BounceInterpolator());

        // Agrupar ambas animaciones
        AnimationSet animacionFinal = new AnimationSet(true);
        animacionFinal.addAnimation(fadeIn);
        animacionFinal.addAnimation(bounce);

        // Aplicar la animación a la vista
        view.startAnimation(animacionFinal);
    }

    // Método para reproducir sonidos en la guía
    private void reproducirSonido(int sonidoResId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(requireContext(), sonidoResId);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
