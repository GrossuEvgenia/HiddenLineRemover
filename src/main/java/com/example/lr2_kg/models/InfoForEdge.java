package com.example.lr2_kg.models;

public class InfoForEdge {

    double xCrossing;
    double xIncrement;
    int numberOfScanString;

    double xStart;
    double yStart;
    double xEnd;
    double yEnd;
    int idPolygion;
    int flagActivity;

    public  InfoForEdge( int idPolygion, double xStart, double yStart,
                         double xEnd,  double yEnd)
    {
        this.idPolygion=idPolygion;
        flagActivity=0;
        this.xStart=xStart;
        this.yStart=yStart;
        this.xEnd =xEnd;
        this.yEnd=yEnd;
        numberOfScanString=(int)Math.ceil(yEnd-yStart);

    }

    public double getxCrossing() {
        return xCrossing;
    }

    public double getxIncrement() {
        return xIncrement;
    }

    public int getNumberOfScanString() {
        return numberOfScanString;
    }

    public double getxStart() {
        return xStart;
    }

    public double getyStart() {
        return yStart;
    }

    public double getxEnd() {
        return xEnd;
    }

    public double getyEnd() {
        return yEnd;
    }

    public int getIdPolygion() {
        return idPolygion;
    }

    public int getFlagActivity() {
        return flagActivity;
    }

    public void setxCrossing(double xCrossing) {
        this.xCrossing = xCrossing;
    }

    public void setxIncrement(double newXCrossing){
        xIncrement=newXCrossing-xCrossing;
    }

    public void setNumberOfScanString(int numberOfScanString) {
        this.numberOfScanString = numberOfScanString;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public void setxEnd(double xEnd) {
        this.xEnd = xEnd;
    }

    public void setyEnd(double yEnd) {
        this.yEnd = yEnd;
    }

    public void setIdPolygion(int idPolygion) {
        this.idPolygion = idPolygion;
    }

    public void setFlagActivity(int flagActivity) {
        this.flagActivity = flagActivity;
    }


}
