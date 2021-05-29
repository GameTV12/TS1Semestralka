package cz.cvut.fel.myselect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

/**
 * @author Kozhemiakin Viktor
 * Bottom menu
 */
public class MyBottom extends AppCompatActivity {

    Button bottomNavigationView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bottom);
    }
}