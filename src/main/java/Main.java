
import java.io.IOException;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//наш основной клася для запуска программы
public class Main extends Application{

    public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage stage) {

        Button buttonPipe = new Button("Труба");
        Button buttonElbow = new Button("Отвод");
        Button buttonCap = new Button("Выпуклое днище(заглушка)");
        Button button3 = new Button("Тройник");

      //  button.setMinSize(50,50);
       // button.setMinSize(100,100);
        buttonPipe.setPrefSize(200,50);
        buttonElbow.setPrefSize(200,50);
        buttonCap.setPrefSize(200,50);
        button3.setPrefSize(200,50);
//buttonPipe.setAlignment(Pos.CENTER);

        FlowPane root = new FlowPane(10,10);

        root.setAlignment(Pos.CENTER);
        root.setOrientation(Orientation.VERTICAL);
        root.getChildren().add(buttonPipe);
        root.getChildren().add(buttonElbow);
        root.getChildren().add(buttonCap);
        root.getChildren().add(button3);


        Scene scene = new Scene(root, 50, 150);

        scene.setFill(Color.GRAY);
           stage.setScene(scene);


       stage.setTitle("Calcul");
        stage.setWidth(300);
        stage.setHeight(300);
        stage.show();



        buttonPipe.setOnAction(event-> {
            try {
                WindowPipe windowPipe = new WindowPipe();
                windowPipe.newWindow("Расчет трубы");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        buttonElbow.setOnAction(event-> {
            try {
                WindowElbow windowElbow = new WindowElbow();
                windowElbow.newWindow("Расчет отвода");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        buttonCap.setOnAction(event-> {
            try {
                WindowCap windowCap = new WindowCap();
                windowCap.newWindow("Расчет эллиптических, торосферических, полусферических днищ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


}
