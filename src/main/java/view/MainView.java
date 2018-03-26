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

public class MainView extends Region {
    private TextField textField = new TextField();
    private Button generateButton;
    private Button clearButton;
    private Text testCaseText;


    private TextFlow textFlow = new TextFlow();
    ScrollPane scrollPane = new ScrollPane();
    private VBox controllsContainer;
    private HBox mainInputContainer;
    private HBox utilityContainer = new HBox();

    public Button testButton;

    public MainView(){
        this.setPrefHeight(400);
        this.setPrefWidth(400);

        initContainer();
        initFieldsAndButtons();

    }

    private void initContainer(){
        controllsContainer = new VBox();
        scrollPane = new ScrollPane();
        mainInputContainer = new HBox();
        utilityContainer = new HBox();

        scrollPane.setContent(textFlow);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(200);




        this.getChildren().add(controllsContainer);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        controllsContainer.getChildren().add(mainInputContainer);

        HBox.setHgrow(textField, Priority.ALWAYS);

        controllsContainer.getChildren().add(scrollPane);
        controllsContainer.getChildren().add(utilityContainer);

    }

    private void initFieldsAndButtons(){

        clearButton = new Button("Lösche TextBox");
        generateButton = new Button("Generiere Stereoisomere");
        testButton = new Button("testButton");
        textField = new TextField();
        testCaseText = new Text("Beispiele:");

        textField.setPromptText("Bitte SMILES eingeben");
        textField.setFocusTraversable(false);


        utilityContainer.getChildren().add(clearButton);
        clearButton.setOnMouseClicked(event -> textFlow.getChildren().clear());

        mainInputContainer.getChildren().add(textField);
        mainInputContainer.getChildren().add(generateButton);
        controllsContainer.getChildren().add(testCaseText);
        controllsContainer.getChildren().add(testButton);
    }


    public Button getGenerateButton() {
        return generateButton;
    }

    public TextField getTextField() {
        return textField;
    }

    public void addToTextflow(String message){
        textFlow.getChildren().add(new Text(message+"\n"));
    }


}
