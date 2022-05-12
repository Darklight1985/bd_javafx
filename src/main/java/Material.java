import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Material {


    private final Double temperatureCalc;
    private final String typeIsg;
    protected String typeMaterial;
    private double yieldStrength;
    private double YieldStrength10;
    private double ultimateStrength;
    private double conditionalYieldStrength02;
    private double conditionalYieldStrength10;
    private double conditionalultimateStrength;
    private String markMaterial;
    protected double dopuskStress;
    public static final String DB_Driver = "org.h2.Driver";


    public Material (Double temperatureCalc, String markMaterial, String typeIsg) {
        this.temperatureCalc = temperatureCalc;
        this.markMaterial = markMaterial;
        this.typeIsg = typeIsg;
    }

    public double getYieldStrength() {
        return yieldStrength;
    }

    public double getYieldStrength10() {
        return YieldStrength10;
    }

    public double getUltimateStrength() {
        return ultimateStrength;
    }

    public double getConditionalYieldStrength02() {
        return conditionalYieldStrength02;
    }

    public double getConditionalYieldStrength10() {
        return conditionalYieldStrength10;
    }

    public double getConditionalultimateStrength() {
        return conditionalultimateStrength;
    }


    public double getDopuskStress() {
        return this.dopuskStress;
    }

    public String getTypeMaterial(String markMaterial) {
        return typeMaterial;
    }

    public String getMarkMaterial(String dir) {

        while (!Files.exists(Path.of(Path.of(dir) + "/db/"+markMaterial+".mv.db"))) {;
        Scanner scan = new Scanner(System.in);
        markMaterial= scan.nextLine();
        if (!Files.exists(Path.of(Path.of(dir) + "/db/"+markMaterial+".mv.db")))
        System.out.println("БД для данной марки материала не обнаружено, введите другое наименование или наберите выйти");
        if (markMaterial=="выйти") System.exit(0);
        }

        System.out.println("БД обнаружено");
        return markMaterial;
    }


    public void setDopuskStress (double dopuskStress) {
        this.dopuskStress = dopuskStress;
    }

    public void setYieldStrength (double yieldStrength) {
        this.yieldStrength = yieldStrength;
    }

    public void setUltimateStrength (double ultimateStrength) {
        this.ultimateStrength = ultimateStrength;
    }

    public void setYieldStrength10(double yieldStrength10) {
        this.YieldStrength10 = yieldStrength10;
    }

    public void setConditionalYieldStrength02(double conditionalYieldStrength02) {
        this.conditionalYieldStrength02 = conditionalYieldStrength02;
    }

    public void setConditionalYieldStrength10(double conditionalYieldStrength10) {
        this.conditionalYieldStrength10 = conditionalYieldStrength10;
    }

    public void setConditionalultimateStrength(double conditionalultimateStrength) {
        this.conditionalultimateStrength = conditionalultimateStrength;
    }
    public void setTypeMaterial(String typeMaterial) {
        this.typeMaterial = typeMaterial;
    }


        //метод получения допускаемого напряжения
        public void solution () {

            String dir = System.getProperty("user.dir");

          double dopuskStress = 0.0;
            System.out.println("Введите марку материала");

          //  String mark = getMarkMaterial(dir);

            Connector connector = new Connector();
            connector.creater ("jdbc:h2:/"+ Path.of(dir)+"/db/", DB_Driver, markMaterial, temperatureCalc, typeIsg);



            setYieldStrength(connector.yieldStrength);
            setUltimateStrength(connector.ultimateStrength);
            setConditionalultimateStrength(connector.conditionalUltimateStrength);
            setConditionalYieldStrength02(connector.conditionalYieldStrength02);
            setYieldStrength10(connector.yieldStrength10);
            setConditionalYieldStrength10(connector.conditionalYieldStrength10);
            setTypeMaterial(connector.typeMaterial);

            dopuskStress= Math.min(conditionalYieldStrength02/1.5,conditionalultimateStrength/2.6);
            setDopuskStress(dopuskStress);


          System.out.println("допускаемое напряжение составляет "+dopuskStress);
      }

}
