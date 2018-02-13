package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.word.DatabaseWord;
import parisnanterre.fr.lexify.word.Word;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerbalGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerbalGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerbalGameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CPT = "cpt";
    private static final String ARG_SCORE = "score";

    // TODO: Rename and change types of parameters
    public int score;
    public int cpt = 1;
    public boolean lastround;

    private OnFragmentInteractionListener mListener;

    public VerbalGameFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static VerbalGameFragment newInstance(int cpt, int score) {
        VerbalGameFragment fragment = new VerbalGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CPT, cpt);
        args.putInt(ARG_SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verbal_game, container, false);

        final VerbalGameActivity gameActivity = (VerbalGameActivity) getActivity();
        score = gameActivity.score;
        lastround = gameActivity.lastround;
        final Player player = gameActivity.getCurrentPlayer();

        final DatabaseWord db = new DatabaseWord(view.getContext());

        //final Button btn_play_game = (Button) findViewById(R.id.fragment_verbal_game_btn_round);
        final Button btn_true = (Button) view.findViewById(R.id.fragment_verbal_game_btn_true);
        final Button btn_false = (Button) view.findViewById(R.id.fragment_verbal_game_btn_false);
        final Button btn_pass = (Button) view.findViewById(R.id.fragment_verbal_game_btn_pass);
        final TextView txt_nbmanche = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_manche);
        final TextView txt_word = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_word);
        final TextView txt_score = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_score);

        txt_nbmanche.setText("Round 1/4");
        txt_score.setText("Score :" + score);
        txt_word.setText(db.getRandomWord().getWord());


        btn_pass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                player.incNotFoundWord();

                if(cpt==4) {
                    score = score -5;

                    if(lastround){gameActivity.score = score; finishGame();}
                    else {
                        gameActivity.changeCurrentPlayer();
                        newRound();
                        gameActivity.lastround = true;
                        gameActivity.score = score;
                    }
                }
                else{
                    score= score - 5;
                    cpt++;
                    txt_score.setText("Score :" + score);

                    Word random = db.getRandomWord();
                    txt_word.setText(random.getWord());
                    txt_nbmanche.setText("Round " + cpt +"/4");
                }

            }
        });

        btn_false.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                score--;
                txt_score.setText("Score :" + score);

            }
        });


        btn_true.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                player.incFoundWord();

                if(cpt==4)
                {
                    score++;

                    if(lastround){
                        gameActivity.score = score;
                        finishGame();
                    }
                    else {
                        gameActivity.changeCurrentPlayer();
                        newRound();
                        gameActivity.lastround = true;
                        gameActivity.score = score;
                    }

                }
                else {
                    cpt++;

                    score++;
                    txt_score.setText("Score :" + score);

                    Word random = db.getRandomWord();
                    txt_word.setText(random.getWord());
                    txt_nbmanche.setText("Round " + cpt +"/4");


                }


            }
        });



        return view;
    }

    private void finishGame() {

        Fragment fragment = new VerbalGameResultsFragment();
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_verbal_game_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void newRound() {

        Fragment fragment = new VerbalGameSigningFragment();
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_verbal_game_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
