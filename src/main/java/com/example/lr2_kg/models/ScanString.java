package com.example.lr2_kg.models;

import java.util.*;

//класс скан строки
public class ScanString {

    private double y;
    private List<Edge> activityEdge;
    //список обрабатываемых многоугольников
    private List<InfoForPolygon> polygonProcess;

    public ScanString(double y) {
        this.y=y;
        activityEdge = new ArrayList<>();
        polygonProcess=new ArrayList<>();

    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<Edge> getActivityEdge() {
        return activityEdge;
    }

    public void setActivityEdge(List<Edge> activityEdge) {
        this.activityEdge = activityEdge;
    }

    public List<InfoForPolygon> getPolygonProcess() {
        return polygonProcess;
    }

    public void setPolygonProcess(List<InfoForPolygon> polygonProcess) {
        this.polygonProcess = polygonProcess;
    }

    public void addY() {
        y--;
    }

    public int getIdActyvePolygon()
    {
        for(InfoForPolygon p: polygonProcess)
        {
            if(p.getFlagActivity()==1){
                return p.getIdPolygon();
            }

        }
        return 0;
    }

    //поиск новых активных ребер
    public void findNewActivityEdge(List<InfoForPolygon> polygonList){
        activityEdge.clear();
        polygonProcess.clear();

        int colActiveEdgeForPolygon=0;
        for(InfoForPolygon p: polygonList){
            p.clearxCrossing();
            colActiveEdgeForPolygon=0;
            if(p.getyMaxScanString()>=y){
                for(Edge e : p.getEdgeList())
                {
                    if(e.checkActivity(y)==true){

                      if( e.countXCrossing(y)==true) {


                          activityEdge.add(e);
                          colActiveEdgeForPolygon++;
                        p.addxCrossing(e.getxCrossing());
                      }
                      }
                }
                p.setNumberOfActiveEdge(colActiveEdgeForPolygon);

                polygonProcess.add(p);
            }
        }
        sortedActivityEdge();
    }

    private void sortedActivityEdge() {
        Collections.sort(activityEdge, Comparator.comparing(Edge::getxCrossing));
    }

    public void increaseNumberScanString()
    {
        for(InfoForPolygon p: polygonProcess)
        {
            p.increaseNumberScanString();
        }
    }


}
