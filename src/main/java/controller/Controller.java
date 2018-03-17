package controller;

import model.Analyzer;
import view.MainView;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {
    MainView view;
    Analyzer analyzer;

    public Controller(MainView view, Analyzer analyzer){
        this.view = view;
        this.analyzer = analyzer;

        analyzer.addObserver(this);
    }


    @Override
    public void update(Observable o, Object arg) {
        System.out.println((String) arg);
    }
}
