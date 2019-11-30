package com.example.myapplication;

import java.util.ArrayList;

public class DataSpinner {
    ArrayList<String> listroom = new ArrayList<>();

    public DataSpinner(ArrayList<String> listroom) {
        this.listroom = listroom;
    }

    public DataSpinner() {
    }

    public ArrayList<String> getListroom() {
        return listroom;
    }

    public void setListroom(ArrayList<String> listroom) {
        this.listroom = listroom;
    }
}
