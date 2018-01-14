package com.example.dkmb_000.rentbicycle.com.database;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.ChiefView;
import com.example.dkmb_000.rentbicycle.JsonParser;
import com.example.dkmb_000.rentbicycle.UrlConstant;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by dawidk on 10.08.2017.
 */

public class GetAccountBalance extends AsyncTask<JSONObject, Void, String>{

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

        String userNameAndAccountBalanceFromApi = httpUrlReq.ConnHttp(params[0], urlConstant.getCurrentUpdateBalanceUrl());

        return userNameAndAccountBalanceFromApi;


    }


    @Override
    protected void onPostExecute(String jSonDataString) {
        super.onPostExecute(jSonDataString);
        pdCreateNewUser.dismiss(); //turn off progress dialog

        Log.d(TAG, "name and balance [getAccountBalance]: "+jSonDataString);

        JsonParser jsonParser = new JsonParser();
        HashMap<String,String> userDataFromJson = jsonParser.getParsedJson(jSonDataString);
        //utworzenie nowej aktywności i przekazanie jej parametrów do wyświetlenia


        if (Integer.parseInt(userDataFromJson.get("success")) == 1) {
            Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
            //activity.finish();

            Intent ChiefViewIntent = new Intent(context, ChiefView.class);
            ChiefViewIntent.putExtra("userDataFromJsonMap", userDataFromJson);
            context.startActivity(ChiefViewIntent);


        }else {
            Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
        }



    }
}

