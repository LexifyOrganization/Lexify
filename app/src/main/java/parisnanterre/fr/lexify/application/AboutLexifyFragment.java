package parisnanterre.fr.lexify.application;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import parisnanterre.fr.lexify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutLexifyFragment extends Fragment {


    public AboutLexifyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_lexify, container, false);
        final TextView txt_about_application = view.findViewById(R.id.fragment_about_lexify_txt);
        final ImageView image_github = view.findViewById(R.id.fragment_about_github);
        final ImageView image_website = view.findViewById(R.id.fragment_about_website);
        final LinearLayout social_media = view.findViewById(R.id.fragment_social_media);

        image_github.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/LexifyOrganization/Lexify"));
                startActivity(intent);
            }
        });

        image_website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://lexifyorganization.github.io/Lexify/"));
                startActivity(intent);
            }
        });

        txt_about_application.setText(R.string.aboutapplication);

        return view;
    }

}
