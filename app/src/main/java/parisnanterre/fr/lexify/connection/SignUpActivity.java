package parisnanterre.fr.lexify.connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;

/**
 * Created by piotn_000 on 30/01/2018.
 */

public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Button btn_signup = (Button) findViewById(R.id.signup_btn_signup);
        final EditText edt_pseudo = (EditText) findViewById(R.id.signup_edt_pseudo);
        final EditText edt_pass = (EditText) findViewById(R.id.signup_edt_password);
        final EditText edt_confirm_pass = (EditText) findViewById(R.id.signup_edt_confirmpassword);
        final EditText edt_email = (EditText) findViewById(R.id.signup_edt_email);

        final TextView txt_errors = (TextView) findViewById(R.id.signup_txt_errors);

        final DatabaseUser db = new DatabaseUser(this);


        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String pseudo = edt_pseudo.getText().toString();
                String pass = edt_pass.getText().toString();
                String confirm_pass = edt_confirm_pass.getText().toString();
                String email = edt_email.getText().toString();

                txt_errors.setText("");

                if(!Pattern.compile("^([a-zA-Z0-9-_]{2,36})$", Pattern.CASE_INSENSITIVE).matcher(pseudo).find()
                        && (pseudo.length() >= 1)) {
                    txt_errors.append("Wrong pseudo format \n");
                }

                if(!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email).find()
                        && (email.length() >= 1)) {
                    txt_errors.append("Wrong email format \n");
                }

                if(!pass.equals(confirm_pass) && pass.length()>=4 && confirm_pass.length()>=4) {
                    txt_errors.append("Passwords do not match \n");
                }

                if((confirm_pass.length()>36 || pass.length()>36 || confirm_pass.length()<4 || pass.length()<4) && (confirm_pass.length()>=1 && pass.length()>=1)) {
                    txt_errors.append("Password must have at least 4 characters and at most 36 characters \n");
                }

                if(pseudo.length() == 0)
                {
                    txt_errors.append("You must choose a pseudo \n");
                }

                if(email.length() == 0) {
                    txt_errors.append("You must enter an email \n");
                }

                if(pass.length() == 0)
                {
                    txt_errors.append("You must enter password \n");
                }

                if(confirm_pass.length() == 0)
                {
                    txt_errors.append("You must confirm password \n");
                }


                if(txt_errors.getText().toString().length()==0) {
                    List<User> users = db.getAllUsers();

                    for(User u : users) {
                        if(pseudo.equals(u.get_pseudo())) {
                            txt_errors.append("Pseudo already exist \n");
                        }
                        if(email.equals(u.get_email())) {
                            txt_errors.append("This email is already used \n");
                        }
                    }
                }




                if(txt_errors.getText().toString().length()==0) {
                    db.addUser(new User(edt_pseudo.getText().toString(), edt_email.getText().toString(), edt_pass.getText().toString()));

                    Context context = getApplicationContext();
                    CharSequence text = "Your account " + pseudo + " has been created";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);

                }


                Log.d("Reading: ", "Reading all contacts..");
                List<User> contacts = db.getAllUsers();

                for (User cn : contacts) {
                    String log = "Id: " + cn.get_id() + " ,Pseudo: " + cn.get_pseudo() + " ,Pass: " + cn.get_pass() + " ,Email" + cn.get_email();
                    // Writing Contacts to log
                    Log.d("Name: ", log);

                }
            }
        });


    }

}
