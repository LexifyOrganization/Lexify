package parisnanterre.fr.lexify.application;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.computergame.ComputerGameActivity;
import parisnanterre.fr.lexify.lexicalfields.LexicalFieldActivity;
import parisnanterre.fr.lexify.verbalgame.VerbalGameActivity;
import parisnanterre.fr.lexify.wordsmemory.WordsMemoryActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayingFragment newInstance(String param1, String param2) {
        PlayingFragment fragment = new PlayingFragment();
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
        View view = inflater.inflate(R.layout.fragment_playing, container, false);
        Button btn_quit_games = (Button) view.findViewById(R.id.fragment_playing_btn_quit_games);
        Button btn_verbal_game = (Button) view.findViewById(R.id.fragment_playing_btn_play_game);
        Button btn_computer = (Button) view.findViewById(R.id.fragment_playing_btn_computer_game);
        Button btn_words_memory = (Button) view.findViewById(R.id.fragment_playing_btn_words_memory);
        Button btn_lexical_fields = (Button) view.findViewById(R.id.fragment_playing_btn_lexical_fields);
        final MainActivity gameActivity = (MainActivity) getActivity();

        btn_quit_games.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameActivity.setFragment(new MainFragment());
            }
        });

        btn_verbal_game.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), VerbalGameActivity.class);
                startActivity(i);

            }
        });

        btn_computer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ComputerGameActivity.class);
                startActivity(i);

            }
        });

        btn_words_memory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), WordsMemoryActivity.class);
                startActivity(i);
            }
        });

        btn_lexical_fields.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LexicalFieldActivity.class);
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
