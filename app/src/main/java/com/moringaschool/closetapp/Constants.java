package com.moringaschool.closetapp;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.closetapp.models.Garment;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static List<Garment> RESTORE= new ArrayList<Garment>();
    public static List<Garment> GARMENTS= new ArrayList<Garment>();
    public  final static String BASE_URL = "https://api.revery.ai/console/v1/";
    public  final static String SECRET_KEY = BuildConfig.SECRET_KEY;
    public final static String PUBLIC_KEY = BuildConfig.PUBLIC_KEY;
    public final static String SAVED_CLOTHES="Outfits";



    public final static String uid=  FirebaseAuth.getInstance().getCurrentUser().getUid();

}
