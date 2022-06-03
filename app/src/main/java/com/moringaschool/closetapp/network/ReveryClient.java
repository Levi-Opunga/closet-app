package com.moringaschool.closetapp.network;

import static com.moringaschool.closetapp.Constants.BASE_URL;
import static com.moringaschool.closetapp.Constants.PUBLIC_KEY;
import static com.moringaschool.closetapp.Constants.SECRET_KEY;

import com.moringaschool.closetapp.Encryption;
import com.moringaschool.closetapp.interfaces.ReveryApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReveryClient {
private static Retrofit retrofit= null;
public static ReveryApi getClient(){
if(retrofit ==null) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    long time = System.currentTimeMillis()/1000;
                    String derivedKey = Encryption.pbkdf2(SECRET_KEY,String.valueOf(time),128,32);
                    Request newRequest  = chain.request().newBuilder()
                            .addHeader("public_key", PUBLIC_KEY)
                            .addHeader("one_time_code",derivedKey)
                            .addHeader("timestamp",String.valueOf(time))
                            .build();
                    return chain.proceed(newRequest);
                }
            })
            .build();
    retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
return retrofit.create(ReveryApi.class);
    }

}
