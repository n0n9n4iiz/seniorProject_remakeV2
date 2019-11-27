package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class MeetingDateByDate extends Fragment {
View v;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    ArrayList<Data> arrItem;
    RecyclerView recyclerView;
    AdapterMeetingDateByDate adapterMeetingDateByDate;
    String d;
    int id;
    public MeetingDateByDate(int id,String d) {
        // Required empty public constructor
        this.d = d;
        this.id =id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_meeting_date_by_date, container, false);
        System.out.println("String : "+d);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_meet);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loaddata(id,d);

        return v;
    }
    public void  loaddata(int id,String date){
        Call<List<Data>> call = api.getMeetByall(id,date);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                arrItem = new ArrayList<>(response.body());
                System.out.println(arrItem.size());
                adapterMeetingDateByDate = new AdapterMeetingDateByDate(getContext(),arrItem);
                recyclerView.setAdapter(adapterMeetingDateByDate);
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                System.out.println(t);
            }
        });

    }

}
