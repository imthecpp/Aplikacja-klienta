package com.example.dkmb_000.rentbicycle;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by dkmb_000 on 09.08.2017.
 */

public class JsonParser {

    HashMap<String, String> userDataFromJson = new HashMap<>();
    HashMap<String, String> userAccountBalanceFromJson = new HashMap<>();
    HashMap<String, String> userSuccesInfo = new HashMap<>();
    HashMap<String, String> receivedLockerCode = new HashMap<>();

    public HashMap<String, String> getParsedJson(String jsonUserData) {

        try {
            JSONObject dataFromServerJson = new JSONObject(jsonUserData);
            userDataFromJson.put("success", Integer.toString(dataFromServerJson.getInt("success")));
            userDataFromJson.put("message", dataFromServerJson.getString("message"));

            Log.d(TAG, "result to string from json [json parser]: " + userDataFromJson.get("success"));
            if (Integer.parseInt(userDataFromJson.get("success")) == 1) {

                JSONArray newUserApiJsonArray = dataFromServerJson.getJSONArray("user");
                for (int i = 0; i < newUserApiJsonArray.length(); i++) {
                    JSONObject newUserApiJsonObject = newUserApiJsonArray.getJSONObject(i);
                    //userDataFromJson.put("userId", newUserApiJsonObject.getString("userId"));
                    userDataFromJson.put("userName", newUserApiJsonObject.getString("userName"));
                    userDataFromJson.put("userEmail", newUserApiJsonObject.getString("userEmail"));
                    userDataFromJson.put("accountBalance", Integer.toString(newUserApiJsonObject.getInt("accountBalance")));

                }
                //Log.d(TAG, "result to string from json[json parser]: " + userDataFromJson.get("accountBalance"));
                return userDataFromJson;
            } else {
                return userDataFromJson;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userDataFromJson.put("message", "błąd parsera");
        return userDataFromJson;
    }

    public HashMap<String, String> getAccountBalanceParsedJson(String jsonUserData) {
        try {
            JSONObject dataFromServerJson = new JSONObject(jsonUserData);
            userAccountBalanceFromJson.put("success", Integer.toString(dataFromServerJson.getInt("success")));
            userAccountBalanceFromJson.put("message", dataFromServerJson.getString("message"));

            Log.d(TAG, "result to string from json [json parser]: " + userAccountBalanceFromJson.get("success"));
            if (Integer.parseInt(userAccountBalanceFromJson.get("success")) == 1) {

                JSONArray newUserApiJsonArray = dataFromServerJson.getJSONArray("user");
                for (int i = 0; i < newUserApiJsonArray.length(); i++) {
                    JSONObject newUserApiJsonObject = newUserApiJsonArray.getJSONObject(i);
                    userAccountBalanceFromJson.put("accountBalance", Integer.toString(newUserApiJsonObject.getInt("accountBalance")));

                }
                //Log.d(TAG, "result to string from json[json parser]: " + userDataFromJson.get("accountBalance"));
                return userAccountBalanceFromJson;
            } else {
                return userAccountBalanceFromJson;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userAccountBalanceFromJson.put("message", "błąd parsera");
        return userDataFromJson;

    }

    public HashMap<String, String> getUserSuccessInfoParsedJson(String jsonUserData) {
        try {
            JSONObject dataFromServerJson = new JSONObject(jsonUserData);
            userSuccesInfo.put("success", Integer.toString(dataFromServerJson.getInt("success")));
            userSuccesInfo.put("message", dataFromServerJson.getString("message"));

            Log.d(TAG, "result to string from json [json parser]: " + userSuccesInfo.get("success"));
            if (Integer.parseInt(userSuccesInfo.get("success")) == 1) {
                return userSuccesInfo;
            } else {
                return userSuccesInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        userSuccesInfo.put("message", "błąd parsera");

        return userSuccesInfo;
    }

    public HashMap<String, String> getLockerCodeParsedJson(String jsonUserData) {
        try {
            JSONObject dataFromServerJson = new JSONObject(jsonUserData);
            receivedLockerCode.put("success", Integer.toString(dataFromServerJson.getInt("success")));
            receivedLockerCode.put("message", dataFromServerJson.getString("message"));
            receivedLockerCode.put("lockerCode", dataFromServerJson.getString("lockerCode"));

            Log.d(TAG, "result to string from json [json parser]: " + receivedLockerCode.get("success"));

            if (Integer.parseInt(receivedLockerCode.get("success")) == 1) {
                return receivedLockerCode;
            } else {
                return receivedLockerCode;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        receivedLockerCode.put("message", "błąd parsera");

        return receivedLockerCode;
    }


}
