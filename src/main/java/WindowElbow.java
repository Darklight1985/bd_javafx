import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WindowElbow {





    public void newWindow (String title) throws IOException {

        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        Parent root = FXMLLoader.load(getClass().getResource("WindowElbow.fxml"));
        Scene scene = new Scene(root);

        ControllerElbow controllerElbow = new ControllerElbow();

        window.setScene(scene);

        window.setTitle("Расчет толщин стенок отвода");
        window.setWidth(674);
       window.setHeight(590);

//initialize();

        window.show();
    }



}


