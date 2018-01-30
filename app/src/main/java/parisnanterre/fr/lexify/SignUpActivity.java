package parisnanterre.fr.lexify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import parisnanterre.fr.lexify.database.DatabaseHandler;
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
        final EditText edt_email = (EditText) findViewById(R.id.signup_edt_email);

        final DatabaseHandler db = new DatabaseHandler(this);


        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                db.addUser(new User(edt_pseudo.getText().toString(), edt_pass.getText().toString(), edt_email.getText().toString()));

                Log.d("Reading: ", "Reading all contacts..");
                List<User> contacts = db.getAllContacts();

                for (User cn : contacts) {
                    String log = "Id: " + cn.get_id() + " ,Pseudo: " + cn.get_pseudo() + " ,Pass: " + cn.get_pass() + " ,Email" + cn.get_email();
                    // Writing Contacts to log
                    Log.d("Name: ", log);

                }
            }
        });


    }

}
