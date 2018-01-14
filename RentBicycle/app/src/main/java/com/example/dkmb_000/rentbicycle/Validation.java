package com.example.dkmb_000.rentbicycle;

import android.app.Activity;
import android.media.MediaCodec;
import android.view.View;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dkmb_000 on 23.07.2017.
 */

public class Validation {

    private static final short TEXT_LENGTH = 4;
    private boolean usernameFieldStatus, passwordFieldStatus, emailFieldStatus;

    public void isValidUsrAndPass(String usernameField, String passwordField){
        if (usernameField.isEmpty() || usernameField.length() < TEXT_LENGTH) {
            usernameFieldStatus = false;
        }else{
            usernameFieldStatus = true;
        }

        if(passwordField.isEmpty() || passwordField.length() < TEXT_LENGTH){
            passwordFieldStatus = false;
        }else{
            passwordFieldStatus = true;
        }

    }

    public void isValidEmail(String email){

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        emailFieldStatus = matcher.matches();
    }


    //getters
    public boolean getUsernameFieldStatus() {
        return usernameFieldStatus;
    }

    public boolean getPasswordFieldStatus() {
        return passwordFieldStatus;
    }

    public boolean getEmailFieldStatus() {
        return emailFieldStatus;
    }
}
