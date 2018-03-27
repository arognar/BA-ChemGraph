

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Analyzer;
import view.MainView;

/**
 * Startklasse des Programms.
 * Zusammenspiel aus Model,View und Controller.
 * Für die Berechnung der Moleküle, eine Darstellung auf dem Bildschirm und die Interaktion.
 */
public class ChemGraFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainView view = new MainView();
        Analyzer analyzer = new Analyzer();
        Controller controller = new Controller(view,analyzer);
        Scene scene = new Scene(view,400,444);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
