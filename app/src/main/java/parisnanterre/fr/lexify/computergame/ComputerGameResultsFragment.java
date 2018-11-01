package parisnanterre.fr.lexify.computergame;

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

import org.w3c.dom.Text;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComputerGameResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ComputerGameResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComputerGameResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ComputerGameResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComputerGameResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComputerGameResultsFragment newInstance(String param1, String param2) {
        ComputerGameResultsFragment fragment = new ComputerGameResultsFragment();
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
        View view= inflater.inflate(R.layout.fragment_computer_game_results, container, false);
        TableRow round1 = (TableRow) view.findViewById(R.id.fragment_computer_game_results_round1);
        TableRow round2 = (TableRow) view.findViewById(R.id.fragment_computer_game_results_round2);
        TableRow round3 = (TableRow) view.findViewById(R.id.fragment_computer_game_results_round3);
        TableRow round4 = (TableRow) view.findViewById(R.id.fragment_computer_game_results_round4);
        TextView score = (TextView) view.findViewById(R.id.fragment_computer_game_results_score);
        Button btn_retry = (Button) view.findViewById(R.id.fragment_computer_game_results_btn_retry);
        Button btn_menu = (Button) view.findViewById(R.id.fragment_computer_game_results_btn_menu);
        final ComputerGameActivity gameActivity = (ComputerGameActivity) getActivity();

        btn_retry.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gameActivity.setWordsFound(0);
                gameActivity.setFragment(new ComputerGameFragment());
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });

        score.setText(String.valueOf(gameActivity.getWordsFound()));
        TextView foundornot= (TextView) round1.getChildAt(2);
        foundornot.setText(gameActivity.getWordsFoundList().get(0));
        foundornot= (TextView) round2.getChildAt(2);
        foundornot.setText(gameActivity.getWordsFoundList().get(1));
        foundornot= (TextView) round3.getChildAt(2);
        foundornot.setText(gameActivity.getWordsFoundList().get(2));
        foundornot= (TextView) round4.getChildAt(2);
        foundornot.setText(gameActivity.getWordsFoundList().get(3));
        TextView wordsComputer= (TextView) round1.getChildAt(1);
        wordsComputer.setText(gameActivity.getWordsComputer().get(0));
        wordsComputer= (TextView) round2.getChildAt(1);
        wordsComputer.setText(gameActivity.getWordsComputer().get(1));
        wordsComputer= (TextView) round3.getChildAt(1);
        wordsComputer.setText(gameActivity.getWordsComputer().get(2));
        wordsComputer= (TextView) round4.getChildAt(1);
        wordsComputer.setText(gameActivity.getWordsComputer().get(3));

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
