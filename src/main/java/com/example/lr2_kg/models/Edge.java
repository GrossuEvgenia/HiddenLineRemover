package com.example.lr2_kg.models;

//класс с информацией для ребра
public class Edge {
    private Point start;
    private Point end;
    private double xCrossing;

    private double xIncrement;
    private int numberOfScanString;
    private int idPolygon;
    private int flagActivity;
    public Edge(Point start, Point end, int idPolygon) {
        this.start = start;
        this.end = end;
        this.idPolygon=idPolygon;
    }

    public double getxCrossing() {
        return xCrossing;
    }

    public void setxCrossing(double xCrossing) {
        this.xCrossing = xCrossing;
    }

    public double getxIncrement() {
        return xIncrement;
    }

    public void setxIncrement(double xIncrement) {
        this.xIncrement = xIncrement;
    }

    public int getNumberOfScanString() {
        return numberOfScanString;
    }

    public void setNumberOfScanString(int numberOfScanString) {
        this.numberOfScanString = numberOfScanString;
    }

    public int getIdPolygon() {
        return idPolygon;
    }

    public void setIdPolygon(int idPolygon) {
        this.idPolygon = idPolygon;
    }

    public int getFlagActivity() {
        return flagActivity;
    }

    public void setFlagActivity(int flagActivity) {
        this.flagActivity = flagActivity;
    }


    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
    public void increaseNumberScanString(){
        numberOfScanString--;
    }

    public double findMaxY() {
        if(start.getY()>end.getY()){
            return start.getY();
        }
       return end.getY();
    }

    public double findMinY() {
        if(start.getY()<end.getY()){
            return start.getY();
        }
        return end.getY();
    }

    public boolean checkActivity(double yScanString){

        if(yScanString>=findMinY()&&yScanString<=findMaxY()){

            flagActivity=1;
            return true;

        }

        return false;
    }

    public boolean countXCrossing(double yScanString)
    {
        if(start.getY() != end.getY()) {
            xCrossing = ((end.getX() - start.getX()) * (yScanString - start.getY())) / (end.getY() - start.getY()) + start.getX();
            return true;
        }
        return false;
    }




    private void countNumberScanString()
    {

        numberOfScanString = (int)Math.ceil(findMaxY()-findMinY());

    }
}