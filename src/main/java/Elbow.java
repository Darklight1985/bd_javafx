public class Elbow extends Detail{



    public Elbow rashotTolshini(Double pressCalc, Double outDiameter, Double corros, Double minusDopusk, Double fi, Double radius, Double oval) {

        double k1,k2,k3,y11,y12,y31,y32,b,q,y1,y3,y2,sr1,sr2,sr3,tnij,tverh;

        k1=(4*radius+outDiameter)/(4*radius+2*outDiameter);
        k2=(4*radius-outDiameter)/(4*radius-2*outDiameter);
        k3=1;

        b=pressCalc/(2*material.dopuskStress+pressCalc);
        q=2*b*radius/outDiameter+0.5;

        y11=0.12*(1+Math.sqrt(1+0.4*oval*q/b));
        y31=0.12*(1+Math.sqrt(1+0.4*oval/b));

        y12=0.12*(1+Math.sqrt(1+0.4*oval*q/b));
        y32=0.12*(1+Math.sqrt(1+0.4*oval/b));

        if (material.typeMaterial.equals("carbon")){
         tnij=350;
        tverh=450;}
         else{
        tnij=350;
        tverh=450;}

if (temperatureCalc<=tnij){
    y1=y11;
   y3=y31;}
else if (temperatureCalc>=tverh){
    y1=y12;
    y3=y32;
}
else{
        y1=Interpolater(tnij,tverh,y11,y12,temperatureCalc);
        y3=Interpolater(tnij,tverh,y31,y32,temperatureCalc);}

        y2=y1;

        sr1 = pressCalc * outDiameter*y1*k1 / ((2 * fi * material.dopuskStress) + pressCalc);
        sr2 = pressCalc * outDiameter*y2*k2 / ((2 * fi * material.dopuskStress) + pressCalc);
        sr3 = pressCalc * outDiameter*y3*k3 / ((2 * fi * material.dopuskStress) + pressCalc);

        System.out.println(y2);
        System.out.println(sr1);
        System.out.println(sr2);
        System.out.println(sr3);

        thickness=Math.max(sr1,sr2);
        thickness=Math.max(thickness,sr3);

        setThickness(thickness);

        thicknessPlusDopusk = thickness+corros+minusDopusk;

        //Добавить выбор тощины или выписка из норматива
        nomThickness= thicknessPlusDopusk;

        analitThick=nomThickness-corros-minusDopusk;
        setNomThickness(nomThickness);
        setAnalitThick(analitThick);

        dopuskPress = (nomThickness-corros)*2*fi*material.dopuskStress/((outDiameter-nomThickness)+(nomThickness-corros));

        setDopuskPress(dopuskPress);

        System.out.println(thickness);

        return this;
    }

    public double Interpolater(double nijnyayaTemp, double verhnyayaTemp, double nijneeSnachenie, double verhneeShachenie, double tempCalc) {
        double snachenie = 0;

        snachenie= ((verhneeShachenie-nijneeSnachenie)/(verhnyayaTemp-nijnyayaTemp))*(tempCalc-nijnyayaTemp)+nijneeSnachenie;

        return snachenie;
    }


}
