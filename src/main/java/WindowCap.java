import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowCap {



    public void newWindow (String title) throws IOException {

        Stage window = new Stage();
        window.setResizable(false);
        window.initModality(Modality.APPLICATION_MODAL);

        Parent root = FXMLLoader.load(getClass().getResource("WindowCap.fxml"));
        Scene scene = new Scene(root);

        ControllerElbow controllerElbow = new ControllerElbow();

        window.setScene(scene);

        window.setTitle("Расчет толщин стенок днища");
        window.setWidth(674);
       window.setHeight(590);

//initialize();

        window.show();
    }



}


