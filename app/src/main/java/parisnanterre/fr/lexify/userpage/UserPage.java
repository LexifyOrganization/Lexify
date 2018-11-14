package parisnanterre.fr.lexify.userpage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.User;

public class UserPage extends Activity implements Serializable{

    User u = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        u = (User) getIntent().getSerializableExtra("user");

        TextView name = findViewById(R.id.activity_user_page_name);
        ImageView edit = findViewById(R.id.edit_profile);
        TextView realName = findViewById(R.id.real_name_val);
        TextView description = findViewById(R.id.tvDescription);
        TextView age = findViewById(R.id.age_val);
        TextView email = findViewById(R.id.e_mail_val);
        TextView mobile = findViewById(R.id.mobile_val);

        LinearLayout ageLayout = findViewById(R.id.age);
        LinearLayout emailLayout = findViewById(R.id.e_mail);
        LinearLayout mobileLayout = findViewById(R.id.mobile);
        LinearLayout realNameLayout = findViewById(R.id.real_name);


        realName.setText(u.getName());
        age.setText(Integer.toString(u.getAge()));
        email.setText(u.get_email());
        mobile.setText(u.getMobile());
        description.setText(u.getDescription());


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
}
