package parisnanterre.fr.lexify.wordsmemory;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.word.Word;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WordsMemoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WordsMemoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordsMemoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WordsMemoryActivity gameActivity;
    private List<Word> wordsNotSeen;
    private List<Word> wordsSeen;
    private Word currentWord;
    private TextView txt_word;
    private int cpt = 1;
    private  int totalwords = 0;
    private int LivesLeft = 3;

    private OnFragmentInteractionListener mListener;

    public WordsMemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WordsMemoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordsMemoryFragment newInstance(String param1, String param2) {
        WordsMemoryFragment fragment = new WordsMemoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_words_memory, container, false);
        final TextView txt_numberword = (TextView) view.findViewById(R.id.fragment_words_memory_txt_number_word);
        txt_word = (TextView) view.findViewById(R.id.fragment_words_memory_txt_word);
        final Button btn_view = (Button) view.findViewById(R.id.fragment_words_memory_btn_view);
        Button btn_notview = (Button) view.findViewById(R.id.fragment_words_memory_btn_not_view);
        Button btn_retry = (Button) view.findViewById(R.id.fragment_words_memory_btn_retry);
        Button btn_menu = (Button) view.findViewById(R.id.fragment_words_memory_btn_menu);
        final LinearLayout buttons = (LinearLayout) view.findViewById(R.id.fragment_words_memory_buttons_layout);
        final TextView fail_view = (TextView) view.findViewById(R.id.fragment_words_memory_txt_fail_view);
        final TextView fail_notview = (TextView) view.findViewById(R.id.fragment_words_memory_txt_fail_notview);
        final TextView win = (TextView) view.findViewById(R.id.fragment_words_memory_txt_win);
        final LottieAnimationView live1 = (LottieAnimationView) view.findViewById(R.id.fragment_words_memory_animation_1);
        final LottieAnimationView live2 = (LottieAnimationView) view.findViewById(R.id.fragment_words_memory_animation_2);
        final LottieAnimationView live3 = (LottieAnimationView) view.findViewById(R.id.fragment_words_memory_animation_3);
        final TextView livesleft = (TextView) view.findViewById(R.id.fragment_words_memory_txt_livesleft);

        btn_view.setVisibility(View.INVISIBLE);
        btn_view.setClickable(false);
        gameActivity = (WordsMemoryActivity) getActivity();
        wordsNotSeen = gameActivity.getWords();
        Collections.shuffle(wordsNotSeen);
        wordsSeen = new ArrayList<Word>();
        currentWord = wordsNotSeen.get(0);

        totalwords = wordsNotSeen.size();
        txt_numberword.setText(getResources().getString(R.string.word) + " 1/" + totalwords);

        String lang = Paper.book().read("language");

        if(lang==null)
            lang="en";

        txt_word.setText(currentWord.getWord(lang));

        buttons.setVisibility(View.VISIBLE);
        fail_view.setVisibility(View.GONE);
        fail_notview.setVisibility(View.GONE);
        win.setVisibility(View.GONE);


        btn_notview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(btn_view.getVisibility()==View.INVISIBLE) {
                    btn_view.setVisibility(View.VISIBLE);
                    btn_view.setClickable(true);
                }

                if(!wordsNotSeen.contains(currentWord)) {
                    if(LivesLeft==0) {
                        buttons.setVisibility(View.GONE);
                        fail_notview.setVisibility(View.VISIBLE);
                    }
                    else {
                        LivesLeft--;

                        switch (LivesLeft) {
                            case 2:live1.playAnimation(); break;
                            case 1:live2.playAnimation(); break;
                            case 0:live3.playAnimation(); break;
                        }
                        livesleft.setText(livesleft.getText().subSequence(0, livesleft.getText().toString().length()-1) + Integer.toString(LivesLeft));
                        changeWord();
                    }
                }
                else {
                    wordsNotSeen.remove(currentWord);
                    wordsSeen.add(currentWord);
                    cpt++;

                    txt_numberword.setText(getResources().getString(R.string.word) + " " + cpt + "/" + totalwords);
                    if(wordsNotSeen.isEmpty()) {
                        buttons.setVisibility(View.GONE);
                        win.setVisibility(View.VISIBLE);
                    }
                    else {
                        changeWord();
                    }

                }
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!wordsSeen.contains(currentWord)) {

                    if(LivesLeft==0) {
                        buttons.setVisibility(View.GONE);
                        fail_view.setVisibility(View.VISIBLE);
                    }
                    else {
                        LivesLeft--;

                        switch (LivesLeft) {
                            case 2:live1.playAnimation(); break;
                            case 1:live2.playAnimation(); break;
                            case 0:live3.playAnimation(); break;
                        }
                        livesleft.setText(livesleft.getText().subSequence(0, livesleft.getText().toString().length()-1) + Integer.toString(LivesLeft));
                        changeWord();
                    }


                }
                else {
                    changeWord();
                }
            }
        });


        btn_retry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameActivity.initialize();
                gameActivity.setFragment(new WordsMemoryFragment());
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

    private void changeWord() {

        Random rand = new Random();

        String lang = Paper.book().read("language");

        if(lang==null)
            lang="en";

        if (rand.nextInt(5) == 0 && wordsSeen.size()!=1) {

            Word newWord = wordsSeen.get(rand.nextInt(wordsSeen.size()));

            while(currentWord.getWord().equals(newWord.getWord())) {
                newWord = wordsSeen.get(rand.nextInt(wordsSeen.size()));
            }
            currentWord = newWord;
            txt_word.setText(currentWord.getWord(lang));
        }
        else {
            currentWord = wordsNotSeen.get(rand.nextInt(wordsNotSeen.size()));
            txt_word.setText(currentWord.getWord(lang));
        }


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
