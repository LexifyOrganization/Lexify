package parisnanterre.fr.lexify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import parisnanterre.fr.lexify.database.User;

public class MainActivity extends Activity {

    User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt_welcome = (TextView) findViewById(R.id.activity_main_txt_welcome);

        Button btn_disconnect = (Button) findViewById(R.id.activity_main_btn_disconnect);
        final LinearLayout lil_user = (LinearLayout) findViewById(R.id.activity_main_lil_user);

       /* Bundle b = this.getIntent().getExtras();
        if (b != null)
            currentUser = (User) b.getSerializable("Current user");*/

        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("user.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            currentUser = (User) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        if(currentUser!=null) {
            txt_welcome.setText("Welcome " + currentUser.get_pseudo() + " !");
            lil_user.setVisibility(View.VISIBLE);
        }
        else{
            lil_user.setVisibility(View.GONE);
        }

        Button button = (Button) findViewById(R.id.button);
        //test

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(currentUser!=null) {
                    //go pour le jeu
                }else {
                    Intent i = new Intent(getApplicationContext(),SignInActivity.class);
                    startActivity(i);
                }

            }
        });

        btn_disconnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                currentUser = null;
                lil_user.setVisibility(View.GONE);

                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(getApplicationContext().getFileStreamPath("user.txt"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                writer.print("");
                writer.close();

                Context context = getApplicationContext();
                CharSequence text = "You are disconnected";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });

    }
}
