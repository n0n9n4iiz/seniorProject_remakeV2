package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface DirectionGraph {
    @GET("directiongraphinfo")
Call<List<DirectionGraphData>> getDirectionGraphInfo();
}
