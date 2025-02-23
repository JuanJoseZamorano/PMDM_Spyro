package dam.pmdm.spyrothedragon.adapters;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Collectible;

public class CollectiblesAdapter extends RecyclerView.Adapter<CollectiblesAdapter.CollectiblesViewHolder> {

    private List<Collectible> list;
    private final Context context;
    private int contadorToques = 0;
    private static final int TOQUES_NECESARIOS = 4;

    public CollectiblesAdapter(List<Collectible> collectibleList,Context context) {

        this.list = collectibleList;
        this.context = context;
    }

    @Override
    public CollectiblesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CollectiblesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CollectiblesViewHolder holder, int position) {
        Collectible collectible = list.get(position);
        holder.nameTextView.setText(collectible.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(collectible.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);
        if (collectible.getName().equalsIgnoreCase("Gemas")) {
            holder.imageImageView.setOnClickListener(v -> {
                contadorToques++;
                if (contadorToques >= TOQUES_NECESARIOS) {
                    contadorToques = 0; // Reiniciar el contador solo cuando se activa el Easter Egg
                    mostrarVideoEasterEgg();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CollectiblesViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;

        public CollectiblesViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
        }
    }
    private void mostrarVideoEasterEgg() {
        // Crear un VideoView en pantalla completa
        Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.video_eastereggs); // ⬅ Nombre correcto del layout
        dialog.setCancelable(false);

        VideoView videoView = dialog.findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.videospyro1); // ⬅ Archivo en minúsculas y sin espacios

        videoView.setVideoURI(uri);

        // Crear MediaPlayer para controlar el audio del vídeo
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.videospyro1);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(mp -> {
                mp.release(); // Liberar recursos de audio cuando termine
            });
        }

        // Reproducir el video automáticamente
        videoView.setOnPreparedListener(MediaPlayer::start);

        // Cerrar el diálogo cuando termine el vídeo
        videoView.setOnCompletionListener(mp -> {
            dialog.dismiss();
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
        });

        dialog.show();
    }
}
