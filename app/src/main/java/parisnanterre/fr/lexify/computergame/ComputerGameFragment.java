package parisnanterre.fr.lexify.computergame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.verbalgame.VerbalGameActivity;
import parisnanterre.fr.lexify.verbalgame.VerbalGameFragment;

public class ComputerGameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int currentPosition = 0;
    private int currentRound = 1;
    private String motOrdi = "aimer";
    private List<String> synonymes = new ArrayList<String>(Arrays.asList("adorer", "désirer", "apprécier", "admirer"));


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

        round.setText("Round "+currentRound+"/4");

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentRound++;
                round.setText("Round "+currentRound+"/4");
                reset(left);
                reset(right);
                currentPosition = 0;

                edittext.setVisibility(View.VISIBLE);
                edittext.setText("");
                layout.setVisibility(View.GONE);

                TextView text = (TextView) left.getChildAt(currentPosition);
                text.setText(synonymes.get(currentPosition));

                InputMethodManager imm = (InputMethodManager)   getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                edittext.findFocus();
                edittext.hasFocus();

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
        text.setText(synonymes.get(currentPosition));

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(edittext.getText().toString().isEmpty()) {
                        return true;
                    }

                    TextView text = (TextView) right.getChildAt(currentPosition);
                    text.setText(edittext.getText().toString());
                    if(edittext.getText().toString().equalsIgnoreCase(motOrdi) || currentPosition==3){
                        edittext.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                        if(currentRound==4) {
                            abandon.setText("Revenir menu principal");
                            next.setVisibility(View.GONE);
                        }

                        if(edittext.getText().toString().equalsIgnoreCase(motOrdi)) {
                            endText.setText("Bravo vous avez gagné !");
                        }
                        else if (currentPosition==3) {
                            endText.setText("Vous avez perdu !");
                        }

                        InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                        edittext.clearFocus();
                    } else {
                        edittext.setText("");
                        currentPosition++;
                        text = (TextView) left.getChildAt(currentPosition);
                        text.setText(synonymes.get(currentPosition));
                    }

                    return true;
                }
                return false;
            }
        });

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
