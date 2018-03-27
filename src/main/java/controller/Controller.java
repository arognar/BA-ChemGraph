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
        this.view.testButton.setOnMouseClicked(event -> {
            String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
            analyzer.analyze(weinsaeure);
        });

        initButtons();
    }

    private void initButtons() {
        view.getGenerateButton().setOnMouseClicked(event -> {
            if(view.getTextField().getText().trim().isEmpty()) {
                view.addToTextflow("Bitte SMILES eingeben");
                return;
            }
            analyzer.analyze(view.getTextField().getText());
        });



        view.getButenDiButton().setOnMouseClicked(event -> {
            String butendisäure = "(C(=O)(O(H))(C(H)(=C(H)(C(=O)(O(H))))))";
            analyzer.analyze(butendisäure);

        });

        view.getWeinSButton().setOnMouseClicked(event -> {
            String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
            analyzer.analyze(weinsaeure);
        });
    }


    @Override
    public void update(Observable o, Object arg) {
        view.addToTextflow((String)arg);
    }
}
