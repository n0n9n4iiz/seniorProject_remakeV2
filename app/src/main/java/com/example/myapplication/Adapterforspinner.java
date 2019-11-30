package com.example.myapplication;

import java.util.ArrayList;

public class Adapterforspinner {
    private ArrayList<String> listSpin = new ArrayList<>();
    private ArrayList<Integer> idofListSpin = new ArrayList<>();
    private int currentID;
    private int destID;

    public ArrayList<String> getListSpin() {
        return listSpin;
    }

    public void setListSpin(ArrayList<String> listSpin) {
        this.listSpin = listSpin;
    }

    public ArrayList<Integer> getIdofListSpin() {
        return idofListSpin;
    }

    public void setIdofListSpin(ArrayList<Integer> idofListSpin) {
        this.idofListSpin = idofListSpin;
    }

    public int getCurrentID() {
        return currentID;
    }

    public void setCurrentID(int currentID) {
        this.currentID = currentID;
    }

    public int getDestID() {
        return destID;
    }

    public void setDestID(int destID) {
        this.destID = destID;
    }
}
