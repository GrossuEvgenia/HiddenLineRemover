package com.example.lr2_kg.models;

import java.util.ArrayList;
import java.util.List;

//класс с содержимым для многоугольника
public class InfoForPolygon {
    //список точек, для определения ребер многоугольника
    private List<Point> pointList;
    //число сканирующих строк
    private int numberScanString;
    //список ребер многоугольника
    private List<Edge> edgeList;
    //коеффициенты несущей плоскости
    private double a;
    private double b;
    private double c;
    private double d;

    //визуальный атрибут: код цвета
    private String color;
    //атрибут: id-принадлежность определенного многоугольника
    private int idPolygon;

    private double yMaxScanString;

    private int flagActivity;
    private int numberOfActiveEdge;

    private double xCross;
    private double yCross;

    private double xCross1;
    private double yCross1;

    private List<Double> xCrossing;


    public InfoForPolygon(List<Point> pointList, int idPolygon, String color) {
       // this.edgeList=edgeList;
        edgeList= new ArrayList<>();
        this.pointList=pointList;
        this.idPolygon=idPolygon;
        this.color =color;
        countCoef(pointList.get(0), pointList.get(1), pointList.get(2));
        countNumberScanString();
        createEdge();
        yMaxScanString = findMaxY(pointList);
        flagActivity=0;
        numberOfActiveEdge=0;
        xCrossing=new ArrayList<>();
    }


    public List<Double> getxCrossing() {
        return xCrossing;
    }

    public void setxCrossing(List<Double> xCrossing) {
        this.xCrossing = xCrossing;
    }

    public void addxCrossing(double xCrossing) {
        this.xCrossing.add(xCrossing);
    }
    public void clearxCrossing(){
        xCrossing.clear();
    }

    public double getxCross() {
        return xCross;
    }

    public void setxCross(double xCross) {
        this.xCross = xCross;
    }

    public double getyCross() {
        return yCross;
    }

    public void setyCross(double yCross) {
        this.yCross = yCross;
    }

    public double getxCross1() {
        return xCross1;
    }

    public void setxCross1(double xCross1) {
        this.xCross1 = xCross1;
    }

    public double getyCross1() {
        return yCross1;
    }

    public void setyCross1(double yCross1) {
        this.yCross1 = yCross1;
    }

    public int getNumberOfActiveEdge() {
        return numberOfActiveEdge;
    }

    public void setNumberOfActiveEdge(int numberOfActiveEdge) {
        this.numberOfActiveEdge = numberOfActiveEdge;
    }
    public void increaseNumberOfActiveEdge() {
        numberOfActiveEdge--;
    }

    public double getyMaxScanString() {
        return yMaxScanString;
    }

    public int getFlagActivity() {
        return flagActivity;
    }

    public void setFlagActivity(int flagActivity) {
        this.flagActivity = flagActivity;
    }

    public void setyMaxScanString(double yMaxScanString) {
        this.yMaxScanString = yMaxScanString;
    }


    public int getIdPolygon() {
        return idPolygon;
    }

    public void setIdPolygon(int idPolygon) {
        this.idPolygon = idPolygon;
    }


    private void createEdge() {
        for(int i=0; i<pointList.size()-1; i++) {
            edgeList.add(new Edge(pointList.get(i), pointList.get(i+1), idPolygon));
        }
        edgeList.add(new Edge(pointList.get(pointList.size()-1), pointList.get(0), idPolygon));
    }
    public double findMaxY(List<Point> points)
    {
       double max=-Double.MAX_VALUE;

        for(Point p :points)
        {
            if(max<p.getY())
            {
                max=p.getY();
            }
        }

        return  max;
    }
    public double findMinY(List<Point> points)
    {
        double min=Double.MAX_VALUE;
        for(Point p : points)
        {
            if(min>p.getY())
            {
                min=p.getY();
            }
        }

        return  min;
    }
    private void countNumberScanString()
    {

        numberScanString = (int)Math.ceil(findMaxY(pointList)-findMinY(pointList));

    }
    //для расчета коефициентов плоскости всегда берем три точки
    private void countCoef(Point point1, Point point2, Point point3) {
    a=(point2.getY()-point1.getY())*(point3.getZ()-point1.getZ())-(point2.getZ()-point1.getZ())*(point3.getY()-point1.getY());
    b=-((point2.getX()-point1.getX())*(point3.getZ()-point1.getZ())-(point3.getX()-point1.getX())*(point2.getZ   ()-point1.getZ()));
    c=(point2.getX()-point1.getX())*(point3.getY()-point1.getY())-(point3.getX()-point1.getX())*(point2.getY()- point1.getY());
    d=a*(-point1.getX())+b*(-point1.getY())+c*(-point1.getZ());

    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public int getNumberScanString() {
        return numberScanString;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void increaseNumberScanString(){
        numberScanString-=1;
    }

    private double lengthVectors(double xs, double ys, double xe, double ye){
        double length =Math.sqrt((Math.pow((xe-xs),2))+(Math.pow((ye-ys),2)));
        return  length;
    }

    private double findMaxX()
    {

        double max = -Double.MAX_VALUE;

        for(int i =0; i<xCrossing.size();i++)
        {
            if(max<xCrossing.get(i)){
                max=xCrossing.get(i);
            }
        }


        return max;
    }

    private double findMinX()
    {
        double min = Double.MAX_VALUE;

        for(int i =0; i<xCrossing.size();i++)
        {
            if(min>xCrossing.get(i)){
                min=xCrossing.get(i);
            }
        }

        return min;

    }
//проверка принадлежности отрезка полигонуц
    public boolean checkBelonging(double x, double y, double x1, double y1){


        if(x>=findMinX() && x1 <=findMaxX()){
          return true;
      }
              return false;
    }

    public double countZ(double x, double y){
        double z=(-a*x-b*y-d)/c;
        return z;
    }

}
