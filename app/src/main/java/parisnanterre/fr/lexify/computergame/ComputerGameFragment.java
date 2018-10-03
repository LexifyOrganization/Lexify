package parisnanterre.fr.lexify.computergame;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.verbalgame.VerbalGameFragment;

public class ComputerGameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int currentPosition = 0;
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
        final LinearLayout layout = view.findViewById(R.id.fragment_verbal_game_end);

        edittext.hasFocus();
        TextView text = (TextView) left.getChildAt(currentPosition);
        text.setText(synonymes.get(currentPosition));

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    TextView text = (TextView) right.getChildAt(currentPosition);
                    text.setText(edittext.getText().toString());
                    if(edittext.getText().toString().equalsIgnoreCase(motOrdi) || currentPosition==3){
                        edittext.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);
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
