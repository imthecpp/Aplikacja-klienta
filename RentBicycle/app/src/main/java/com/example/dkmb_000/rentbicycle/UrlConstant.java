package com.example.dkmb_000.rentbicycle;

/**
 * Created by dkmb_000 on 06.08.2017.
 */

public class UrlConstant {

    //home links...
    //private String currentRegisterUrl = "http://192.168.0.247/webapp/_Android/rentBicycleAPI/RegisterNewUser.php/";
    //private String currentLoginUrl = "http://192.168.0.247/webapp/_Android/rentBicycleAPI/LoginUser.php/";
   // private String currentGetBalanceUrl = "http://192.168.0.247/webapp/_Android/rentBicycleAPI/GetAccountBalance.php/";
    //private String currentUpdateBalanceUrl = "http://192.168.0.247/webapp/_Android/rentBicycleAPI/UpdateAccountBalance.php/";
    //private String currentRentBicycleUrl = "http://192.168.0.247/webapp/_Android/rentBicycleAPI/UpdateBicycleTableWithResult.php/";

    //work links
    //private String currentRegisterUrl = "http://10.40.17.65/api/rentBicycleAPI/RegisterNewUser.php/";
    //private String currentLoginUrl = "http://10.40.17.65/api/rentBicycleAPI/LoginUser.php/";
    //private String currentNameBalanceUrl = "http://10.40.17.65/api/rentBicycleAPI/GetAccountBalanceAndUserName.php/";
    //serwer online links
    private String currentRegisterUrl = "http://rentbicycle.ct8.pl/RegisterNewUser.php/";
    private String currentLoginUrl = "http://rentbicycle.ct8.pl/LoginUser.php/";
    private String currentGetBalanceUrl = "http://rentbicycle.ct8.pl/GetAccountBalance.php/";
    private String currentUpdateBalanceUrl = "http://rentbicycle.ct8.pl/UpdateAccountBalance.php/";
    private String currentRentBicycleUrl = "http://rentbicycle.ct8.pl/UpdateBicycleTableWithResult.php/";

    public String getCurrentRegisterUrl() {
        return currentRegisterUrl;
    }

    public String getCurrentLoginUrl() {
        return currentLoginUrl;
    }

    public String getCurrentBalanceUrl() {
        return currentGetBalanceUrl;
    }

    public String getCurrentUpdateBalanceUrl() {
        return currentUpdateBalanceUrl;
    }

    public String getCurrentRentBicycleUrl() {
        return currentRentBicycleUrl;
    }
}
