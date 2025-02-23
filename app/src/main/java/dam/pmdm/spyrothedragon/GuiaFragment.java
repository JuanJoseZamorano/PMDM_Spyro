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

public class GuiaFragment extends Fragment {

    private FragmentGuiaBinding binding;
    private int pantallaActual = 1;
    private static final int TOTAL_PANTALLAS = 6;
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuiaBinding.inflate(inflater, container, false);
        // Iniciar la música de fondo
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.fondospyro);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // La música se repite en bucle
            // Ajustar volumen al 30% (0.3f de 1.0f)
            mediaPlayer.setVolume(0.3f, 0.3f);
            mediaPlayer.start();
        }
        // Cargar la primera pantalla
        mostrarPantalla(1);

        return binding.getRoot();

    }

    // Método para cambiar entre pantallas de la guía
    private void mostrarPantalla(int numeroPantalla) {

        int layoutResId = obtenerLayoutPantalla(numeroPantalla);
        if (layoutResId == 0) return;

        View nuevaPantalla = getLayoutInflater().inflate(layoutResId, binding.contenedorPantallas, false);
        if (nuevaPantalla == null) return;

        // ⬇️ ANIMACIÓN DE TRANSICIÓN (Deslizamiento)
        TranslateAnimation slideIn = new TranslateAnimation(300, 0, 0, 0); // Desliza de derecha a izquierda
        slideIn.setDuration(900);



        AnimationSet animacionFinal = new AnimationSet(true);
        animacionFinal.addAnimation(slideIn);


        nuevaPantalla.startAnimation(animacionFinal); // ⬅ Aplicamos la animación antes de cambiar la pantalla

        // Reemplazar la pantalla anterior
        binding.contenedorPantallas.removeAllViews();
        binding.contenedorPantallas.addView(nuevaPantalla);

        // Configurar los elementos de la nueva pantalla
        configurarBotones(nuevaPantalla);
        configurarAnimacionesBocadillo(nuevaPantalla);

        // cambio de pestaña seguin la guia
        Activity activity = getActivity();
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;

            switch (numeroPantalla) {
                case 3:
                    mainActivity.cambiarPestana(R.id.nav_worlds); // Botón de "Mundos"
                    break;
                case 4:
                    mainActivity.cambiarPestana(R.id.nav_collectibles); // Botón de "Coleccionables"
                    break;
                case 5:
                    mainActivity.cambiarPestana(R.id.nav_characters); // Botón de "Personajes"
                    break;
                default:
                    // No hacer nada si no es una pantalla que cambie la pestaña
                    break;
            }
        }

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
            // Crear una animación de "entrada y salida" en el margen derecho
            TranslateAnimation animacion = new TranslateAnimation(0, -20, 0, 0);
            animacion.setDuration(700);
            animacion.setRepeatMode(TranslateAnimation.REVERSE);
            animacion.setRepeatCount(TranslateAnimation.INFINITE);

            botonAvanzar.startAnimation(animacion); // Aplicar la animación
            botonAvanzar.setOnClickListener(v -> {

                if (pantallaActual < TOTAL_PANTALLAS) {
                    if (pantallaActual==5){
                        reproducirSonido(R.raw.fin_guia);
                        }else if (pantallaActual > 1 && pantallaActual < 5){
                        reproducirSonido(R.raw.boton_avanzar);
                    }
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
        // Detener la música de fondo al cerrar la guía
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // Ocultar la guía en la actividad principal
        requireActivity().findViewById(R.id.includeGuia).setVisibility(View.GONE);
    }
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
    private void aplicarAnimacion(View view){
            // Fade-in (aparece de la nada)
            AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
            fadeIn.setDuration(700);

            // Rebote
            ScaleAnimation bounce = new ScaleAnimation(
                    0.8f, 1f,  // Escala de X
                    0.8f, 1f,  // Escala de Y
                    Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            bounce.setDuration(800);
            bounce.setInterpolator(new BounceInterpolator());

            // Agrupar animaciones
            AnimationSet animacionFinal = new AnimationSet(true);
            animacionFinal.addAnimation(fadeIn);
            animacionFinal.addAnimation(bounce);

            // Aplicar animación
            view.startAnimation(animacionFinal);
        }

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