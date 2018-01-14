package com.example.dkmb_000.rentbicycle;


import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by dawidk on 10.08.2017.
 */

public class AccountBalanceHelper {

    //składowe
    private int updatedAccountBalance;
    private boolean currentAccountBalanceCheck;
    private final static int CONST_PRICE_10 = 10;
    private final static int CONST_PRICE_20 = 20;
    private final static int CONST_PRICE_50 = 50;
    private final static int CONST_PRICE_100 = 100;

    //metoda aktualizująca konto w zależności od wybranej kwoty
    //doładowania
    public void updatePriceToAccount(String price, int currentAccountBalance){

        if(price.equals("10 złotych")){
            //Log.d(TAG, "account balance [account balance helper]: "+currentAccountBalance);
            updatedAccountBalance = CONST_PRICE_10 + currentAccountBalance;
        }else if(price.equals(("20 złotych"))){
            updatedAccountBalance = CONST_PRICE_20 + currentAccountBalance;
        }else if(price.equals("50 złotych")){
            updatedAccountBalance = CONST_PRICE_50 + currentAccountBalance;
        }else if(price.equals("100 złotych")){
            updatedAccountBalance = CONST_PRICE_100 + currentAccountBalance;
        }else{
            updatedAccountBalance = 0;
        }

    }
    //sprawdza czy został przekroczony dopuszczalny limit doładowania
    public boolean checkCurrentAccountBalance(int currentAccountBalance){

        Log.d(TAG, "account balance check [account balance helper]: "+currentAccountBalance);
        if (currentAccountBalance >= 500){
            currentAccountBalanceCheck = true;
        }else{
            currentAccountBalanceCheck = false;
        }

        return currentAccountBalanceCheck;
    }

    //metoda zwracające zaktualizowany stan konta
    public int getUpdatedAccountBalance() {
        return updatedAccountBalance;
    }

    public boolean getCurrentAccountBalanceCheck() {
        return currentAccountBalanceCheck;
    }
}
