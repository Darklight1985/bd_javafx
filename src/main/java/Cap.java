public class Cap extends Detail{


    //расчет толщины стенки для прямой трубы
    public Cap rashotTolshini(Double pressCalc, Double outDiameter, Double corros,
                              Double minusDopusk, Double fi, Double H, String typeCap) {

        // solution(String.valueOf(temperatureCalc));
          int m1 = 4,m2 = 1;
          double m3 = 1;


        switch (typeCap){
            case ("Эллиптическое"):

                 m1=4; m2=1;m3=outDiameter/(2*H);
                 break;
            case ("Торосферическое"):
                m1=4; m2=1;m3=outDiameter/(2*H);
                      break;
            case ("Полусферическое"):
                m1=4; m2=1;m3=1;
            break;

        }

        thickness = (pressCalc * outDiameter*m3) / (m1 * fi * material.dopuskStress - pressCalc)*(1/m2);

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




}