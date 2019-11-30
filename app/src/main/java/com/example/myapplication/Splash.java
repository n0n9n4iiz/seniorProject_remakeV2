package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Splash extends Activity {

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    //set api
    Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://obscure-refuge-47108.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    PointService api2 = retrofit2.create(PointService.class);
    static ArrayList<String> listSpin = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        listSpin.add(0,"กรุณาเลือก");
        loadSpiinerData();

        handler = new Handler();

        runnable = new Runnable() {
            public void run() {

                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                CustomIntent.customType(Splash.this,"fadein-to-fadeout");
                finish();
            }
        };
    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }


    public void loadSpiinerData() {

        Call<List<Demo.Contributor>> call = api2.getRoom("1");
        call.enqueue(new Callback<List<Demo.Contributor>>() {

            @Override
            public void onResponse(Call<List<Demo.Contributor>> call, Response<List<Demo.Contributor>> response) {
                List<Demo.Contributor> data = response.body();

                for (Demo.Contributor d : data) {

                    listSpin.add(d.p_name);


                }


            }

            @Override
            public void onFailure(Call<List<Demo.Contributor>> call, Throwable t) {
                System.out.println("call failed");
            }

        });
    }

    ArrayList<String> getListSpin(){
        return listSpin;
    }
}