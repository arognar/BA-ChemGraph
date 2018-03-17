

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.Analyzer;
import model.MoleculeGenerator;
import model.StringGen;
import model.chemGraph.Molecule;
import model.graph.Node;
import view.MainView;

public class ChemGraFX extends Application {
    private String webSMILES = "http://n2s.openmolecules.org/?name=";
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*BorderPane borderPane = new BorderPane();
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
            molecule[0] = moleculeGenerator.generate(5);
            //molecule[0].printMolecule();
            System.out.println("TEST ISOMORPHISM "+molecule[0].test());
            molecule[0].getNodes().forEach((s, node) -> {
                String smile = stringGen.getSmilesString(node);
                Button b = new Button(smile);
                vBox.getChildren().add(b);
                b.setOnMouseClicked(event1 -> {
                    webView.getEngine().load(webSMILES+smile);
                });
            });
            molecule[0] = moleculeGenerator.generate2(7);
            final Node[] n = new Node[1];
            molecule[0].getNodes().forEach((s, node) -> {
                n[0] = node;
            });
            String smile = stringGen.getSmilesString(n[0]);
            Button b = new Button(molecule[0].test());
            vBox.getChildren().add(b);
            b.setOnMouseClicked(event1 -> {
                System.out.println(smile);
                webView.getEngine().load(webSMILES+smile);
            });

        });
        chirality.setOnMouseClicked(event -> {
            label.setText(""+molecule[0].numberOfChiralityAtoms());
        });

        String s = "double";
        char c = 'c';
        byte[] b = s.getBytes();
        System.out.println(b);
        System.out.println(Integer.toBinaryString(b[0]));
        System.out.println(Integer.parseInt(Integer.toBinaryString(b[0]),2));

        System.out.println(Integer.parseInt(Integer.toBinaryString(b[1]),2));
        System.out.println(Integer.parseInt(Integer.toBinaryString(b[1]),2));
        System.out.println(Integer.parseInt(Integer.toBinaryString(b[2]),2));

        */
        MainView view = new MainView();
        Analyzer analyzer = new Analyzer();
        Controller controller = new Controller(view,analyzer);
        Scene scene = new Scene(view,400,444);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
