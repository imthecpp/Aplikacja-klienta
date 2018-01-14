package com.example.dkmb_000.rentbicycle;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dkmb_000 on 06.08.2017.
 */

public class JsonData {

    private JSONObject newUserJsonFormat;
    private JSONObject loginUserJsonFormat;
    private JSONObject userNameJsonFormat;
    private JSONObject userNameAccountBalanceJsonFormat;
    private JSONObject userRentBicycleJsonFormat;


    public void makeJSonObjectRegister(String userName, String userEmail, String userPassword, int accountBalance){

        try{
            newUserJsonFormat = new JSONObject();
            newUserJsonFormat.put("userName", userName );
            newUserJsonFormat.put("userEmail", userEmail);
            newUserJsonFormat.put("userPassword", userPassword );
            newUserJsonFormat.put("accountBalance", accountBalance );
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void makeJSonObjectLogin(String userName, String userPassword){

        try{
            loginUserJsonFormat = new JSONObject();
            loginUserJsonFormat.put("userName", userName );
            loginUserJsonFormat.put("userPassword", userPassword );
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void makeJsonObjectName(String userName){
        try{
            userNameJsonFormat = new JSONObject();
            userNameJsonFormat.put("userName", userName );
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void makeJsonObjectNameAndAccountBalance(String userName, String accountBalance){
        try{
            userNameAccountBalanceJsonFormat = new JSONObject();
            userNameAccountBalanceJsonFormat.put("userName", userName );
            userNameAccountBalanceJsonFormat.put("accountBalance", accountBalance);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void makeJsonObjectRentBicycle(String userName, String qrCode, String isRent){
        try{
            userRentBicycleJsonFormat = new JSONObject();
            userRentBicycleJsonFormat.put("userName", userName );
            userRentBicycleJsonFormat.put("qrCode", qrCode);
            userRentBicycleJsonFormat.put("isRent", isRent);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }




    public JSONObject getNewUserJsonFormat() {
        return newUserJsonFormat;
    }

    public JSONObject getLoginUserJsonFormat() {
        return loginUserJsonFormat;
    }

    public JSONObject getUserNameJsonFormat() {
        return userNameJsonFormat;
    }

    public JSONObject getUserNameAccountBalanceJsonFormat() {
        return userNameAccountBalanceJsonFormat;
    }

    public JSONObject getUserRentBicycleJsonFormat() {
        return userRentBicycleJsonFormat;
    }
}
