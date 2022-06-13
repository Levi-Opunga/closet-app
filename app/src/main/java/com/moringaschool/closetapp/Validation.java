package com.moringaschool.closetapp;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Validation {
    public static boolean ValidEmail(String input){
        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
    }

    public static boolean ValidPhone(String input){
        return input.length() >= 10;
    }
    public static boolean ValidPassword(String input){
        return input.length() >= 6;
    } public static boolean ValidName(String input){
        return input.length() >= 3;
    }

}
