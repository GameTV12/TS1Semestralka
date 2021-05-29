package cz.cvut.fel.myselect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

import cz.cvut.fel.myselect.Fragments.SignUp;
import io.paperdb.Paper;
/**
 * @author Kozhemiakin Viktor
 * Class for mainpage. User sees this page, if she/he is authorized
 */
public class MainActivity extends AppCompatActivity {
    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("MainActivity success", "Application started");
        BottomNavigationItemView changerLang = findViewById(R.id.changeLangId_Bottom);
        BottomNavigationItemView logOut = findViewById(R.id.logOut_Bottom);
        BottomNavigationItemView newPost = findViewById(R.id.writePost_Bottom);

        logOut.setOnClickListener(v->{
            //change fragments
            SharedPreferences userPref = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
            userPref.edit().putBoolean("isLoggedIn", false).apply();
            finish();
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        });

        changerLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SelectAppLangDialog().show(getSupportFragmentManager(), "fragmentDialog");
            }
        });

        SharedPreferences userPref = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        boolean isLoggedIn = userPref.getBoolean("isLoggedIn",false);

        if (!isLoggedIn){
            finish();
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        }

        newPost.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddPostActivity.class));
        });
    }

}