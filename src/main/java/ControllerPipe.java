
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



public class ControllerPipe implements Initializable {

    @FXML private Button btnCalc;
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


    public static final String DB_Driver = "org.h2.Driver";


    public void calcul(ActionEvent actionEvent) {
// вытаскиваем из комбоБокс маркуматерила и тип изготовления
        String selectedMarkMat = (String) comboMat.getSelectionModel().getSelectedItem();
        String selectedTypeIsg = (String) comboType.getSelectionModel().getSelectedItem();

     checked(selectedMarkMat, selectedTypeIsg);

     System.out.println("Давление равно"+pressField.getText());

     StraightPipe straightPipe = new StraightPipe();

     straightPipe.solution(tempField.getText(),selectedMarkMat,selectedTypeIsg);

     straightPipe.rashotTolshini(Double.parseDouble(pressField.getText()),
                Double.parseDouble(fieldOutd.getText()),Double.parseDouble(fieldCor.getText()),
                Double.parseDouble(fieldMinusDopusk.getText()),Double.parseDouble(fieldSvarka.getText()));

        //пропписывание результатов расчетов
        minThick.setText(String.format("%.1f", straightPipe.thickness));
        fieldYs.setText(String.format("%.1f", straightPipe.material.getYieldStrength()));
        fieldYst.setText(String.format("%.2f", straightPipe.material.getConditionalYieldStrength02()));
       fieldYs1.setText(String.format("%.2f", straightPipe.material.getYieldStrength10()));
        fieldYs1t.setText(String.format("%.2f", straightPipe.material.getConditionalYieldStrength10()));
        fieldUs.setText(String.format("%.2f", straightPipe.material.getUltimateStrength()));
        fieldUst.setText(String.format("%.2f", straightPipe.material.getConditionalultimateStrength()));
        fieldFinalThick.setText(String.format("%.2f", straightPipe.nomThickness));
      fieldMinThickPlusDopusk.setText(String.format("%.2f", straightPipe.thicknessPlusDopusk));
        fieldDopusk.setText(String.format("%.2f", straightPipe.material.dopuskStress));
        fieldDopuskPress.setText(String.format("%.2f", straightPipe.dopuskPress));
        fieldAnalitThick.setText(String.format("%.2f", straightPipe.analitThick));
    }
//инициализация окна
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

     comboMat.getItems().clear();

        String dir = System.getProperty("user.dir");
        dir =Path.of(dir)+"/db/";

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
                "ПНАЭГ 7-002-86");
             //   "ЕN 13480-3");


  comboMat.setValue(stringList.get(0));
  choiseMat(new ActionEvent());

    }

    //при выборе материала нужно чтобы добавлял в другой комбобокс список таблиц из БД соответствующей этому материалу
    public void choiseMat(ActionEvent actionEvent) {

        String dir = System.getProperty("user.dir");

        String selectedValue = (String) comboMat.getSelectionModel().getSelectedItem();
        // а это я его просто вывожу в консольку
        System.out.println(selectedValue);

         ArrayList list = sapros("jdbc:h2:/"+ Path.of(dir)+"/db/", DB_Driver, selectedValue);
        comboType.getItems().addAll(list);
        comboType.setValue(list.get(0));

    }


   //метод для получения списка таблиц в БД
    public ArrayList sapros (String DB_URL, String DB_Driver, String mark){

        Scanner scan = new Scanner(System.in);

        Statement stmt = null;
        Connection connection = null;
        String nameBase = null;
        ArrayList<String> list = new ArrayList<String>();

        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД

            //имя базы должно совпадать с маркой материала
            nameBase = mark;


            connection = DriverManager.getConnection(DB_URL+nameBase);//соединениесБД
            System.out.println("Соединение с СУБД выполнено.");

            // STEP 3: Execute a query
            stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            String sql = "SHOW TABLES";

            //  stmt.executeQuery(sql);
            ResultSet rs = stmt.executeQuery(sql);

            int tableCount = 0;

            while (rs.next()) {

                tableCount = tableCount + 1;// получить количество таблиц
            }
            System.out.println("В этой базе находится " + tableCount + "таблиц(ы)");

            rs = stmt.executeQuery(sql); //запрос приходится делать заново

                       //получаем имена таблиц
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

            list.forEach(s -> System.out.println(s));

            rs.close();
            System.out.println("");
            System.out.println("Отключение от СУБД выполнено.");

        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try{
                if(stmt!=null) stmt.close();
            } catch(SQLException se2) {
            } // nothing we can do
            try {
                if(connection!=null) connection.close();
            } catch(SQLException se){
                se.printStackTrace();
            } //end finally try
        } //end try
        System.out.println("Goodbye!");


        return list;

    }
//метод проверки данных в окне, с вызовом окна предупреждений
    public static boolean isDouble(String s,String param){

        if (s==null || s.equals("")){
            System.out.println("Параметр "+param+" не задан");
            Warning warning = new Warning();
            warning.newWindow("Параметр "+param+" не задан");
            return false;
        }

        try{
            double iVal = Double.parseDouble(s);
            if (param.equals("Расчетное давление")&& (iVal<=0)){
                System.out.println(param+" должно быть больше 0");
                Warning warning = new Warning();
                warning.newWindow(param+" должно быть больше 0");
            return false;}
            else
            return true;
        }
        catch (NumberFormatException e){
            System.out.println("В окно "+param+" необходимо ввести число");
            Warning warning = new Warning();
            warning.newWindow("В окно "+param+" необходимо ввести число");
        }
        return false;
    }

    //проверка заполненности формы
    public  void checked(String selectedTypeIsg, String selectedMarkMat){

             if ((!isDouble(pressField.getText(),"Расчетное давление")) ||
                (!isDouble(tempField.getText(),"Расчетная температура"))||
                (!isDouble(fieldSvarka.getText(),"Коэффициент соединения"))||
                (!isDouble(fieldOutd.getText(),"Наружный диаметр"))||
                (!isDouble(fieldCor.getText(),"Прибавка на коррозию"))||
                (!isDouble(fieldMinusDopusk.getText(),"Отрицательный допуск")))
        {
            return;
        }


        if (selectedTypeIsg.equals(null) || selectedMarkMat.equals(null)){
            // System.exit(1);
            System.out.println("Не выбран материал");
            Warning warning = new Warning();
            warning.newWindow("Не выбрана марка материала");
             return;
        }


    }




}
