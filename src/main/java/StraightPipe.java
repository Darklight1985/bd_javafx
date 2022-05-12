public class StraightPipe extends Detail{


    //расчет толщины стенки для прямой трубы
    public StraightPipe rashotTolshini(Double pressCalc, Double outDiameter, Double corros,
                                       Double minusDopusk, Double fi) {

        // solution(String.valueOf(temperatureCalc));


        thickness = pressCalc * outDiameter / ((2 * fi * material.dopuskStress) + pressCalc);

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