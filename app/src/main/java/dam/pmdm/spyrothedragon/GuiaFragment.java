package dam.pmdm.spyrothedragon.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import dam.pmdm.spyrothedragon.databinding.FragmentGuiaBinding;

public class GuiaFragment extends Fragment {

    private FragmentGuiaBinding binding;
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGuiaBinding.inflate(inflater, container, false);

        // Obtener las preferencias compartidas para saber si la guía ya se mostró
        preferences = requireActivity().getSharedPreferences("configuracion", 0);
        boolean guiaVista = preferences.getBoolean("guiaVista", false);

        if (guiaVista) {
            binding.guiaLayout.setVisibility(View.GONE); // Ocultar guía si ya se mostró antes
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
