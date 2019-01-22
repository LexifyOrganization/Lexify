package parisnanterre.fr.lexify.lexicalfields;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.text.Collator;
import java.util.Random;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;


public class LexicalFieldFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private LexicalFieldActivity lexicalFieldActivity;
    private LexicalField current;

    public LexicalFieldFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lexical_field, container, false);



        final TextView txt_word = (TextView) view.findViewById(R.id.fragment_lexical_field_txt_word);
        Button btn_retry = (Button) view.findViewById(R.id.fragment_lexical_field_btn_retry);
        Button btn_menu = (Button) view.findViewById(R.id.fragment_lexical_field_btn_menu);
        final LinearLayout edit = (LinearLayout) view.findViewById(R.id.fragment_lexical_field_buttons_layout);
        final LinearLayout animation = (LinearLayout) view.findViewById(R.id.fragment_lexical_field_animation);
        final TextView fail = (TextView) view.findViewById(R.id.fragment_lexical_field_txt_fail);
        final TextView win = (TextView) view.findViewById(R.id.fragment_lexical_field_txt_win);
        final LottieAnimationView live1 = (LottieAnimationView) view.findViewById(R.id.fragment_lexical_field_animation_1);
        final LottieAnimationView live2 = (LottieAnimationView) view.findViewById(R.id.fragment_lexical_field_animation_2);
        final LottieAnimationView live3 = (LottieAnimationView) view.findViewById(R.id.fragment_lexical_field_animation_3);
        final TextView livesleft = (TextView) view.findViewById(R.id.fragment_lexical_field_txt_livesleft);
        final EditText edittext = (EditText) view.findViewById(R.id.fragment_lexical_field_edit);

        livesleft.setVisibility(View.GONE);
        animation.setVisibility(View.GONE);

        edittext.requestFocus();
       edittext.setText("");

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        edittext.findFocus();
        edittext.hasFocus();

        lexicalFieldActivity = (LexicalFieldActivity) getActivity();
        current = lexicalFieldActivity.getCurrent();

        String lang = Paper.book().read("language");
        if(lang==null)
            lang="en";

        final String lang2 = lang;

        txt_word.setText(current.getDesc(lang));

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {


                    if(equalsIgnoreAccent(current.getWord(lang2), edittext.getText().toString())) {
                        win.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.GONE);
                    }
                    else {
                        fail.setVisibility(View.VISIBLE);
                        fail.setText(getResources().getString(R.string.lexical_fail) + " " + current.getWord(lang2));
                        edit.setVisibility(View.GONE);
                    }

                }
                return false;
            }});

        btn_retry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*edittext.setText("");
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                edittext.clearFocus();*/
                win.setVisibility(View.GONE);
                fail.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
                lexicalFieldActivity.initialize();
                current = lexicalFieldActivity.getCurrent();
                txt_word.setText(current.getDesc(lang2));

                edittext.setText("");

                edittext.requestFocus();
                InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                edittext.findFocus();
                edittext.hasFocus();
                //lexicalFieldActivity.setFragment(new LexicalFieldFragment());
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

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

    private boolean equalsIgnoreAccent(String a, String b) {

        final Collator instance = Collator.getInstance();

        // This strategy mean it'll ignore the accents
        instance.setStrength(Collator.NO_DECOMPOSITION);

        // Will print 0 because its EQUAL
        if(instance.compare(a,b)==0)
            return true;
        else
            return false;

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
