package com.example.letseat.Control;


import android.app.ProgressDialog;

import com.example.letseat.Boundary.LoginUI;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class SystemControl {

    private static AccountValidation av = new AccountValidation();


    public static int checkInputRequirement(String email, String password, String cfmPassword, String username){
        return av.checkUserInput(email, password, cfmPassword, username);
    }

    public static void setUpNewAccount(String username){
        av.setUpNewAccounts(username);
    }

    //DNU
    public static void verifyUser(String email, String password, ProgressDialog pd){
        av.validateUser(email,password, pd);
    }

    public static ArrayList<String> getUserDetails(DataSnapshot ds){
        return av.getAllUserDetails(ds);
    }

    public static void setUserDetails(int choice, String value, ProgressDialog pd){
        /*
        Choice 0: Name
        Choice 1: Email
         */
        av.setUserDetailsAV(choice, value, pd);
    }

}
