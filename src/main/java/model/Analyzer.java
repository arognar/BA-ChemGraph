package model;


public class Analyzer extends java.util.Observable {

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
