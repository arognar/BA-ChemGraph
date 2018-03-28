package controller;

import model.Analyzer;
import view.MainView;
import java.util.Observable;
import java.util.Observer;

/**
 * Liefert die Schnittstelle zwischen Model und View.
 * Verknüpft die Button mit Logik.
 * Überwacht den Analyzer auf Nachrichten (Observer).
 */
public class Controller implements Observer {
    private MainView view;
    private Analyzer analyzer;

    public Controller(MainView view, Analyzer analyzer){
        this.view = view;
        this.analyzer = analyzer;
        analyzer.addObserver(this);
        initButtons();

    }

    /**
     * Fügt den verschiedenen Button die richtige Logik hinzu.
     */
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

        view.getAnalyzeChiralityButton().setOnMouseClicked(event -> {
            if(view.getTextField().getText().trim().isEmpty()) {
                view.addToTextflow("Bitte SMILES eingeben");
                return;
            }
            analyzer.analyzeChirality(view.getTextField().getText());
        });

        view.getWeinSButton().setOnMouseClicked(event -> {
            String weinsaeure = "(C(=O)(O(H))(C(O(H))(H)(C(H)(O(H))(C(=O)(O(H))))))";
            analyzer.analyze(weinsaeure);
        });
    }


    /**
     * Überwacht den Analyzer und übergibt neue Nachrichten an den TextFlow, damit die View den Text anzeigen kann.
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        view.addToTextflow((String)arg);
    }
}
