package parisnanterre.fr.lexify.verbalgame;


import android.content.Context;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.Set;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.enumeration.PassingType;
import parisnanterre.fr.lexify.exception.noCurrentPlayerException;
import parisnanterre.fr.lexify.settings.Settings;
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
    private int score;
    private int cpt = 1;
    private Player player;

    TextView txt_nbmanche;
    TextView txt_word;
    TextView txt_score;
    TextView txt_time;
    VerbalGameActivity gameActivity;
    CountDownTimer chrono;
    ProgressBar progressBar;
    Settings settings;

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

        gameActivity = (VerbalGameActivity) getActivity();
        score = gameActivity.getScore();

        try {
            player = gameActivity.getCurrentPlayer();
        } catch (noCurrentPlayerException e) {
            e.printStackTrace();
        }


        final Button btn_true = (Button) view.findViewById(R.id.fragment_verbal_game_btn_true);
        final Button btn_false = (Button) view.findViewById(R.id.fragment_verbal_game_btn_false);
        final Button btn_pass = (Button) view.findViewById(R.id.fragment_verbal_game_btn_pass);
        txt_nbmanche = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_manche);
        txt_word = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_word);
        txt_score = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_score);
        txt_time = (TextView) view.findViewById(R.id.fragment_verbal_game_txt_chrono);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_verbal_game_progressbar);
        LinearLayout layout_chrono = (LinearLayout) view.findViewById(R.id.fragment_verbal_game_layout_chrono);

        txt_nbmanche.setText("Round 1/4");
        txt_score.setText("Score :" + score);
        txt_word.setText(gameActivity.getWords().get(0).getWord());

        if(Settings.chrono_enabled){
            layout_chrono.setVisibility(View.VISIBLE);
        }else {
            layout_chrono.setVisibility(View.GONE);
        }

        progressBar.setMax(20);
        progressBar.setProgress(20);

        chrono = initializeTimer();
        chrono.start();

        btn_pass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                player.incNbPass();

                if (cpt == 4) {
                    score = score - 5;

                    changeFragment();
                } else {


                    changeWord(PassingType.PASS);

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

                if (cpt == 4) {
                    score++;

                    changeFragment();

                } else {

                    changeWord(PassingType.TRUE);

                }


            }
        });


        return view;
    }

    private CountDownTimer initializeTimer() {
        return new CountDownTimer(21000, 1000) {

            public void onTick(long millisUntilFinished) {

                int progress = (int) (millisUntilFinished / 1000);
                txt_time.setText(String.valueOf(millisUntilFinished/1000));
                progressBar.setProgress(progress);
            }

            public void onFinish() {
                txt_time.setText("Time's up !");
                player.incNotFoundWord();
                progressBar.setProgress(0);
                if (cpt != 4)
                    changeWord(PassingType.PASS);
                else {
                    score = score - 5;
                    changeFragment();
                }

            }
        };
    }


    private void changeFragment() {

        chrono.cancel();

        if (gameActivity.isLastRound()) {
            gameActivity.setScore(score);
            gameActivity.setFragment(new VerbalGameResultsFragment());
        } else {
            try {
                gameActivity.changeCurrentPlayer();
            } catch (noCurrentPlayerException e) {
                e.printStackTrace();
            }
            gameActivity.setFragment(new VerbalGameSigningFragment());
            gameActivity.setLastRound(true);
            gameActivity.setScore(score);
        }

    }

    //fct qui change le mot
    //parametre type = TRUE ou PASS (voir fichier enum PassingType) TRUE augmente le score et PASS le baisse
    private void changeWord(PassingType type) {

        chrono.cancel();

        if (type.equals(PassingType.TRUE)) {
            score++;
        } else {
            score = score - 5;
        }


        cpt++;
        txt_score.setText("Score : " + score);

        Word random = gameActivity.getWords().get(cpt - 1);
        txt_word.setText(random.getWord());
        txt_nbmanche.setText("Round " + cpt + "/4");

        chrono.start();

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
