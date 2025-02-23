package dam.pmdm.spyrothedragon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.view.View;

public class FuegoView extends View {

    private Paint paintFuego;
    private int alturaFuego = 0; // Altura de la llama
    private boolean animar = true;
    private Handler handler = new Handler();

    public FuegoView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paintFuego = new Paint();
        paintFuego.setStyle(Paint.Style.FILL);
        paintFuego.setAntiAlias(true);

        // Iniciar la animación
        handler.postDelayed(animacionRunnable, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Dibujar la llama
        Path path = new Path();
        path.moveTo(width / 2f, height / 2f - alturaFuego);
        path.lineTo(width / 2f - 50, height / 2f + 100 - alturaFuego);
        path.lineTo(width / 2f + 50, height / 2f + 100 - alturaFuego);
        path.close();

        // Cambiar color entre rojo y amarillo para simular fuego
        if (alturaFuego % 2 == 0) {
            paintFuego.setColor(Color.RED);
        } else {
            paintFuego.setColor(Color.YELLOW);
        }

        canvas.drawPath(path, paintFuego);

        // Redibujar la animación
        if (animar) {
            alturaFuego += 5;
            if (alturaFuego > 100) {
                alturaFuego = 0; // Reiniciar la animación
            }
            invalidate();
        }
    }

    private Runnable animacionRunnable = new Runnable() {
        @Override
        public void run() {
            if (animar) {
                invalidate();
                handler.postDelayed(this, 50);
            }
        }
    };

    public void detenerAnimacion() {
        animar = false;
        handler.removeCallbacks(animacionRunnable);
    }
}