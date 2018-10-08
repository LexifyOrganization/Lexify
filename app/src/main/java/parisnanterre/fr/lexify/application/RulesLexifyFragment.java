package parisnanterre.fr.lexify;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import parisnanterre.fr.lexify.debug.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RulesLexifyFragment extends Fragment {


    public RulesLexifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rules_lexify, container, false);
    }

}
