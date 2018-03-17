package model;


import model.smileParser.SmileParser;

public class Analyzer extends java.util.Observable {
    StringGen stringGen;
    SmileParser smileParser;


    public Analyzer(){

    }

    public void analyze(){

    }

    public void test(){

    }

    private void notifyController(String s){
        setChanged();
        notifyObservers(s);
    }
}
