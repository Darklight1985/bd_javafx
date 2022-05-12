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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerElbow extends ControllerPipe implements Initializable {

    @FXML
    private TextField fieldRadius;
    @FXML private TextField tempField;
    @FXML private TextField fieldCor;
    @FXML private TextField pressField;
    @FXML private TextField fieldOutd;
    @FXML private ComboBox comboMat;
    @FXML private ComboBox comboType;

    @FXML private TextField fieldMinusDopusk;
    @FXML private TextField fieldSvarka;
    @FXML private TextField fieldOval;
    @FXML private TextField minThick;
    @FXML private TextField fieldFinalThick;
    @FXML private TextField fieldMinThickPlusDopusk;
    @FXML private TextField fieldDopusk;
    @FXML private TextField fieldDopuskPress;
    @FXML private TextField fieldYs;
    @FXML private TextField fieldYs1;
    @FXML private TextField fieldYst;
    @FXML private TextField fieldYs1t;
    @FXML private TextField fieldUs;
    @FXML private TextField fieldUst;
    @FXML private TextField fieldAnalitThick;


    @Override
    public void calcul(ActionEvent actionEvent) {
        super.calcul(actionEvent);

        String selectedMarkMat = (String) comboMat.getSelectionModel().getSelectedItem();

        String selectedTypeIsg = (String) comboType.getSelectionModel().getSelectedItem();

        Elbow elbow = new Elbow();
        elbow.solution(tempField.getText(),selectedMarkMat,selectedTypeIsg);

       elbow.rashotTolshini(Double.parseDouble(pressField.getText()),
                Double.parseDouble(fieldOutd.getText()),Double.parseDouble(fieldCor.getText()),
                Double.parseDouble(fieldMinusDopusk.getText()),Double.parseDouble(fieldSvarka.getText()),
               Double.parseDouble(fieldRadius.getText()),Double.parseDouble(fieldOval.getText()));

        minThick.setText(String.format("%.1f", elbow.thickness));
        fieldYs.setText(String.format("%.1f", elbow.material.getYieldStrength()));
        fieldYst.setText(String.format("%.2f", elbow.material.getConditionalYieldStrength02()));
        fieldYs1.setText(String.format("%.2f", elbow.material.getYieldStrength10()));
        fieldYs1t.setText(String.format("%.2f", elbow.material.getConditionalYieldStrength10()));
        fieldUs.setText(String.format("%.2f", elbow.material.getUltimateStrength()));
        fieldUst.setText(String.format("%.2f", elbow.material.getConditionalultimateStrength()));
        fieldFinalThick.setText(String.format("%.2f", elbow.nomThickness));
        fieldMinThickPlusDopusk.setText(String.format("%.2f", elbow.thicknessPlusDopusk));
        fieldDopusk.setText(String.format("%.2f", elbow.material.dopuskStress));
        fieldDopuskPress.setText(String.format("%.2f", elbow.dopuskPress));
        fieldAnalitThick.setText(String.format("%.2f", elbow.analitThick));


    }
}
