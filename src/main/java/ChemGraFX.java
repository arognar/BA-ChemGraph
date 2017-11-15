

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.MoleculeGenerator;
import model.StringGen;
import model.chemGraph.Molecule;

public class ChemGraFX extends Application {
    private String webSMILES = "http://n2s.openmolecules.org/?name=";
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Button generateButton = new Button("generate");
        Button chirality = new Button("chirality");

        Label label = new Label();
        MoleculeGenerator moleculeGenerator = new MoleculeGenerator();
        HBox hBox = new HBox();
        final Molecule[] molecule = new Molecule[1];
        StringGen stringGen = new StringGen();
        VBox vBox = new VBox();
        hBox.getChildren().add(generateButton);
        hBox.getChildren().add(chirality);
        hBox.getChildren().add(label);
        borderPane.setLeft(vBox);
        WebView webView = new WebView();
        webView.getEngine().load(webSMILES+"CCC");
        borderPane.setCenter(webView);
        borderPane.setTop(hBox);
        generateButton.setOnMouseClicked(event -> {
            vBox.getChildren().clear();
            molecule[0] = moleculeGenerator.generate(8);
            molecule[0].printMolecule();
            molecule[0].getNodes().forEach((s, node) -> {
                String smile = stringGen.getSmilesString(node);
                Button b = new Button(smile);
                vBox.getChildren().add(b);
                b.setOnMouseClicked(event1 -> {
                    webView.getEngine().load(webSMILES+smile);
                });
            });
        });
        chirality.setOnMouseClicked(event -> {
            label.setText(""+molecule[0].numberOfChiralityAtoms());
        });

        Scene scene = new Scene(borderPane,400,400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
