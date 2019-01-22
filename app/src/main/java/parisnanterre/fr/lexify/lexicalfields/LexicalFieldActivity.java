package parisnanterre.fr.lexify.lexicalfields;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.Random;

import parisnanterre.fr.lexify.R;

public class LexicalFieldActivity extends Activity implements LexicalFieldFragment.OnFragmentInteractionListener {

    List<LexicalField> lexicalFields = new ArrayList<LexicalField>();
    LexicalField current = new LexicalField();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialize();
        setContentView(R.layout.activity_lexical_field);
        setFragment(new LexicalFieldFragment());
    }

    public void setFragment(Fragment f) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.activity_lexical_fields_fragment, f);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void initialize() {

        try {
            //getAssets().open("liste_fr.txt"), "iso-8859-1");

            InputStream inputFile = getAssets().open("lexicalfields.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            doc.getDocumentElement().getNodeName();
            NodeList nList = doc.getElementsByTagName("lexical");

            for (int temp = 0; temp < nList.getLength(); temp++) {



                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    int id = Integer.parseInt(eElement.getAttribute("id"));



                    String wordFr = ((Element) ((Element) nNode).getElementsByTagName("word").item(0)).getElementsByTagName("fr")
                            .item(0).getTextContent();
                    String wordEn = ((Element) ((Element) nNode).getElementsByTagName("word").item(0)).getElementsByTagName("en")
                            .item(0).getTextContent();

                    String descFr = ((Element) ((Element) nNode).getElementsByTagName("description").item(0)).getElementsByTagName("fr")
                            .item(0).getTextContent();

                    String descEn = ((Element) ((Element) nNode).getElementsByTagName("description").item(0)).getElementsByTagName("en")
                            .item(0).getTextContent();

                    lexicalFields.add(new LexicalField(id, wordFr, wordEn, descFr, descEn));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        int limit = getLexicalFields().size();

        Random r = new Random();
        int low = 0;
        int high = limit;
        int result = r.nextInt(high-low) + low;

        LexicalField newCurrent = getLexicalFields().get(result);

        while(current.getId()==newCurrent.getId()) {
            r = new Random();
            low = 0;
            high = limit;
            result = r.nextInt(high-low) + low;

            newCurrent = getLexicalFields().get(result);
        }

        current = newCurrent;

    }

    public LexicalField getCurrent() {
        return current;
    }

    public List<LexicalField> getLexicalFields() {
        return lexicalFields;
    }
}
