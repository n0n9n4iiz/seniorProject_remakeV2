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
public class allMeetdate extends Fragment {
    View v;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    ArrayList<Data> arrData = new ArrayList<>();
    int idlogin;
    int hn;
    private AdapterAllMeet adapterAllMeet;
    private RecyclerView recyclerView;
    private TextView Empty_date;

    public allMeetdate(int idlogin,int hn) {
        this.idlogin = idlogin;
        this.hn = hn;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_all_meetdate, container, false);

        Empty_date = (TextView) v.findViewById(R.id.empty_view);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_all_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        getAllMeetDate();
        return v;
    }

    private void getAllMeetDate() {
        Call<List<Data>> call = api.getAllMeetDate(idlogin);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                arrData = new ArrayList<>(response.body());
                if(arrData.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    Empty_date.setVisibility(View.VISIBLE);

                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    Empty_date.setVisibility(View.GONE);
                }
                adapterAllMeet = new AdapterAllMeet(getContext(),arrData,idlogin,hn,allMeetdate.this);
                recyclerView.setAdapter(adapterAllMeet);

            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }

}
