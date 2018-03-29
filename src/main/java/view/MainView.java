package view;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Zustaendig für die grafische Repräsentation.
 */
public class MainView extends Region {
    /**
     * Textfeld fuer die SMILES-Eingabe.
     */
    private TextField textField;

    private Button generateButton;
    private Button clearButton;
    private Button weinSButton;
    private Button butenDiButton;
    private Button analyzeChiralityButton;
    private Button konstitutionButton;
    private Button konformationButton;
    private Button konfigurationButton;

    private Text testCaseText;

    /**
     * Darstellung der generierten Analyse.
     */
    private TextFlow textFlow;

    private ScrollPane scrollPane;
    private VBox controllsContainer;
    private HBox mainInputContainer;
    private HBox testCaseContainer;
    private HBox utilityContainer;
    private VBox analyzeOperationsContainer;

    public MainView() {
        this.setPrefHeight(400);
        this.setPrefWidth(400);

        initContainer();
        initFieldsAndButtons();
        initLayout();

    }

    /**
     * Fuegt die einzelnen Bestandteile so zusammen, dass das gewuenschte GUI erzielt wird.
     */
    private void initLayout() {
        textFlow = new TextFlow();
        scrollPane.setContent(textFlow);
        this.getChildren().add(controllsContainer);

        controllsContainer.getChildren().add(mainInputContainer);
        mainInputContainer.getChildren().add(textField);
        mainInputContainer.getChildren().add(generateButton);

        controllsContainer.getChildren().add(scrollPane);

        controllsContainer.getChildren().add(utilityContainer);

        controllsContainer.getChildren().add(new Text(""));
        controllsContainer.getChildren().add(analyzeOperationsContainer);

        utilityContainer.getChildren().add(clearButton);
        controllsContainer.getChildren().add(new Text(""));
        controllsContainer.getChildren().add(testCaseText);
        controllsContainer.getChildren().add(testCaseContainer);
        testCaseContainer.getChildren().add(weinSButton);
        testCaseContainer.getChildren().add(butenDiButton);

        analyzeOperationsContainer.getChildren().add(analyzeChiralityButton);
        analyzeOperationsContainer.getChildren().add(konfigurationButton);
        analyzeOperationsContainer.getChildren().add(konstitutionButton);
        analyzeOperationsContainer.getChildren().add(konformationButton);

    }

    /**
     * Initialisiert die Container, welche die Buttons und Texte fassen und anordnen.
     */
    private void initContainer() {
        controllsContainer = new VBox();
        scrollPane = new ScrollPane();
        mainInputContainer = new HBox();
        testCaseContainer = new HBox();
        utilityContainer = new HBox();
        analyzeOperationsContainer = new VBox();


        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(200);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


    }

    /**
     * Erstellt die Button und beschriftet sie.
     */
    private void initFieldsAndButtons() {

        clearButton = new Button("Loesche Textfeld-Inhalt");
        generateButton = new Button("Generiere Stereoisomere");
        textField = new TextField();
        HBox.setHgrow(textField, Priority.ALWAYS);
        testCaseText = new Text("Beispiele:");

        weinSButton = new Button("Weinsaeure");
        butenDiButton = new Button("Butendisaeure");

        analyzeChiralityButton = new Button("Überpruefe Chiralitaet");

        konfigurationButton = new Button("Generiere Konfigurations-String");
        konstitutionButton = new Button("Generiere Konstitutions-String");
        konformationButton = new Button("Generiere Konformations-String");

        textField.setPromptText("Bitte SMILES eingeben");
        textField.setFocusTraversable(false);

        clearButton.setOnMouseClicked(event -> textFlow.getChildren().clear());

    }


    public Button getGenerateButton() {
        return generateButton;
    }

    public Button getWeinSButton() {
        return weinSButton;
    }

    public Button getButenDiButton() {
        return butenDiButton;
    }

    public Button getAnalyzeChiralityButton() {
        return analyzeChiralityButton;
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getKonfigurationButton() {
        return konfigurationButton;
    }

    public Button getKonformationButton() {
        return konformationButton;
    }

    public Button getKonstitutionButton() {
        return konstitutionButton;
    }

    public void addToTextflow(String message) {
        textFlow.getChildren().add(new Text(message + "\n"));
    }


}
