package com.example.dkmb_000.rentbicycle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.com.database.HttpUrlReq;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SummaryView extends Activity {

    private final static String CONST_IS_RENT = "true";
    Intent summaryViewIntent;
    String scannedQrCodeString;
    TextView scannedQrCode, recivedLockerCodeEditText;
    HashMap<String, String> userDataFromJson;
    JsonData userRentBicycleJsonData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        scannedQrCode = (TextView) findViewById(R.id.scannedQrCode);
        recivedLockerCodeEditText = (TextView) findViewById(R.id.recivedLockerCode);

        //odbiór danych z poprzedniej aktywności
        summaryViewIntent = getIntent();
        userDataFromJson = (HashMap<String, String>) summaryViewIntent.getSerializableExtra("userDataFromJsonMap");
        scannedQrCodeString = summaryViewIntent.getStringExtra("scannedQrCode");


        //zapisz kto wypożyczy rower (userName) oraz na podstawie (qrCode)
        //zwróć kod do zamka (lockerCode)
        userRentBicycleJsonData = new JsonData();
        userRentBicycleJsonData.makeJsonObjectRentBicycle(userDataFromJson.get("userName"), scannedQrCodeString, CONST_IS_RENT);
        RentBicycle rentBicycle = new RentBicycle(this);
        rentBicycle.execute(userRentBicycleJsonData.getUserRentBicycleJsonFormat());

    }

    public void onClickGoToChiefView(View v){
        Intent chiefView = new Intent(this, ChiefView.class);
        chiefView.putExtra("userDataFromJsonMap", userDataFromJson);
        startActivity(chiefView);
        this.finish();

    }

    //pobierz klucz do kłódki w oparciu o qrkod


    private class RentBicycle extends AsyncTask<JSONObject, Void, String> {

        private HttpUrlReq httpUrlReq = new HttpUrlReq();
        private UrlConstant urlConstant = new UrlConstant();
        private Context context;
        private ProgressDialog pdCreateNewUser;

        public RentBicycle(Context context) {
            this.context = context;
            pdCreateNewUser = new ProgressDialog(context);
            pdCreateNewUser.setMessage("\tLoading...");
            pdCreateNewUser.setCancelable(false);
            pdCreateNewUser.show();
        }


        @Override
        protected String doInBackground(JSONObject... params) {
            String bicycleLockerCode = httpUrlReq.ConnHttp(params[0], urlConstant.getCurrentRentBicycleUrl());

            return bicycleLockerCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String jSonDataString) {
            super.onPostExecute(jSonDataString);
            pdCreateNewUser.dismiss(); //turn off progress dialog

            //utworzenie nowego parsera
            JsonParser jsonParser = new JsonParser();
            //przekazanie danych do konwersji ze string to json i do hashmapy
            //do odpowiedniej metody
            HashMap<String, String> recivedLockerCode = jsonParser.getLockerCodeParsedJson(jSonDataString);
            if (Integer.parseInt(recivedLockerCode.get("success")) == 1) {
                Toast.makeText(context, recivedLockerCode.get("message"), Toast.LENGTH_LONG).show();
                scannedQrCode.setText(recivedLockerCode.get("lockerCode"));

            } else {
                //wyświetla wiadomości o błędach, jeśli zwróci je api
                recivedLockerCodeEditText.setVisibility(View.GONE);
                scannedQrCode.setText(recivedLockerCode.get("lockerCode"));
                Toast.makeText(context, recivedLockerCode.get("message"), Toast.LENGTH_LONG).show();
            }
        }


    }

}