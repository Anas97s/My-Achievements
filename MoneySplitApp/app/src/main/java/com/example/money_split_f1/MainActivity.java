package com.example.money_split_f1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.money_split_f1.Repos.EventRepository;
import com.example.money_split_f1.Repos.SharedPrefsRepo;
import com.example.money_split_f1.Guest.GuestActivity;
import com.example.money_split_f1.signup.SignUpActivity;
import com.example.money_split_f1.ui.startScreen.StartActivity;
import com.example.money_split_f1.User.UserData;
import com.google.android.material.textfield.TextInputLayout;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

/**This class contains the main screen of the app.
 * App name, email field, password field, sign up field, and guest view
 * its also make a network request for log in
 *
 * @author  Anas Salameh
 * */
public class MainActivity extends AppCompatActivity {

    Button logIn;
    TextView  signUp, guestGo;
    EditText emailLogIn, password;
    TextInputLayout emailLogInLayout, passwordLayout;
    ProgressBar loading;
    CheckBox keepLogin;
    UserData userData = UserData.getInstance();
    SharedPrefsRepo prefsRepo = SharedPrefsRepo.getInstance();



    //Connects to the server to check weather the login token is still correct
    //returns true upon successful connection to the server and false otherwise
    public void checkLoginStatus(){
        //TODO: make this wait for the network request
        RequestQueue queue = Volley.newRequestQueue(SuperApplication.getContext());
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_user, new SharedPrefsRepo().getServerIp());

        JSONObject object = new JSONObject();
        Log.println(Log.INFO,"NetworkRequestLogin","Checking Login Status");
        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.println(Log.INFO,"NetworkRequestLogin","Token checks out");
                Toast.makeText(MainActivity.this, SuperApplication.getContext().getResources().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
                return;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("NetworkRequestLogin", "Token didn't check out");
                Log.e("NetworkRequestLogin", error.toString());
                Toast.makeText(SuperApplication.getContext(), SuperApplication.getContext().getResources().getString(R.string.timeout), Toast.LENGTH_LONG).show(); //error auch bei falschem pw etc (?)
                prefsRepo.setKeepLogin(false);

            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", UserData.getInstance().getCookie());
                return headers;
            }
        };
        queue.add(jsonRequest);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        if (prefsRepo.setup()){
            ServerIPDialogue dialogue = new ServerIPDialogue();
            dialogue.setCancelable(false);
            dialogue.show(this.getSupportFragmentManager(),"SERVER_IP_DIALOG");
        }




        emailLogIn.setText(prefsRepo.getUserEmail());
        if(prefsRepo.getKeepLogin()){
            checkLoginStatus();

        }
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogIn();
            }

        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp.setTextColor(ColorStateList.valueOf(Color.rgb(157,12,25)));
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        guestGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestGo.setTextColor(ColorStateList.valueOf(Color.rgb(157,12,25)));
                startActivity(new Intent(MainActivity.this, GuestActivity.class));
            }
        });


    }

    private void initView() {
        logIn = (Button) findViewById(R.id.LogIn);
        signUp = (TextView) findViewById(R.id.signUp1);
        guestGo = (TextView) findViewById(R.id.guestGo);

        emailLogIn = (EditText) findViewById(R.id.emailLogIn);
        emailLogInLayout = (TextInputLayout) findViewById(R.id.textInputEmailLogIn);

        password = (EditText) findViewById(R.id.passwordLogIn);
        passwordLayout = (TextInputLayout) findViewById(R.id.textInputPasswordLogIn);

        loading = (ProgressBar) findViewById(R.id.progressBarLogIn);
        keepLogin = (CheckBox) findViewById(R.id.checkBoxKeepLogin);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**This methode is to check if email and password are valid written and make network request*/
    private void userLogIn() {
        String emailUser = emailLogIn.getText().toString().toLowerCase().trim(); //Ignoring Upper case in emails by converting all to lower case
        String passWord = password.getText().toString().trim();


        if (emailUser.isEmpty()){
            emailLogIn.setError(SuperApplication.getContext().getResources().getString(R.string.email_required));
            emailLogIn.requestFocus();
            return;
        }

        if (passWord.isEmpty()){
            password.setError(getResources().getString(R.string.password_required));
            password.requestFocus();
            return;
        }
        loading.setVisibility(View.VISIBLE);

        makeLoginRequest(emailUser, passWord);
        loading.setVisibility(View.INVISIBLE);
    }


    private void makeLoginRequest(String user, String pw){

        //Instantiate a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_login,baseUrl);

        JSONObject object = null;
        try {
            object = new JSONObject().put("email", user).put("password", pw);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "JSONObject error!", Toast.LENGTH_SHORT);
        }

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.opt("message").toString().equals("success")) {
                    userData.setEmail(user);
                    keepLogin();
                    EventRepository.getInstance().updateEventList();
                    try {
                        String token = response.getString("jwt");
                        if (!token.isEmpty()){
                            userData.setToken(token);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    getPaypalMe(user);

                    userData.makeGetProfilePictureRequest();
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERORR", error.toString());
                Toast.makeText(MainActivity.this, "Email oder Passwort ist falsch", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonRequest);
    }

    private void getPaypalMe(String email){
        //Instantiate a RequestQueue
        RequestQueue queue = Volley.newRequestQueue(this);
        String baseUrl = SharedPrefsRepo.getInstance().getServerIp();
        String url = SuperApplication.getContext().getResources().getString(R.string.api_get_paypal,baseUrl);

        JSONObject object = null;
        try {
            object = new JSONObject().put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "JSONObject error!", Toast.LENGTH_SHORT).show();
        }

        //Request a JSON response
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String paypalme = response.opt("paypalme").toString();
                userData.setPaypal(paypalme);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERORR", error.toString());
                Toast.makeText(MainActivity.this, "get paypalme failure", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders(){
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Cookie", userData.getCookie());
                return headers;
            }
        };

        queue.add(jsonRequest);

    }

    private void keepLogin(){
        if (keepLogin.isChecked()){

            prefsRepo.setKeepLogin(true);

        }else {
            prefsRepo.setKeepLogin(false);
        }
    }

    public static class ServerIPDialogue extends AppCompatDialogFragment {


        public ServerIPDialogue(){

        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setTitle("ServerIP")
                    .setMessage("Bitte geben sie die IP-Adresse des Servers ein auf den Zugegriffen werden soll.\nDiese sollte in etwa so aussehen: " +
                            "serverip.de")
                    .setView(input)
                    .setPositiveButton("Bestätigen", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPrefsRepo.getInstance().setServerIP(input.getText().toString());
                        }
                    });
            return builder.create();
        }
    }

}