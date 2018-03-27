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
    private Button weinSButton;
    private Button butenDiButton;
    private Text testCaseText;


    private TextFlow textFlow = new TextFlow();
    ScrollPane scrollPane = new ScrollPane();
    private VBox controllsContainer;
    private HBox mainInputContainer;
    private HBox testCaseContainer;
    private HBox utilityContainer = new HBox();

    public Button testButton;

    public MainView(){
        this.setPrefHeight(400);
        this.setPrefWidth(400);

        initContainer();
        initFieldsAndButtons();
        initLayout();

    }

    private void initLayout() {
        scrollPane.setContent(textFlow);
        this.getChildren().add(controllsContainer);


        controllsContainer.getChildren().add(mainInputContainer);
        controllsContainer.getChildren().add(scrollPane);
        controllsContainer.getChildren().add(utilityContainer);


        utilityContainer.getChildren().add(clearButton);
        mainInputContainer.getChildren().add(textField);
        mainInputContainer.getChildren().add(generateButton);
        controllsContainer.getChildren().add(testCaseText);
        controllsContainer.getChildren().add(testCaseContainer);
        testCaseContainer.getChildren().add(weinSButton);
        testCaseContainer.getChildren().add(butenDiButton);

    }

    private void initContainer(){
        controllsContainer = new VBox();
        scrollPane = new ScrollPane();
        mainInputContainer = new HBox();
        testCaseContainer = new HBox();
        utilityContainer = new HBox();


        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(200);




        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


        HBox.setHgrow(textField, Priority.ALWAYS);

    }

    private void initFieldsAndButtons(){

        clearButton = new Button("Lösche TextBox");
        generateButton = new Button("Generiere Stereoisomere");
        testButton = new Button("testButton");
        textField = new TextField();
        testCaseText = new Text("Beispiele:");

        weinSButton = new Button("Weinsäure");
        butenDiButton = new Button("Butendisäure");

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

    public TextField getTextField() {
        return textField;
    }

    public void addToTextflow(String message){
        textFlow.getChildren().add(new Text(message+"\n"));
    }


}
