package com.example.dkmb_000.rentbicycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.com.database.CreateUserDB;
import com.example.dkmb_000.rentbicycle.com.database.LoginUserDB;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static android.content.ContentValues.TAG;
public class LoginUser extends Activity {

    private EditText usernameEditText, userPasswordEditText;
    private Validation validation = new Validation();
    private User user = new User();
    private boolean usernameFieldStatus, passwordFieldStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void onClickLogin(View v){
        usernameEditText = (EditText) findViewById(R.id.username);
        userPasswordEditText = (EditText) findViewById(R.id.userPassword);
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword((userPasswordEditText.getText().toString()));

        validation.isValidUsrAndPass(user.getUsername(), user.getPassword());

        if(!validation.getUsernameFieldStatus()){
            usernameEditText.setError("Błędna nazwa");
            usernameFieldStatus = false;
        }else{
            usernameFieldStatus = true;
        }
        if(!validation.getPasswordFieldStatus()){
            userPasswordEditText.setError("Błędne hasło");
            passwordFieldStatus = false;
        }else{
            passwordFieldStatus = true;
        }

        if(usernameFieldStatus && passwordFieldStatus){
            if(checkInternetConnection()){
                JsonData loginUserDataJson = new JsonData();
                loginUserDataJson.makeJSonObjectLogin(user.getUsername(), user.getPassword());

                LoginUserDB loginUserDB = new LoginUserDB(this, this);
                loginUserDB.execute(loginUserDataJson.getLoginUserJsonFormat());
            }else{
                Toast.makeText(this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
            }

        }

    }

    public void onClickNewAccount(View v){
        Intent signUpIntent = new Intent(LoginUser.this, SignUpUser.class);
        //myIntent.putExtra("key", value); //Optional parameters
        LoginUser.this.startActivity(signUpIntent);
        //finish();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.v(TAG, "Internet Connection Not Present");
            return false;
        }
    }
}
