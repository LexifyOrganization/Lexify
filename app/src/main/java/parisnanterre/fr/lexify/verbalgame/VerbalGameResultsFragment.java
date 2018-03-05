package parisnanterre.fr.lexify.verbalgame;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerbalGameResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerbalGameResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerbalGameResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VerbalGameResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerbalGameResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerbalGameResultsFragment newInstance(String param1, String param2) {
        VerbalGameResultsFragment fragment = new VerbalGameResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verbal_game_results, container, false);

        TableRow player1 = (TableRow) view.findViewById(R.id.fragment_verbal_game_results_player1);
        TableRow player2 = (TableRow) view.findViewById(R.id.fragment_verbal_game_results_player2);
        TextView score = (TextView) view.findViewById(R.id.fragment_verbal_game_results_score);
        Button btn_retry = (Button) view.findViewById(R.id.fragment_verbal_game_results_btn_retry);
        Button btn_menu = (Button) view.findViewById(R.id.fragment_verbal_game_results_btn_menu);


        btn_retry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                VerbalGameActivity gameActivity = (VerbalGameActivity) getActivity();
                gameActivity.initialize();
                gameActivity.setFragment(new VerbalGameSigningFragment());

            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getActivity(),MainActivity.class);
                startActivity(i);

            }
        });

        final VerbalGameActivity gameActivity = (VerbalGameActivity) getActivity();

        int scr = gameActivity.getScore();
        score.setText(String.valueOf(scr));

        int p1val[] = {gameActivity.getPlayer1().getNbWordFound(), gameActivity.getPlayer1().getNbPass()};
        int p2val[] = {gameActivity.getPlayer2().getNbWordFound(), gameActivity.getPlayer2().getNbPass()};

        TextView names = (TextView) player1.getChildAt(0);
        names.setText(gameActivity.getPlayer1().getName());

        names = (TextView) player2.getChildAt(0);
        names.setText(gameActivity.getPlayer2().getName());

        for (int i =0; i<2; i++)  {
            TextView txt = (TextView) player1.getChildAt(i+1);
            txt.setText(String.valueOf(p1val[i]));
        }

        for (int i =0; i<2; i++)  {
            TextView txt = (TextView) player2.getChildAt(i+1);
            txt.setText(String.valueOf(p2val[i]));
        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
