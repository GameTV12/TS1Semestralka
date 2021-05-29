package cz.cvut.fel.myselect;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import cz.cvut.fel.myselect.Fragments.HomeFragment;
import cz.cvut.fel.myselect.Models.Post;
import cz.cvut.fel.myselect.Models.User;
import cz.cvut.fel.myselect.validators.post.PostValidator;

/**
 * @author Kozhemiakin Viktor
 * Class for creating of new post, validates values and makes request
 */
public class AddPostActivity extends AppCompatActivity {
    private Button btnPost;
    private EditText txtTextTitleAdd, txtTextPostAdd;
    private ProgressDialog dialog;
    private SharedPreferences preferences;
    private PostValidator validator;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        init();
    }

    private void init(){
        validator = PostValidator.Default;
        preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        btnPost = findViewById(R.id.btnAddPost);
        txtTextPostAdd = findViewById(R.id.txtTextPostAdd);
        txtTextTitleAdd = findViewById(R.id.txtTextTitleAdd);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        btnPost.setOnClickListener(v -> {
            if(!validator.validateTitle(txtTextTitleAdd.getText().toString())){
                Toast.makeText(this, getString(R.string.max_size_for_title), Toast.LENGTH_SHORT).show();
            }
            else if(validator.validateTextPost(txtTextPostAdd.getText().toString())&&validator.validateTitle(txtTextTitleAdd.getText().toString())){
                post();
            } else {
                Toast.makeText(this, getString(R.string.required_text), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void post(){
        dialog.setMessage("Posting");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,Constant.ADD_POST, response -> {

            try {
                JSONObject object = new JSONObject(response);
                if (object.getBoolean("success")){
                    Log.v("Post success", "Post was added");
                    JSONObject postObject = object.getJSONObject("post");
                    JSONObject userObject = postObject.getJSONObject("user");

                    User user = new User();
                    user.setId(userObject.getInt("id"));
                    user.setUserName(userObject.getString("name")+" "+userObject.getString("lastname"));

                    Post post = new Post();
                    post.setId(postObject.getInt("id"));
                    post.setUser_id(user.getId());
                    post.setTextPost(postObject.getString("textpost"));
                    if (!postObject.getString("texttitle").equals("")){
                        post.setTextPost(postObject.getString("texttitle"));
                    }

                    HomeFragment.arrayList.add(0, post);
                    HomeFragment.recyclerView.getAdapter().notifyItemInserted(0);
                    HomeFragment.recyclerView.getAdapter().notifyDataSetChanged();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();

        }, error -> {
                error.printStackTrace();
                dialog.dismiss();
        }){

            //add token


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = preferences.getString("token", "");
                HashMap<String,String> map = new HashMap<>();
                map.put("Authorization", "Bearer" +token);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("textpost", txtTextPostAdd.getText().toString().trim());
                if (!txtTextTitleAdd.getText().toString().equals("")){
                    map.put("texttitle", txtTextTitleAdd.getText().toString().trim());
                }
                return map;
            }

            // add params
        };

        RequestQueue queue = Volley.newRequestQueue(AddPostActivity.this);
        queue.add(request);
    }

    /**
     *
     * @param view
     */
    public void cancelPost(View view) {
        startActivity(new Intent(AddPostActivity.this, MainActivity.class));
    }
}