package com.example.dkmb_000.rentbicycle.com.database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.dkmb_000.rentbicycle.ChiefView;
import com.example.dkmb_000.rentbicycle.JsonParser;
import com.example.dkmb_000.rentbicycle.UrlConstant;

import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by dkmb_000 on 09.08.2017.
 */

public class LoginUserDB extends AsyncTask<JSONObject, Void, String> {

    private Context context;
    private Activity activity;
    private HttpUrlReq httpUrlReq = new HttpUrlReq();
    private UrlConstant urlConstant = new UrlConstant();
    private ProgressDialog pdCreateNewUser;

    public LoginUserDB(Context context, Activity activity){

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

        String loggedUserStringFromApi = httpUrlReq.ConnHttp(params[0], urlConstant.getCurrentLoginUrl());

        return loggedUserStringFromApi;
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String jSonDataString) {
        super.onPostExecute(jSonDataString);

        pdCreateNewUser.dismiss(); //turn off progress dialog
        Log.d(TAG, "login user json from api[login user]: "+jSonDataString);

        JsonParser jsonParser = new JsonParser();
        HashMap<String,String> userDataFromJson = jsonParser.getParsedJson(jSonDataString);
        //utworzenie nowej aktywności i przekazanie jej parametrów do wyświetlenia

        if (Integer.parseInt(userDataFromJson.get("success")) == 1) {
            Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
            //activity.finish();
            Intent ChiefViewIntent = new Intent(context, ChiefView.class);
            ChiefViewIntent.putExtra("userDataFromJsonMap", userDataFromJson);
            context.startActivity(ChiefViewIntent);
            activity.finish();
        }else {
            Toast.makeText(context, userDataFromJson.get("message"), Toast.LENGTH_LONG).show();
        }

    }

}