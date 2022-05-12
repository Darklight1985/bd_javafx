import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControllerCap extends ControllerPipe implements Initializable {

    @FXML
    private Button btnCalc;
    @FXML private ComboBox comboNorm;
    @FXML private ComboBox comboMat;
    @FXML private ComboBox comboType;
    @FXML private TextField tempField;
    @FXML private TextField minThick;
    @FXML private TextField fieldFinalThick;
    @FXML private TextField fieldMinThickPlusDopusk;
    @FXML private TextField fieldDopusk;
    @FXML private TextField fieldDopuskPress;
    @FXML private TextField pressField;
    @FXML private TextField fieldOutd;
    @FXML private TextField fieldSvarka;
    @FXML private TextField fieldYs;
    @FXML private TextField fieldYs1;
    @FXML private TextField fieldYst;
    @FXML private TextField fieldYs1t;
    @FXML private TextField fieldUs;
    @FXML private TextField fieldUst;
    @FXML private TextField fieldCor;
    @FXML private TextField fieldMinusDopusk;
    @FXML private TextField fieldAnalitThick;
    @FXML private TextField fieldH;
    @FXML private ComboBox capType;


    public void calcul(ActionEvent actionEvent) {

// вытаскиваем из комбоБокс маркуматерила и тип изготовления
        String selectedMarkMat = (String) comboMat.getSelectionModel().getSelectedItem();
        String selectedTypeIsg = (String) comboType.getSelectionModel().getSelectedItem();
        String selectedTypeCap = (String) capType.getSelectionModel().getSelectedItem();


        checked(selectedMarkMat, selectedTypeIsg);


        System.out.println("Давление равно"+pressField.getText());

        Cap cap = new Cap();


        cap.solution(tempField.getText(),selectedMarkMat,selectedTypeIsg);

        cap.rashotTolshini(Double.parseDouble(pressField.getText()),
                Double.parseDouble(fieldOutd.getText()),Double.parseDouble(fieldCor.getText()),
                Double.parseDouble(fieldMinusDopusk.getText()),Double.parseDouble(fieldSvarka.getText()), Double.parseDouble(fieldH.getText()), selectedTypeCap);

        //пропписывание результатов расчетов
        minThick.setText(String.format("%.1f", cap.thickness));
        fieldYs.setText(String.format("%.1f", cap.material.getYieldStrength()));
        fieldYst.setText(String.format("%.2f", cap.material.getConditionalYieldStrength02()));
        fieldYs1.setText(String.format("%.2f", cap.material.getYieldStrength10()));
        fieldYs1t.setText(String.format("%.2f", cap.material.getConditionalYieldStrength10()));
        fieldUs.setText(String.format("%.2f", cap.material.getUltimateStrength()));
        fieldUst.setText(String.format("%.2f", cap.material.getConditionalultimateStrength()));
        fieldFinalThick.setText(String.format("%.2f", cap.nomThickness));
        fieldMinThickPlusDopusk.setText(String.format("%.2f", cap.thicknessPlusDopusk));
        fieldDopusk.setText(String.format("%.2f", cap.material.dopuskStress));
        fieldDopuskPress.setText(String.format("%.2f", cap.dopuskPress));
        fieldAnalitThick.setText(String.format("%.2f", cap.analitThick));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboMat.getItems().clear();

        String dir = System.getProperty("user.dir");
        dir = Path.of(dir)+"/db/";

//получаем список названий БД которые у нас есть и загружаем в КомбоБокс
        DirectoryStream<Path> files = null;
        ArrayList<String> list = new ArrayList<String>();

//получаем список файлов в папке базы данных и режем расширение файлов
        try {
            files = Files.newDirectoryStream(Path.of(dir));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Path path : files){
            String str =path.getFileName().toString();
            int index = str.indexOf(".");
            list.add(str.substring(0,index));}

        //удаляем повторяющиеся названия и снова получаем список
        Stream<String> stringStream = list.stream();
        Stream<String> stringStream1= stringStream.distinct();
        List<String> stringList = stringStream1.collect(Collectors.toList());



        //добавляем список в нащ комбобокс c марками материалов
        for (String s: stringList)
            //   System.out.println(s);
            comboMat.getItems().add(s);


        //Добавление норм в комбобокс с видами норм
        comboNorm.getItems().clear();
        comboNorm.getItems().addAll(
                "ПНАЭГ 7-002-86",
                "ЕN 13480-3");


        capType.getItems().clear();
        capType.getItems().addAll(
                "Эллиптическое",
                "Торосферическое", "Полусферическое");


        comboMat.setValue(stringList.get(0));
        choiseMat(new ActionEvent());

    }

}
