package parisnanterre.fr.lexify.computergame;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parisnanterre.fr.lexify.R;

public class ComputerGameActivity extends Activity implements ComputerGameFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_computer_game);
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_computer_game_fragment, new ComputerGameFragment()); // give your fragment container id in first parameter
        //transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();
    }

    public List<List<String>> create_liste_synonymes(){
        List<List<String>> synonymes = new ArrayList<List<String>>();
        BufferedReader lecteur = null;
        try {
            lecteur = new BufferedReader (new InputStreamReader(getAssets().open("liste_synonymes_fr.txt"), "iso-8859-1"));
            String line;
            StringBuilder out = new StringBuilder();
            while((line = lecteur.readLine()) != null){
                out.append(line);
                String tmp = out.toString();
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
            lecteur = new BufferedReader (new InputStreamReader(getAssets().open("liste_reduite_fr.txt"), "iso-8859-1"));
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

    private List<List<String>> synonymes= new ArrayList<List<String>>(create_liste_synonymes());
    private List<String> mots = new ArrayList<String>(create_liste_mots());

    public List<List<String>> getSynonymes() {
        return synonymes;
    }

    public List<String> getMots() {
        return mots;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
