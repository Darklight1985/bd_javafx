
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class WindowPipe {

    public StraightPipe straightPipe;

    public void setStraightPipe(StraightPipe straightPipe) {
        this.straightPipe = straightPipe;
    }

    public void newWindow (String title) throws IOException {

        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        Parent root = FXMLLoader.load(getClass().getResource("WindowPipe.fxml"));
        Scene scene = new Scene(root);

        ControllerPipe controllerPipe = new ControllerPipe();
       // scene.setFill(Color.CORNFLOWERBLUE);
        window.setScene(scene);

        window.setTitle(title);
        window.setWidth(674);
        window.setHeight(590);

        window.show();
         }
}
