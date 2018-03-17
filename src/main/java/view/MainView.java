package view;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class MainView extends Region {
    TextField textField = new TextField();
    TextArea text = new TextArea();

    public MainView(){
        this.getChildren().add(text);
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test\n");
        text.appendText("test");
        text.appendText("test");
        text.appendText("test");
        text.appendText("test");
        text.clear();
    }
}
