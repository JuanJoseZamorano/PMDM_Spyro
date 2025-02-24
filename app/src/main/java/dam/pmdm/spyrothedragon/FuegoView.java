package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.view.View;

// Clase que dibuja una animación de fuego con Canvas
public class FuegoView extends View {

    private Paint paintFuego; // Objeto para dibujar el fuego
    private int alturaFuego = 0; // Controla la altura de la llama en la animación
    private boolean animar = true; // Indica si la animación está activa
    private Handler handler = new Handler(); // Maneja la repetición de la animación


    // Constructor que inicializa la vista
    public FuegoView(Context context) {
        super(context);
        init();
    }

    // Configuración inicial del Paint y arranque de la animación
    private void init() {
        paintFuego = new Paint();
        paintFuego.setStyle(Paint.Style.FILL);
        paintFuego.setAntiAlias(true);

        // Inicia la animación con un retraso
        handler.postDelayed(animacionRunnable, 50);
    }

    // Método que dibuja la animación del fuego
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Crea la forma del fuego con un triángulo
        Path path = new Path();
        path.moveTo(width / 2f, height / 2f - alturaFuego);
        path.lineTo(width / 2f - 50, height / 2f + 100 - alturaFuego);
        path.lineTo(width / 2f + 50, height / 2f + 100 - alturaFuego);
        path.close();

        // Alterna colores para simular llamas
        paintFuego.setColor((alturaFuego % 2 == 0) ? Color.RED : Color.YELLOW);

        // Dibuja la llama en el Canvas
        canvas.drawPath(path, paintFuego);

        // Controla la animación aumentando la altura
        if (animar) {
            alturaFuego += 5;
            if (alturaFuego > 100) {
                alturaFuego = 0; // Reinicia la animación
            }
            invalidate(); // Vuelve a dibujar
        }
    }

    // Runnable que repite la animación
    private Runnable animacionRunnable = new Runnable() {
        @Override
        public void run() {
            if (animar) {
                invalidate();
                handler.postDelayed(this, 50);
            }
        }
    };

    // Método para detener la animación
    public void detenerAnimacion() {
        animar = false;
        handler.removeCallbacks(animacionRunnable);
    }
}

