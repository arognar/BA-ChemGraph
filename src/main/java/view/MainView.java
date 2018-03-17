package view;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class MainView extends Region {
    TextField textField = new TextField();
    TextArea textArea = new TextArea();
    TextFlow textFlow = new TextFlow();
    ScrollPane scrollPane = new ScrollPane();

    public MainView(){
        scrollPane.setContent(textFlow);
        scrollPane.setPrefWidth(200);
        scrollPane.setPrefHeight(200);
        //textArea.setEditable(false);
        this.getChildren().add(scrollPane);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Text test = new Text("test\n");
        test.setStyle("-fx-fill: red");
        Text test2 = new Text("test2\n");
        test2.setStyle("-fx-fill: green");
        textFlow.getChildren().add(test);
        textFlow.getChildren().add(test2);
        textFlow.getChildren().add(new Text("test"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));
        textFlow.getChildren().add(new Text("test\n"));


    }


}
