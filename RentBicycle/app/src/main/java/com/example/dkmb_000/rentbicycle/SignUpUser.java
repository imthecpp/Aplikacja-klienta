package com.example.dkmb_000.rentbicycle;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.dkmb_000.rentbicycle.com.database.CreateUserDB;

import static android.content.ContentValues.TAG;

public class SignUpUser extends Activity {

    private EditText usernameEditText, userPasswordEditText, userEmailEditText;
    private Validation validation = new Validation();
    private User user = new User();
    private boolean usernameFieldStatus, passwordFieldStatus, emailFieldStatus;
    JsonData newUserDataJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onClickSignUp(View v){
        //get view components
        usernameEditText = (EditText) findViewById(R.id.username);
        userPasswordEditText = (EditText) findViewById(R.id.userPassword);
        userEmailEditText = (EditText)findViewById(R.id.emailaddress);
        //read data typed by user and set them in User class
        user.setUsername(usernameEditText.getText().toString());
        user.setPassword((userPasswordEditText.getText().toString()));
        user.setEmail(userEmailEditText.getText().toString());

        //validate username and password data for our user
        validation.isValidUsrAndPass(user.getUsername(), user.getPassword());
        validation.isValidEmail(user.getEmail());
        //get status of data validation
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
        if(!validation.getEmailFieldStatus()){
            userEmailEditText.setError("Błędny e-mail");
            emailFieldStatus = false;
        }else{
            emailFieldStatus = true;
        }

        if(usernameFieldStatus && passwordFieldStatus && emailFieldStatus){
            //TO - DO
            //Toast.makeText(this, "Do bazy!"+user.getUsername()+user.getEmail()+user.getPassword(), Toast.LENGTH_SHORT).show();
            //pobierz dane z formularza rejestracji i zamień na format json
            if(checkInternetConnection()){
                newUserDataJson = new JsonData();
                newUserDataJson.makeJSonObjectRegister(user.getUsername(), user.getEmail(), user.getPassword() , user.getAccountBalance());

                CreateUserDB createUserDB = new CreateUserDB(this, this);
                //mogę przekazać dane json jako parametr lub konstruktor tutaj
                createUserDB.execute(newUserDataJson.getNewUserJsonFormat());
               // finish();

                //Log.d(TAG, "myMessage: "+user.getAccountBalance());
            }else{
                Log.d(TAG, "nie ma internetu");
                Toast.makeText(this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onClickLoginAct(View v){
        Intent loginUserIntent = new Intent(this, LoginUser.class);
        //myIntent.putExtra("key", value); //Optional parameters
        SignUpUser.this.startActivity(loginUserIntent);

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
