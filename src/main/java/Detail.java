
import java.util.Scanner;


public class Detail{

   protected double thickness;
   protected static double temperatureCalc;
   protected Material material;
   protected double nomThickness;
   protected double thicknessPlusDopusk;
   protected double dopuskPress;
   protected double analitThick;

  // Detail (String temperatureCalc) {};

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setAnalitThick(double analitThick) {
        this.analitThick = analitThick;
    }

    public void setThickness(double thickness) {
        this.thickness = thickness;
    }

    public void setNomThickness(double nomThickness) {
        this.nomThickness = nomThickness;
    }

    public void setDopuskPress(double dopuskPress) {
        this.dopuskPress = dopuskPress;
    }


   // public abstract Detail rashotTolshini(Double pressf, Double outDiameter, Double corros,Double minusDopusk,
   //                                       Double cofSvarka, Double radius, Double oval, String capType);

    public void solution(String tempString, String markMaterial, String typeIsg) {

       // Scanner scan = new Scanner(System.in);

        temperatureCalc= Double.parseDouble(tempString);

        Material material = new Material(temperatureCalc, markMaterial, typeIsg);
        material.solution();

        setMaterial(material);

        }

    }





