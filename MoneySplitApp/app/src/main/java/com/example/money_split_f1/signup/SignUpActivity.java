package com.example.money_split_f1.signup;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.MainActivity;
import com.example.money_split_f1.R;
import com.example.money_split_f1.SuperApplication;
import com.example.money_split_f1.databinding.ActivitySignUpBinding;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;

/**This class contains a sign up activity, such like user name field, email field, password field and button for signup
 * its contains also a network request for creating new account
 *
 * @author Anas Salameh
 * */
public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText username = binding.usernameSignUp;
        TextInputLayout usernameLayout = binding.textInputUsernameSignUp;

        EditText email = binding.emailSignUp;
        TextInputLayout emailLayout = binding.textInputEmailSignup;

        EditText paypal = binding.PaypalSignUp;
        TextInputLayout paypalLayout = binding.textInputPayPalSignup;

        EditText password = binding.passwordSignUp;
        TextInputLayout passwordLayout = binding.textInputPasswordSignUp;

        EditText password2 = binding.passwordSignUp2;
        TextInputLayout passwordLayout2 = binding.textInputPasswordSignUp2;

        Button create = binding.signUP;
        ProgressBar loading = binding.progressBarSignUp;


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSignUp();
            }

            /**This methode checks if username, email and password are valid
             * user name must be longer than 3 characters
             * email must be written in email form that mean its should contain @ and . in email
             * password must be longer or equals to 8 characters
             * then at end make a network request to create the account
             * */
            private void userSignUp() {
                String userName = username.getText().toString().trim();
                String eMail = email.getText().toString().trim().toLowerCase();
                String payPal = paypal.getText().toString().trim();
                String passWord = password.getText().toString().trim();
                String passWord2 = password2.getText().toString().trim();

                if (userName.isEmpty() || userName.length() < 3){
                    username.setError(SuperApplication.getContext().getResources().getString(R.string.username_required));
                    username.requestFocus();
                    return;
                }else{
                    usernameLayout.setHelperText(SuperApplication.getContext().getResources().getString(R.string.required));
                    usernameLayout.setHelperTextColor(ColorStateList.valueOf(Color.rgb(0,115,72)));
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(eMail).matches()){
                    email.setError("E-Mail ist ungültig");
                    email.requestFocus();
                    return;
                }else{
                    emailLayout.setHelperText(SuperApplication.getContext().getResources().getString(R.string.required));
                    emailLayout.setHelperTextColor(ColorStateList.valueOf(Color.rgb(0,115,72)));
                }

                if (passWord.isEmpty()){
                    password.setError(SuperApplication.getContext().getResources().getString(R.string.password_required));
                    password.requestFocus();
                    return;
                }


                if (passWord.length() < 8){
                    password.setError(SuperApplication.getContext().getResources().getString(R.string.invalid_password));
                    password.requestFocus();
                    return;
                }

                passwordLayout.setHelperText(SuperApplication.getContext().getResources().getString(R.string.required));
                passwordLayout.setHelperTextColor(ColorStateList.valueOf(Color.rgb(0,115,72)));

                if (passWord2.isEmpty()){
                    password2.setError(SuperApplication.getContext().getResources().getString(R.string.password_required));
                    password2.requestFocus();
                    return;
                }

                if (!passWord.equals(passWord2)){
                    password2.setError(SuperApplication.getContext().getResources().getString(R.string.identical_passwords));
                    password2.requestFocus();
                    return;
                }

                if(payPal.isEmpty()){
                    payPal = "";
                }


                passwordLayout2.setHelperText(SuperApplication.getContext().getResources().getString(R.string.required));
                passwordLayout2.setHelperTextColor(ColorStateList.valueOf(Color.rgb(0,115,72)));

                loading.setVisibility(View.VISIBLE);


                makeSignUpRequest(userName, passWord, eMail, payPal);
            }


            private void makeSignUpRequest(String name, String pw, String email, String pp){

                //Instantiate a RequestQueue
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
                String url = SuperApplication.getContext().getResources().getString(R.string.api_register,baseUrl);

                JSONObject object = null;
                try {
                    object = new JSONObject().put("email", email).put("password", pw).put("username", name).put("paypalme", pp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Request a JSON response
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        Toast.makeText(SignUpActivity.this, SuperApplication.getContext().getResources().getString(R.string.register_success), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = SuperApplication.getContext().getResources().getString(R.string.register_failure);
                        Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                queue.add(jsonRequest);
            }

        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //back button
    }

}