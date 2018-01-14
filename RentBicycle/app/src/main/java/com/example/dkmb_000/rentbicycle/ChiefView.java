package com.example.dkmb_000.rentbicycle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.com.database.GetAccountBalance;
import com.example.dkmb_000.rentbicycle.com.database.HttpUrlReq;
import com.example.dkmb_000.rentbicycle.com.database.LoginUserDB;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class ChiefView extends Activity{

    //dane, które przechodzą do innych widoków
    HashMap<String, String> userDataFromJson;
    TextView userNameEditText, accountBalanceEditText, toLowAccountEditText;
    Intent chiefViewIntent;

    JsonData userAccountBalanceJsonData;
    GetAccountBalance getAccountBalance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chief_view);
        userNameEditText= (TextView)findViewById(R.id.username);
        accountBalanceEditText = (TextView)findViewById(R.id.accountbalance);
        toLowAccountEditText = (TextView) findViewById(R.id.toLowAccount);
        //toLowAccountEditText.setVisibility();

        //odbiór danych z asyncTasku
        chiefViewIntent = getIntent();
        //Bundle intentBundle = intent.getExtras(); wcześniej było potrzebne, przy prymitywnych typach
        userDataFromJson = (HashMap<String, String>)chiefViewIntent.getSerializableExtra("userDataFromJsonMap");



        //Log.d(TAG, "name [chief view]: "+userDataFromJson.get("userName"));
        //próba pobrania samego stanu konta po nazwie użytkownika...
        userAccountBalanceJsonData = new JsonData();
        userAccountBalanceJsonData.makeJsonObjectName(userDataFromJson.get("userName"));
        getAccountBalance = new GetAccountBalance(this, this);
        getAccountBalance.execute(userAccountBalanceJsonData.getUserNameJsonFormat());

        //wyświetlenie stanu konta i nazwy użytkownika w głownym widoku.
        //accountBalanceEditText.setText(userDataFromJson.get("accountBalance"));
        userNameEditText.setText(userDataFromJson.get("userName"));

    }

    //przejście do widoku skanowania qr kodu po naciśnięciu przycisku
    public void onClickGoToQrCodeView(View v){
    if(checkInternetConnection()){
        if(Integer.parseInt(userDataFromJson.get("accountBalance")) <= 10){
            toLowAccountEditText.setVisibility(View.VISIBLE);
        }else{
            Intent qrCodeView = new Intent(this, QrCodeView.class);
            qrCodeView.putExtra("userDataFromJsonMap", userDataFromJson);
            startActivity(qrCodeView);
            this.finish();
        }
    }else{
        toLowAccountEditText.setVisibility(View.VISIBLE);
        toLowAccountEditText.setText("Brak połączenia z internetem!");
    }


    }

    //przejście do widoku doładowania konta po naciśnięciu przycisku
    public void onClickGoToAccountView(View v){

        Intent accountView = new Intent(this, AccountView.class);
        //accountView.putExtra("accountBalanceHM", userDataFromJson);
        accountView.putExtra("accountBalanceHM", userDataFromJson);
        startActivity(accountView);
    }

    public void goToLoginView(View v){
        Intent loginView = new Intent(this, LoginUser.class);
        this.finish();
        //loginView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(loginView);
    }

    public void goToMapView(View v){
        Intent mapView = new Intent(this, MapsActivity.class);
        //this.finish();
        //loginView.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(mapView);
    }


    //klasa aktualizująca bieżący widok, konkretnie stan konta
    private class GetAccountBalance extends AsyncTask<JSONObject, Void, String> {

        private HttpUrlReq httpUrlReq = new HttpUrlReq();
        private UrlConstant urlConstant = new UrlConstant();
        private Context context;
        private Activity activity;
        private ProgressDialog pdCreateNewUser;

        public GetAccountBalance(Context context, Activity activity){

            this.context = context;
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdCreateNewUser = new ProgressDialog(context);
            pdCreateNewUser.setMessage("\tLoading...");
            pdCreateNewUser.setCancelable(false);
            pdCreateNewUser.show();
        }


        @Override
        protected String doInBackground(JSONObject... params) {

            String userAccountBalanceFromApi = httpUrlReq.ConnHttp(params[0], urlConstant.getCurrentBalanceUrl());

            return userAccountBalanceFromApi;


        }


        @Override
        protected void onPostExecute(String jSonDataString) {
            super.onPostExecute(jSonDataString);
            pdCreateNewUser.dismiss(); //turn off progress dialog

            Log.d(TAG, "name and balance [getAccountBalance]: "+jSonDataString);
            //utworzenie nowego parsera
            JsonParser jsonParser = new JsonParser();
            //przekazanie danych do konwersji ze string to json i do hashmapy
            //do odpowiedniej metody. Jest to tymczasowa hashMapa, dzięki której aktualizujemy wartość
            //accountBalance hashMapy głownej "userDataFromJson"
            HashMap<String,String> userDataFromJson_ = jsonParser.getAccountBalanceParsedJson(jSonDataString);

            userDataFromJson.put("accountBalance", userDataFromJson_.get("accountBalance"));
            //userDataFromJson = jsonParser.getAccountBalanceParsedJson(jSonDataString);

            //sprawdzenie wiadomości zwróconych z API
            if (Integer.parseInt(userDataFromJson.get("success")) == 1) {
                Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
                //aktualizacja textview pokazującego aktualny stan konta
                accountBalanceEditText.setText(userDataFromJson.get("accountBalance")+" zł");
                //to tylko test czy asynctask zmienia imie jak chcę...
                //userNameEditText.setText("dawinci");
            }else {
                //wyświetla wiadomości o błędach, jeśli zwróci je api
                Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
            }

        }
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
