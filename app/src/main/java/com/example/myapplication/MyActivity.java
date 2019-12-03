package com.example.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyActivity extends Fragment {
View v;
    Context context;
    public static TextView tvEmptyTextView;
    public static RecyclerView mRecyclerView;
    private ArrayList<Data> mDataSet;
    int idlogin;
    int hn;
    String dateToday = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://endproject62.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create(API.class);
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;

    public MyActivity() {
        // Required empty public constructor
    }
    public MyActivity(int idlogin,int hn) {
        this.idlogin = idlogin;
        this.hn = hn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_my, container, false);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("รายการฉัน");
        tvEmptyTextView = (TextView) v.findViewById(R.id.empty_view);
        //etv = (TextView) findViewById(R.id.empty_view2);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mDataSet = new ArrayList<>();
        loadData();

        Button btnaddnew = (Button)v.findViewById(R.id.addnew);
        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewRoomseq();
            }
        });
        return v;
    }


    public void loadData() {

        TextView txtime = (TextView) v.findViewById(R.id.txttime);
        txtime.setText(dateToday);

        Call<List<Data>> call = api.getRoomseqIdDate(idlogin,dateToday);
        call.enqueue(new Callback<List<Data>>() {

            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {

                mDataSet = new ArrayList<>(response.body());

                SwipeRecyclerViewAdapter mAdapter = new SwipeRecyclerViewAdapter(context, mDataSet,MyActivity.this);
                SwipeRecyclerViewAdapter setid = new SwipeRecyclerViewAdapter(idlogin);
                if(SwipeRecyclerViewAdapter.studentList.isEmpty()){
                    mRecyclerView.setVisibility(View.GONE);
                    tvEmptyTextView.setVisibility(View.VISIBLE);

                }else{
                    mRecyclerView.setVisibility(View.VISIBLE);
                    tvEmptyTextView.setVisibility(View.GONE);
                }

                ((SwipeRecyclerViewAdapter) mAdapter).setMode(Attributes.Mode.Single);

                mRecyclerView.setAdapter(mAdapter);

                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        Log.e("RecyclerView", "onScrollStateChanged");
                    }
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {

            }
        });
    }


    public void addnewRoomseq(){
//        Intent intent = new Intent(this,AddnewActivity.class);
//        intent.putExtra("id",idlogin);
//        intent.putExtra("hn",hn);
//        startActivity(intent);
//        CustomIntent.customType(this,"left-to-right");
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new AddnewActivity(idlogin,hn)).addToBackStack(null);
        fragmentTransaction.commit();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("เพิ่มรายการ");
    }

}
