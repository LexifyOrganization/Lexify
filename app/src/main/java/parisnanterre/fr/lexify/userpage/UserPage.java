package parisnanterre.fr.lexify.userpage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.User;

public class UserPage extends Activity implements Serializable{

    User u = null;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        u = (User) getIntent().getSerializableExtra("user");

        TextView name = findViewById(R.id.activity_user_page_name);
        TextView wordsfound = findViewById(R.id.activity_user_page_textview_found_words);
        TextView wordsguess = findViewById(R.id.activity_user_page_textview_words_made_guess);
        TextView description = findViewById(R.id.activity_user_page_textview_description);
        ImageView edit = findViewById(R.id.activity_user_page_edit_profile);
        TextView realName = findViewById(R.id.activity_user_page_real_name_val);
        TextView age = findViewById(R.id.activity_user_page_age_val);
        TextView email = findViewById(R.id.activity_user_page_e_mail_val);
        TextView mobile = findViewById(R.id.activity_user_page_mobile_val);
        CircleImageView avatar = findViewById(R.id.activity_user_page_imageview_profile);
        LinearLayout ageLayout = findViewById(R.id.activity_user_page_age);
        LinearLayout emailLayout = findViewById(R.id.activity_user_page_e_mail);
        LinearLayout mobileLayout = findViewById(R.id.activity_user_page_mobile);
        LinearLayout realNameLayout = findViewById(R.id.activity_user_page_real_name);


        realName.setText(u.get_name());
        age.setText(Integer.toString(u.get_age()));
        email.setText(u.get_email());
        mobile.setText(u.get_mobile());
        description.setText(u.get_description());


        if(realName.getText().toString().isEmpty()) {
            realNameLayout.setVisibility(View.GONE);
        }
        if(age.getText().toString().isEmpty() || age.getText().equals("0")) {
            ageLayout.setVisibility(View.GONE);
        }
        if(email.getText().toString().isEmpty()) {
            emailLayout.setVisibility(View.GONE);
        }
        if(mobile.getText().toString().isEmpty()) {
            mobileLayout.setVisibility(View.GONE);
        }


        avatar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }

        });

        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditPage.class);
                i.putExtra("user", u);
                startActivity(i);

            }

        });

        name.setText(u.get_pseudo());
        wordsfound.setText(String.valueOf(u.get_foundwords()));
        wordsguess.setText(String.valueOf(u.get_wordsguess()));
        description.setText(u.get_description());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                CircleImageView image = (CircleImageView) findViewById(R.id.activity_user_page_imageview_profile);
                image.setImageBitmap(bitmap);
                u.set_avatar(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
