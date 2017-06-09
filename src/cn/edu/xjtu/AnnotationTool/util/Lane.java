package cn.edu.xjtu.AnnotationTool.util;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Henry on 2017/5/25.
 */
public class Lane {
    private String object;
    private int label;
    private List<Point2D> pointList;
    private String label1;
    private String label2;
    private String label3;

    public Lane() {
        label = 0;
        label1 = ""+0;
        label2 = ""+0;
        label3 = ""+0;
        pointList = new LinkedList<Point2D>();

    }

    public Lane(String object, int label, List<Point2D> pointList, String label1, String label2, String label3) {
        this.object = object;
        this.label = label;
        this.pointList = pointList;
        this.label1 = label1;
        this.label2 = label2;
        this.label3 = label3;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public List<Point2D> getPointList() {
        return pointList;
    }

    public void setPointList(List<Point2D> pointList) {
        this.pointList = pointList;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }
}
