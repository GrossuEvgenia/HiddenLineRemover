package com.example.lr2_kg;

import com.example.lr2_kg.models.Edge;
import com.example.lr2_kg.models.InfoForPolygon;
import com.example.lr2_kg.models.Point;
import com.example.lr2_kg.models.ScanString;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
//import javafx.scene.shape.LineBuilder;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HelloController {
    List<Point> points = new ArrayList<Point>();
    List<InfoForPolygon> polygonList = new ArrayList<>();
    ScanString scanString;
    @FXML
    private Canvas canvas;

    @FXML
    private Canvas canvas1;

    private GraphicsContext graphicsContext;
    private GraphicsContext graphicsContext1;
    private List<Edge> edges;
    private Timeline timeline;
    private List<Edge> drawEdge;
    public HelloController()
    {
        drawEdge = new ArrayList<>();
        InfoForPolygon polygon ;
        edges=new ArrayList<>();
         points.add(new Point(50, 25, 50));
        points.add(new Point(50, 125, 50));
        points.add(new Point(125, 125, 50));
        points.add(new Point(125, 25, 50));
        polygon = new InfoForPolygon(points, 1, "red");
        polygonList.add(polygon);
        points.clear();
       points.add(new Point(75,75 , 75));
        points.add(new Point(125, 125, 25));
        points.add(new Point(150, 50, 25));
        polygon = new InfoForPolygon(points, 2, "blue");
        polygonList.add(polygon);
        points.clear();


        points.add(new Point(250, 150,100));
        points.add(new Point(250, 100,100));
        points.add(new Point(100, 100,100));
        points.add(new Point(100, 150,100));
        polygon = new InfoForPolygon(points,3,"green");
       polygonList.add(polygon);
        points.clear();
        points.add(new Point(50,200,70));
        points.add(new Point(134,150,250));
        points.add(new Point(270,110,20));
        polygon = new InfoForPolygon(points,4,"yellow");
        polygonList.add(polygon);
        points.clear();
        points.add(new Point(100,50,150));
        points.add(new Point(200,150,50));
        points.add(new Point(150,50,50));
        polygon=new InfoForPolygon(points, 5, "orange");
        polygonList.add(polygon);
        scanString = new ScanString(findMaxYScanString());

    }
    @FXML
    public void initialize()
    {
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        //канвас для отрисовки ребер
        //доделать
        graphicsContext1=canvas1.getGraphicsContext2D();
        graphicsContext1.translate(canvas1.getWidth()/2, canvas.getHeight()/2);


    }


    //найти наивысшую скан плоскость
    public double findMaxYScanString()
    {
        double max=-Double.MAX_VALUE;
        for(InfoForPolygon p: polygonList)
        {
            if(p.getyMaxScanString()>max){
                max=p.getyMaxScanString();
            }
        }
        return  max;
    }

    //сканирование полгонов
    public void scanPolygon(){
        boolean flag =true;
        int i=0;
        while (flag){
            scanString.findNewActivityEdge(polygonList);
            workScanString();
            for(InfoForPolygon p: polygonList) {
                p.setFlagActivity(0);
            }
            //если есть еще скан плоскости у полигона
            for(InfoForPolygon p: polygonList) {
            if(p.getNumberScanString()>0){
                break;
            }
            flag=false;
            }
            //увеличиваем Y (уменьшаем так как перевернута ось)
            scanString.addY();


        }

    }
    //точка пересечения отрезков
    public double xCrossEdge(double zL1, double zR1, double zL2, double zR2, double xl, double xR){

        double k1=(zR1-zL1)/(xR-xl);
        double k2=(zR2-zL2)/(xR-xl);
        double b1=zL1-k1*xl;
        double b2=zL2-k2*xl;


        return (b2-b1)/(k1-k2);

    }


    //определение то, что необходимо отрисовывать
    public void workScanString() {

        int countPolygon = 0;
        double xLeft = 0;
        double xRight = 0;
        int flag = 0;       //флаг для проверки активного многоугольника
        Edge showEdge = new Edge(new Point(0, 0, 0), new Point(0, 0, 0), 0);
        //список активных многоугольников
        List<InfoForPolygon> activePolygon= new ArrayList<>();

        for (Edge e : scanString.getActivityEdge()) {
            flag = 0;
            xRight = e.getxCrossing();
            activePolygon.clear();

            for (InfoForPolygon p : scanString.getPolygonProcess()) {
                if (p.checkBelonging(xLeft, scanString.getY(), xRight, scanString.getY()) == true) {
                    p.setFlagActivity(1);
                    activePolygon.add(p);
                }
            }

            //изменяем счетчик полигонов
            countPolygon = activePolygon.size();

            //если счетчиков многоугольников не 0, то
            if (countPolygon != 0 ) {

                //если счетчик больше 1
               if (countPolygon > 1) {
                    List<Double> steckCrossing =new ArrayList<>();
                    double zLeft1, zRight1, zLeft2, zRight2;
                    boolean flagCroosing= true;
                    //пока на интервале есть пересечения
                    while (flagCroosing) {
                        zLeft1 = activePolygon.get(0).countZ(xLeft+0.5, scanString.getY());
                        zRight1 = activePolygon.get(0).countZ(xRight+0.5, scanString.getY());

                        zLeft2 = activePolygon.get(1).countZ(xLeft+0.5, scanString.getY());
                        zRight2 = activePolygon.get(1).countZ(xRight+0.5, scanString.getY());

                        //проверка на пересечение
                        if (Math.signum(zLeft1 - zLeft2) != Math.signum(zRight1 - zRight2)) {
                            steckCrossing.add(xRight);
                            xRight = xCrossEdge(zLeft1, zRight1, zLeft2, zRight2, xLeft+0.5, xRight-0.5);


                        }

                        //иначе касаются
                        else {
                            flag=1;

                            double xCentr=(xLeft+xRight)/2;
                            //смотрим какой полигон отрисовывать по max z
                            if(activePolygon.get(0).countZ(xCentr,scanString.getY())>activePolygon.get(1).countZ(xCentr,scanString.getY())){
                                showEdge= new Edge( new Point( xLeft,scanString.getY(),0),
                                                    new Point(xRight,scanString.getY(),0),
                                                     activePolygon.get(0).getIdPolygon());

                            }
                            else {
                                showEdge= new Edge( new Point( xLeft,scanString.getY(),0),
                                        new Point(xRight,scanString.getY(),0),
                                        activePolygon.get(1).getIdPolygon());
                            }
                            //сли стек пересечений пуст
                            if(steckCrossing.isEmpty()){
                                flagCroosing=false;
                            }
                            //берем очередной подинтервал
                            else{
                                drawAll(showEdge);
                                xLeft=xRight;
                                xRight=steckCrossing.get(steckCrossing.size()-1);
                                steckCrossing.remove(steckCrossing.size()-1);
                            }
                        }

                        //избежать обработку вершин
                        if(Math.abs(xLeft-xRight)<1){
                            if(!steckCrossing.isEmpty()){
                                xLeft=xRight;
                                xRight=steckCrossing.get(steckCrossing.size()-1);
                                steckCrossing.remove(steckCrossing.size()-1);
                            }
                            else {
                                flagCroosing=false;
                            }
                        }
                    }

                }
                else {
                    //видимый отрезок - активный многоугольник
                    showEdge = new Edge(new Point(xLeft, scanString.getY(), 0),
                            new Point(xRight, scanString.getY(), 0),
                           scanString.getIdActyvePolygon());
                    flag = 1;
                }

            }


        for (InfoForPolygon p : scanString.getPolygonProcess()) {
            p.setFlagActivity(0);
        }

        //изобразить видимый отрезок
        if (flag == 1) {
           // drawPolygon1(showEdge);
            drawAll(showEdge);
        }
        xLeft = xRight;


        }
       scanString.increaseNumberScanString();
    }

    //выбрать выводимый цвет
    private Color setColor(Edge e){
        String colorString ="";
        Color color = Color.WHITE;
        for (InfoForPolygon p : polygonList) {
            if (p.getIdPolygon() == e.getIdPolygon()) {
                colorString=p.getColor();
                break;
            }
        }
        switch (colorString){
            case "red":
                color=Color.RED;
                break;
            case "blue":
                color=Color.BLUE;
                break;
            case "green":
                color=Color.GREEN;
                break;
            case "yellow":
                color = Color.YELLOW;
                break;
            case "orange":
                color = Color.ORANGE;

        }
        return color;
    }
    //отрисовка на кавасе
    private void drawAll(Edge e1){
        clearCanvas();
        drawCanvas();
        drawPolygon1(e1);
    }
    //отрисовать видимый отрезок
    private void drawPolygon1(Edge e1)
    {

        clearCanvas();
        drawCanvas();
        edges.add(e1);
    Color color = Color.WHITE;
    for(Edge e:edges){

        graphicsContext.setStroke(setColor(e));
        graphicsContext.setLineWidth(2);
        graphicsContext.strokeLine(e.getStart().getX(), (-1)*e.getStart().getY(),
                e.getEnd().getX(), (-1)*e.getEnd().getY());

    }
    }

    //отрисовать видимые линии
    private  void drawScanString(List<Edge> edges1)
    {
       // clearCanvas1();
        for(Edge e:edges1){

            graphicsContext1.setStroke(setColor(e));
            graphicsContext1.setLineWidth(2);
            graphicsContext1.strokeLine(e.getStart().getX(), (-1)*e.getStart().getY(),
                    e.getEnd().getX(), (-1)*e.getEnd().getY());

        }



    }

    private  void clearCanvas(){
        graphicsContext.clearRect(-canvas.getWidth() / 2,
                -canvas.getHeight() / 2,
                canvas.getWidth(),
                canvas.getHeight());

    }

    private  void clearCanvas1(){
        graphicsContext1.clearRect(-canvas1.getWidth() / 2,
                -canvas1.getHeight() / 2,
                canvas1.getWidth(),
                canvas1.getHeight());

    }

    //отрисовать ребра полигонов для заполнения
    private void drawCanvas(){

        for(InfoForPolygon p: polygonList)
        {
            for(Edge e:p.getEdgeList()){
                graphicsContext.setStroke(setColor(e));
                graphicsContext.setLineWidth(2);
                graphicsContext.strokeLine(e.getStart().getX(), (-1)*e.getStart().getY(),
                        e.getEnd().getX(), (-1)*e.getEnd().getY());

            }
        }
    }

    //для отрисовки интервала
    private List<Edge> createInterval(List<Edge> edges, double y) {
      List<Edge> edges1 = new ArrayList<>();
      for(int i=0; i<edges.size();i++){
          if(i==0){
          edges1.add(new Edge(new Point(0,y, 0), new Point(edges.get(i).getxCrossing(), y, 0), 0));
          }
          else {
              edges1.add(new Edge(new Point(edges.get(i-1).getxCrossing(),y, 0),
                      new Point(edges.get(i).getxCrossing(), y, 0), edges.get(i).getIdPolygon()));
          }
      }
      return edges1;
    }
    //анимация
    private  void animationStart()
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(100   ), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                boolean flag =true;
                while (flag){
                    drawScanString(createInterval(scanString.getActivityEdge(), scanString.getY()));
                    scanString.findNewActivityEdge(polygonList);
                    workScanString();
                    for(InfoForPolygon p: polygonList) {
                        p.setFlagActivity(0);
                    }
                    for(InfoForPolygon p: polygonList) {
                        if(p.getNumberScanString()>0){
                            break;
                        }
                        flag=false;
                    }
                    scanString.addY();


                }


            }


        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    @FXML
    protected void btnSrartAnimate()
    {


        animationStart();
    }
}
