package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHistory extends Fragment {
View v;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);

    private ArrayList<Data> itemlist = new ArrayList<>();
    private AdepterHislist adepterHislist;
    private RecyclerView recyclerView;

    private int idlogin;
    private String date;
    private TextView dateshow;
    public ListHistory(int idlogin,String date) {
        this.idlogin = idlogin;
        this.date = date;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_list_history, container, false);
        dateshow = (TextView) v.findViewById(R.id.datehis);
        dateshow.setText(date);
        recyclerView = (RecyclerView)v.findViewById(R.id.recyhlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadlist();
        return v;
    }

    private void loadlist(){
        System.out.println("listhis : "+idlogin);
        System.out.println("listhis : "+date);
        Call<List<Data>> call = api.hislist(idlogin,date);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                itemlist = new ArrayList<>(response.body());
                adepterHislist = new AdepterHislist(itemlist,getContext());
                recyclerView.setAdapter(adepterHislist);
            }


            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }

        });


    }

}
