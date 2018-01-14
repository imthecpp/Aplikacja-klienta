package com.example.dkmb_000.rentbicycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class QrCodeView extends Activity {

    HashMap<String, String> userDataFromJson;
    Intent qrCodeViewIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);


        //odbiór danych z poprzedniej aktywności
        qrCodeViewIntent = getIntent();
        userDataFromJson = (HashMap<String, String>)qrCodeViewIntent.getSerializableExtra("userDataFromJsonMap");

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("skanowanie kodu QR");
        //intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.initiateScan();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            if(scanResult.getContents() == null){

            Toast.makeText(this, "Anulowałeś skanowanie!", Toast.LENGTH_SHORT).show();
                Intent accountView = new Intent(this, ChiefView.class);
                accountView.putExtra("userDataFromJsonMap", userDataFromJson);
                startActivity(accountView);

        }else {

                String scannedQrCodeString = scanResult.getContents();
                Intent summaryView = new Intent(this, SummaryView.class);
                summaryView.putExtra("userDataFromJsonMap",userDataFromJson);
                summaryView.putExtra("scannedQrCode", scannedQrCodeString);
                startActivity(summaryView);
                this.finish();
        }

        }else{
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }



}
