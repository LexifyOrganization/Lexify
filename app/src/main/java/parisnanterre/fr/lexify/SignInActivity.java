package parisnanterre.fr.lexify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;

/**
 * Created by piotn_000 on 30/01/2018.
 */

public class SignInActivity extends Activity {

    User currentUser = null;
    final DatabaseUser db = new DatabaseUser(this);

    Button btn_signin;
    Button btn_playnoaccount;
    Button btn_signup;

    EditText edt_pseudo;
    EditText edt_pass;

    TextView txt_errors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        btn_signin = (Button) findViewById(R.id.signin_btn_login);
        btn_playnoaccount = (Button) findViewById(R.id.signin_btn_playnoaccount);
        btn_signup = (Button) findViewById(R.id.signin_btn_signup);

        edt_pseudo = (EditText) findViewById(R.id.signin_edt_name);
        edt_pass = (EditText) findViewById(R.id.signin_edt_password);

        txt_errors = (TextView) findViewById(R.id.signin_txt_errors);

        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });



    }

    public void SignIn(View v) {

        String pseudo = edt_pseudo.getText().toString();
        String pass = edt_pass.getText().toString();

        txt_errors.setText("");

        if(pseudo.length() == 0)
        {
            txt_errors.append("Please enter your pseudo \n");
        }

        if(pass.length() == 0)
        {
            txt_errors.append("Please enter your password \n");
        }

        if(txt_errors.length()==0) {
            List<User> users = db.getAllUsers();

            for(User u : users) {
                if(u.get_pseudo().equals(pseudo) && u.get_pass().equals(pass)) {
                    currentUser = u;
                }
            }

            if(currentUser==null) {
                txt_errors.append("Can't find this account, please check if the informations you entered are correct");
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "You are connected";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                try {
                    FileOutputStream fileOutputStream = context.openFileOutput("user.txt", Context.MODE_PRIVATE);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(currentUser);
                    objectOutputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                Intent i = new Intent();
                /*Bundle b = new Bundle();
                b.putSerializable("Current user", currentUser);
                i.putExtras(b);*/
                i.setClass(this, MainActivity.class);
                startActivity(i);
            }

        }



    }

}
