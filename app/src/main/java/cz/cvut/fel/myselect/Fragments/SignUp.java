package cz.cvut.fel.myselect.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.cvut.fel.myselect.AuthActivity;
import cz.cvut.fel.myselect.Constant;
import cz.cvut.fel.myselect.MainActivity;
import cz.cvut.fel.myselect.R;
import cz.cvut.fel.myselect.validators.signup.SignUpValidator;

/**
 * @author Kozhemiakin Viktor
 * Page for registration
 */
public class SignUp extends Fragment {
    private View view;
    private TextInputLayout layoutEmail, layoutPassword, layoutConfirm, layoutName;
    private TextInputEditText txtEmail, txtPassword, txtConfirm, txtName;
    private TextView txtSignIn;
    private Button btnSignUp;
    private ProgressDialog dialog;
    private SignUpValidator validator;

    public SignUp() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        return view;
    }

    private void init() {
        layoutPassword = view.findViewById(R.id.inputPass_SignUpPage);
        layoutEmail = view.findViewById(R.id.inputMail_SignUpPage);
        layoutConfirm = view.findViewById(R.id.inputRepeatPass_SignUpPage);
        layoutName = view.findViewById(R.id.inputLogin_SignUpPage);
        txtPassword = view.findViewById(R.id.inputPasswordText_SignUpPage);
        txtConfirm = view.findViewById(R.id.inputPasswordRepeatText_SignUpPage);
        txtSignIn = view.findViewById(R.id.login_here);
        txtEmail = view.findViewById(R.id.inputEmailText_SignUpPage);
        txtName = view.findViewById(R.id.inputLoginText_SignUpPage);
        btnSignUp = view.findViewById(R.id.btnForLogin_SignUpPage);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        validator = SignUpValidator.Default;

        txtSignIn.setOnClickListener(v -> {
            //change fragments
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer, new Login()).commit();
        });

        btnSignUp.setOnClickListener(v -> {
            //validate fields first
            if (validate()) {
                register();
            }
        });


        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!txtEmail.getText().toString().isEmpty()) {
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
                if (txtPassword.getText().toString().length() > 7) {
                    layoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
                    layoutConfirm.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public boolean validate() {
        if (!validator.validateName(txtName.getText().toString())) {
            layoutName.setErrorEnabled(true);
            layoutName.setError("Required 6-32 characters");
            return false;
        }
        if (!validator.validateEmail(txtEmail.getText().toString())) {
            layoutEmail.setErrorEnabled(true);
            layoutEmail.setError("Required 4-128 characters and \"@\", \".\"");
            return false;
        }
        if (!validator.validatePassword(txtPassword.getText().toString())) {
            layoutPassword.setErrorEnabled(true);
            layoutPassword.setError("Required 6-128 characters");
            return false;
        }
        if (!txtConfirm.getText().toString().equals(txtPassword.getText().toString())) {
            layoutConfirm.setErrorEnabled(true);
            layoutConfirm.setError("Password does not match");
            return false;
        }


        return true;
    }

    public void register() {
        dialog.setMessage("Registering");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, Constant.REGISTER, response -> {
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
                    Toast.makeText(getContext(), "Register Success", Toast.LENGTH_SHORT).show();
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
                map.put("name", txtName.getText().toString());
                map.put("password", txtPassword.getText().toString());
                return map;
            }
        };

        //add this request to requestqueue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}