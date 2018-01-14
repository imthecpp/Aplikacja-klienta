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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.com.database.HttpUrlReq;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class AccountView extends Activity {

    Spinner pricesSpinner;
    AccountBalanceHelper accountBalanceHelper = new AccountBalanceHelper();
    Intent AccountViewIntent;
    HashMap<String, String> userDataFromJson;
    TextView failTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_view);
        failTextView = (TextView)findViewById(R.id.failinfo);
        failTextView.setVisibility(View.GONE);

        //odbiór danych z aktywności ChiefView
        AccountViewIntent = getIntent();
        userDataFromJson = (HashMap<String, String>)AccountViewIntent.getSerializableExtra("accountBalanceHM");

        //utworzene spinnera
        pricesSpinner = (Spinner) findViewById(R.id.rates);

    }


    public void onClickGetPrice(View v){
        //wyświetla okno pop up dla użytkownika

        //pobieramy aktualny stan konta z przekazanej hashmapy i rzutujemy ze string na int
        int currentAccountBalance = Integer.parseInt(userDataFromJson.get("accountBalance"));
        Log.d(TAG, "new account balance[account View]: " + currentAccountBalance);
        accountBalanceHelper.checkCurrentAccountBalance(currentAccountBalance);

        if(checkInternetConnection()) {
            if(accountBalanceHelper.getCurrentAccountBalanceCheck()){
                //sprawdzamy czy obecny stan konta nie jest wyższy od 500 zł
                //jeśli jest większy wyświetlamy wiadomość
                //Toast.makeText(this, "Twój stan konta przekracza dopuszczalny limit!", Toast.LENGTH_LONG).show();
                failTextView.setVisibility(View.VISIBLE);
                failTextView.setText("Twój stan konta przekracza dopuszczalny limit!");
            }else {
                //pobieramy wybraną wartość ze spinnera
                String costFromSpinner = pricesSpinner.getSelectedItem().toString();


                //wywołujemy metodę klasy accountBalancehelper w celu dodania wybranej kwoty
                //doładowania do naszego aktualnego stanu konta
                accountBalanceHelper.updatePriceToAccount(costFromSpinner, currentAccountBalance);
                //pobieramy nową wartość naszego stanu konta z pomocą metody klasy accountBalanceHelper.
                //rzutujemy z powrtem na string i zapisujemy ją w hashmapie userDataFromJason
                userDataFromJson.put("accountBalance", Integer.toString(accountBalanceHelper.getUpdatedAccountBalance()));
                Log.d(TAG, "new account balance hashMap[account View]: " + userDataFromJson.get("accountBalance"));


                //dodajemy uaktualniony stan konta do bazy dla zalogowanego użytkonika
                Log.d(TAG, "zalogowany użytkownik [account View]: " + userDataFromJson.get("userName"));

                JsonData userAccountBalanceUpdateJson = new JsonData();
                userAccountBalanceUpdateJson.makeJsonObjectNameAndAccountBalance(userDataFromJson.get("userName"),
                        userDataFromJson.get("accountBalance"));
                UpdateAccountBalanceDB updateAccountBalanceDB = new UpdateAccountBalanceDB(this, this);
                updateAccountBalanceDB.execute(userAccountBalanceUpdateJson.getUserNameAccountBalanceJsonFormat());
                //przechodzimy do widoku ChiefViewIntent przekazując jej hashmape z uaktualnionym
                //stanem konta

            }
        }else {
            Log.d(TAG, "nie ma internetu");
            //Toast.makeText(this, "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
            failTextView.setVisibility(View.VISIBLE);
            failTextView.setText("Brak połączenia z internetem!");
        }

            Log.d(TAG, "updated account balance[account View]: "+Integer.toString(accountBalanceHelper.getUpdatedAccountBalance()));

    }

    private class UpdateAccountBalanceDB extends AsyncTask<JSONObject, Void, String> {

        HttpUrlReq httpUrlReq = new HttpUrlReq();
        UrlConstant urlConstant = new UrlConstant();
        ProgressDialog pdCreateNewUser;
        Context context;
        Activity activity;

        public UpdateAccountBalanceDB(Context context, Activity activity){
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

            String userAccountBalanceStatusApi = httpUrlReq.ConnHttp(params[0], urlConstant.getCurrentUpdateBalanceUrl());

            return userAccountBalanceStatusApi;
        }


        @Override
        protected void onPostExecute(String jSonDataString) {
            super.onPostExecute(jSonDataString);
            pdCreateNewUser.dismiss(); //turn off progress dialog

            Log.d(TAG, "result to string from API [accountView]: " + jSonDataString);


            JsonParser jsonParser = new JsonParser();
            //przekazanie danych do konwersji ze string to json i załadowanie do hashmapy
            //a następnie zwrócenie wyników
            HashMap<String,String> errorInfo = jsonParser.getUserSuccessInfoParsedJson(jSonDataString);
            //userDataFromJson = jsonParser.getUserSuccessInfoParsedJson(jSonDataString);

            //sprawdzenie wiadomości zwróconych z API
           if (Integer.parseInt(errorInfo.get("success")) == 1) {
               Toast.makeText(context, errorInfo.get("message"), Toast.LENGTH_LONG).show();
               //przechodzimy do widoku ChiefViewIntent przekazując jej hashmape z uaktualnionym
               //stanem konta
               Intent ChiefViewIntent = new Intent(context, ChiefView.class);
               ChiefViewIntent.putExtra("userDataFromJsonMap", userDataFromJson);
               context.startActivity(ChiefViewIntent);
               activity.finish();

            }else {
                //wyświetla wiadomości o błędach, jeśli zwróci je api
                Toast.makeText(context, errorInfo.get("message"), Toast.LENGTH_LONG).show();
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
