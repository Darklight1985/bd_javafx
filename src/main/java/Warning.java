import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


public class Warning extends Application {
    @Override
    public void start(Stage stage) throws Exception {
    }

    public void newWindow(String title) {
        Label oshibka = new Label(title);
        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        FlowPane root = new FlowPane(10, 10);
        root.getChildren().add(oshibka);
        root.setAlignment(Pos.CENTER);
        root.setOrientation(Orientation.VERTICAL);

        Scene scene = new Scene(root, 50, 150);
        Font shrift = new Font("Arial",14);
        oshibka.setFont(shrift);

        scene.setFill(Color.GRAY);
        window.setScene(scene);
        window.setTitle("Warning");
        window.setWidth(550);
        window.setHeight(100);
        window.show();
    }
}
