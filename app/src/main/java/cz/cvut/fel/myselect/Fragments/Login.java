package cz.cvut.fel.myselect.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import cz.cvut.fel.myselect.AuthActivity;
import cz.cvut.fel.myselect.Constant;
//import cz.cvut.fel.myselect.HomeActivity;
import cz.cvut.fel.myselect.MainActivity;
import cz.cvut.fel.myselect.R;
import cz.cvut.fel.myselect.validators.signin.SignInValidator;

/**
 * @author Kozhemiakin Viktor
 * Class for login, a person can login if she/he has account
 * User sees this page, if she/he isn't authorized
 */
public class Login extends Fragment {
    private View view;
    private TextInputLayout layoutEmail, layoutPassword;
    private TextInputEditText txtEmail, txtPassword;
    private TextView txtSignUp;
    private Button btnSignIn;
    private ProgressDialog dialog;
    private SignInValidator signInValidator;

    public Login() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        init();
        return view;
    }

    private void init() {
        layoutPassword = view.findViewById(R.id.inputPass_LoginPage);
        layoutEmail = view.findViewById(R.id.inputLogin_LoginPage);
        txtPassword = view.findViewById(R.id.txtLayoutPasswordSignIn);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        txtEmail = view.findViewById(R.id.txtEmailSignIn);
        btnSignIn = view.findViewById(R.id.btnForLogin_LoginPage);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        signInValidator = SignInValidator.Default;

        txtSignUp.setOnClickListener(v -> {
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new SignUp()).commit();
        });

        btnSignIn.setOnClickListener(v -> {
            //validate fields first
            if (signInValidator.validateEmail(txtEmail.getText().toString()) && signInValidator.validatePassword(txtPassword.getText().toString())) {
                login();
            }
            if (!signInValidator.validateEmail(txtEmail.getText().toString())) {
                layoutEmail.setErrorEnabled(true);
                layoutEmail.setError("Required 4-128 characters and \"@\", \".\"");
            }
            if (!signInValidator.validatePassword(txtPassword.getText().toString())) {
                layoutPassword.setErrorEnabled(true);
                layoutPassword.setError("Required 6-128 characters");
            }
        });


        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!signInValidator.validateEmail(txtEmail.getText().toString())) {
                    layoutEmail.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtPassword.getText().toString().length() > 5) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void login() {
        dialog.setMessage("Logging in");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.LOGIN, response -> {
            //we get response if connection success
            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")) {
                    JSONObject user = object.getJSONObject("user");
                    //make shared preference user
                    SharedPreferences userPref = getActivity().getApplicationContext().getSharedPreferences("user", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("name", user.getString("name"));
                    editor.putInt("id", user.getInt("id"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();
                    //if success
                    startActivity(new Intent(((AuthActivity) getContext()), MainActivity.class));
                    ((AuthActivity) getContext()).finish();
                    Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    Log.v("Login success", "Login success");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }, error -> {
            // error if connection not success
            error.printStackTrace();
            dialog.dismiss();
        }) {

            // add parameters


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("email", txtEmail.getText().toString().trim());
                map.put("password", txtPassword.getText().toString());
                return map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}