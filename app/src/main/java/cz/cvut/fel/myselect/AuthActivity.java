package cz.cvut.fel.myselect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cz.cvut.fel.myselect.Fragments.Login;

/**
 * @author Kozhemiakin Viktor
 * Page for fragments Login and SignUp
 */
public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,new Login()).commit();
    }
}