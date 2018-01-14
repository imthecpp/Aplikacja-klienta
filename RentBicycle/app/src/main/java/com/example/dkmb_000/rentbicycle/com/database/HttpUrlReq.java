package com.example.dkmb_000.rentbicycle.com.database;

import android.util.Log;

import com.example.dkmb_000.rentbicycle.JsonData;
import com.example.dkmb_000.rentbicycle.UrlConstant;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.ContentValues.TAG;


public class HttpUrlReq {


    HttpURLConnection conn;
    URL url = null;
    UrlConstant urlConstant = new UrlConstant();
    InputStream inputStream;
    BufferedOutputStream os;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    public String ConnHttp(JSONObject jSonUserData, String dbUrl) {

        if(jSonUserData != null) {
            try {
                url = new URL(dbUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //utworzenie połączenia
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.connect();
                //wysłanie danych...
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jSonUserData.toString().getBytes());
                os.flush();
                //InputStream is = conn.getInputStream();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //odebranie danych
            try {
                int successHttpCode = conn.getResponseCode();

                if (successHttpCode == HttpURLConnection.HTTP_OK) {
                    inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder resultFromApiString = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        resultFromApiString.append(line);
                    }
                    inputStream.close();
                    Log.d(TAG, "result to string from API: " + resultFromApiString.toString());

                    return (resultFromApiString.toString());

                } else {
                    return ("unsuccesful");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }else
        {
            return "HttpUrl: brak danych";
        }
    return null;
    }

}