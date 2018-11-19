package parisnanterre.fr.lexify.connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import parisnanterre.fr.lexify.application.MainActivity;
import parisnanterre.fr.lexify.R;
import parisnanterre.fr.lexify.database.DatabaseUser;
import parisnanterre.fr.lexify.database.User;

import static parisnanterre.fr.lexify.application.MainActivity.currentUser;
//import static parisnanterre.fr.lexify.application.MainActivity.lastUser;
import static parisnanterre.fr.lexify.application.MainActivity.userList;

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
        setContentView(R.layout.activity_signin);

        btn_signin = (Button) findViewById(R.id.signin_btn_login);
        btn_signup = (Button) findViewById(R.id.signin_btn_signup);

        edt_pseudo = (EditText) findViewById(R.id.signin_edt_name);
        edt_pass = (EditText) findViewById(R.id.signin_edt_password);

        txt_errors = (TextView) findViewById(R.id.signin_txt_errors);

        btn_signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(i);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(v);
            }
        });

        //Toast toast = Toast.makeText(this, lastUser, Toast.LENGTH_SHORT);
        //toast.show();

    }

    public void SignIn(View v) {

        String pseudo = edt_pseudo.getText().toString();
        String pass = edt_pass.getText().toString();

        txt_errors.setText("");

        if (pseudo.length() == 0) {
            txt_errors.append("Please enter your pseudo \n");
        }

        if (pass.length() == 0) {
            txt_errors.append("Please enter your password \n");
        }

        if (txt_errors.length() == 0) {
            List<User> users = db.getAllUsers();

            for (User u : users) {
                if (u.get_pseudo().equals(pseudo) && u.get_pass().equals(pass)) {
                    MainActivity.currentUser = u;
                }
            }

            if (MainActivity.currentUser == null) {
                txt_errors.append("Can't find this account, please check if the informations you entered are correct");
            } else {
                Context context = getApplicationContext();
                CharSequence text = "You are connected";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                updateUserInfo();

                Intent i = new Intent();
                /*Bundle b = new Bundle();
                b.putSerializable("Current user", currentUser);
                i.putExtras(b);*/
                i.setClass(this, MainActivity.class);
                startActivity(i);
            }

        }


    }

    public void updateUserInfo(){
        /*try{
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.json");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            //checks if json is empty by checking the content and file size
            //if yes, fills the userList with users from the local DB
            //else, fills it with the json file content
            if (objectInputStream.toString().equals("{}") || objectInputStream.available()==0){
                final DatabaseUser db = new DatabaseUser(this);
                List<User> tmplist = db.getAllUsers();
                final int size = tmplist.size();
                for (int i = 0; i < size; i++) {
                    userList.put(tmplist.get(i).get_id(), tmplist.get(i));
                }
            }
            else{
                //userList is a Hashmap<Integer,User> where the key is the _id from the User object
                userList = (HashMap<Integer,User>) objectInputStream.readObject();
            }
            MainActivity.currentUser = userList.get(this.currentUser.get_id());
            objectInputStream.close();
            fileInputStream.close();
            Toast toast_tmp = Toast.makeText(getApplicationContext(), String.valueOf(MainActivity.currentUser.get_gamesPlayed()), Toast.LENGTH_SHORT);
            toast_tmp.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ClassCastException e ) {
            e.printStackTrace();
        }*/

        try {
            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString("userList", "");
            Type type = new TypeToken<HashMap<Integer, User>>(){}.getType();
            //userList is a Hashmap<Integer,User> where the key is the _id from the User object
            userList = gson.fromJson(json, type);
            if (json.equals("") || userList.isEmpty()) {
                final DatabaseUser db = new DatabaseUser(this);
                List<User> tmplist = db.getAllUsers();
                final int size = tmplist.size();
                for (int i = 0; i < size; i++) {
                    userList.put(tmplist.get(i).get_id(), tmplist.get(i));
                }
            }
            //Put in the global variable CurrentUser the value of the just logged in user
            MainActivity.currentUser = userList.get(this.currentUser.get_id());
            Toast toast_tmp = Toast.makeText(getApplicationContext(), String.valueOf(MainActivity.currentUser.get_gamesPlayed()), Toast.LENGTH_SHORT);
            toast_tmp.show();
        } catch (Exception e ){
            e.printStackTrace();
        }
    }

}
