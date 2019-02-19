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
import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.database.User;

import static parisnanterre.fr.lexify.application.MainActivity.currentUser;
import static parisnanterre.fr.lexify.application.MainActivity.loadUser;

public class UserPage extends Activity implements Serializable{

    User u = null;
    private int PICK_IMAGE_REQUEST = 1;

    TextView realName;
    TextView age;
    TextView email;
    TextView mobile;
    TextView description;
    LinearLayout ageLayout;
    LinearLayout emailLayout;
    LinearLayout mobileLayout;
    LinearLayout realNameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        u = (User) getIntent().getSerializableExtra("user");

        TextView name = findViewById(R.id.activity_user_page_name);
        ImageView edit = findViewById(R.id.edit_profile);
        realName = findViewById(R.id.real_name_val);
        description = findViewById(R.id.tvDescription);
        age = findViewById(R.id.age_val);
        email = findViewById(R.id.e_mail_val);
        mobile = findViewById(R.id.mobile_val);
        TextView gender = findViewById(R.id.gender_val);
        CircleImageView avatar = findViewById(R.id.ivProfile);
        //tv1 is the number of words found by the user.
        //tv2 is the number of games played
        //tv3 is the number of games failed/abandoned
        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView friendCode = findViewById(R.id.friendCode);

        ageLayout = findViewById(R.id.age);
        emailLayout = findViewById(R.id.e_mail);
        mobileLayout = findViewById(R.id.mobile);
        realNameLayout = findViewById(R.id.real_name);
        LinearLayout genderLayout = findViewById(R.id.gender);


        realName.setText(u.getName());
        age.setText(Integer.toString(u.getAge()));
        email.setText(u.get_email());
        mobile.setText(u.getMobile());
        description.setText(u.getDescription());

        tv1.setText(Integer.toString(currentUser.get_wordGuessed()));
        tv2.setText(Integer.toString(currentUser.get_gamesPlayed()));
        tv3.setText(Integer.toString(currentUser.get_gamesFailed()));

        friendCode.setText(buildFriendCodeDisplay(currentUser.get_friendCode()));


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
        if(gender.getText().toString().isEmpty()){
            genderLayout.setVisibility(View.GONE);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                CircleImageView image = (CircleImageView) findViewById(R.id.ivProfile);
                image.setImageBitmap(bitmap);
                //u.setAvatar(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildFriendCodeDisplay(String[] fc){
        StringBuilder sb = new StringBuilder();

        //As a friend code is always 12 characters long, we can use hard coded values here
        for(int i = 0; i<12; i++){
            sb.append(fc[i]);
            if((i+1)%4 == 0 && i!=11){
                sb.append("-");
            }
        }

        return sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();

        u = loadUser(this);

        realName.setText(u.getName());
        age.setText(Integer.toString(u.getAge()));
        email.setText(u.get_email());
        mobile.setText(u.getMobile());
        description.setText(u.getDescription());

        if(realName.getText().toString().isEmpty()) {
            realNameLayout.setVisibility(View.GONE);
        }
        else {
            realNameLayout.setVisibility(View.VISIBLE);
        }


        if(age.getText().toString().isEmpty() || age.getText().equals("0")) {
            ageLayout.setVisibility(View.GONE);
        }
        else {
            ageLayout.setVisibility(View.VISIBLE);
        }


        if(email.getText().toString().isEmpty()) {
            emailLayout.setVisibility(View.GONE);
        }
        else {
            emailLayout.setVisibility(View.VISIBLE);
        }

        if(mobile.getText().toString().isEmpty()) {
            mobileLayout.setVisibility(View.GONE);
        }
        else {
            mobileLayout.setVisibility(View.VISIBLE);
        }


    }
}
