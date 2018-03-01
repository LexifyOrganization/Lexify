package parisnanterre.fr.lexify.verbalgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.exception.noCurrentPlayerException;
import parisnanterre.fr.lexify.word.Word;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VerbalGameSigningFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VerbalGameSigningFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerbalGameSigningFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Word> words;
    boolean isChosenWords;

    private OnFragmentInteractionListener mListener;

    public VerbalGameSigningFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerbalGameSigningFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerbalGameSigningFragment newInstance(String param1, String param2) {
        VerbalGameSigningFragment fragment = new VerbalGameSigningFragment();
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
        View view = inflater.inflate(R.layout.fragment_verbal_game_signing, container, false);

        final TextView txt_turn = (TextView) view.findViewById(R.id.fragment_verbal_game_signing_playerturn);
        final TextView txt_player = (TextView) view.findViewById(R.id.fragment_verbal_game_signing_player);
        final TextView txt_rules = (TextView) view.findViewById(R.id.fragment_verbal_game_signing_rules);
        FrameLayout frm_game = (FrameLayout) view.findViewById(R.id.fragment_verbal_game_signing_fragment);
        final Button btn_choose = (Button) view.findViewById(R.id.fragment_verbal_game_signing_btn_choose_words);
        final Button btn_change = (Button) view.findViewById(R.id.fragment_verbal_game_signing_btn_change_name);
        final VerbalGameActivity gameActivity = (VerbalGameActivity) getActivity();



        //si joueur 2 alors peut plus changer pseudo
        if(gameActivity.isLastRound()){
            btn_change.setVisibility(View.GONE);
        }

       words =  gameActivity.getEightRandomWords();
       isChosenWords = false;

        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChooseDialog(gameActivity);
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_verbal_game_playersname, null);

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(view)
                        // Add action buttons
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null);

                final EditText edt_player1 = (EditText) view.findViewById(R.id.dialog_verbal_game_player1);
                final EditText edt_player2 = (EditText) view.findViewById(R.id.dialog_verbal_game_player2);
                edt_player1.setText(gameActivity.getPlayer1().getName());
                edt_player2.setText(gameActivity.getPlayer2().getName());

                final Dialog dialog = builder.create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialogInterface) {

                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {

                                if(edt_player1.getText().toString().length()==0 || edt_player2.getText().toString().length()==0) {
                                    Toast toast = Toast.makeText(getActivity(), "Please enter name for player 1 and 2", Toast.LENGTH_SHORT);
                                    toast.show();
                                }else {

                                    gameActivity.getPlayer1().setName(edt_player1.getText().toString());
                                    gameActivity.getPlayer2().setName(edt_player2.getText().toString());

                                    Player player = null;
                                    try {
                                        player = gameActivity.getCurrentPlayer();
                                    } catch (noCurrentPlayerException e) {
                                        e.printStackTrace();
                                    }
                                    txt_turn.setText(player.getName() + " it's your turn !");
                                    txt_player.setText(player.getName());
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });
                dialog.show();

            }
        });


        frm_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isChosenWords) {
                    gameActivity.initializeWords();
                }

                gameActivity.setFragment(new VerbalGameFragment());
            }
        });


        Player player = null;
        try {
            player = gameActivity.getCurrentPlayer();
        } catch (noCurrentPlayerException e) {
            e.printStackTrace();
        }

        txt_turn.setText(player.getName() + " it's your turn !");
        txt_player.setText(player.getName());
        //txt_rules.setText("Take the phone and try to guess the word to your partner only by using one word at a time !\nClick on the screen to start the game");

        return view;
    }

    public void showChooseDialog(final VerbalGameActivity gameActivity)
    {


        final CharSequence[] items = new CharSequence[8];
        final boolean itemsChecked[] = new boolean[items.length];
        
        for (int i = 0; i<items.length;i++) {
            items[i] = words.get(i).getWord();
        }



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose 4 words");

        builder.setMultiChoiceItems(items, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {



            int count = 0;

            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                count += isChecked ? 1 : -1;
                itemsChecked[which] = isChecked;
                Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);

                if(count !=4) {
                    b.setEnabled(false);
                }
                else {
                    b.setEnabled(true);
                }

            }

        })

                   .setPositiveButton("Ok", null)
            .setNegativeButton("Cancel", null);



        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setEnabled(false);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                            gameActivity.cleanWords();


                            for(int i = 0;i<items.length;i++) {
                                if(itemsChecked[i]){
                                    gameActivity.addWord(words.get(i));
                                }
                            }
                            isChosenWords = true;
                            dialog.dismiss();

                    }
                });
            }
        });
        dialog.show();



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
