package parisnanterre.fr.lexify.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import parisnanterre.fr.lexify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutWordsMemoryFragment extends Fragment {

    public AboutWordsMemoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_words_memory, container, false);
        final TextView txt_about_application = view.findViewById(R.id.fragment_about_words_memory_txt);
        txt_about_application.setText(R.string.aboutwordsmemory);

        return view;
    }

}
