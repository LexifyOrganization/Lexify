package parisnanterre.fr.lexify.computergame;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.paperdb.Paper;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.verbalgame.VerbalGameFragment;

import static parisnanterre.fr.lexify.application.MainActivity.currentUser;
import static parisnanterre.fr.lexify.application.MainActivity.userList;

public class ComputerGameFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private int wordsFound=0;
    private int currentPosition = 0;
    private int currentRound = 1;
    List<String> wordsFoundList = new ArrayList<String>();
    List<String> wordsDB = new ArrayList<String>();
    List<List<String>> synonymesDB = new ArrayList<List<String>>();
    List<String> ComputerWords = new ArrayList<String>();
    List<List<String>> ComputerSynonymes = new ArrayList<List<String>>();
    private int currentTimerProgress = 100; //percentage of progress, not number of seconds
    private int nombre_tire;
    int currentTimeLeft;
    public static int timeSettingComputer;
    ProgressBar progressBar;
    CountDownTimer countDownTimer;
    ObjectAnimator animateProgressBar;
    ComputerGameActivity computerGameActivity;

    public List<List<String>> create_liste_synonymes(){
        List<List<String>> synonymes = new ArrayList<List<String>>();
        BufferedReader lecteur = null;
        try {

            String lang = Paper.book().read("language");
            if(lang==null)
                lang="en";
            if(lang.equals("fr")) {
                lecteur = new BufferedReader (new InputStreamReader(getActivity().getAssets().open("liste_synonymes_fr.txt"), "iso-8859-1"));
            }
            else {
                lecteur = new BufferedReader (new InputStreamReader(getActivity().getAssets().open("liste_synonymes_en.txt"), "iso-8859-1"));
            }
            String line;
            StringBuilder out = new StringBuilder();
            while((line = lecteur.readLine()) != null){
                String tmp = line.toString();
                String [] filelineTab = tmp.split(", ");
                List <String> filelineListe = new ArrayList<String>();
                Collections.addAll(filelineListe,filelineTab);
                synonymes.add(filelineListe);
            }
        }catch (Exception e){
        }
        finally {
            if(lecteur != null) try {
                lecteur.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return synonymes;
    }

    public List<String> create_liste_mots(){
        List<String> mots = new ArrayList<String>();
        BufferedReader lecteur = null;
        try {

            String lang = Paper.book().read("language");
            if(lang==null)
                lang="en";
            if(lang.equals("fr")) {
                lecteur = new BufferedReader (new InputStreamReader(getActivity().getAssets().open("liste_reduite_fr.txt"), "iso-8859-1"));
            }
            else {
                lecteur = new BufferedReader (new InputStreamReader(getActivity().getAssets().open("liste_reduite_en.txt"), "iso-8859-1"));
            }
            String line;
            while((line = lecteur.readLine()) != null){
                mots.add(line);
            }
        }catch (Exception e){
        }
        finally {
            if(lecteur != null) try {
                lecteur.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mots;
    }

    public void initialize_listes() {
        wordsDB = create_liste_mots();
        synonymesDB = create_liste_synonymes();
    }

    public void determine_computer_words_and_synonymes() {
        List<String> tmp = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            nombre_tire = (int) (Math.random() * ((wordsDB.size())));
            ComputerWords.add(wordsDB.get(nombre_tire));
            for (int j = 0; j < 4; j++) {
                int random = (int) (Math.random() * (synonymesDB.get(nombre_tire).size()));
                tmp.add(synonymesDB.get(nombre_tire).get(random));
                synonymesDB.get(nombre_tire).remove(random);
            }
            ComputerSynonymes.add(new ArrayList<String>(tmp));
            tmp.clear();
            wordsDB.remove(nombre_tire);
            synonymesDB.remove(nombre_tire);
        }
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

        edittext.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        edittext.findFocus();
        edittext.hasFocus();

        computerGameActivity = (ComputerGameActivity) getActivity();
        initialize_listes();
        determine_computer_words_and_synonymes();
        computerGameActivity.setWordsComputer(ComputerWords);
        progressBar = view.findViewById(R.id.fragment_computer_game_progressbar_countdown);

        round.setText(getResources().getString(R.string.round)+" "+currentRound+"/4");

        if (timeSettingComputer != 0 ) {
            progressBar.setVisibility(View.VISIBLE);
            currentTimerProgress = timeSettingComputer;
            progressBar.setProgress(currentTimerProgress);
            animateProgressBar = ObjectAnimator.ofInt(progressBar, "progress", 100, 0);
            animateProgressBar.setDuration(timeSettingComputer);
            animateProgressBar.setInterpolator(new LinearInterpolator());

            countDownTimer = createTimer(view);
            animateProgressBar.start();
            computerGameActivity.setChrono(countDownTimer);
            countDownTimer.start();
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentRound++;
                if(currentRound==5){
                    computerGameActivity.setFragment(new ComputerGameResultsFragment());
                }
                else {
                    round.setText("Round " + currentRound + "/4");
                    reset(left);
                    reset(right);
                    currentPosition = 0;
                    if (timeSettingComputer != 0) {
                        currentTimerProgress = 100;
                        countDownTimer = createTimer(getView());
                    }

                    edittext.setVisibility(View.VISIBLE);
                    edittext.setText("");
                    layout.setVisibility(View.GONE);

                    TextView text = (TextView) left.getChildAt(currentPosition);
                    text.setText(ComputerSynonymes.get(currentRound - 1).get(currentPosition));

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    edittext.findFocus();
                    edittext.hasFocus();

                    if (timeSettingComputer != 0) {
                        computerGameActivity.setChrono(countDownTimer);
                        countDownTimer.start();
                        animateProgressBar.start();
                    }
                }
            }

        });

        abandon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentRound != 4){
                    currentUser.update_gamesFailed();
                }
                saveUserStats();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }

        });


        edittext.hasFocus();
        TextView text = (TextView) left.getChildAt(currentPosition);
        text.setText(ComputerSynonymes.get(currentRound-1).get(currentPosition));

        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if(edittext.getText().toString().isEmpty()) {
                        return true;
                    }

                    TextView text = (TextView) right.getChildAt(currentPosition);
                    text.setText(edittext.getText().toString());
                    if(equalsIgnoreAccent(ComputerWords.get(currentRound-1), edittext.getText().toString()) || currentPosition==3){
                        edittext.setVisibility(View.GONE);
                        layout.setVisibility(View.VISIBLE);

                        if(currentRound==4) {
                            next.setText(getResources().getString(R.string.statistics));
                            abandon.setVisibility(View.GONE);
                            //called to conterbalance the use of the button, not a real abandon
                            //currentUser.fix_gamesFailed();
                            currentUser.update_gamesPlayed();
                            saveUserStats();

                            if (timeSettingComputer!=0) {
                                animateProgressBar.end();
                                countDownTimer.cancel();
                            }
                        }

                        if(edittext.getText().toString().equalsIgnoreCase(ComputerWords.get(currentRound-1))) {
                            endText.setText(getResources().getString(R.string.cg_win));
                            wordsFound++;
                            wordsFoundList.add((getResources().getString(R.string.yes)));
                            computerGameActivity.setWordsFound(wordsFound);
                            currentUser.update_wordGuessed();
                            saveUserStats();
                            if (timeSettingComputer!=0) {
                                animateProgressBar.end();
                                countDownTimer.cancel();
                            }
                        }
                        else if (currentPosition==3) {
                            endText.setText(getResources().getString(R.string.cg_lose) + ComputerWords.get(currentRound-1));
                            wordsFoundList.add((getResources().getString(R.string.no)));
                            if (timeSettingComputer!=0) {
                                animateProgressBar.end();
                                countDownTimer.cancel();
                            }
                        }

                        InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                        edittext.clearFocus();
                    } else {
                        edittext.setText("");
                        currentPosition++;
                        text = (TextView) left.getChildAt(currentPosition);
                        text.setText(ComputerSynonymes.get(currentRound-1).get(currentPosition));
                    }

                    return true;
                }
                return false;
            }});

        computerGameActivity.setWordsFoundList(wordsFoundList);

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
        computerGameActivity.setChrono(countDownTimer);
    }

    public CountDownTimer createTimer(View view){
        final EditText edittext = view.findViewById(R.id.fragment_computer_game_edit);
        final LinearLayout layout = view.findViewById(R.id.fragment_computer_game_end);
        final TextView endText = view.findViewById(R.id.fragment_computer_game_txt_end);
        final Button next = view.findViewById(R.id.fragment_computer_game_btn_next);
        final Button abandon = view.findViewById(R.id.fragment_computer_game_btn_abandon);

        return new CountDownTimer(timeSettingComputer,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTimerProgress--;
                currentTimeLeft = currentTimeLeft - 1000;
                progressBar.setProgress((int) currentTimerProgress * 100 / (timeSettingComputer / 1000));
            }
            @Override
            public void onFinish() {
                edittext.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                if (currentRound==4) {
                    next.setText("Voir statistiques");
                    abandon.setVisibility(View.GONE);
                }
                endText.setText(getResources().getString(R.string.cg_lose) + ComputerWords.get(currentRound-1));
                InputMethodManager input = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                edittext.clearFocus();
                animateProgressBar.end();
                countDownTimer.cancel();
                currentTimeLeft = timeSettingComputer;
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

    @TargetApi(Build.VERSION_CODES.M)
    private void saveUserStats(){
        try{
            FileOutputStream fileOutputStream = getContext().openFileOutput("user.json", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            userList.put(currentUser.get_id(),currentUser);
            objectOutputStream.writeObject(userList);
            objectOutputStream.flush();
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}