package parisnanterre.fr.lexify.computergame;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

public class ComputerGameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int currentPosition = 0;
    private int currentRound = 1;
    private String ComputerWord;
    private String ComputerSynonyme;
    final private int nombre_mots = 23;
    private int nombre_tire;
    private int currentTimerProgress = 100; //percentage of progress, not number of seconds
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    ObjectAnimator animateProgressBar;
    ComputerGameActivity computerGameActivity;

    public void determine_computer_word(){
        nombre_tire = (int)(Math.random() * ((nombre_mots)));
        ComputerWord = computerGameActivity.getMots().get(nombre_tire);
    }

    public void determine_computer_synonyme(){
        int i = (int)(Math.random() * (computerGameActivity.getSynonymes().get(nombre_tire).size()));
        ComputerSynonyme = computerGameActivity.getSynonymes().get(nombre_tire).get(i);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computer_game, container, false);
        final TableLayout left = view.findViewById(R.id.fragment_computer_game_left);
        final TableLayout right = view.findViewById(R.id.fragment_computer_game_right);
        final EditText edittext = view.findViewById(R.id.fragment_computer_game_edit);
        final LinearLayout layout = view.findViewById(R.id.fragment_computer_game_end);
        final TextView endText = view.findViewById(R.id.fragment_computer_game_txt_end);
        final TextView round = view.findViewById(R.id.fragment_computer_game_txt_manche);
        final Button next = view.findViewById(R.id.fragment_computer_game_btn_next);
        final Button abandon = view.findViewById(R.id.fragment_computer_game_btn_abandon);

        computerGameActivity = (ComputerGameActivity) getActivity();
        determine_computer_word();
        determine_computer_synonyme();
        progressBar = view.findViewById(R.id.fragment_computer_game_progressbar_countdown);

        round.setText("Round "+currentRound+"/4");

        progressBar.setProgress(currentTimerProgress);
        animateProgressBar = ObjectAnimator.ofInt(progressBar, "progress", 100, 0);
        animateProgressBar.setDuration(30000);
        animateProgressBar.setInterpolator(new LinearInterpolator());

        countDownTimer = createTimer(view);
        animateProgressBar.start();
        countDownTimer.start();

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentRound++;
                determine_computer_word();
                determine_computer_synonyme();
                round.setText("Round "+currentRound+"/4");
                reset(left);
                reset(right);
                currentPosition = 0;
                currentTimerProgress=100;
                countDownTimer=createTimer(getView());

                edittext.setVisibility(View.VISIBLE);
                edittext.setText("");
                layout.setVisibility(View.GONE);

                TextView text = (TextView) left.getChildAt(currentPosition);
                text.setText(ComputerSynonyme);

                InputMethodManager imm = (InputMethodManager)   getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                edittext.findFocus();
                edittext.hasFocus();

                countDownTimer.start();
                animateProgressBar.start();
            }

        });

        abandon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }

        });


        edittext.hasFocus();
        TextView text = (TextView) left.getChildAt(currentPosition);
        text.setText(ComputerSynonyme);

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(edittext.getText().toString().isEmpty()) {
                        return true;
                    }

                    TextView text = (TextView) right.getChildAt(currentPosition);
                    text.setText(edittext.getText().toString());
                    if(edittext.getText().toString().equalsIgnoreCase(ComputerWord) || currentPosition==3){
                        edittext.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                        if(currentRound==4) {
                            abandon.setText("Revenir menu principal");
                            next.setVisibility(View.GONE);
                            animateProgressBar.end();
                            countDownTimer.cancel();
                        }

                        if(edittext.getText().toString().equalsIgnoreCase(ComputerWord)) {
                            endText.setText("Bravo vous avez gagné !");
                            animateProgressBar.end();
                            countDownTimer.cancel();
                        }
                        else if (currentPosition==3) {
                            endText.setText("Vous avez perdu ! Le mot à deviner était " + ComputerWord);
                            endText.setText("Vous avez perdu !");
                            animateProgressBar.end();
                            countDownTimer.cancel();
                        }

                        InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                        edittext.clearFocus();
                    } else {
                        edittext.setText("");
                        currentPosition++;
                        determine_computer_synonyme();
                        text = (TextView) left.getChildAt(currentPosition);
                        text.setText(ComputerSynonyme);
                    }

                    return true;
                }
                return false;
        }});

        return view;
    }

    private void reset(TableLayout table) {
        int count = table.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = table.getChildAt(i);
            if (child instanceof TextView) {
                TextView tv = (TextView) child;
                tv.setText("");
            }
        }
    }

    public CountDownTimer createTimer(View view){
        final EditText edittext = view.findViewById(R.id.fragment_computer_game_edit);
        final LinearLayout layout = view.findViewById(R.id.fragment_computer_game_end);
        final TextView endText = view.findViewById(R.id.fragment_computer_game_txt_end);
        final Button next = view.findViewById(R.id.fragment_computer_game_btn_next);
        final Button abandon = view.findViewById(R.id.fragment_computer_game_btn_abandon);

        return new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTimerProgress--;
                progressBar.setProgress((int) currentTimerProgress * 100 / (30000 / 1000));
            }
            @Override
            public void onFinish() {
                Toast.makeText(getActivity(), "Time's up !", Toast.LENGTH_SHORT).show();
                edittext.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                if (currentRound==4) {
                    abandon.setText("Revenir menu principal");
                    next.setVisibility(View.GONE);
                }
                endText.setText("Vous avez perdu !");
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                edittext.clearFocus();
                animateProgressBar.end();
                countDownTimer.cancel();
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComputerGameFragment.OnFragmentInteractionListener) {
            mListener = (ComputerGameFragment.OnFragmentInteractionListener) context;
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
}