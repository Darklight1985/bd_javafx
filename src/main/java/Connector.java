import java.sql.*;
import java.util.Scanner;

public class Connector {
    public String typeMaterial;
    protected double yieldStrength;
    public double yieldStrength10;
    public double ultimateStrength;
    public double conditionalUltimateStrength;
    public double conditionalYieldStrength02;
    public double conditionalYieldStrength10=0;

    public void creater (String DB_URL, String DB_Driver, String mark, Double temperatureCalc, String typeIsg){

        Scanner scan = new Scanner(System.in);

        Statement stmt = null;
        Connection connection = null;
        String nameTable = null;
        String nameBase = null;

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
                // System.out.println(rs.getString(1));
                tableCount = tableCount + 1;// получить количество таблиц
            }
            System.out.println("В этой базе находится " + tableCount + "таблиц(ы)");

            String[] tableN = new String[tableCount];
            rs = stmt.executeQuery(sql); //запрос приходится делать заново

            int j = 0;                         //получаем имена таблиц
            while (rs.next())
            {
                tableN[j] = rs.getString(1);
                j++;
            }
            for (int k = 0; k < tableN.length; k++)
               System.out.println(tableN[k]);

              //здесь мы укажем по какому документы нам нужны свойства материала
      //      System.out.println("Введите название таблицы которая вас интересует");
        //    nameTable = scan.next();

            sql = "SELECT * FROM "+typeIsg;
            rs = stmt.executeQuery(sql);

            rs.last();
            if (rs.getDouble("Temperature")<=temperatureCalc) {
                System.out.println("Расчетная температура выше максимальной в БД, выберите другую расчетную температуру");
                   System.exit(0);
            }

            rs.first();
            if (temperatureCalc<20) {
                System.out.println("Расчетная температура ниже 20 С");
            }
               //вытаскиваем данннные для двух температур которые меньше и больше нашей расчетной температуры, затем интерполируем

            if (temperatureCalc>20){
              rs.next();
              while (rs.getDouble("Temperature")<temperatureCalc)
                    rs.next(); }
               double verhnyayaTemp = rs.getDouble("Temperature");
              double verhUltimateStrength = rs.getDouble("Sigmam");
               double verhConditionalYieldStrength02 = rs.getDouble("Sigmat02");
               double verhConditionalYieldStrength10 = rs.getDouble("Sigmat10");

            System.out.println("Температура "+verhnyayaTemp+ " выше расчетной "+temperatureCalc);

            if (temperatureCalc>20){
                 rs.last();
            while (rs.getDouble("Temperature")>=temperatureCalc)
               rs.previous();}
                double nijnyayaTemp = rs.getDouble("Temperature");
            double nijUltimateStrength = rs.getDouble("Sigmam");
            double nijConditionalYieldStrength02 = rs.getDouble("Sigmat02");
            double nijConditionalYieldStrength10 = rs.getDouble("Sigmat10");
            System.out.println("Температура "+verhnyayaTemp+ " ниже расчетной "+temperatureCalc);

            //вытаксиваем данные при комнтатной температуре
            rs.first();
            ultimateStrength=rs.getDouble("Sigmam");
            yieldStrength= rs.getDouble("Sigmat02");
            yieldStrength10 = rs.getDouble("Sigmat10");
            typeMaterial = rs.getString("grouping");
            nijConditionalYieldStrength10 = rs.getDouble("Sigmat10");
            System.out.println("предел текучести 0.2 при 20 С равен "+yieldStrength);
            System.out.println("предел временного сопротивления при 20 С равен "+ultimateStrength);

            //интерполируем прочностные характеристики из БД
            if (temperatureCalc>20) {
                conditionalUltimateStrength = Interpolater(nijnyayaTemp, verhnyayaTemp, nijUltimateStrength, verhUltimateStrength, temperatureCalc);
                conditionalYieldStrength02 = Interpolater(nijnyayaTemp, verhnyayaTemp, nijConditionalYieldStrength02, verhConditionalYieldStrength02, temperatureCalc);
                conditionalYieldStrength10 = Interpolater(nijnyayaTemp, verhnyayaTemp, nijConditionalYieldStrength10, verhConditionalYieldStrength10, temperatureCalc);
            } else {
              conditionalUltimateStrength = ultimateStrength;
              conditionalYieldStrength10 = yieldStrength10;
              conditionalYieldStrength02 = yieldStrength;
            }

            System.out.println("предел текучести 0.2 при " + temperatureCalc+ " равен "+conditionalYieldStrength02);
            System.out.println("предел текучести 1.0 при " + temperatureCalc+ " равен "+conditionalYieldStrength10);
            System.out.println("предел временного сопротивления при " + temperatureCalc+ " равен "+conditionalUltimateStrength);

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


    }


    // функция интерполяции значений между данными БД
      public double Interpolater(double nijnyayaTemp, double verhnyayaTemp, double nijneeSnachenie, double verhneeShachenie, double tempCalc) {
        double snachenie = 0;

        snachenie= ((verhneeShachenie-nijneeSnachenie)/(verhnyayaTemp-nijnyayaTemp))*(tempCalc-nijnyayaTemp)+nijneeSnachenie;

       return snachenie;
      }

}
